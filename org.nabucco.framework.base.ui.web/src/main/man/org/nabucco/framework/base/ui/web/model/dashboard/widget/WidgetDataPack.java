/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.ui.web.model.dashboard.widget;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;

/**
 * WidgetDataPack
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
class WidgetDataPack {

    String filterId;

    List<NabuccoDatatype> datatypeList;

    String viewName;

    /**
     * 
     * Creates a new {@link WidgetDataPack} instance.
     * 
     * @param filterId
     *            the id of the filter
     * @param datatypeList
     *            the datatype list
     * @param viewName
     *            the name of the view
     */
    public WidgetDataPack(String filterId, List<NabuccoDatatype> datatypeList, String viewName) {
        this.filterId = filterId;
        this.datatypeList = datatypeList;
        this.viewName = viewName;
    }

    /**
     * Getter for the filterId.
     * 
     * @return Returns the filterId.
     */
    public String getFilterId() {
        return filterId;
    }

    /**
     * Getter for the datatypeList.
     * 
     * @return Returns the datatypeList.
     */
    public List<NabuccoDatatype> getDatatypeList() {
        return datatypeList;
    }

    /**
     * Getter for the viewName.
     * 
     * @return Returns the viewName.
     */
    public String getViewName() {
        return viewName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((datatypeList == null) ? 0 : datatypeList.size());
        result = prime * result + ((filterId == null) ? 0 : filterId.hashCode());
        result = prime * result + ((viewName == null) ? 0 : viewName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        WidgetDataPack other = (WidgetDataPack) obj;
        if (datatypeList == null) {
            if (other.datatypeList != null) {
                return false;
            }
        } else if (datatypeList.size() != other.datatypeList.size()) {
            return false;
        }
        if (filterId == null) {
            if (other.filterId != null) {
                return false;
            }
        } else if (!filterId.equals(other.filterId)) {
            return false;
        }
        if (viewName == null) {
            if (other.viewName != null) {
                return false;
            }
        } else if (!viewName.equals(other.viewName)) {
            return false;
        }
        return true;
    }

}
