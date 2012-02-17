/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.code;

import java.util.Arrays;
import java.util.List;

import org.nabucco.common.cache.BasicCache;
import org.nabucco.common.cache.CacheFactory;
import org.nabucco.common.cache.CacheManager;
import org.nabucco.common.cache.CacheType;
import org.nabucco.framework.base.facade.datatype.FunctionalIdentifier;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * Facade for Dynamic Code operations. This class a
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
public final class CodeFacade {

    /** The cache timeout */
    static final long CACHE_TIMEOUT = 1800000L; // half hour

    /** Empty Result */
    private static final Code[] EMPTY_RESULT = new Code[0];

    /** The Logger Instance */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(CodeFacade.class);

    /** The Singleton Instance */
    private static CodeFacade instance;

    /** The Code Provider */
    private CodeProvider provider;

    /** The Code Cache Manager */
    private CacheManager cacheManager;

    /** The Code Cache */
    private BasicCache<CodeGroup> codeCache;

    /** Whether the Code Cache has to be evicted. */
    private boolean evicted = true;

    /** The Root Path */
    private CodePath rootPath;

    /**
     * Creates a new {@link CodeFacade} instance.
     */
    private CodeFacade() {
        logger.debug("Initialize new CodeFacade instance.");

        this.cacheManager = new CacheManager("CodeCache");
        CacheFactory<CodeGroup> factory = new CacheFactory<CodeGroup>();
        this.codeCache = factory.newCache(CacheType.SIMPLE, this.cacheManager);
    }

    /**
     * Getter for the singleton instance.
     * 
     * @return the {@link CodeFacade} Singleton
     */
    public static synchronized CodeFacade getInstance() {
        if (instance == null) {
            instance = new CodeFacade();
        }

        if (!instance.isConfigured()) {
            logger.warning("CodeFacade has not been configured yet.");
        }

        return instance;
    }

    /**
     * Configures the CodeManager once with a CodeProvider. Any additional atempt to call this will
     * raise an {@link IllegalStateException}.
     * 
     * @param provider
     *            an instance of {@link CodeProvider} capable of providing a fully loaded hieratic
     *            {@link CodeGroup} tree.
     * 
     * @throws IllegalArgumentException
     *             when the {@link CodeProvider} is null
     * @throws IllegalStateException
     *             when the CodeFacade is already configured
     */
    public synchronized void configure(CodeProvider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("Cannot configure CodeFacade with provider [null].");
        }
        if (this.isConfigured()) {
            throw new IllegalStateException("CodeFacade is already configured.");
        }

