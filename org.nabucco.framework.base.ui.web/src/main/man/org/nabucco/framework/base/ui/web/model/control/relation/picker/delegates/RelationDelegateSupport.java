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
package org.nabucco.framework.base.ui.web.model.control.relation.picker.delegates;

import java.util.regex.Pattern;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.ui.web.model.common.renderer.DefaultWebRenderer;
import org.nabucco.framework.base.ui.web.model.common.renderer.PropertyLabelProvider;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProvider;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebRenderer;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * Support class for relation delegates.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
abstract class RelationDelegateSupport<P extends NabuccoProperty, N extends NType> implements RelationDelegate<P, N> {

    private TableModel<Datatype> tableModel;

    protected static final String JSON_ID = "id";

    protected static final Pattern SPLIT_PATTERN = Pattern.compile(",");

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(RelationDelegateSupport.class);

    /**
     * Creates a new {@link RelationDelegateSupport} instance.
     * 
     * @param tableModel
     *            the table model
     */
    public RelationDelegateSupport(TableModel<Datatype> tableModel) {
        this.tableModel = tableModel;
    }

    /**
     * Resolve the datatype with the given id from the table model.
     * 
     * @param rowId
     *            the id of the datatype to resolve from the table model
     * 
     * @return the datatype with the given id, or null if the table model does not contain a
     *         datatype with the given id
     */
    protected Datatype getDatatypeById(String rowId) {
        if (rowId == null || rowId.isEmpty()) {
            return null;
        }

        try {
            long id = Long.parseLong(rowId);
            return this.tableModel.getDatatypeByObjectId(id);
        } catch (NumberFormatException nfe) {
            logger.warning(nfe, "Cannot parse id '", rowId, "' as long.");
        }

        return null;
    }

    @Override
    public final String getLabel(Datatype datatype, String displayPath) {
        if (datatype == null) {
            return DEFAULT_LABEL;
        }

        WebLabelProvider<Datatype> labelProvider = new PropertyLabelProvider<Datatype>(datatype);
        WebRenderer renderer = new DefaultWebRenderer();
        String retVal = labelProvider.getLabelFromMessage(displayPath, renderer);

        return retVal;
    }

    public final String getId(Datatype datatype) {
        String retVal = "";

        if (datatype instanceof NabuccoDatatype) {
            retVal = String.valueOf(((NabuccoDatatype) datatype).getId());
        }

        return retVal;
    }
}
