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
package org.nabucco.framework.base.ui.web.component.messageset;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Locale;

import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.messageset.MessageSetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.ConstraintType;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.model.control.ControlType;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSessionFactory;

/**
 * UiMessageSet
 * 
 * Message set for ui
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class UiMessageSet extends AbstractMessageSetNode {

    private static UiMessageSet instance;

    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(UiMessageSet.class);

    /**
     * Creates a new {@link UiMessageSet} instance.
     */
    private UiMessageSet() {

    }

    /**
     * Initialize the tree
     */
    private void init() {
        try {

            ExtensionResolver er = new ExtensionResolver();
            ExtensionMap extensionMap = er.resolveExtensions(ExtensionPointType.ORG_NABUCCO_UI_MESSAGE_SET);

            String[] extensionNames = extensionMap.getExtensionNames();

            for (String extName : extensionNames) {
                MessageSetExtension msExt = (MessageSetExtension) extensionMap.getExtension(extName);

                String language = PropertyLoader.loadProperty(msExt.getLanguage());
                if (!super.childrenMap.containsKey(language)) {
                    LanguageNode node = new LanguageNode(language);
                    super.childrenMap.put(language, node);
                }
                LanguageNode node = (LanguageNode) super.childrenMap.get(language);
                node.addExtension(msExt);
            }

            this.logger.debug(this.toString());

        } catch (Exception e) {
            this.logger.error(e, "Cannot configure MessageResolver with extensions");
        }
    }

    /**
     * Resolves the most passing label for the constraint
     * 
     * @param controlType
     *            control type id
     * @param control
     *            control id if any
     * @param constraint
     *            constraint to search label for
     * @param originalMessage
     *            the original message
     * @return configured or the original message
     */
    public String resolveText(ControlType controlType, String control, MessageSetConstraintType constraint,
            String originalMessage) {
        WorkItem selectedWorkItem = NabuccoServletUtil.getSelectedWorkItem();
        String workingItemId = selectedWorkItem.getId();
        return this.resolveText(workingItemId, controlType, control, constraint, originalMessage);
    }

    /**
     * Resolves the most passing label for the constraint
     * 
     * @param controlType
     *            control type id
     * @param control
     *            control id if any
     * @param constraint
     *            constraint to search label for
     * @param originalMessage
     *            the original message
     * @return configured or the original message
     */
    public String resolveText(ControlType controlType, String control, ConstraintType constraint, String originalMessage) {
        WorkItem selectedWorkItem = NabuccoServletUtil.getSelectedWorkItem();
        String workingItemId = selectedWorkItem.getId();

        MessageSetConstraintType mappedConstraintType = this.mapConstraintType(constraint);

        return this.resolveText(workingItemId, controlType, control, mappedConstraintType, originalMessage);
    }

    /**
     * Resolves the most passing label for the constraint
     * 
     * @param workingItem
     *            working item id if any
     * @param controlType
     *            control type id
     * @param control
     *            control id if any
     * @param constraint
     *            constraint to search label for
     * @param originalMessage
     *            the original message
     * @return configured or the original message
     */
    public String resolveText(String workingItem, ControlType controlType, String control,
            MessageSetConstraintType constraint, String originalMessage) {
        NabuccoWebSession session = NabuccoWebSessionFactory.getCurrentSession();
        return this.resolveText(session.getLocale(), workingItem, controlType, control, constraint, originalMessage);
    }

    /**
     * Resolves the most passing label for the constraint
     * 
     * @param locale
     *            the locale
     * @param workingItem
     *            working item id if any
     * @param controlType
     *            control type id
     * @param control
     *            control id if any
     * @param constraint
     *            constraint to search label for
     * @return error text or original message if nothing defined
     */
    public String resolveText(Locale locale, String workingItem, ControlType controlType, String control,
            MessageSetConstraintType constraint, String originalMessage) {
        String country = locale.getCountry();
        Deque<String> searchStack = new LinkedList<String>();

        MessageSetControlType controlTypeEnum = this.mapControlType(controlType);

        searchStack.add(country);
        searchStack.add(workingItem);
        searchStack.add(controlTypeEnum.getValue());
        searchStack.add(control);
        searchStack.add(constraint.getValue());

        MessageSetNode foundNode = this.processTreeSearch(searchStack, this);

        if (foundNode instanceof ConstraintNode) {
            ConstraintNode node = (ConstraintNode) foundNode;
            return node.getText();
        }

        return originalMessage;
    }

    /**
     * Process the actual tree search recursively
     * 
     * @param searchParameter
     *            the parameter list
     * @param parentNode
     *            the parent node to search
     */
    private MessageSetNode processTreeSearch(Deque<String> searchParameter, MessageSetNode parentNode) {

        if (!searchParameter.isEmpty()) {

            // Look on the top element of search
            String searchNode = searchParameter.poll();

            // Search for a most passing child or default child if child not found
            MessageSetNode child = parentNode.getChild(searchNode);

            // Not configured -> start fallback
            if (child == null) {
                return null;
            }

            // If child exist try to search the last parameter part on it
            Deque<String> searchStack = new LinkedList<String>(searchParameter);
            MessageSetNode retVal = this.processTreeSearch(searchStack, child);

            // If the tree part doesnt have any defaults, try by default of actual
            if (retVal == null) {
                MessageSetNode defaultChild = parentNode.getChild(STAR_SYMBOL);

                // If no default for the actual path, go one level higher
                if (defaultChild == null) {
                    return null;
                }

                // Try to go to default path recursively
                searchStack = new LinkedList<String>(searchParameter);
                retVal = this.processTreeSearch(searchStack, defaultChild);

            }

            return retVal;

        }

        return parentNode;
    }

    /**
     * Returns a instance of ui message set
     * 
     * @return instance
     */
    public static UiMessageSet getInstance() {
        if (instance == null) {
            instance = new UiMessageSet();
            instance.init();
        }
        return instance;
    }

    /**
     * Maps contraint type
     * 
     * @param type
     *            type to be converted
     * @return converted type
     */
    private MessageSetConstraintType mapConstraintType(ConstraintType type) {
        if (type == null) {
            return MessageSetConstraintType.NONE;
        }

        MessageSetConstraintType retVal = null;

        switch (type) {
        case LENGTH:
            retVal = MessageSetConstraintType.LENGTH;
            break;
        case MULTIPLICITY:
            retVal = MessageSetConstraintType.MULTIPLICITY;
            break;
        case VALUE:
            retVal = MessageSetConstraintType.VALUE;
            break;
        default:
            retVal = MessageSetConstraintType.NONE;
            break;
        }

        return retVal;
    }

    /**
     * Maps the control type to the message set control type
     * 
     * @param type
     *            type to be mapped
     * @return mapped type
     */
    private MessageSetControlType mapControlType(ControlType type) {

        if (type == null) {
            return MessageSetControlType.NONE;
        }

        MessageSetControlType retVal = null;

        switch (type) {
        case CHECKBOX:
            retVal = MessageSetControlType.CHECKBOX;
            break;
        case CURRENCY:
            retVal = MessageSetControlType.CURRENCY;
            break;
        case DATE:
            retVal = MessageSetControlType.DATE;
            break;
        case DROPDOWN:
            retVal = MessageSetControlType.DROP_DOWN;
            break;
        case PICKER:
            retVal = MessageSetControlType.PICKER;
            break;
        case RADIO:
            retVal = MessageSetControlType.RADIO;
            break;
        case TEXT:
            retVal = MessageSetControlType.TEXT;
            break;
        case TEXTAREA:
            retVal = MessageSetControlType.TEXTAREA;
            break;
        default:
            retVal = MessageSetControlType.NONE;
            break;
        }

        return retVal;

    }

}
