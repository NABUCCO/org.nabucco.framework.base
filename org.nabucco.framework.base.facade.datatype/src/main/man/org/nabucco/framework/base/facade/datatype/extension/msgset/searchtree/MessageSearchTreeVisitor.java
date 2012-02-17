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
package org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree;

/**
 * MessageSearchTreeVisitor
 * <p/>
 * Visitor to traverse over the message search tree.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public interface MessageSearchTreeVisitor {

    /**
     * Visit the {@link MessageSearchComponent} instance.
     * 
     * @param component
     *            the component to visit
     */
    void visit(MessageSearchComponent component);

    /**
     * Visit the {@link MessageSearchElement} instance.
     * 
     * @param component
     *            the component to visit
     */
    void visit(MessageSearchElement component);

    /**
     * Visit the {@link MessageSearchComposite} instance.
     * 
     * @param component
     *            the component to visit
     */
    void visit(MessageSearchComposite component);

    /**
     * Visit the {@link MessageSearchExceptionNode} instance.
     * 
     * @param component
     *            the component to visit
     */
    void visit(MessageSearchExceptionNode component);

    /**
     * Visit the {@link MessageSearchLanguageNode} instance.
     * 
     * @param component
     *            the component to visit
     */
    void visit(MessageSearchLanguageNode component);

    /**
     * Visit the {@link MessageSearchRootNode} instance.
     * 
     * @param component
     *            the component to visit
     */
    void visit(MessageSearchRootNode component);

    /**
     * Visit the {@link MessageSearchServiceNode} instance.
     * 
     * @param component
     *            the component to visit
     */
    void visit(MessageSearchServiceNode component);

    /**
     * Visit the {@link ServiceOperationElement} instance.
     * 
     * @param component
     *            the component to visit
     */
    void visit(ServiceOperationElement component);

    /**
     * Visit the {@link MessageSearchPackageNode} instance.
     * 
     * @param component
     *            the component to visit
     */
    void visit(MessageSearchPackageNode component);

    /**
     * Visit the {@link MessageSearchServiceOperationNode} instance.
     * 
     * @param component
     *            the component to visit
     */
    void visit(MessageSearchServiceOperationNode component);

}
