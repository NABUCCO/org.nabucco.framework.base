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
package org.nabucco.framework.base.facade.datatype.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * NabuccoServletStreamWriter
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class StreamUtil {

    /**
     * Copy the input stream into the output stream.
     * 
     * @param in
     *            the input stream to read
     * @param out
     *            the output stream to write into
     * 
     * @throws IOException
     *             when the stream cannot be copied
     */
    public static void copy(InputStream in, OutputStream out) throws IOException {
        ReadableByteChannel source = Channels.newChannel(in);
        WritableByteChannel destination = Channels.newChannel(out);

        copy(source, destination);
    }

    /**
     * Copy the source to target chanel vio java nio.
     * 
     * @param source
     *            the source channel
     * @param destination
     *            the destination channel
     * 
     * @throws IOException
     *             when the cannels cannot be copied
     */
    public static void copy(ReadableByteChannel source, WritableByteChannel destination) throws IOException {

        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (source.read(buffer) != -1) {
            buffer.flip();
            destination.write(buffer);
            buffer.compact();
        }

        buffer.flip();

        while (buffer.hasRemaining()) {
            destination.write(buffer);
        }
    }

}
