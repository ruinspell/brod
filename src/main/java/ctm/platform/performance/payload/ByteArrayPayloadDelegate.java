package ctm.platform.performance.payload;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerRecord;

import ctm.platform.performance.PayloadDelegate;

public class ByteArrayPayloadDelegate extends AbstractPayloadDelegate implements PayloadDelegate {

    protected final String TOPIC_NAME = "topicName";
    protected final String RECORD_SIZE = "recordSize";

    private final String topicName;
    private final int recordSize;

    public String getTopicName() {
        return topicName;
    }

    public int getRecordSize() {
        return recordSize;
    }

    /**
     *
     * @param props
     */
    public ByteArrayPayloadDelegate(final Properties props) {
        super(props);
        if (!this.props.containsKey(TOPIC_NAME)) {
            throw new IllegalArgumentException();
        }

        this.topicName = (String) this.props.remove(TOPIC_NAME);

        if (!this.props.containsKey(RECORD_SIZE)) {
            throw new IllegalArgumentException();
        }

        this.recordSize
                = Integer.parseInt((String) this.props.remove(RECORD_SIZE));
    }

    /**
     *
     */
    @Override
    protected void generatePayload() {
        this.payload = new byte[this.recordSize];
        Arrays.fill(this.payload, (byte) 1);
    }

    /**
     *
     */
    @Override
    public ProducerRecord<byte[], byte[]> getRecord() {
        if (null == this.payload) {
            generatePayload();
        }
        return new ProducerRecord<>(topicName, this.payload);
    }
}
