/*
 * orpSDK Copyright (C) 2014 Sebastian Werner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU LesserGeneral Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.dailab.orp.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

/**
 * This Class can be used to record log messages. All messages are stored
 * into a GZiped file to save disk space.
 *
 * Messages a stored in a buffer and are written only periodically to disk.
 * To reduce overhead there can only be one MessageRecorder per Application Singleton)
 */
public class MessageRecorder implements Runnable {
    private final static Logger logger = LogManager.getLogger(MessageRecorder.class);

    private static MessageRecorder instance;

    public static MessageRecorder getInstance(String file) throws IOException {
        if(instance == null){
            instance = new MessageRecorder(file);
        }
        return instance;
    }


    StringBuffer buffer = new StringBuffer();

    final ScheduledExecutorService writeThread;

    final Path logFile;

    private MessageRecorder(String file) throws IOException {
        logFile = Paths.get(file);
        if(!Files.exists(logFile)){
            Files.createFile(logFile);
        }
        writeThread = Executors.newSingleThreadScheduledExecutor();
        writeThread.scheduleAtFixedRate(this,2,10,TimeUnit.MINUTES);
    }


    public void write(String msg){
        buffer.append(msg).append("\n");
    }

    @Override
    public void run() {
        OutputStream output = null;
        try {
            if(!Files.exists(logFile)){
                Files.createFile(logFile);
            }
            output = Files.newOutputStream(logFile,StandardOpenOption.APPEND);
            Writer writer = new OutputStreamWriter(new GZIPOutputStream(output),"UTF-8");

            synchronized (buffer){
                writer.write(buffer.toString());
                buffer = new StringBuffer();
            }

        } catch (Exception e) {
            buffer = new StringBuffer();
        } finally {
            try {
                if(output != null)
                    output.close();
            } catch (IOException e) {
                logger.trace(e);
            }
        }
    }
}
