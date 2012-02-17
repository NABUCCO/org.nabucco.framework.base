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

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * Default {@link CodeProvider} implementation for unconfigured {@link CodeFacade}.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
final class DefaultCodeProvider implements CodeProvider {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(CodeFacade.class);

    /**
     * Singleton instance.
     */
    private static DefaultCodeProvider instance = new DefaultCodeProvider();

    /**
     * Private constructor.
     */
    private DefaultCodeProvider() {
    }

    /**
     * Singleton access.
     * 
     * @return the DefaultCodeProvider instance.
     */
    public static DefaultCodeProvider getInstance() {
        return instance;
    }

    @Override
    public CodeGroup getCodeRoot() throws CodeProviderException {
        logger.warning("CodeFacade has not been configured yet.");
        return null;
    }

    @Override
    public CodeGroup reloadPath(String codePath) throws CodeProviderException {
        logger.warning("CodeFacade has not been configured yet.");
        return null;
    }

}
