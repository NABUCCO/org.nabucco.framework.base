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
package org.nabucco.framework.base.facade.datatype.extension.setup;

import java.util.List;
import java.util.Map;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.LabelExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.UserVarExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.UserVariableExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.UserVariableGroupExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.UserVariableValueExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

public class SetupUserVarParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_ID = "id";

    private static final String ATTR_TYPE = "type";

    private static final String ATTR_PATH = "path";

    private static final String ATTR_DEFAULT = "default";

    private static final String ATTR_LANG = "lang";

    private static final String ATTR_EDITOR = "editor";

    private static final String ATTR_VALUE = "value";

    private static final String ELEMENT_USRVAR = "uservar";

    private static final String ELEMENT_LABEL = "label";

    private static final String ELEMENT_LABELS = "labels";

    private static final String ELEMENT_VALUE = "value";

    private static final String ELEMENT_GROUP = "group";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(SetupUserVarParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element e) throws ExtensionParserException {

        logger.debug("Parsing User Variable configuration");

        try {
            UserVarExtension ext = new UserVarExtension();
            ExtensionId id = new ExtensionId(e.getAttribute(ATTR_ID));
            ext.setIdentifier(id);

            List<Element> userVarList = getElementsByTagName(e, ELEMENT_USRVAR);
            List<UserVariableExtension> resList = ext.getUserVarList();

            for (Element usrVarElement : userVarList) {

                UserVariableExtension uv = new UserVariableExtension();
                uv.setId(getStringProperty(usrVarElement, ATTR_ID));
                uv.setPath(getStringProperty(usrVarElement, ATTR_PATH));
                uv.setType(getStringProperty(usrVarElement, ATTR_TYPE));
                uv.setDefaultValue(getStringProperty(usrVarElement, ATTR_DEFAULT));
                uv.setEditor(getStringProperty(usrVarElement, ATTR_EDITOR));
                resList.add(uv);

                List<Element> valueElementList = getElementsByTagName(usrVarElement, ELEMENT_VALUE);
                for (Element valueElement : valueElementList) {
                    UserVariableValueExtension uvve = new UserVariableValueExtension();
                    uvve.setValue(getStringProperty(valueElement, ATTR_VALUE));
                    Map<String, LabelExtension> labelMap = uvve.getLabelMap();
                    fillLabels(valueElement, labelMap);

                }

                Map<String, LabelExtension> labelMap = uv.getLabelMap();
                fillLabels(usrVarElement, labelMap);
            }

            Element groupElement = getElementsByTagName(e, ELEMENT_GROUP).get(0);
            UserVariableGroupExtension ge = new UserVariableGroupExtension();
            ge.setId(getStringProperty(groupElement, ATTR_ID));
            Map<String, LabelExtension> labelMap = ge.getLabelMap();
            fillLabels(groupElement, labelMap);
            parseGroups(groupElement, ge);

            return new NabucoExtensionContainer(ext);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }

    }

    private void parseGroups(Element e, UserVariableGroupExtension parent) throws ExtensionException,
            ExtensionParserException {

        List<Element> groupElementList = getElementsByTagName(e, ELEMENT_GROUP);
        if (groupElementList.size() == 0) {
            return;
        }

        Map<String, UserVariableGroupExtension> groupMap = parent.getGroupMap();

        for (Element groupElement : groupElementList) {

            UserVariableGroupExtension ge = new UserVariableGroupExtension();
            ge.setId(getStringProperty(groupElement, ATTR_ID));
            Map<String, LabelExtension> labelMap = ge.getLabelMap();
            fillLabels(groupElement, labelMap);

            String id = ge.getId().getValue().getValue();

            groupMap.put(id, ge);

            this.parseGroups(groupElement, parent);
        }
    }

    private void fillLabels(Element element, Map<String, LabelExtension> labelMap) throws ExtensionException,
            ExtensionParserException {

        Element labelListElement = super.getElementsByTagName(element, ELEMENT_LABELS).get(0);
        List<Element> labelElementList = super.getElementsByTagName(labelListElement, ELEMENT_LABEL);

        for (Element labelElement : labelElementList) {
            LabelExtension labelExtension = this.getLabelExtension(labelElement);
            String language = labelExtension.getLang().getValue().getValue();
            labelMap.put(language, labelExtension);
        }
    }

    private LabelExtension getLabelExtension(Element element) throws ExtensionParserException {
        LabelExtension extension = new LabelExtension();
        extension.setLang(super.getStringProperty(element, ATTR_LANG));
        extension.setLabel(super.getStringProperty(element, ATTR_VALUE));
        return extension;
    }

}
