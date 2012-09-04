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
package org.nabucco.framework.base.ui.web.servlet.util;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.ui.web.component.WebApplication;
import org.nabucco.framework.base.ui.web.component.browser.Browser;
import org.nabucco.framework.base.ui.web.component.browser.BrowserArea;
import org.nabucco.framework.base.ui.web.component.common.controller.DialogController;
import org.nabucco.framework.base.ui.web.component.common.controller.PickerDialogController;
import org.nabucco.framework.base.ui.web.component.common.controller.resource.ResourceController;
import org.nabucco.framework.base.ui.web.component.common.grid.GridWidgetElement;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;
import org.nabucco.framework.base.ui.web.component.error.ErrorLogContainer;
import org.nabucco.framework.base.ui.web.component.navigator.NavigationArea;
import org.nabucco.framework.base.ui.web.component.navigator.Navigator;
import org.nabucco.framework.base.ui.web.component.perspective.Perspective;
import org.nabucco.framework.base.ui.web.component.perspective.PerspectiveArea;
import org.nabucco.framework.base.ui.web.component.statusbar.StatusBar;
import org.nabucco.framework.base.ui.web.component.titlebar.TitleBar;
import org.nabucco.framework.base.ui.web.component.widgets.WidgetElement;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.dashboard.DashboardItem;
import org.nabucco.framework.base.ui.web.component.work.editor.EditArea;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationArea;
import org.nabucco.framework.base.ui.web.component.work.list.ListItem;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSessionFactory;

