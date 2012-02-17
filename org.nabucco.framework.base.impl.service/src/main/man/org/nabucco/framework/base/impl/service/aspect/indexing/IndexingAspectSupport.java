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
package org.nabucco.framework.base.impl.service.aspect.indexing;

import javax.naming.InitialContext;

import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextDocument;
import org.nabucco.framework.base.facade.datatype.search.index.IndexContext;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.message.search.IndexRequestMsg;
import org.nabucco.framework.base.facade.service.index.IndexService;
import org.nabucco.framework.base.facade.service.index.IndexServiceLocator;

/**
 * IndexingAspectSupport
 * <p/>
 * Support for index aspect implementations.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class IndexingAspectSupport {

    private ServiceMessageContext context;

    /**
     * Setter for the service context.
     * 
     * @param context
     *            the service context to set
     */
    void setServiceContext(ServiceMessageContext context) {
        this.context = context;
    }

    /**
     * Getter for the service context.
     * 
     * @return the service context
     */
    public ServiceMessageContext getServiceContext() {
        return this.context;
    }

    /**
     * Executes the fulltext indexing to the given index.
     * 
     * @param ctx
     *            the index context
     * @param doc
     *            the document to index
     * 
     * @throws Exception
     *             when the indexing fails
     */
    public void index(IndexContext ctx, FulltextDocument doc) throws Exception {

        IndexServiceLocator locator = new IndexServiceLocator();
        IndexService indexService = locator.getIndexService(new InitialContext());

        IndexRequestMsg msg = new IndexRequestMsg();
        ctx.setOwner(this.context.getOwner());

        msg.setContext(ctx);
        msg.setDocument(doc);

        ServiceRequest<IndexRequestMsg> request = new ServiceRequest<IndexRequestMsg>(this.context);
        request.setRequestMessage(msg);
        indexService.resolveExtension(request);
    }
}
