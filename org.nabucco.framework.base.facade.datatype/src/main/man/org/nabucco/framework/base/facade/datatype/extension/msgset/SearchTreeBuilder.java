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
package org.nabucco.framework.base.facade.datatype.extension.msgset;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.exceptionmsg.field.FieldMessageCodeType;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.CodeMessageElement;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageElement;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchComponent;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchExceptionNode;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchLanguageNode;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchPackageNode;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchRootNode;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchServiceNode;
import org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree.MessageSearchServiceOperationNode;
import org.nabucco.framework.base.facade.datatype.extension.schema.msgset.MessageCodeExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.msgset.MessageExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.msgset.MessageSetExtension;

/**
 * SearchTreeBuilder
 * <p/>
 * Builds the search tree depending on the configured message extensions.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class SearchTreeBuilder {

    private List<MessageSetExtension> messageSetList;

    private MessageSearchRootNode rootNode;

    /**
     * Creates a new {@link SearchTreeBuilder} instance.
     * 
     * @param messageSetList
     *            the list of message extension sets
     */
    public SearchTreeBuilder(List<MessageSetExtension> messageSetList) {
        this.messageSetList = messageSetList;
        this.rootNode = new MessageSearchRootNode("");

        this.buildSearchTree();
    }

    /**
     * Build the complete search tree.
     */
    private void buildSearchTree() {

        for (MessageSetExtension msgSetExt : this.messageSetList) {

            MessageSearchLanguageNode languageNode = getLanguageNode(msgSetExt);
            MessageSearchServiceNode serviceNode = getSeviceNode(languageNode, msgSetExt);

            getCodeNode(languageNode, msgSetExt);

            for (MessageExtension msgExt : msgSetExt.getMessageList()) {
                MessageSearchServiceOperationNode serviceOperationNode = getSeviceOperationNode(serviceNode, msgExt);
                MessageSearchExceptionNode exceptionNode = getExceptionNode(serviceOperationNode, msgExt);

                MessageElement msg = (MessageElement) exceptionNode.get("msg");
                if (msg == null) {
                    msg = new MessageElement(exceptionNode, "msg");
                    exceptionNode.add(msg);
                }
                msg.setSeverity(msgExt.getSeverity().getValue().getValue());
                msg.setMessage(msgExt.getMessageText().getValue().getValue());
                msg.setForceOriginalMessage(msgExt.getForceOriginalMessage().getValue().getValue());

                exceptionNode.add(msg);

                getCodeNode(exceptionNode, msgExt);
            }
        }
    }

    private MessageSearchServiceOperationNode getSeviceOperationNode(MessageSearchServiceNode serviceNode,
            MessageExtension msgExt) {

        String method = msgExt.getMethod().getValue().getValue();
        MessageSearchServiceOperationNode opNode = (MessageSearchServiceOperationNode) serviceNode.get(method);
        if (opNode == null) {
            opNode = new MessageSearchServiceOperationNode(serviceNode, method);
            serviceNode.add(opNode);
        }

        return opNode;
    }

    private MessageSearchExceptionNode getExceptionNode(MessageSearchComponent node, MessageExtension msgExt) {

        String[] exceptionPath = msgExt.getExceptionPath().getValue().getValue().split("\\/");
        if (exceptionPath.length == 0 || exceptionPath[0].isEmpty()) {
            exceptionPath = new String[] { "*" };
        }

        for (String path : exceptionPath) {

            MessageSearchExceptionNode newNode = (MessageSearchExceptionNode) node.get(path);
            if (newNode == null) {
                newNode = new MessageSearchExceptionNode(node, path);
                node.add(newNode);
            }

            node = newNode;

        }

        return (MessageSearchExceptionNode) node;
    }

    private MessageSearchServiceNode getSeviceNode(MessageSearchComponent node, MessageSetExtension msgSet) {
        String[] packageParts = getPackageParts(msgSet);
        String service = getService(msgSet);
        for (String s : packageParts) {
            MessageSearchComponent comp = node.get(s);
            if (comp == null) {
                MessageSearchPackageNode newNode = new MessageSearchPackageNode(node, s);
                node.add(newNode);
                node = newNode;
            } else {
                node = comp;
            }
        }
        MessageSearchServiceNode serviceNode;
        if (node.get(service) == null) {
            serviceNode = new MessageSearchServiceNode(node, service);
            node.add(serviceNode);
        } else {
            serviceNode = (MessageSearchServiceNode) node.get(service);
        }

        return serviceNode;
    }

    private MessageSearchLanguageNode getLanguageNode(MessageSetExtension msgSet) {
        String language = msgSet.getLanguage().getValue().getValue();
        MessageSearchLanguageNode languageNode = rootNode.getLanguageNode(language);
        if (languageNode == null) {
            languageNode = new MessageSearchLanguageNode(rootNode, language);
            rootNode.add(languageNode);
        }
        return languageNode;
    }

    private CodeMessageElement getCodeNode(MessageSearchComponent node, MessageSetExtension msgSet) {

        CodeMessageElement element = (CodeMessageElement) node.get("code");
        if (element == null) {
            element = new CodeMessageElement(node, "code");
            node.add(element);
        }

        for (MessageCodeExtension codeExt : msgSet.getCodeList()) {
            String code = codeExt.getId().getValue().getValue();
            String text = codeExt.getText().getValue().getValue();

            element.getCodeMap().put(FieldMessageCodeType.valueOf(code), text);
        }

        return element;
    }

    private CodeMessageElement getCodeNode(MessageSearchComponent node, MessageExtension msgSet) {

        CodeMessageElement element = (CodeMessageElement) node.get("code");
        if (element == null) {
            element = new CodeMessageElement(node, "code");
            node.add(element);
        }

        for (MessageCodeExtension codeExt : msgSet.getCodeList()) {
            String code = codeExt.getId().getValue().getValue();
            String text = codeExt.getText().getValue().getValue();

            element.getCodeMap().put(FieldMessageCodeType.valueOf(code), text);
        }

        return element;
    }

    private String getService(MessageSetExtension msgSet) {
        return msgSet.getService().getValue().getValue();
    }

    private String[] getPackageParts(MessageSetExtension msgSet) {
        String pkg = msgSet.getPkg().getValue().getValue();
        String[] pkgPath = pkg.split("\\.");
        if (pkgPath.length == 0) {
            return new String[] { "*" };
        }
        return pkgPath;
    }

    /**
     * Getter for the created search tree.
     * 
     * @return the search tree
     */
    public MessageSearchComponent getSearchTree() {
        return this.rootNode;
    }
}