/**
 * Utility class for servlet operations.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoServletUtil {

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NabuccoServletUtil.class);

    /**
     * Private constructor must not be invoked.
     */
    private NabuccoServletUtil() {
        throw new IllegalStateException("Must not create an instance of 'NabuccoServletUtil'.");
    }

    /**
     * Returns the web application instance.
     * 
     * @return the web application
     */
    public static WebApplication getApplication() {
        NabuccoWebSession session = NabuccoWebSessionFactory.getCurrentSession();
        return session.getWebApplication();
    }

    /**
     * Returns the application title bar.
     * 
     * @return the title bar.
     */
    public static TitleBar getTitleBar() {
        WebApplication application = getApplication();
        if (application == null) {
            logger.warning("No Application Configured.");
            return null;
        }
        return application.getTitleBar();
    }

    /**
     * Returns the application status bar.
     * 
     * @return the status bar.
     */
    public static StatusBar getStatusBar() {
        WebApplication application = getApplication();
        if (application == null) {
            logger.warning("No Application Configured.");
            return null;
        }
        return application.getStatusBar();
    }

    /**
     * Getter for the widget with the given id.
     * 
     * @param id
     *            id of the widget to resolve
     * 
     * @return the widget with the given id, or null if none was found
     */
    public static WidgetElement getWidget(String id) {

        // TitleBar Widgets
        TitleBar titleBar = getTitleBar();
        if (titleBar != null) {
            GridWidgetElement grid = titleBar.getGrid();
            if (grid != null) {
                WidgetElement widget = grid.getWidget(id);
                if (widget != null) {
                    return widget;
                }
            }
        }

        // StatusBar Widgets
        StatusBar statusBar = getStatusBar();
        if (statusBar != null) {
            GridWidgetElement grid = statusBar.getGrid();
            if (grid != null) {
                WidgetElement widget = grid.getWidget(id);
                if (widget != null) {
                    return widget;
                }
            }
        }

        // TODO: Widget Registration

        return null;
    }

    /**
     * Getter for the perspective area of the.
     * 
     * @return the perspective area
     */
    public static PerspectiveArea getPerspectiveArea() {
        WebApplication application = getApplication();
        if (application == null) {
            logger.warning("No Application Configured.");
            return null;
        }
        return application.getPerspectiveArea();
    }

    /**
     * Getter for the selected perspective.
     * 
     * @return the selected perspective
     */
    public static Perspective getPerspective(String perspectiveId) {
        PerspectiveArea perspectiveArea = getPerspectiveArea();
        if (perspectiveArea == null) {
            logger.warning("No perspective area configured.");
            return null;
        }
        return perspectiveArea.getPerspective(perspectiveId);
    }

    /**
     * Getter for the dialog with given instance Id.
     * 
     * @return the dialog
     */
    public static Dialog getDialog(String instanceId) {
        WebApplication application = getApplication();
        if (application == null) {
            logger.warning("No Application Configured.");
            return null;
        }
        return application.getDialogController().getDialog(instanceId);
    }

    /**
     * Getter for the dialog controller
     * 
     * @return the dialog controller
     */
    public static DialogController getDialogController() {
        WebApplication application = getApplication();
        if (application == null) {
            logger.warning("No Application Configured.");
            return null;
        }
        return application.getDialogController();
    }

    /**
     * Getter for the error Container
     * 
     * @return the error container instance
     */
    public static ErrorLogContainer getErrorContainer() {
        WebApplication application = getApplication();
        if (application == null) {
            logger.warning("No Application Configured.");
            return null;
        }
        return application.getErrorContainer();
    }

    /**
     * Getter for the picker dialog controller
     * 
     * @return the picker dialog controller
     */
    public static PickerDialogController getPickerDialogController() {
        WebApplication application = getApplication();
        if (application == null) {
            logger.warning("No Application Configured.");
            return null;
        }
        return application.getPickerDialogController();
    }

    /**
     * Getter for the resource controller
     * 
     * @return the resource controller instance
     */
    public static ResourceController getResourceController() {
        WebApplication application = getApplication();
        if (application == null) {
            logger.warning("No Application Configured.");
            return null;
        }
        return application.getResourceController();
    }
    /**
     * Getter for the selected perspective.
     * 
     * @return the selected perspective
     */
    public static Perspective getSelectedPerspective() {
        PerspectiveArea perspectiveArea = getPerspectiveArea();
        if (perspectiveArea == null) {
            logger.warning("No perspective area configured.");
            return null;
        }
        return perspectiveArea.getCurrentPerspective();
    }

    /**
     * Getter for the navigation area of the selected perspective.
     * 
     * @return the navigation area
     */
    public static NavigationArea getNavigationArea() {
        Perspective perspective = getSelectedPerspective();
        if (perspective == null) {
            logger.warning("Currently no perspective selected.");
            return null;
        }
        return perspective.getNavigationArea();
    }

    /**
     * Getter for the navigator with the given id.
     * 
     * @param navigatorId
     *            the navigator id
     * 
     * @return the navigator with the given navigator id, or null if not defined
     */
    public static Navigator getNavigator(String navigatorId) {
        NavigationArea navigationArea = getNavigationArea();
        if (navigationArea == null) {
            logger.warning("Currently no navigation area selected.");
            return null;
        }
        return navigationArea.getNavigator(navigatorId);
    }

    /**
     * Getter for the browser area of the selected perspective.
     * 
     * @return the browser area
     */
    public static BrowserArea getBrowserArea() {
        Perspective perspective = getSelectedPerspective();
        if (perspective == null) {
            logger.warning("Currently no perspective selected.");
            return null;
        }
        return perspective.getBrowserArea();
    }

    /**
     * Getter for the browser with the given id.
     * 
     * @param browserId
     *            the browser id
     * 
     * @return the browser with the given browser id, or null if not defined
     */
    public static Browser getBrowser(String browserId) {
        BrowserArea browserArea = getBrowserArea();
        if (browserArea == null) {
            logger.warning("Currently no browser area selected.");
            return null;
        }
        return browserArea.getBrowser(browserId);
    }

    /**
     * Getter for the selected browser.
     * 
     * @return the selected browser, or null if none is selected
     */
    public static Browser getSelectedBrowser() {
        BrowserArea browserArea = getBrowserArea();
        if (browserArea == null) {
            logger.warning("Currently no browser area selected.");
            return null;
        }
        return browserArea.getSelectedBrowser();
    }

    /**
     * Getter for the work area of the selected perspective.
     * 
     * @return the work area
     */
    public static WorkArea getWorkArea() {
        Perspective perspective = getSelectedPerspective();
        if (perspective == null) {
            logger.warning("Currently no perspective selected.");
            return null;
        }
        return perspective.getWorkArea();
    }

    /**
     * Getter for the work item with the given id.
     * 
     * @param workItemId
     *            the work item id
     * 
     * @return the work item with the given id, or null if not defined
     */
    public static WorkItem getWorkItem(String workItemId) {
        WorkArea workArea = getWorkArea();
        if (workArea == null) {
            logger.warning("Currently no work area selected.");
            return null;
        }
        return workArea.getItem(workItemId);
    }

    /**
     * Getter for the selected work item.
     * 
     * @return the selected work item, or null if none is open
     */
    public static WorkItem getSelectedWorkItem() {
        WorkArea workArea = getWorkArea();
        if (workArea == null) {
            logger.warning("Currently no work area selected.");
            return null;
        }
        return workArea.getCurrentItem();
    }

    /**
     * Getter for the editor with the given id.
     * 
     * @param editorId
     *            the editor id
     * 
     * @return the editor with the given id, or null if not defined
     */
    public static EditorItem getEditor(String editorId) {
        WorkItem editor = getWorkItem(editorId);
        if (editor instanceof EditorItem) {
            return (EditorItem) editor;
        }
        return null;
    }

    /**
     * Getter for the edit area of the editor with the given id.
     * 
     * @param editorId
     *            the editor id
     * 
     * @return the edit area of the editor with the given id, or null if no editor with the given id
     *         is defined
     */
    public static EditArea getEditArea(String editorId) {
        EditorItem editor = getEditor(editorId);
        if (editor == null) {
            logger.warning("No Editor with id '", editorId, "' defined.");
            return null;
        }
        return editor.getEditArea();
    }

    /**
     * Getter for the relation area of the editor with the given id.
     * 
     * @param editorId
     *            the editor id
     * 
     * @return the relation area of the editor with the given id, or null if no editor with the
     *         given id is defined
     */
    public static RelationArea getRelationArea(String editorId) {
        EditorItem editor = getEditor(editorId);
        if (editor == null) {
            logger.warning("No Editor with id '", editorId, "' defined.");
            return null;
        }
        return editor.getRelationArea();
    }

    /**
     * Getter for the list with the given id.
     * 
     * @param listId
     *            the list id
     * 
     * @return the list with the given id, or null if not defined
     */
    public static ListItem getList(String listId) {
        WorkItem list = getWorkItem(listId);
        if (list instanceof ListItem) {
            return (ListItem) list;
        }
        return null;
    }

    /**
     * Getter for the dashboard with the given id.
     * 
     * @param dashboardId
     *            the dashboard id
     * 
     * @return the dashboard with the given id, or null if not defined
     */
    public static DashboardItem getDashboard(String dashboardId) {
        WorkItem dashboard = getWorkItem(dashboardId);
        if (dashboard instanceof DashboardItem) {
            return (DashboardItem) dashboard;
        }
        return null;
    }

}
