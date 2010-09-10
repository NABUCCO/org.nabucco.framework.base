/*
 * Copyright 2010 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.message.visitor;

import org.nabucco.framework.base.facade.message.ServiceMessage;

import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.Visitable;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * ServiceMessageVisitor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ServiceMessageVisitor extends DatatypeVisitor implements Visitor {

    @Override
    public void visit(Visitable visitable) throws VisitorException {
        if (visitable instanceof ServiceMessage) {
            this.visit((ServiceMessage) visitable);
        }
        super.visit(visitable);
    }

    /**
     * Visit a {@link ServiceMessage} instance.
     * 
     * @param message
     *            the service message to visit
     * 
     * @throws VisitorException
     */
    public void visit(ServiceMessage message) throws VisitorException {
        super.visitChildren(message);
    }

}
