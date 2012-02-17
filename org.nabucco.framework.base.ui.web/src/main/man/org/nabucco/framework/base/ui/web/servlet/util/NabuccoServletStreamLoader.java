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
package org.nabucco.framework.base.ui.web.servlet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;

/**
 * NabuccoServletStreamLoader
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoServletStreamLoader {

    /** The Servlet Input Stream */
    private ServletInputStream inputStream;

    /** AJAX Requests are always UTF-8 */
    private static final Charset CHARSET = Charset.forName("UTF-8");

    /** The Logger. */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NabuccoServletRequest.class);

    /**
     * Creates a new {@link NabuccoServletStreamLoader} instance.
     * 
     * @param inputStream
     *            the servlet input stream to load
     * 
     * @throws ClientException
     *             when no servlet stream is provided
     */
    public NabuccoServletStreamLoader(HttpServletRequest request) throws ClientException {
        if (request == null) {
            throw new IllegalArgumentException("Cannot read from servlet request [null].");
        }
        try {
            this.inputStream = request.getInputStream();
        } catch (IOException ioe) {
            logger.error(ioe, "Cannot load HTTP Request Stream.");
            throw new ClientException("Cannot load HTTP Request Stream.", ioe);
        }
    }

    /**
     * Load the servlet input stream to a string.
     * 
     * @return the loaded string
     * 
     * @throws ClientException
     *             when the servlet stream cannot be loaded
     */
    public String loadFromStream() throws ClientException {
        try {
            return this.loadFromStream(this.inputStream);
        } catch (IOException ioe) {
            logger.error(ioe, "Error converting servlet request data to string.");
            throw new ClientException("Error converting servlet request data to string.", ioe);
        } catch (Exception e) {
            logger.error(e, "Error converting servlet request data to string.");
            throw new ClientException("Error converting servlet request data to string.", e);
        }
    }

    /**
     * Convert an input stream to a string.
     * 
     * @param is
     *            the input stream
     * 
     * @return the string
     * 
     * @throws IOException
     *             when the input stream cannot be read
     */
    private String loadFromStream(InputStream is) throws IOException {

        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];

            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, CHARSET));

                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        }

        return null;
    }
}
