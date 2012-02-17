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
 * MessageSearchTreeDumper
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class MessageSearchTreeDumper implements MessageSearchTreeVisitor {

    private int depth = -1;

    private static String[] spaces;

    static {
        spaces = new String[255];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 255; i++) {
            spaces[i] = sb.toString();
            sb.append("\t");
        }
    }

    @Override
    public void visit(MessageSearchComponent component) {

        if (component instanceof MessageSearchComposite) {
            ++depth;
            visit((MessageSearchComposite) component);
            --depth;
        } else if (component instanceof MessageSearchElement) {
            ++depth;
            visit((MessageSearchElement) component);
            --depth;
        }
    }

    @Override
    public void visit(MessageSearchElement component) {
        System.out.println(spaces[depth] + component.getId() + " (Message)");
    }

    @Override
    public void visit(MessageSearchComposite c) {

        if (c instanceof MessageSearchExceptionNode) {
            visit((MessageSearchExceptionNode) c);
        } else if (c instanceof MessageSearchLanguageNode) {
            visit((MessageSearchLanguageNode) c);
        } else if (c instanceof MessageSearchRootNode) {
            visit((MessageSearchRootNode) c);
        } else if (c instanceof MessageSearchServiceNode) {
            visit((MessageSearchServiceNode) c);
        } else if (c instanceof ServiceOperationElement) {
            visit((ServiceOperationElement) c);
        } else if (c instanceof MessageSearchPackageNode) {
            visit((MessageSearchPackageNode) c);
        } else if (c instanceof MessageSearchServiceOperationNode) {
            visit((MessageSearchServiceOperationNode) c);
        } else {
            // System.out.println("? " + c.getClass().getName() + " id " + c.getId());
        }

        visit(c.getComponents());
    }

    public void visit(MessageSearchComponent[] components) {
        for (MessageSearchComponent c : components) {
            visit(c);
        }
    }

    @Override
    public void visit(MessageSearchServiceOperationNode component) {
        System.out.println(spaces[depth] + component.getId() + " (OperationNode)");
    }

    @Override
    public void visit(MessageSearchExceptionNode component) {
        System.out.println(spaces[depth] + component.getId() + " (ExceptionNode)");
    }

    @Override
    public void visit(MessageSearchLanguageNode component) {
        System.out.println(spaces[depth] + component.getId() + " (LanguageNode)");
    }

    @Override
    public void visit(MessageSearchRootNode component) {
        System.out.println(spaces[depth] + component.getId() + " (RootNode)");
    }

    @Override
    public void visit(MessageSearchPackageNode component) {
        System.out.println(spaces[depth] + component.getId() + " (PackageNode)");
    }

    @Override
    public void visit(MessageSearchServiceNode component) {
        System.out.println(spaces[depth] + component.getId() + " (ServiceNode)");
    }

    @Override
    public void visit(ServiceOperationElement component) {
        System.out.println(spaces[depth] + component.getId() + " (OperationNode)");
    }

}
