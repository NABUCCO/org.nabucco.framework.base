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
package org.nabucco.framework.base.facade.exception.exceptionmsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.exceptionmsg.MessageOwner;
import org.nabucco.framework.base.facade.datatype.exceptionmsg.MessageSeverityType;
import org.nabucco.framework.base.facade.datatype.exceptionmsg.field.FieldMessage;
import org.nabucco.framework.base.facade.datatype.exceptionmsg.field.FieldMessageCodeType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.msgset.SearchTreeBuilder;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.CodeMessageElement;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageElement;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchComponent;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchExceptionNode;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchLanguageNode;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchPackageNode;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchRootNode;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchServiceNode;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchServiceOperationNode;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchTreeDumper;
import org.nabucco.framework.base.facade.datatype.extension.schema.msgset.MessageSetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.NestedThrowable;
import org.nabucco.framework.base.facade.exception.adapter.AdapterException;

/**
 * Resolves a best matching message for a called operation and a catched exception.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public final class MessageResolver {

    private static final String DEFAULT_OPERATION = "*";

    private static final String DEFAULT_CLASS = "*";

    private static final String DEFAULT_EXCEPTION_NODE = "*";

    private static final String MESSAGE_NODE = "msg";

    private static final String CODE_NODE = "code";

    private static final String DEFAULT_LANGUAGE = "*";

    private static final String DEFAULT_PACKAGE = "*";

    private static final String[] DEFAULT_EXCEPTION_PATH = new String[] { "*" };

    private static final String[] DEFAULT_PACKAGE_PATH = new String[] { "*" };

    private static final String PACKAGE_DOT = "\\.";

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(MessageResolver.class);

    private static MessageResolver instance;

    private MessageSearchRootNode rootNode;

    /**
     * Creates a new {@link MessageResolver} instance.
     */
    private MessageResolver() {
        this.init();
    }

    /**
     * Configuring the message resolver with the messageset extensions.
     */
    private void init() {

        try {

            ExtensionResolver er = new ExtensionResolver();
            ExtensionMap extensionMap = er.resolveExtensions(ExtensionPointType.ORG_NABUCCO_FRAMEWORK_MESSAGESET);

            String[] extensionNames = extensionMap.getExtensionNames();
            List<MessageSetExtension> list = new ArrayList<MessageSetExtension>();

            for (String extName : extensionNames) {
                MessageSetExtension msExt = (MessageSetExtension) extensionMap.getExtension(extName);
                list.add(msExt);
            }

            SearchTreeBuilder treeBuilder = new SearchTreeBuilder(list);
            this.rootNode = (MessageSearchRootNode) treeBuilder.getSearchTree();

            if (logger.isTraceEnabled()) {
                MessageSearchTreeDumper dumper = new MessageSearchTreeDumper();
                dumper.visit((MessageSearchComponent) this.rootNode);
            }

        } catch (Exception e) {
            logger.error(e, "Error configuring Exception MessageResolver.");
        }
    }

    /**
     * Getter for the singleton instance.
     * 
     * @return the instance
     */
    public static synchronized MessageResolver getInstance() {
        if (instance == null) {
            instance = new MessageResolver();
        }
        return instance;
    }

    /**
     * Resolve the exception message for the given parameters.
     * 
     * @param locale
     *            the locale to resolve
     * @param fqClassName
     *            the fully qualified class name
     * @param operation
     *            the operation name
     * @param exception
     *            the raised exception
     * 
     * @return the resolved exception message
     */
    public ExceptionMessage resolve(Locale locale, String fqClassName, String operation, Exception exception) {

        String packagePart = getPackagePart(fqClassName);
        String className = getClassName(fqClassName);

        return resolve(locale.getCountry(), packagePart, className, operation, exception);
    }

    /**
     * Resolve the package of a fully qualified class name string.
     * 
     * @param className
     *            the fully qualified class name
     * 
     * @return the package of the class
     */
    private String getPackagePart(String className) {
        if (className == null || className.isEmpty()) {
            return DEFAULT_PACKAGE;
        }

        int lastDot = className.lastIndexOf('.');

        if (lastDot == -1) {
            return DEFAULT_PACKAGE;
        }

        return className.substring(0, lastDot);
    }

    /**
     * Resolve the class name of a fully qualified class name string.
     * 
     * @param className
     *            the fully qualified class name
     * 
     * @return the simple name of the class
     */
    private String getClassName(String className) {
        if (className == null || className.isEmpty()) {
            return DEFAULT_CLASS;
        }

        int lastDot = className.lastIndexOf('.');

        if (lastDot == -1) {
            return DEFAULT_CLASS;
        }

        return className.substring(lastDot + 1);
    }

    /**
     * Resolve the exception message for the given parameters. When a parameter cannot be resolved
     * the default value is used.
     * 
     * @param language
     *            the language
     * @param pkg
     *            the service package
     * @param className
     *            the service class name
     * @param operation
     *            the operation name
     * @param exception
     *            the exception name
     * 
     * @return the exception message
     */
    private ExceptionMessage resolve(String language, String pkg, String className, String operation,
            Exception exception) {

        ExceptionMessage resultMsg = null;

        String[] pkgPath = this.getPkgPath(pkg);
        String[] exPath = this.getExceptionPath(exception);

        String originalMessage = this.getOriginalMessage(exception);
        Set<FieldMessage> messageSet = this.getFieldMessages(exception);

        try {

            resultMsg = this.resolve(language, pkgPath, className, operation, exPath, messageSet, originalMessage);

            if (resultMsg == null) {
                resultMsg = this.resolve(language, pkgPath, className, operation, DEFAULT_EXCEPTION_PATH, messageSet,
                        originalMessage);
            }

            if (resultMsg == null) {
                resultMsg = this.resolve(language, pkgPath, className, DEFAULT_OPERATION, DEFAULT_EXCEPTION_PATH,
                        messageSet, originalMessage);
            }

            if (resultMsg == null) {
                resultMsg = this.resolve(language, DEFAULT_PACKAGE_PATH, DEFAULT_CLASS, DEFAULT_OPERATION,
                        DEFAULT_EXCEPTION_PATH, messageSet, originalMessage);
            }
            if (resultMsg == null) {
                resultMsg = this.resolve(DEFAULT_LANGUAGE, DEFAULT_PACKAGE_PATH, DEFAULT_CLASS, DEFAULT_OPERATION,
                        DEFAULT_EXCEPTION_PATH, messageSet, originalMessage);
            }

        } catch (Exception e) {
            logger.warning(e, "Catched exception while searching exception message");
        } finally {
            if (resultMsg == null) {
                resultMsg = new ExceptionMessage();
                resultMsg.setMatchedException(null);
                resultMsg.setMessage("System Error Occured");
                resultMsg.setSeverity(MessageSeverityType.SYSERROR);
            }
        }

        return resultMsg;
    }

    private ExceptionMessage resolve(String language, String[] pkgPath, String service, String operation,
            String[] exceptionPath, Set<FieldMessage> fieldMessages, String originalMessage) {

        logger.debug("Starting resolving exception message");

        MessageSearchComponent node = this.rootNode.getLanguageNode(language);

        if (node == null) {
            return null;
        }

        logger.debug("Language: +" + language);

        // searching package nodes
        for (String pkg : pkgPath) {
            if (node.get(pkg) != null) {
                logger.debug("Package: +", pkg);
                node = node.get(pkg);
            }
        }

        // no service available, trying so search next default service
        if (node.get(service) == null) {
            while (node.get(DEFAULT_CLASS) == null) {
                node = node.getParent();
                if (node instanceof MessageSearchLanguageNode) {
                    node = node.get(DEFAULT_PACKAGE);
                    break;
                }
            }
            // Using default package
            if (node.get(DEFAULT_PACKAGE) != null && node.get(DEFAULT_PACKAGE) instanceof MessageSearchPackageNode) {
                node = node.get(DEFAULT_PACKAGE);
            }
        }

        // searching service node
        if (node.get(service) != null && node.get(service) instanceof MessageSearchServiceNode) {
            logger.debug("Service: +", service);
            node = node.get(service);
        } else {
            logger.debug("Service: -", service);
            if (node.get(DEFAULT_CLASS) != null) {
                node = node.get(DEFAULT_CLASS);
            } else {
                return null;
            }
        }

        // serching operation node
        if (node.get(operation) != null && node.get(operation) instanceof MessageSearchServiceOperationNode) {
            logger.debug("Operation: +", operation);
            node = node.get(operation);
        } else {
            logger.debug("Operation: -", operation);
            if (node.get(DEFAULT_OPERATION) != null) {
                node = node.get(DEFAULT_OPERATION);
            } else {
                return null;
            }
        }

        // searching exception nodes
        for (String ex : exceptionPath) {
            if (node.get(ex) instanceof MessageSearchExceptionNode) {
                logger.debug("Exception: +", ex);
                node = node.get(ex);
            }
        }

        // Using default Exception node if available
        if (!(node instanceof MessageSearchExceptionNode)) {
            if (node.get(DEFAULT_EXCEPTION_NODE) != null) {
                node = node.get(DEFAULT_EXCEPTION_NODE);
            } else {
                return null;
            }
        }

        if (node.get(MESSAGE_NODE) == null) {
            while (node.get(MESSAGE_NODE) == null) {
                node = node.getParent();
                if (node instanceof MessageSearchServiceOperationNode) {
                    node = node.get(DEFAULT_EXCEPTION_NODE);
                    break;
                }
            }
        }

        if (node.get(MESSAGE_NODE) instanceof MessageElement) {
            logger.debug("Message: +");
            MessageElement me = (MessageElement) node.get(MESSAGE_NODE);
            ExceptionMessage message = new ExceptionMessage();

            if (me.isForceOriginalMessage()) {
                message.setMessage(originalMessage);
            } else {
                message.setMessage(me.getMessage());
            }

            message.setSeverity(me.getSeverity());

            this.resolveFieldMessages(language, fieldMessages, node, message);

            return message;
        }

        logger.debug("\n");

        return null;
    }

    /**
     * Resolve the field messages.
     * 
     * @param language
     *            the language
     * @param fieldMessages
     *            the field messages to resolve
     * @param node
     *            the current node
     * @param message
     *            the message
     */
    private void resolveFieldMessages(String language, Set<FieldMessage> fieldMessages, MessageSearchComponent node,
            ExceptionMessage message) {

        if (fieldMessages == null) {
            return;
        }

        Map<FieldMessageCodeType, String> fieldMessageMap = new HashMap<FieldMessageCodeType, String>();
        MessageSearchLanguageNode languageNode = this.rootNode.getLanguageNode(language);

        if (languageNode.get(CODE_NODE) != null && languageNode.get(CODE_NODE) instanceof CodeMessageElement) {
            CodeMessageElement ce = (CodeMessageElement) languageNode.get(CODE_NODE);
            fieldMessageMap.putAll(ce.getCodeMap());
        }

        if (node.get(CODE_NODE) != null && node.get(CODE_NODE) instanceof CodeMessageElement) {
            CodeMessageElement ce = (CodeMessageElement) node.get(CODE_NODE);

            fieldMessageMap.putAll(ce.getCodeMap());
        }

        for (FieldMessage fieldMessage : fieldMessages) {
            logger.debug("Code: +");
            ExceptionCodeMessage exceptionCode = new ExceptionCodeMessage();
            String codeMessage = fieldMessageMap.get(fieldMessage.getCode());
            exceptionCode.setMessage(codeMessage);
            exceptionCode.setPath(fieldMessage.getPath());
            message.getCodeMessages().add(exceptionCode);
        }
    }

    /**
     * Splitting the package into package path elements.
     * 
     * @param pkg
     *            the package to split
     * 
     * @return the string tokens
     */
    private String[] getPkgPath(String pkg) {
        return pkg.split(PACKAGE_DOT);
    }

    /**
     * Gets the class names of the first three exceptions in the exception chain and builds a
     * String[].
     * 
     * @param e
     *            The top level exception
     * @return An array of the first three classnames of the exception chain
     */
    private String[] getExceptionPath(Exception exception) {
        if (exception == null) {
            return DEFAULT_EXCEPTION_PATH;
        }

        List<String> result = new ArrayList<String>();
        String exceptionClass = exception.getClass().getSimpleName();
        result.add(exceptionClass);

        while (exception.getCause() instanceof Exception) {

            exception = (Exception) exception.getCause();

            String causingClass;

            if (exception instanceof NestedThrowable) {
                NestedThrowable nestedThrowable = (NestedThrowable) exception;
                causingClass = nestedThrowable.getSimpleName();
            } else {
                causingClass = exception.getClass().getSimpleName();
            }

            result.add(causingClass);
        }

        return result.toArray(new String[result.size()]);
    }

    /**
     * Getter for the original exception message.
     * 
     * @param exception
     *            the raised exception
     * 
     * @return the original exception message
     */
    private String getOriginalMessage(Throwable exception) {
        while (exception != null) {
            if (exception instanceof AdapterException) {
                return ((AdapterException) exception).getDetailMessage();
            }
            if (exception instanceof NestedThrowable) {
                NestedThrowable nested = (NestedThrowable) exception;

                if (nested.getDetailMessage() != null) {
                    return nested.getDetailMessage();
                }
            }
            exception = exception.getCause();
        }
        return null;
    }

    /**
     * Getter for field messages of the given exception
     * 
     * @param exception
     *            the raised exception
     * 
     * @return the set of field messages or null if no exist
     */
    private Set<FieldMessage> getFieldMessages(Exception exception) {
        if (exception instanceof MessageOwner) {
            MessageOwner messageOwner = (MessageOwner) exception;

            if (messageOwner.getFieldMessageSet() != null) {
                return messageOwner.getFieldMessageSet().getAll();
            }
        }
        return null;
    }
}
