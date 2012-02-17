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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * DeserializationResourceContainer
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DeserializationResourceContainer implements Closeable {

    private static String DELIMITER = "/|\\.";

    private ZipInputStream stream;

    /**
     * Creates a new {@link DeserializationResourceContainer} instance.
     * 
     * @param data
     *            the resource archive as byte array
     */
    public DeserializationResourceContainer(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        this.stream = new ZipInputStream(in);
    }

    /**
     * Decompress a resource from the archive.
     * 
     * @param name
     *            the resource name
     * 
     * @return the decompressed resource
     * 
     * @throws IOException
     */
    public synchronized Resource getResources(String name) throws IOException {

        ZipEntry entry = null;
        while ((entry = this.stream.getNextEntry()) != null) {

            if (!(entry.getName().equals(name))) {
                continue;
            }

            String[] path = name.split("/");

            byte[] data = readEntry();
            this.stream.closeEntry();

            return new Resource(path[2], data, ResourceType.get(path[1]));
        }

        return null;
    }

    /**
     * Decompress a resource from the archive.
     * 
     * @return the decompressed resources
     * 
     * @throws IOException
     *             when the resource cannot be read
     */
    public synchronized Map<String, Resource> getAllResources() throws IOException {

        Map<String, Resource> resourceMap = new HashMap<String, Resource>();

        ZipEntry entry = null;
        while ((entry = this.stream.getNextEntry()) != null) {

            String name = entry.getName();
            String[] path = name.split(DELIMITER);
            if (path.length < 3) {
                continue;
            }

            byte[] data = readEntry();
            this.stream.closeEntry();

            resourceMap.put(name, new Resource(path[2], data, ResourceType.get(path[1])));
        }

        return resourceMap;
    }

    /**
     * Read the content of a ZIP entry.
     * 
     * @return the zip entry content
     * 
     * @throws IOException
     */
    private byte[] readEntry() throws IOException {
        ByteArrayOutputStream streamBuilder = new ByteArrayOutputStream();
        int bytesRead;
        byte[] tempBuffer = new byte[8192 * 2];
        while ((bytesRead = this.stream.read(tempBuffer)) != -1) {
            streamBuilder.write(tempBuffer, 0, bytesRead);
        }

        return streamBuilder.toByteArray();
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }
}
