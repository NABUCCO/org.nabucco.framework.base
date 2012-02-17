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

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.AgentCronExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.AgentDurationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.AgentExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * SetupAgentConfigParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SetupAgentConfigParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_ID = "id";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_TYPE = "type";

    private static final String ATTR_AUTOACTIVATE = "autoActivate";

    private static final String ATTR_EXECUTOR = "executor";

    private static final String ATTR_YEAR = "year";

    private static final String ATTR_WEEKDAY = "weekday";

    private static final String ATTR_MONTH = "month";

    private static final String ATTR_DAY = "day";

    private static final String ATTR_HOUR = "hour";

    private static final String ATTR_MINUTE = "minute";

    private static final String TAG_AGENT = "agent";

    private static final String TAG_CRON = "cron";

    private static final String TAG_DURATION = "duration";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(SetupAgentConfigParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        logger.debug("Parsing agent configuration.");

        try {
            AgentExtension extension = new AgentExtension();
            extension.setIdentifier(new ExtensionId(element.getAttribute(ATTR_ID)));

            List<Element> agents = super.getElementsByTagName(element, TAG_AGENT);

            if (agents.size() != 1) {
                throw new ExtensionParserException("There must be exaclty 1 agent configured but found "
                        + agents.size() + " in configuration [" + extension.getIdentifier() + "].");
            }

            Element agent = agents.get(0);
            extension.setName(super.getStringProperty(agent, ATTR_NAME));
            extension.setType(super.getEnumerationProperty(agent, ATTR_TYPE));
            extension.setAutoActivate(super.getBooleanProperty(agent, ATTR_AUTOACTIVATE));
            extension.setExecutor(super.getClassProperty(agent, ATTR_EXECUTOR));

            List<Element> crons = super.getElementsByTagName(agent, TAG_CRON);

            for (Element cron : crons) {
                AgentCronExtension cronExtension = this.parseCronExtension(cron);
                extension.getCronExtensions().add(cronExtension);
            }

            List<Element> durations = super.getElementsByTagName(agent, TAG_DURATION);

            if (!crons.isEmpty() && !durations.isEmpty()) {
                throw new ExtensionParserException("Only one of <cron> or <duration> tag is allowed for agent ["
                        + extension.getIdentifier() + "].");
            }

            if (durations.size() > 1) {
                throw new ExtensionParserException("Only 1 <duration> tag is allowed for agent ["
                        + extension.getIdentifier() + "].");
            }

            if (!durations.isEmpty()) {
                Element duration = durations.get(0);

                AgentDurationExtension durationExtension = new AgentDurationExtension();
                durationExtension.setMinutes(super.getIntegerProperty(duration, ATTR_MINUTE, 0));
                durationExtension.setHours(super.getIntegerProperty(duration, ATTR_HOUR, 0));
                extension.setDurationExtension(durationExtension);
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException(e);
        }
    }

    /**
     * Parse the <cron> extension.
     * 
     * @param cron
     *            the cron xml element
     * 
     * @return the cron extension
     * 
     * @throws ExtensionParserException
     *             when the cron extension cannot be parsed
     */
    private AgentCronExtension parseCronExtension(Element cron) throws ExtensionParserException {
        AgentCronExtension cronExtension = new AgentCronExtension();
        cronExtension.setYear(super.getIntegerProperty(cron, ATTR_YEAR, -1));
        cronExtension.setWeekday(super.getIntegerProperty(cron, ATTR_WEEKDAY, -1));
        cronExtension.setMonth(super.getIntegerProperty(cron, ATTR_MONTH, -1));
        cronExtension.setDay(super.getIntegerProperty(cron, ATTR_DAY, -1));
        cronExtension.setHour(super.getIntegerProperty(cron, ATTR_HOUR, -1));
        cronExtension.setMinute(super.getIntegerProperty(cron, ATTR_MINUTE, 0));
        cronExtension.setMinute(super.getIntegerProperty(cron, ATTR_MINUTE, 0));
        return cronExtension;
    }

}
