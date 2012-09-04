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
package org.nabucco.framework.base.ui.web.component;

import java.util.Locale;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.application.ApplicationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.application.LocalizationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.application.LocalizationLanguageExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.error.ErrorLogExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.messagequeue.MessageQueueExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.perspective.PerspectiveAreaExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.statusbar.StatusBarExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.titlebar.TitleBarExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.base.ui.web.component.common.controller.DialogController;
import org.nabucco.framework.base.ui.web.component.common.controller.PickerDialogController;
import org.nabucco.framework.base.ui.web.component.common.controller.resource.ResourceController;
import org.nabucco.framework.base.ui.web.component.error.ErrorLogContainer;
import org.nabucco.framework.base.ui.web.component.messagequeue.MessageQueue;
import org.nabucco.framework.base.ui.web.component.perspective.PerspectiveArea;
import org.nabucco.framework.base.ui.web.component.statusbar.StatusBar;
import org.nabucco.framework.base.ui.web.component.titlebar.TitleBar;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSessionFactory;

/**
 * A web application is the root {@link WebComposite} that contains the main application elements
 * like titlebar, perspective and statusbar.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class WebApplication extends WebComposite {

    private static final long serialVersionUID = 1L;

    private static final String JSON_TITLE = "title";

    private static final String JSON_RESOURCE = "resource";

    private static final String JSON_DEBUG = "debug";

    private DialogController dialogController = new DialogController();

    private ResourceController resourceController = new ResourceController();

    private PickerDialogController pickerDialogController = new PickerDialogController();

    private MessageQueue messageQueue;

    private ErrorLogContainer errorContainer;

    /** The configured web application extension. */
    private ApplicationExtension extension = null;

    NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WebApplication.class);

    /**
     * Creates a new {@link WebApplication} instance.
     * 
     * @param extension
     *            the web application extension
     */
    public WebApplication(ApplicationExtension extension) {
        super(WebElementType.APPLICATION, extension);
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        this.loadLocalization();
        this.loadTitleBar();
        this.loadPerspectiveArea();
        this.loadStatusBar();
        this.loadErrorLog();
        this.loadMessageQueue();
    }

    /**
     * Loads localization configuration and sets the language to default configured
     */
    private void loadLocalization() throws ExtensionException {
        LocalizationExtension localizationExtension = this.extension.getLocalization();
        ExtensionId defaultLanguageIdentifier = localizationExtension.getDefaultLanguageIdentifier();
        if (defaultLanguageIdentifier == null || defaultLanguageIdentifier.getValue().isEmpty()) {
            logger.debug("No default language is defined. Using system default");
            return;
        }

        NabuccoList<LocalizationLanguageExtension> languageList = localizationExtension.getLocalizationLanguageList();
        if (languageList.isEmpty()) {
            logger.debug("There are no languages defined. Using system default.");
            return;
        }

        for (LocalizationLanguageExtension langExt : languageList) {
            if (langExt.getIdentifier() != null && langExt.getIdentifier().equals(defaultLanguageIdentifier)) {
                String country = PropertyLoader.loadProperty(langExt.getCountry());
                String language = PropertyLoader.loadProperty(langExt.getLanguage());

                Locale defaultLanguage = new Locale(language, country);

                NabuccoWebSession session = NabuccoWebSessionFactory.getCurrentSession();
                session.setLocale(defaultLanguage);

                I18N.setLocale(defaultLanguage);
                break;
            }
        }

    }

    /**
     * Load the application message queue
     * 
     * @throws ExtensionException
     */
    private void loadMessageQueue() throws ExtensionException {
        MessageQueueExtension ext = (MessageQueueExtension) NabuccoSystem.getExtensionResolver().resolveExtension(
                ExtensionPointType.ORG_NABUCCO_UI_MESSAGE_QUEUE, ExtensionResolver.DEFAULT_EXTENSION);

        this.messageQueue = new MessageQueue(ext);
        this.messageQueue.init();
    }

    /**
     * Load the web application error log.
     * 
     * @throws ExtensionException
     *             when the error log is not configured correctly
     */
    private void loadErrorLog() throws ExtensionException {
        ErrorLogExtension errorLogExtension = (ErrorLogExtension) NabuccoSystem.getExtensionResolver()
                .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_ERROR, ExtensionResolver.DEFAULT_EXTENSION);

        this.errorContainer = new ErrorLogContainer(errorLogExtension);
        this.errorContainer.init();
    }

    /**
     * Load the web application title bar.
     * 
     * @throws ExtensionException
     *             when the title bar is not configured correctly
     */
    private void loadTitleBar() throws ExtensionException {
        String titleBarId = PropertyLoader.loadProperty(this.extension.getTitleBar());
        if (titleBarId == null || titleBarId.isEmpty()) {
            throw new ExtensionException("Cannot load titlebar with id 'null'.");
        }

        TitleBarExtension titleBarExtension = (TitleBarExtension) NabuccoSystem.getExtensionResolver()
                .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_TITLEBAR, titleBarId);

        TitleBar titleBar = new TitleBar(titleBarExtension);
        super.addElement(titleBarId, titleBar);
        titleBar.init();
    }

    /**
     * Load the web application perspective area.
     * 
     * @throws ExtensionException
     *             when the perspective area is not configured correctly
     */
    private void loadPerspectiveArea() throws ExtensionException {
        String id = PropertyLoader.loadProperty(this.extension.getPerspectiveArea());
        if (id == null || id.isEmpty()) {
            throw new ExtensionException("Cannot load perspective area with id 'null'.");
        }

        PerspectiveAreaExtension extension = (PerspectiveAreaExtension) NabuccoSystem.getExtensionResolver()
                .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_PERSPECTIVEAREA, id);

        PerspectiveArea perspectiveArea = new PerspectiveArea(extension);
        super.addElement(id, perspectiveArea);
        perspectiveArea.init();
    }

    /**
     * Load the web application status bar.
     * 
     * @throws ExtensionException
     *             when the status bar is not configured correctly
     */
    private void loadStatusBar() throws ExtensionException {
        String statusBarId = PropertyLoader.loadProperty(this.extension.getStatusBar());
        if (statusBarId == null || statusBarId.isEmpty()) {
            throw new ExtensionException("Cannot load statusbar with id 'null'.");
        }

        StatusBarExtension statusBarExtension = (StatusBarExtension) NabuccoSystem.getExtensionResolver()
                .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_STATUSBAR, statusBarId);

        StatusBar statusBar = new StatusBar(statusBarExtension);
        super.addElement(statusBarId, statusBar);
        statusBar.init();
    }

    /**
     * Getter for the perspective area web element.
     * 
     * @return the perspective area, or null if no relating element is configured
     */
    public PerspectiveArea getPerspectiveArea() {
        String perspectiveAreaId = PropertyLoader.loadProperty(this.extension.getPerspectiveArea());
        WebElement perspectiveArea = this.getElement(perspectiveAreaId);

        if (perspectiveArea instanceof PerspectiveArea) {
            return (PerspectiveArea) perspectiveArea;
        }

        return null;
    }

    /**
     * Getter for the titlebar.
     * 
     * @return the titlebar
     */
    public TitleBar getTitleBar() {
        for (WebElement child : super.getElements()) {
            if (child.getType() == WebElementType.TITLEBAR) {
                return (TitleBar) child;
            }
        }
        return null;
    }

    /**
     * Getter for the statusbar.
     * 
     * @return the statusbar
     */
    public StatusBar getStatusBar() {
        for (WebElement child : super.getElements()) {
            if (child.getType() == WebElementType.STATUSBAR) {
                return (StatusBar) child;
            }
        }
        return null;
    }

    /**
     * Getter for the application message queue instance
     * 
     * @return message queue
     */
    public MessageQueue getMessageQueue() {
        return this.messageQueue;
    }

    /**
     * Getter for the dialogController.
     * 
     * @return Returns the dialogController.
     */
    public DialogController getDialogController() {
        return this.dialogController;
    }

    /**
     * Getter for the pickerDialogController.
     * 
     * @return Returns the pickerDialogController.
     */
    public PickerDialogController getPickerDialogController() {
        return this.pickerDialogController;
    }

    /**
     * Getter for the resource controller
     * 
     * @return returns the resource controller instance
     */
    public ResourceController getResourceController() {
        return this.resourceController;
    }

    /**
     * Getter for the errorContainer.
     * 
     * @return Returns the errorContainer.
     */
    public ErrorLogContainer getErrorContainer() {
        return this.errorContainer;
    }

    /**
     * Whether the application runs in debug mode or not. When in debug mode, some more developer
     * information is shown within the application.
     * 
     * @return <b>true</b> if the application runs in debug mode, <b>false</b> if not
     */
    public boolean isDebugEnabled() {
        return PropertyLoader.loadProperty(this.extension.getDebugMode());
    }

    @Override
    public JsonElement toJson() {

        String title = PropertyLoader.loadProperty(this.extension.getTitle());
        String layout = PropertyLoader.loadProperty(this.extension.getLayout());
        String resource = PropertyLoader.loadProperty(this.extension.getResource());

        JsonMap jsonMap = new JsonMap();
        jsonMap.add(JSON_TITLE, title);
        jsonMap.add(JSON_LAYOUT, layout);
        jsonMap.add(JSON_RESOURCE, resource);
        jsonMap.add(JSON_DEBUG, String.valueOf(this.isDebugEnabled()));

        return jsonMap;
    }

}
