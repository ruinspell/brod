/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctm.platform.performance.payload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author fabiool
 */
public class ByteArrayWithKeyPayloadDelegate extends ByteArrayPayloadDelegate {

    private static final Logger LOG
            = Logger.getLogger(ByteArrayWithKeyPayloadDelegate.class.getName());

    private static final String DATE_FORMAT = "yyyyMMdd'T'HHmmssZ";
    private static final String FILE_PREFIX = "posted_keys_to_";
    private static final String PATH_PROP = "filePath";
    private static final String FILE_SEPARATOR_PROP = "file.separator";

    private static String timestamp() {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }

    private static String buildFileName(String path, String topicName) {
        return (new StringBuilder())
                .append(path)
                .append(System.getProperty(FILE_SEPARATOR_PROP))
                .append(FILE_PREFIX)
                .append(topicName)
                .append("_on_")
                .append(timestamp())
                .append(".txt").toString();
    }

    private static Writer getWriter(String path, String topicName)
            throws FileNotFoundException {
        return new PrintWriter(new File(buildFileName(path, topicName)));
    }

    private Writer writer = null;

    public ByteArrayWithKeyPayloadDelegate(Properties props) {
        super(props);
        if (props.containsKey(PATH_PROP)) {
            try {
                this.writer = getWriter(
                        props.remove(PATH_PROP).toString(), getTopicName());
            } catch (FileNotFoundException ex) {
                LOG.log(Level.WARNING, "Output file could not be created!", ex);
            }
        } else { 
            LOG.log(Level.INFO, (new StringBuilder())
                    .append("Property \"")
                    .append(PATH_PROP)
                    .append("\" was not found. ")
                    .append("Output file will not be created!")
                    .toString());
        }
    }

    @Override
    public ProducerRecord<byte[], byte[]> getRecord() {
        if (null == this.payload) {
            generatePayload();
        }

        String uuid = UUID.randomUUID().toString();

        try {
            this.writer.write(uuid + "\n");
        } catch (IOException ex) {
            LOG.log(Level.WARNING, "Output file could not be written to!", ex);
        }

        return new ProducerRecord<>(getTopicName(),
                uuid.getBytes(), this.payload);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (null != this.writer) {
            try {
                this.writer.flush();
                this.writer.close();
            } catch (IOException ex) {
                LOG.log(Level.WARNING, "Output file could not be closed!", ex);
            }
        }
    }
}
