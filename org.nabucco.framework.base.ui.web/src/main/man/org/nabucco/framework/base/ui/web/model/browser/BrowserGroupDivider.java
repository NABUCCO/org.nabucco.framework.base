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
package org.nabucco.framework.base.ui.web.model.browser;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.ui.web.model.common.grouping.UIGroupDevider;

/**
 * BrowserGroupDevider
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BrowserGroupDivider implements UIGroupDevider<BrowserEntry> {

    private static final String GROUPING_IMG = "./images/icons/grouping.png";

    private int groupingSize;

    /**
     * Creates a new {@link BrowserGroupDivider} instance.
     * 
     * @param groupingSize
     *            size to group or 0 is no grouping needed
     */
    public BrowserGroupDivider(int groupingSize) {
        this.groupingSize = groupingSize;
    }

    @Override
    public List<BrowserEntry> devideElements(String idPrefix, List<BrowserEntry> elements) {
        List<BrowserEntry> retVal = new ArrayList<BrowserEntry>();

        if (elements == null) {
            return retVal;
        }

        if (this.groupingSize == 0) {
            return elements;
        } else if (this.groupingSize > elements.size()) {
            // No grouping needed if there are not enougth elements to group
            return elements;
        }

        BrowserEntry group = null;

        for (int i = 0; i < elements.size(); i++) {

            if (group == null || (group.size() == this.groupingSize)) {
                int startIndex = i + 1;
                int endIndex;

                // Group range may not show more count of elements as exists
                if (i + this.groupingSize > elements.size()) {
                    endIndex = elements.size();
                } else {
                    endIndex = i + this.groupingSize;
                }

                String label = startIndex + ".." + endIndex;

                group = new BrowserEntry(idPrefix + "." + label, label, label, GROUPING_IMG, false, null, null);
                retVal.add(group);
            }

            group.add(elements.get(i));

        }
        return retVal;
    }
}
