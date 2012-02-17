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
package org.nabucco.framework.base.impl.service.aspect;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.AdviceType;
import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;

/**
 * Handles the service requests from the service interceptor and delegates the request to the
 * configures aspects.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class AspectHandler {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(AspectHandler.class);

    private List<AspectHandlerEntry> beforeList;

    private List<AspectHandlerEntry> afterList;

    /**
     * Creates a new {@link AspectHandler} instance. Must not be called manually, use
     * {@link AspectHandlerBuilder} instead.
     */
    AspectHandler() {
    }

    /**
     * Calls all configured aspects before service invocation.
     * 
     * @param context
     *            the aspect context
     * @param request
     *            the service request
     * 
     * @throws AspectException
     *             when the aspect raised an exception
     */
    public void handleBeforeAspect(AspectExecutionContext context, ServiceRequest<?> request) throws AspectException {

        int size = this.beforeList.size();
        AspectHandlerEntry entry;

        for (int i = 0; i < size; i++) {

            entry = this.beforeList.get(i);

            Aspect aspect = this.getAspect(entry, context);
            AspectExecutor executor = this.getAspectExecutor(aspect, entry, context);

            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("Excecuting before aspect [" + entry.getAspectClass() + "].");
                }

                executor.executeBeforeAspect(aspect, context, request);

                // Increment the aspect chain depth.
                context.incrementDepth();

                // Break aspect chain execution when shorten flag is set.
                if (context.isShortenExecution()) {
                    break;
                }

            } catch (AspectException ae) {
                logger.error(ae, "Error executing before Aspect.");
                throw ae;
            } catch (Exception e) {
                logger.error(e, "Error executing before Aspect.");
                throw new AspectException("Error executing before Aspect.", e);
            }
        }
    }

    /**
     * Calls all configured aspects after service invocation.
     * 
     * @param context
     *            the aspect context
     * @param request
     *            the service request
     * @param response
     *            the service response
     * 
     * @throws AspectException
     *             when the aspect raised an exception
     */
    public void handleAfterAspect(AspectExecutionContext context, ServiceRequest<?> request, ServiceResponse<?> response)
            throws AspectException {

        AspectHandlerEntry entry;
        int size = context.getDepth() - 1;

        for (int i = size; i >= 0; i--) {
            entry = this.afterList.get(i);

            Aspect aspect = this.getAspect(entry, context);
            AspectExecutor executor = this.getAspectExecutor(aspect, entry, context);

            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("Excecuting after aspect [" + entry.getAspectClass() + "].");
                }

                executor.executeAfterAspect(aspect, context, request, response);

            } catch (AspectException ae) {
                logger.error(ae, "Error executing after Aspect.");
                throw ae;
            } catch (Exception e) {
                logger.error(e, "Error executing after Aspect.");
                throw new AspectException("Error executing before Aspect.", e);
            }
        }
    }

    /**
     * Getter for the aspect executor for the given aspect handler entry. If none appropriate
     * executor already exists a new one is created.
     * 
     * @param aspect
     *            the aspect to execute
     * @param entry
     *            the aspect handler entry
     * @param context
     *            the aspect execution context holding already created executors
     * 
     * @return the aspect executor instance
     */
    private AspectExecutor getAspectExecutor(Aspect aspect, AspectHandlerEntry entry, AspectExecutionContext context) {

        if (aspect instanceof NoOpAspect) {
            return new NoOpAspectExecutor();
        }

        Class<? extends AspectExecutor> executorClass = entry.getAspectExecutorClass();

        AspectExecutor executor;

        if (context.getAspectExecutor(executorClass) == null) {
            executor = this.createExecutor(executorClass);
            context.saveAspectExcutor(executorClass, executor);
        } else {
            executor = context.getAspectExecutor(executorClass);
        }

        return executor;
    }

    /**
     * Getter for the aspect for the given aspect handler entry. If none appropriate aspect already
     * exists a new one is created.
     * 
     * @param entry
     *            the aspect handler entry
     * @param context
     *            the aspect execution context holding already created aspects
     * 
     * @return the aspect instance
     */
    private Aspect getAspect(AspectHandlerEntry entry, AspectExecutionContext context) {

        Class<? extends Aspect> aspectClass = entry.getAspectClass();

        Aspect aspect;

        if (entry.getAdvice() == AdviceType.AROUND) {
            if (context.getAroundAspect(aspectClass) == null) {
                aspect = this.createAspect(aspectClass);
                context.saveAroundAspect(aspectClass, aspect);
            } else {
                aspect = context.getAroundAspect(aspectClass);
            }
        } else {
            aspect = this.createAspect(aspectClass);
        }

        this.configureAspect(aspect, entry.getAspectProperties());

        return aspect;
    }

    /**
     * Instantiate a new aspect executor.
     * 
     * @param clazz
     *            the aspect executor class
     * 
     * @return the newly created aspect executor instance
     */
    private AspectExecutor createExecutor(Class<? extends AspectExecutor> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            logger.error(e, "Cannot create instance for AspectExecutor class=[" + clazz + "]");
            return new NoOpAspectExecutor();
        }
    }

    /**
     * Instantiate a new aspect for the given aspect class.
     * 
     * @param clazz
     *            the aspect class
     * 
     * @return the new created aspect instance
     */
    private Aspect createAspect(Class<? extends Aspect> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            logger.error(e, "Cannot create instance for Aspect class=[" + clazz.getName() + "]");
            return new NoOpAspect();
        }
    }

    /**
     * Configure the aspect with the aspect properties.
     * 
     * @param aspect
     *            the aspect to configure
     * @param properties
     *            the configured properties
     */
    private void configureAspect(Aspect aspect, PropertyExtension properties) {
        aspect.configure(properties);
    }

    /**
     * Getter for the list of before handlers.
     * 
     * @return the aspect handlers before execution
     */
    List<AspectHandlerEntry> getBeforeList() {
        return this.beforeList;
    }

    /**
     * Setter for the list of before handlers.
     * 
     * @param beforeList
     *            the aspect handlers before execution
     */
    void setBeforeList(List<AspectHandlerEntry> beforeList) {
        this.beforeList = beforeList;
    }

    /**
     * Getter for the list of after handlers.
     * 
     * @return the aspect handlers after execution
     */
    List<AspectHandlerEntry> getAfterList() {
        return this.afterList;
    }

    /**
     * Setter for the list of after handlers.
     * 
     * @param afterList
     *            the aspect handlers after execution
     */
    void setAfterList(List<AspectHandlerEntry> afterList) {
        this.afterList = afterList;
    }

}