        this.provider = provider;
        logger.info("Configured CodeProvider '", provider.getClass().getName(), "' for CodeFacade.");
    }

    /**
     * Safe access on the code provider instance.
     * 
     * @return the configured code provider, or a default provider when no provider is configured
     */
    private CodeProvider getProvider() {
        if (this.provider == null) {
            return DefaultCodeProvider.getInstance();
        }
        return this.provider;
    }

    /**
     * Checks whether the code facade is already configured or not. To configure the code facade the
     * {@link CodeFacade#configure(CodeProvider)} method must be invoked once.
     * 
     * @return <b>true</b> if the code facade is configured with a code provider, <b>false</b> if
     *         not
     */
    public boolean isConfigured() {
        if (this.provider != null) {
            return true;
        }
        return false;
    }

    /**
     * Get a {@link CodeGroup} by path.
     * 
     * @param path
     *            the {@link CodePath} pointing to the code group
     * @return the needed {@link CodeGroup} if it was provided, <code>null</code> with path argument
     *         was <code>null</code> or no result was found.
     */
    public CodeGroup getGroup(CodePath path) {
        if (!this.isConfigured()) {
            logger.warning("CodeFacade has not been configured yet.");
            return null;
        }
        if (path == null || path.getValue() == null) {
            logger.warning("Cannot resolve CodeGroup for Path [null].");
            return null;
        }

        synchronized (this.codeCache) {

            if (this.evicted) {
                logger.info("Reloading Codes due to cache eviction.");
                this.loadCache();
            }

            CodeGroup group = this.retrieveFromCache(path);

            // Entry might have been evicted
            if (group == null) {

                // Attempt to reload
                logger.warning("Failed to load CodeGroup for path: '", path, "'. Try to reload...");

                try {
                    group = this.getProvider().reloadPath(path.getValue());

                    if (group == null) {
                        logger.error("Failed to reload CodeGroup for path: '", path, "'.");
                    } else {
                        logger.info("Successfully loaded CodeGroup for path: '", path, "'.");
                        this.storeInCache(path, group);
                    }

                } catch (CodeProviderException cpe) {
                    logger.error(cpe, "CodeProvider failed to reload path: ", path);
                } catch (Exception e) {
                    logger.error(e, "Unexpected error reloading path: ", path);
                }
            }

            return group;
        }
    }

    /**
     * Load the Code Cache.
     */
    private void loadCache() {
        if (!this.evicted) {
            return;
        }
        if (!this.isConfigured()) {
            logger.warning("CodeFacade has not been configured yet.");
            return;
        }

        try {
            CodeGroup root = this.getProvider().getCodeRoot();

            if (root == null) {
                throw new CodeProviderException("Configured Code Provider does not offer a valid root code group.");
            }

            this.rootPath = new CodePath(root.getName().getValue());
            root.accept(new CodeCacheInitVisitor(this.codeCache));

        } catch (CodeProviderException cpe) {
            logger.error(cpe, "Error resolving provided root Codes Group.");
        } catch (VisitorException ve) {
            logger.error(ve, "Error traversing provided root Code Group.");
        }

        this.evicted = false;
    }

    /**
     * Store the code group for the given path in the cache.
     * 
     * @param path
     *            the path of the code group
     * @param reloadedGroup
     *            the code group to store
     */
    private void storeInCache(CodePath path, CodeGroup group) {
        if (path == null || path.getValue() == null || group == null) {
            return;
        }

        this.codeCache.store(path.getValue(), group, CACHE_TIMEOUT);
    }

    /**
     * Load the code group for the given path from the cache.
     * 
     * @param path
     *            the code path of the code group
     * 
     * @return the stored code group
     */
    private synchronized CodeGroup retrieveFromCache(CodePath path) {
        if (path == null || path.getValue() == null) {
            return null;
        }

        CodeGroup cached = this.codeCache.retrieve(path.getValue());
        return cached;
    }

    /**
     * Get a {@link Code} by path and name.
     * 
     * @param path
     *            the {@link CodePath} pointing to the code group
     * @param name
     *            the name of the code
     * 
     * @return the needed {@link CodeGroup} if it was provided, <code>null</code> with path argument
     *         was <code>null</code> or no result was found.
     */
    public Code getCode(CodePath path, Name name) {
        if (name == null || name.getValue() == null) {
            return null;
        }

        CodeGroup group = this.getGroup(path);
        if (group == null) {
            return null;
        }

        try {
            CodeForNameVisitor visitor = new CodeForNameVisitor(name.getValue());
            group.accept(visitor);

            Code result = visitor.getResult();
            return result;

        } catch (VisitorException e) {
            logger.error(e, "Cannot resolve Code for Path ", path, " and Name:  ", name, ".");
        }

        return null;
    }

    /**
     * Get a {@link Code} by path and functional ID.
     * 
     * @param path
     *            the {@link CodePath} pointing to the code group
     * @param functionalId
     *            a instance of {@link FunctionalIdentifier}.
     * 
     * @return the needed {@link CodeGroup} if it was provided, <code>null</code> any argument was
     *         <code>null</code> or no result was found.
     */
    public Code getCode(CodePath path, FunctionalIdentifier id) {
        if (id == null || id.getValue() == null) {
            return null;
        }

        CodeGroup group = this.getGroup(path);
        if (group == null) {
            return null;
        }

        try {
            CodeForFunctionalIdVisitor visitor = new CodeForFunctionalIdVisitor(id.getValue());
            group.accept(visitor);

            Code result = visitor.getResult();
            return result;

        } catch (VisitorException e) {
            logger.error(e, "Cannot resolve Code for Path ", path, " and Functional ID:  ", id, ".");
        }

        return null;
    }

    /**
     * Search a code for it's {@link Identifier}. This will search all {@link Code} instances in the
     * {@link CodeFacade}.
     * 
     * @param id
     *            the id to search.
     * 
     * @return the {@link Code} instance with Id value or <code>null</code>.
     */
    public Code getCode(Identifier id) {
        if (id == null || id.getValue() == null) {
            return null;
        }

        Code code = this.getCode(id.getValue());

        return code;
    }

    /**
     * Returns a codes for a given id value passed as {@link Long}.
     * 
     * @param id
     *            the id to search for as {@link Long}
     * 
     * @return the code with the given id value or <code>null</code>.
     */
    public Code getCode(Long id) {
        if (id == null) {
            return null;
        }

        CodeGroup group = this.getGroup(this.rootPath);
        if (group == null) {
            return null;
        }

        try {
            CodeForIdVisitor visitor = new CodeForIdVisitor(id);
            group.accept(visitor);

            Code result = visitor.getResult();
            return result;

        } catch (VisitorException e) {
            logger.error(e, "Cannot resolve Code for ID; ", id, ".");
        }
        return null;
    }

    /**
     * Gets all codes for a given {@link CodePath}
     * 
     * @param path
     *            a {@link CodePath}.
     * 
     * @return All found {@link Code} that belong to the {@link CodePath}.
     */
    public List<Code> getCodes(CodePath path) {
        if (path == null || path.getValue() == null) {
            return Arrays.asList(EMPTY_RESULT);
        }

        CodeGroup group = this.getGroup(path);
        if (group == null) {
            return Arrays.asList(EMPTY_RESULT);
        }

        try {
            AllCodesVisitor visitor = new AllCodesVisitor();
            group.accept(visitor);

            Code[] result = visitor.getResult();

            if (result != null) {
                return Arrays.asList(result);
            }

        } catch (VisitorException e) {
            logger.error(e, "Cannot resolve Codes for Path; ", path, ".");
        }

        return Arrays.asList(EMPTY_RESULT);
    }

    /**
     * Marks the CodeManager current data as evicted. The before the next get Operation the cache
     * will be reset and reloaded.
     * 
     * @return <code>true</code> only if this call triggered the evicted flag to be set.
     */
    public boolean evict() {
        return this.evicted ? false : (this.evicted = true);
    }

}
