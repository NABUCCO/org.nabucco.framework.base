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
package org.nabucco.framework.base.ui.web.model.dashboard.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.common.color.ColorScheme;
import org.nabucco.framework.base.ui.web.component.work.dashboard.widget.DashboardWidgetType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.dashboard.widget.common.WidgetAnalyser;
import org.nabucco.framework.base.ui.web.model.dashboard.widget.common.WidgetAnalyserResult;

/**
 * GraphicWidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class GraphicDashboardWidgetModel extends DashboardWidgetModel {

    private int minPercent;

    private WidgetAnalyser analyser;

    private ColorScheme colorSheme;

    private Map<String, String> colorMap = new HashMap<String, String>();

    private WidgetAnalyserResult data;

    /**
     * 
     * Creates a new {@link GraphicDashboardWidgetModel} instance.
     * 
     * @param analyser
     *            the analyser instance
     * @param skaleType
     *            the type of the skale
     * @param colorSheme
     *            the color sheme instance
     * @param minPercent
     *            the smallest percent value to be shown
     */
    public GraphicDashboardWidgetModel(DashboardWidgetType type, WidgetAnalyser analyser, ColorScheme colorSheme,
            int minPercent) {

        super(type);

        if (analyser == null) {
            throw new IllegalArgumentException("Cannot instanciate Graphic Widget. The analyser is 'null'");
        }

        if (colorSheme == null) {
            throw new IllegalArgumentException("Cannot instanciate Graphic Widget. The colorSheme is 'null'");
        }

        this.minPercent = minPercent;
        this.analyser = analyser;
        this.colorSheme = colorSheme;
    }

    @Override
    public void init() {
    }

    @Override
    public void setContent(List<NabuccoDatatype> values) throws ClientException {
        this.data = this.evaluateElements(values);
    }

    /**
     * Process given elements. Build custom statistics using configured method
     * 
     * @param elements
     *            the elements to be analysed
     * @return the result of statictic evaluation
     * @throws ClientException
     *             if problems in the evaluation
     */
    private WidgetAnalyserResult evaluateElements(List<NabuccoDatatype> elements) throws ClientException {

        // The original result
        WidgetAnalyserResult evaluationResult = this.analyser.evaluate(elements);

        // The normalized result
        // TODO: Correct algorithmus to normalize results
        // evaluationResult = this.analyser.normalizeResult(evaluationResult, this.minPercent);

        // Set colors of items
        if (!this.colorSheme.getColors().isEmpty()) {
            this.coloriseResult(evaluationResult);
        }
        return evaluationResult;
    }

    /**
     * Recursively follow the tree and set the color id's
     * 
     * @return
     */
    private void coloriseResult(WidgetAnalyserResult item) {
        for (WidgetAnalyserResult child : item.getChildren()) {
            this.coloriseResult(child);
        }

        String label = item.getLabel();
        if (!label.isEmpty()) {
            if (!this.colorMap.containsKey(label)) {
                int size = this.colorMap.size();
                List<String> colors = this.colorSheme.getColors();
                int colorNr = size % colors.size();
                this.colorMap.put(label, colors.get(colorNr));
            }

            item.setColor(this.colorMap.get(label));
        }
    }

    /**
     * Getter for the minPercent.
     * 
     * @return Returns the minPercent.
     */
    public int getMinPercent() {
        return this.minPercent;
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = (JsonMap) super.toJson();

        if (this.data != null) {
            retVal.add(JSON_VALUE, this.data.toJson());
        }
        return retVal;
    }


}
