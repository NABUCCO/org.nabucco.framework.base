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
package org.nabucco.framework.base.ui.web.model.dashboard.widget.common;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;

/**
 * AbstractWidgetAnalyser
 * 
 * The analyser class that process given list of datatypes and evaluates statistic to be shown
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class AbstractWidgetAnalyser implements WidgetAnalyser {

    private static final String EMPTY_STRING = "";

    /**
     * Creates a new {@link AbstractWidgetAnalyser} instance.
     */
    public AbstractWidgetAnalyser() {

    }

    @Override
    public final WidgetAnalyserResult evaluate(List<NabuccoDatatype> values) throws ClientException {

        WidgetAnalyserResult result = this.createResultInstance(EMPTY_STRING);

        for (NabuccoDatatype item : values) {
            WidgetAnalyserResult evResult = this.evaluateDatatype(item);
            
            if (evResult == null) {
                throw new ClientException(
                        "Evaluating mechanism of the custom widget analyser returns null. Cannot render widget.");
            }
            
            result.appendResult(evResult);
        }

        return result;
    }

    @Override
    public WidgetAnalyserResult normalizeResult(WidgetAnalyserResult resultItem, int minPercent) throws ClientException {
        WidgetAnalyserResult retVal = this.createResultInstance(EMPTY_STRING);

        if (resultItem.isParent()) {
            if (resultItem.getChildren().size() > 2) {
                for (WidgetAnalyserResult child : resultItem.getChildren()) {
                    if (child.getWeight() <= minPercent) {
                        WidgetAnalyserResult normalizedItem = this.normalizeResult(child, minPercent);
                        retVal.appendResult(normalizedItem);
                    } else {
                        retVal.appendResult(child);
                    }
                }
            } else {
                for (WidgetAnalyserResult child : resultItem.getChildren()) {
                    retVal.appendResult(child);
                }
            }

        } else if (!resultItem.isParent() && resultItem.getParent() != null) {
            retVal.appendResult(resultItem);
        }

        return retVal;
    }

    /**
     * Factory method to create a parent item
     * 
     * @param label
     *            the label of the instance
     * @return new instance
     * @throws ClientException
     *             if illegal input
     */
    protected WidgetAnalyserResult createResultInstance(String label) throws ClientException {
        return this.createResultInstance(label, null);
    }

    /**
     * Factory method to create result items
     * 
     * @param label
     *            the label for the item
     * @param parent
     *            the parent element if any (can be null)
     * @return new instance
     * @throws ClientException
     *             if illegal input
     */
    protected WidgetAnalyserResult createResultInstance(String label, WidgetAnalyserResult parent)
            throws ClientException {
        if (label == null) {
            throw new ClientException("Cannot instanciate result item. The label is 'null'");
        }

        if (parent == null) {
            return new WidgetAnalyserResultImpl(label);
        }

        WidgetAnalyserResultImpl retVal = new WidgetAnalyserResultImpl(parent, label);

        parent.addChild(retVal);
        return retVal;
    }

    /**
     * Evaluate a datatype and produce statistic result to be shown
     * 
     * @param item
     *            the datatype to be evaluated
     * @return result of the evaluation as a composite tree
     */
    protected abstract WidgetAnalyserResult evaluateDatatype(NabuccoDatatype item) throws ClientException;

}
