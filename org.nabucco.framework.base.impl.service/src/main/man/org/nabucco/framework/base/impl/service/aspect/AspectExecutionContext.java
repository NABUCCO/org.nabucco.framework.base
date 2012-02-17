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

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;

/**
 * AspectExecutionContext
 * <p/>
 * Context for the aspect interceptor chain.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class AspectExecutionContext {

    private String serviceKey;

    private boolean shortenExecution = false;

    private int depth = 0;

    private ServiceRequest<?> serviceRequest;

    private ServiceResponse<?> serviceResponse;

    private Map<Class<? extends Aspect>, Aspect> aspects = new HashMap<Class<? extends Aspect>, Aspect>();

    private Map<Class<? extends AspectExecutor>, AspectExecutor> aspectExecutors = new HashMap<Class<? extends AspectExecutor>, AspectExecutor>();

    /**
     * Creates a new {@link AspectExecutionContext} instance.
     * 
     * @param serviceKey
     *            the service key
     */
    public AspectExecutionContext(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    /**
     * Getter for the service key.
     * 
     * @return the service key
     */
    public String getServiceKey() {
        return this.serviceKey;
    }

    /**
     * Add an around aspect for the given class.
     * 
     * @param aClass
     *            the aspect class
     * @param instance
     *            the aspect instance
     */
    public void saveAroundAspect(Class<? extends Aspect> aClass, Aspect instance) {
        this.aspects.put(aClass, instance);
    }

    /**
     * Getter for the around aspect.
     * 
     * @param aClass
     *            the aspect class
     * 
     * @return the aspect instance
     */
    public Aspect getAroundAspect(Class<? extends Aspect> aClass) {
        return this.aspects.get(aClass);
    }

    /**
     * Add an aspect executor for the given class.
     * 
     * @param aClass
     *            the aspect executor class
     * 
     * @return the aspect executor instance
     */
    public void saveAspectExcutor(Class<? extends AspectExecutor> aClass, AspectExecutor instance) {
        this.aspectExecutors.put(aClass, instance);
    }

    /**
     * Getter for the aspect executor.
     * 
     * @param aClass
     *            the aspect executor class
     * 
     * @return the aspect executor instance
     */
    public AspectExecutor getAspectExecutor(Class<? extends AspectExecutor> aClass) {
        return this.aspectExecutors.get(aClass);
    }

    /**
     * Getter for the current service operation request.
     * 
     * @return the service request
     */
    public ServiceRequest<?> getServiceRequest() {
        return serviceRequest;
    }

    /**
     * Setter for the service operation request.
     * 
     * @param serviceRequest
     *            the service request
     */
    public void setServiceRequest(ServiceRequest<?> serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    /**
     * Getter for the current service operation response.
     * 
     * @return the service response
     */
    public ServiceResponse<?> getServiceResponse() {
        return serviceResponse;
    }

    /**
     * Setter for the service operation response.
     * 
     * @param serviceResponse
     *            the service response
     */
    public void setServiceResponse(ServiceResponse<?> serviceResponse) {
        this.serviceResponse = serviceResponse;
    }

    /**
     * Getter for bypassing the service operation execution.
     * 
     * @return <b>true</b> for bypassing, <b>false</b> for not
     */
    public boolean isShortenExecution() {
        return this.shortenExecution;
    }

    /**
     * Setter for bypassing the service operation execution.
     * 
     * @param shortenExecution
     *            <b>true</b> for bypassing, <b>false</b> for not
     */
    public void setShortenExecution(boolean shortenExecution) {
        this.shortenExecution = shortenExecution;
    }

    /**
     * Getter for the aspect chain depth.
     * 
     * @return the current depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Increment the aspect chain depth by 1.
     */
    public void incrementDepth() {
        this.depth++;
    }

}
