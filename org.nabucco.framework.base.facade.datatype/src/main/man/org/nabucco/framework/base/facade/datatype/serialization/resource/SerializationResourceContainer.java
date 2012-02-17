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
package org.nabucco.framework.base.facade.datatype.serialization.resource;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.nabucco.framework.base.facade.datatype.serialization.SerializationConstants;

/**
 * SerializationResourceContainer
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SerializationResourceContainer implements Closeable, Flushable {

    private ByteArrayOutputStream out;

    private ZipOutputStream stream;

    private Set<String> entryNames;

    /**
     * Creates a new {@link SerializationResourceContainer} instance.
     */
    public SerializationResourceContainer() {
        this.init();
    }

    /**
     * Initialize the output streams.
     */
    private void init() {
        this.out = new ByteArrayOutputStream();
        this.entryNames = new HashSet<String>();
        this.stream = new ZipOutputStream(this.out);
        this.stream.setLevel(ZipOutputStream.DEFLATED);
        this.stream.setComment(SerializationConstants.RESOURCE_COMMENT);
    }

    /**
     * Add a resource to the ZIP output.
     * 
     * @param resource
     *            the resource to add
     * 
     * @throws IOException
     *             when the resource cannot be added to the archive
     */
    public synchronized void addResource(Resource resource) throws IOException {
        if (entryNames.add(resource.getPath())) {
            ZipEntry entry = new ZipEntry(resource.getPath());
            entry.setComment(resource.getComment());
            this.stream.putNextEntry(entry);
            this.stream.write(resource.getContent());
            this.stream.closeEntry();
        }
    }

    @Override
    public void flush() throws IOException {
        this.stream.flush();
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }

    /**
     * Getter for the serialized resource container.
     * 
     * @return the resource container in a newly created byte array
     */
    public synchronized byte[] toByteArray() {
        return this.out.toByteArray();
    }

}
