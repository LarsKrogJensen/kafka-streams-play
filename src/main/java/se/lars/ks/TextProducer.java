package se.lars.ks;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class TextProducer {
    public static void main(String[] args) throws InterruptedException, IOException {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
//        props.put("bootstrap.servers", "test14-v2.dev.kambi.com:9092");

        props.put("acks", "1");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Enter words:");
            var words = br.readLine();
            producer.send(new ProducerRecord<>("streams-plaintext-input", "aKey", words));
        }

    }
}
