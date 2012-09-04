/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.extension;

import java.util.Collections;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * ExtensionPointType<p/>Enumeration of available extension points.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-04
 */
public enum ExtensionPointType implements Enumeration {
    /** Bootstrap extension for NABUCCO extension mechanism. */
    ORG_NABUCCO_BOOTSTRAP("org.nabucco.bootstrap"),
    /** Extension point for defining aspects. */
    ORG_NABUCCO_ASPECT("org.nabucco.aspect"),
    /** Extension point for defining interceptors. */
    ORG_NABUCCO_INTERCEPTOR("org.nabucco.interceptor"),
    /** Extension point for defining jmx mbeans. */
    ORG_NABUCCO_MANAGEMENT("org.nabucco.management"),
    /** Extension point for monitoring adapters. */
    ORG_NABUCCO_MANAGEMENT_ADAPTER("org.nabucco.management.adapter"),
    /** Extension point for initializing aspect. */
    ORG_NABUCCO_ASPECT_INITIALIZING("org.nabucco.aspect.initializing"),
    /** Extension point for indexing aspect. */
    ORG_NABUCCO_ASPECT_INDEXING("org.nabucco.aspect.indexing"),
    /** Extension point for validating aspect. */
    ORG_NABUCCO_ASPECT_VALIDATING("org.nabucco.aspect.validating"),
    /** Extension point for constraining aspect. */
    ORG_NABUCCO_ASPECT_CONSTRAINING("org.nabucco.aspect.constraining"),
    /** Extension point for relating aspect. */
    ORG_NABUCCO_ASPECT_RELATING("org.nabucco.aspect.relating"),
    /** Extension point for resolving aspect. */
    ORG_NABUCCO_ASPECT_RESOLVING("org.nabucco.aspect.resolving"),
    /** Extension point for transitioning aspect. */
    ORG_NABUCCO_ASPECT_TRANSITIONING("org.nabucco.aspect.transitioning"),
    /** Extension point for permissioning aspect. */
    ORG_NABUCCO_ASPECT_PERMISSIONING("org.nabucco.aspect.permissioning"),
    /** Extension point for caching aspect. */
    ORG_NABUCCO_ASPECT_CACHING("org.nabucco.aspect.caching"),
    /** Extension point for journaling aspect. */
    ORG_NABUCCO_ASPECT_JOURNALING("org.nabucco.aspect.journaling"),
    /** Extension point for linking aspect. */
    ORG_NABUCCO_ASPECT_LINKING("org.nabucco.aspect.linking"),
    /** Extension point for server connection configuration. */
    ORG_NABUCCO_CONNECTION("org.nabucco.connection"),
    /** Extension point for filter definitions. */
    ORG_NABUCCO_QUERYFILTER("org.nabucco.queryfilter"),
    /** Extension point for authentication. */
    ORG_NABUCCO_FRAMEWORK_AUTHORIZATION_AUTHENTICATION("org.nabucco.framework.authorization.authentication"),
    /** Extension point for password policies. */
    ORG_NABUCCO_FRAMEWORK_AUTHORIZATION_PASSWORD_POLICY("org.nabucco.framework.authorization.password.policy"),
    /** Extension point exception to message conversion. */
    ORG_NABUCCO_FRAMEWORK_MESSAGESET("org.nabucco.framework.messageset"),
    /** Extension point for index definitions. */
    ORG_NABUCCO_FRAMEWORK_SEARCH_INDEX("org.nabucco.framework.search.index"),
    /** Extension point for search configurations. */
    ORG_NABUCCO_FRAMEWORK_SEARCH_CONFIG("org.nabucco.framework.search.config"),
    /** Extension point for search result configurations. */
    ORG_NABUCCO_FRAMEWORK_SEARCH_RESULT("org.nabucco.framework.search.result"),
    /** Extension point for search query configurations. */
    ORG_NABUCCO_FRAMEWORK_SEARCH_QUERY("org.nabucco.framework.search.query"),
    /** Extension point for agents. */
    ORG_NABUCCO_FRAMEWORK_SETUP_AGENT("org.nabucco.framework.setup.agent"),
    /** Extension point for journaling. */
    ORG_NABUCCO_FRAMEWORK_SETUP_JOURNAL("org.nabucco.framework.setup.journal"),
    /** Extension point for sequence generators. */
    ORG_NABUCCO_FRAMEWORK_SETUP_SEQUENCE("org.nabucco.framework.setup.sequence"),
    /** Extension point for system variables schema. */
    ORG_NABUCCO_FRAMEWORK_SETUP_SYSVAR_SCHEMA("org.nabucco.framework.setup.sysvar.schema"),
    /** Extension point for user variables schema. */
    ORG_NABUCCO_FRAMEWORK_SETUP_USRVAR_SCHEMA("org.nabucco.framework.setup.usrvar.schema"),
    /** Extension point for geo configuration. */
    ORG_NABUCCO_FRAMEWORK_SETUP_GEO_CONFIG("org.nabucco.framework.setup.geo.config"),
    /** Extension point for template mapping. */
    ORG_NABUCCO_FRAMEWORK_TEMPLATE_MAPPING("org.nabucco.framework.template.mapping"),
    /** Extension point for template datastructure */
    ORG_NABUCCO_FRAMEWORK_TEMPLATE_DATASTRUCTURE("org.nabucco.framework.template.datastructure"),
    /** Extension point for workflow configurations. */
    ORG_NABUCCO_FRAMEWORK_WORKFLOW("org.nabucco.framework.workflow"),
    /** Extension point for bussiness object schema configurations. */
    ORG_NABUCCO_BUSINESS_SCHEMA("org.nabucco.business.schema"),
    /** Extension point for the application of the user interface. */
    ORG_NABUCCO_UI_APPLICATION("org.nabucco.ui.application"),
    /** Extension point for the titlebar of the user interface. */
    ORG_NABUCCO_UI_TITLEBAR("org.nabucco.ui.titlebar"),
    /** Extension point for the perspective area of the user interface. */
    ORG_NABUCCO_UI_PERSPECTIVEAREA("org.nabucco.ui.perspectivearea"),
    /** Extension point for the perspective of the user interface. */
    ORG_NABUCCO_UI_PERSPECTIVE("org.nabucco.ui.perspective"),
    /** Extension point for the navigation area of the user interface. */
    ORG_NABUCCO_UI_NAVIGATIONAREA("org.nabucco.ui.navigationarea"),
    /** Extension point for the navigators of the user interface. */
    ORG_NABUCCO_UI_NAVIGATOR("org.nabucco.ui.navigator"),
    /** Extension point for the browser area of the user interface. */
    ORG_NABUCCO_UI_BROWSERAREA("org.nabucco.ui.browserarea"),
    /** Extension point for the browsers of the user interface. */
    ORG_NABUCCO_UI_BROWSER("org.nabucco.ui.browser"),
    /** Extension point for work area of the user interface. */
    ORG_NABUCCO_UI_WORKAREA("org.nabucco.ui.workarea"),
    /** Extension point for an editor of the user interface. */
    ORG_NABUCCO_UI_EDITOR("org.nabucco.ui.editor"),
    /** Extension point for an bulk editor of the user interface. */
    ORG_NABUCCO_UI_BULK_EDITOR("org.nabucco.ui.bulkeditor"),
    /** Extension point for an dashboard of the user interface. */
    ORG_NABUCCO_UI_DASHBOARD("org.nabucco.ui.dashboard"),
    /** Extension point for a list of the user interface. */
    ORG_NABUCCO_UI_LIST("org.nabucco.ui.list"),
    /** Extension point for actions of the user interface. */
    ORG_NABUCCO_UI_ACTION("org.nabucco.ui.action"),
    /** Extension point for dialogs on the user interface */
    ORG_NABUCCO_UI_DIALOG("org.nabucco.ui.dialog"),
    /** Extension point for picker dialogs on the user interface */
    ORG_NABUCCO_UI_PICKER_DIALOG("org.nabucco.ui.pickerdialog"),
    /** Extension point for the statusbar of the user interface. */
    ORG_NABUCCO_UI_STATUSBAR("org.nabucco.ui.statusbar"),
    /** Extension point for the widgets in the user interface */
    ORG_NABUCCO_UI_WIDGET("org.nabucco.ui.widget"),
    /** Extension point for the error container */
    ORG_NABUCCO_UI_ERROR("org.nabucco.ui.error"),
    /** Extension point for colorshemes in the ui */
    ORG_NABUCCO_UI_COLORSCHEME("org.nabucco.ui.colorscheme"),
    /** Extension point for the dashboard widgets in the ui */
    ORG_NABUCCO_UI_DASHBOARD_WIDGET("org.nabucco.ui.dashboard.widget"),
    /** Extension point for testautomation test engines */
    ORG_NABUCCO_TESTAUTOMATION_TESTENGINE_POOL("org.nabucco.testautomation.testengine.pool"),
    /** Extension point for testautomation refresh interval */
    ORG_NABUCCO_TESTAUTOMATION_TESTRESULT("org.nabucco.testautomation.testresult"),
    /** Extension point for testautomation engine */
    ORG_NABUCCO_TESTAUTOMATION_TESTENGINE("org.nabucco.testautomation.testengine"),
    /** Extension point for ui message pushing */
    ORG_NABUCCO_UI_MESSAGE_QUEUE("org.nabucco.ui.messagequeue"),
    /** Extension point for working item validation error mapping */
    ORG_NABUCCO_UI_MESSAGE_SET("org.nabucco.ui.messageset"),
    /** Extension point for the shortcuts on the ui */
    ORG_NABUCCO_UI_SHORTCUTS("org.nabucco.ui.shortcuts"), ;

    private String id;

    /**
     * Constructs a new ExtensionPointType instance.
     *
     * @param id the String.
     */
    ExtensionPointType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getOrdinal() {
        return this.ordinal();
    }

    @Override
    public Enumeration cloneObject() {
        return this;
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        return Collections.emptySet();
    }

    /**
     * ValueOfId.
     *
     * @param literalId the String.
     * @return the ExtensionPointType.
     */
    public static ExtensionPointType valueOfId(String literalId) {
        for (ExtensionPointType enumeration : ExtensionPointType.values()) {
            if (enumeration.getId().equalsIgnoreCase(literalId)) {
                return enumeration;
            }
        }
        return null;
    }
}
