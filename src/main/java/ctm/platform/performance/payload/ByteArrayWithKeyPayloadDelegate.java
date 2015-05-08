/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctm.platform.performance.payload;

import java.util.Properties;
import java.util.UUID;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author fabiool
 */
public class ByteArrayWithKeyPayloadDelegate extends ByteArrayPayloadDelegate {

    public ByteArrayWithKeyPayloadDelegate(Properties props) {
        super(props);
    }

    @Override
    public ProducerRecord<byte[], byte[]> getRecord() {
        if (null == this.payload) {
            generatePayload();
        }
        return new ProducerRecord<>(getTopicName(), 
                UUID.randomUUID().toString().getBytes(), this.payload);
    }
}
