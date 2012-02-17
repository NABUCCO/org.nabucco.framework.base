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
 * WidgetAnalyser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public interface WidgetAnalyser {

    /**
     * Evaluates list of elements and produce statistic
     * 
     * @param values
     *            list of elements
     * @return result of analyse
     */
    WidgetAnalyserResult evaluate(List<NabuccoDatatype> values) throws ClientException;

    /**
     * Normalise result. akkumulate small values, round percents etc
     * 
     * @param minPercent
     *            the minimal percent weight to be shown (smaller elements will be accumulated)
     * @return normalized result
     */
    WidgetAnalyserResult normalizeResult(WidgetAnalyserResult quellResult, int minPercent) throws ClientException;

}
