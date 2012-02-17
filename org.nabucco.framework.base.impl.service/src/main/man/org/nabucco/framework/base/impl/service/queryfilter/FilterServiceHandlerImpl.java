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
package org.nabucco.framework.base.impl.service.queryfilter;

import java.util.regex.Pattern;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.converter.BasetypeConverter;
import org.nabucco.framework.base.facade.datatype.converter.ConverterException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterParameterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterSetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.queryfilter.QueryFilterParameter;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.DatatypeListMsg;
import org.nabucco.framework.base.facade.message.queryfilter.QueryFilterRq;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;

/**
 * FilterServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class FilterServiceHandlerImpl extends FilterServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final Pattern SPLIT = Pattern.compile("\\.");

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(FilterServiceHandlerImpl.class);

    @Override
    protected DatatypeListMsg filter(QueryFilterRq msg) throws SearchException {

        DatatypeListMsg rs = new DatatypeListMsg();

        ExtensionId filterId = msg.getFilterId();
        if (filterId == null || filterId.getValue() == null) {
            return rs;
        }

        try {
            QueryFilterExtension filterExtension = this.getFilter(filterId.getValue());
            if (filterExtension == null) {
                logger.warning("No query filter with id '", filterId.getValue(), "' configured.");
                return rs;
            }

            String queryString = PropertyLoader.loadProperty(filterExtension.getQuery());
            if (queryString == null) {
                logger.warning("No query for filter with id '", filterId.getValue(), "' configured.");
                return rs;
            }

            // Query
            NabuccoQuery<NabuccoDatatype> query = super.getPersistenceManager().createQuery(queryString);

            // Parameters
            this.addParameters(query, filterExtension, msg.getParameters());

            rs.getDatatypeList().addAll(query.getResultList());

        } catch (ExtensionException ee) {
            logger.error(ee, "No query filter with id '", filterId.getValue(), "' configured.");
            throw new SearchException("No query filter with id '" + filterId.getValue() + "' configured.", ee);
        } catch (PersistenceException pe) {
            logger.error(pe, "Query of filter '", filterId.getValue(), "' is not valid.");
            throw new SearchException("Query of filter '" + filterId.getValue() + "' is not valid.", pe);
        } catch (Exception e) {
            logger.error(e, "Error executing query of filter '", filterId.getValue(), "'.");
            throw new SearchException("Error executing query of filter '" + filterId.getValue() + "'.", e);
        }

        return rs;
    }

    /**
     * Add the parameters to the query. If parameters were sent
     * 
     * @param query
     *            the query to add the parameters to
     * @param filterExtension
     *            the filter extension holding the parameters
     * @param overriddenParameters
     *            parameters that are overriden by the service executor
     * 
     * @throws PersistenceException
     *             when the parameters cannot be added to the query. Either if they are not defined
     *             in the query or if they are invalid.
     */
    private void addParameters(NabuccoQuery<NabuccoDatatype> query, QueryFilterExtension filterExtension,
            NabuccoList<QueryFilterParameter> overriddenParameters) throws PersistenceException {

        for (QueryFilterParameterExtension parameterExtension : filterExtension.getParameters()) {

            // Default Values
            String parameterName = PropertyLoader.loadProperty(parameterExtension.getName());
            String parameterValue = PropertyLoader.loadProperty(parameterExtension.getValue());
            Class<Object> parameterType = PropertyLoader.loadProperty(parameterExtension.getType());

            if (parameterName == null || parameterName.isEmpty() || parameterType == null) {
                continue;
            }

            // System Values
            String systemValue = this.loadSystemVariable(parameterExtension.getSystemValue());
            if (systemValue != null) {
                parameterValue = systemValue;
            }

            // Parameter Values
            for (QueryFilterParameter overriddenParameter : overriddenParameters) {

                // Specific Values
                String overriddenParameterName = overriddenParameter.getName().getValue();
                if (parameterName.equals(overriddenParameterName)) {
                    parameterValue = overriddenParameter.getValue().getValue();
                    break;
                }
            }

            if (parameterValue == null || parameterValue.isEmpty()) {
                Object parameter = null;
                query.setParameter(parameterName, parameter);
            } else {
                Object parameter = this.createParameter(parameterType, parameterValue);
                query.setParameter(parameterName, parameter);
            }
        }
    }

    /**
     * Load System Variable values (like userid, owner, tenant).
     * 
     * @param systemValue
     *            the system value type
     * 
     * @return the system value
     */
    private String loadSystemVariable(EnumerationProperty systemValue) {

        SystemVariableType systemVariableType = PropertyLoader.loadProperty(SystemVariableType.class, systemValue);

        if (systemVariableType != null) {
            switch (systemVariableType) {
            case USERID:
                return super.getContext().getUserId();
            case OWNER:
                return super.getContext().getOwner();
            case TENANT:
                return super.getContext().getTenant();
            }
        }

        return null;
    }

    /**
     * Create the parameter instance.
     * 
     * @param <T>
     *            type of the parameter
     * @param type
     *            class of the parameter
     * @param value
     *            value of the parameter
     * 
     * @return the created parameter instance
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <T> T createParameter(Class<T> type, String value) {
        if (type == null) {
            return null;
        }

        try {
            if (Basetype.class.isAssignableFrom(type)) {
                Basetype basetype = (Basetype) type.newInstance();
                BasetypeConverter.setValueAsString(basetype, value);
                return (T) basetype;
            }

            if (Enumeration.class.isAssignableFrom(type)) {
                Class<Enum> enumType = (Class<Enum>) type;
                Enumeration enumeration = (Enumeration) Enum.valueOf(enumType, value);
                return (T) enumeration;
            }

            if (Long.class.isAssignableFrom(type)) {
                return (T) new Long(value);
            }

            throw new IllegalArgumentException("Cannot create parameter for type '"
                    + type.getCanonicalName() + "'. Must be of type Basetype or Enumeration.");

        } catch (ConverterException ce) {
            logger.error(ce, "Cannot convert query filter parameter to Basetype.");
        } catch (InstantiationException ie) {
            logger.error(ie, "Cannot instantiate query filter parameter type '", type.getCanonicalName(), "'.");
        } catch (IllegalAccessException iae) {
            logger.error(iae, "Cannot access query filter parameter type '", type.getCanonicalName(), "'.");
        } catch (Exception e) {
            logger.error(e, "Cannot convert query filter parameter for type '", type.getCanonicalName(), "'.");
        }

        return null;
    }

    /**
     * Get the filter with the given filter id.
     * 
     * @param id
     *            the filter id
     * 
     * @return the query extension
     * 
     * @throws ExtensionException
     *             when the extension cannot be resolved
     */
    private QueryFilterExtension getFilter(String id) throws ExtensionException {
        if (id == null) {
            return null;
        }

        String[] ids = SPLIT.split(id);
        if (ids.length != 2) {
            return null;
        }

        String filterSetId = ids[0];
        String filterId = ids[1];

        if (filterSetId == null || filterSetId.isEmpty() || filterId == null || filterId.isEmpty()) {
            return null;
        }

        ExtensionResolver resolver = NabuccoSystem.getExtensionResolver();
        ExtensionMap extensions = resolver.resolveExtensions(ExtensionPointType.ORG_NABUCCO_QUERYFILTER);

        for (String filterSet : extensions.getExtensionNames()) {
            QueryFilterSetExtension filterSetExtension = (QueryFilterSetExtension) extensions.getExtension(filterSet);

            if (!filterSetId.equals(PropertyLoader.loadProperty(filterSetExtension.getFilterSetId()))) {
                continue;
            }

            for (QueryFilterExtension filterExtension : filterSetExtension.getFilters()) {
                if (filterId.equals(PropertyLoader.loadProperty(filterExtension.getIdentifier()))) {
                    return filterExtension;
                }
            }
        }

        return null;
    }

}
