package se.lars.ks;

import io.kcache.KafkaCache;
import io.kcache.KafkaCacheConfig;
import org.apache.kafka.common.serialization.Serdes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import static se.lars.ks.KCacheWriter.setup;

public class KCacheReader {
    public static void main(String[] args) throws IOException {
        var cache = setup((key, value, oldValue, tp, offset, timestamp) -> {
            System.out.println("Key: " + key + ", value: " + value + ", oldValue: " + oldValue);
        });

        cache.forEach((k,v) -> System.out.println("Key: " + k + ", value: " + v));

        var reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println("enter key:");
            var key = reader.readLine();
            System.out.println("Key: " + key + ", value: " + cache.get(key));
        }
    }
}
