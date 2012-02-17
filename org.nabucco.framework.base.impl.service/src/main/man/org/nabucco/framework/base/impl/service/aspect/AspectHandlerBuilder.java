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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.AdviceType;
import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.AspectExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * Builds a valid before and after list (reverse order) for executing the aspects without any
 * interpreting logic.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class AspectHandlerBuilder {

    /** The Logger */
    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            AspectHandlerBuilder.class);

    /** The Aspects executed before the service operation. */
    private List<AspectHandlerEntry> beforeAspectList;

    /** The Aspects executed after the service operation. */
    private List<AspectHandlerEntry> afterAspectList;

    /** The List of JoinPoints */
    private String[] joinPointList;

    /** The Aspect Key */
    private String key;

    private static AspectHandlerEntry noOperationBefore = getNoOpAspect(AdviceType.BEFORE);

    private static AspectHandlerEntry noOperationAfter = getNoOpAspect(AdviceType.AFTER);

    /**
     * Creates a new {@link AspectHandlerBuilder} instance.
     * 
     * @param joinPoints
     *            the list of join points
     * @param key
     *            the service key
     */
    public AspectHandlerBuilder(String[] joinPoints, String key) {
        this.joinPointList = joinPoints;
        this.key = key;
        this.beforeAspectList = new ArrayList<AspectHandlerEntry>();
        this.afterAspectList = new ArrayList<AspectHandlerEntry>();
        configure();
    }

    /**
     * Build the aspect handler.
     * 
     * @return the aspect handler
     */
    public AspectHandler getAspectHandler() {
        AspectHandler handler = new AspectHandler();
        handler.setBeforeList(this.beforeAspectList);
        handler.setAfterList(this.afterAspectList);

        return handler;
    }

    /**
     * Configure the aspect handler builder from the aspect configuration.
     */
    private void configure() {

        AspectRegistry registry = AspectRegistry.getInstance();

        for (String joinpoint : joinPointList) {

            List<AspectRegistryEntry> beforeList = registry.getPointcuts(key, joinpoint, AdviceType.BEFORE);
            List<AspectRegistryEntry> afterList = registry.getPointcuts(key, joinpoint, AdviceType.AFTER);
            List<AspectRegistryEntry> aroundList = registry.getPointcuts(key, joinpoint, AdviceType.AROUND);

            for (AspectRegistryEntry entry : beforeList) {
                AspectHandlerEntry execEntry = buildEntry(joinpoint, entry, AdviceType.BEFORE);
                this.beforeAspectList.add(execEntry);
                this.afterAspectList.add(noOperationBefore);
            }

            for (AspectRegistryEntry entry : afterList) {
                AspectHandlerEntry execEntry = buildEntry(joinpoint, entry, AdviceType.AFTER);
                this.beforeAspectList.add(noOperationAfter);
                this.afterAspectList.add(execEntry);
            }

            for (AspectRegistryEntry entry : aroundList) {
                AspectHandlerEntry execEntry = buildEntry(joinpoint, entry, AdviceType.AROUND);
                this.beforeAspectList.add(execEntry);
                this.afterAspectList.add(execEntry);
            }

        }

        Collections.reverse(afterAspectList);
    }

    /**
     * Create the aspect handler entry of the given joinpoint and advice type.
     * 
     * @param joinpoint
     *            the join point
     * @param regEntry
     *            the registry entry
     * @param type
     *            the advice type
     * 
     * @return the aspect handler entry
     */
    private AspectHandlerEntry buildEntry(String joinpoint, AspectRegistryEntry regEntry, AdviceType type) {
        Class<? extends Aspect> aspectClass = this.getAspectClass(regEntry.getExtension().getImplClass());
        Class<? extends AspectExecutor> aspectExecutorClass = this.getAspectExecutorClass(joinpoint);

        PropertyExtension props = regEntry.getExtension().getPropertyExtension();
        AspectHandlerEntry entry = new AspectHandlerEntry(aspectClass, aspectExecutorClass, type, props);

        return entry;
    }

    /**
     * Getter for the aspect executor of the given joinpoint.
     * 
     * @param joinpoint
     *            the joinpoint to find the aspect executor for
     * 
     * @return the aspect executor
     */
    private Class<? extends AspectExecutor> getAspectExecutorClass(String joinpoint) {
        AspectExtension ae = AspectRegistry.getInstance().getAspectExtension(joinpoint);
        return this.getExecutorClass(ae.getImplClass());
    }

    /**
     * Instantiate the aspect executor class.
     * 
     * @param classProperty
     *            the class property holding the aspect executor class
     * 
     * @return the aspect executor class
     */
    @SuppressWarnings("unchecked")
    private Class<? extends AspectExecutor> getExecutorClass(ClassProperty classProperty) {

        Class<?> executorClass = PropertyLoader.loadProperty(classProperty);

        try {
            if (AspectExecutor.class.isAssignableFrom(executorClass)) {
                return (Class<? extends AspectExecutor>) executorClass;
            }

        } catch (Exception e) {
            logger.error(e, "Cannot create class for AspectExecutor for classname=[" + executorClass.getName() + "]");
        }
        return NoOpAspectExecutor.class;
    }

    /**
     * Instantiate the aspect class.
     * 
     * @param classProperty
     *            the class property holding the aspect class
     * 
     * @return the aspect class
     */
    @SuppressWarnings("unchecked")
    private Class<? extends Aspect> getAspectClass(ClassProperty classProperty) {

        Class<?> aspectClass = PropertyLoader.loadProperty(classProperty);

        try {
            if (Aspect.class.isAssignableFrom(aspectClass)) {
                return (Class<? extends Aspect>) aspectClass;
            }
        } catch (Exception e) {
            logger.error(e, "Cannot create class for Aspect for classname=[" + aspectClass.getName() + "]");
        }
        return NoOpAspect.class;
    }

    /**
     * Getter for the no-operation aspect.
     * 
     * @param type
     *            the advice type
     * 
     * @return an aspect handler entry with an empty implementation
     */
    private static AspectHandlerEntry getNoOpAspect(AdviceType type) {
        return new AspectHandlerEntry(NoOpAspect.class, NoOpAspectExecutor.class, type, null);
    }

}
