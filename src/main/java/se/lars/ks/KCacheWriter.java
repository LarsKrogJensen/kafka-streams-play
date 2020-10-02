package se.lars.ks;

import io.kcache.Cache;
import io.kcache.CacheUpdateHandler;
import io.kcache.KafkaCache;
import io.kcache.KafkaCacheConfig;
import org.apache.kafka.common.serialization.Serdes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class KCacheWriter {
    public static void main(String[] args) throws IOException {
        var cache = setup(null);

        var reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("enter kv:");
            var line = reader.readLine();
            var kv = line.split(":");
            cache.put(kv[0], kv[1]);
        }
    }

    public static KafkaCache<String, String> setup(CacheUpdateHandler<String, String> listener) {
        Properties props = new Properties();
        props.put(KafkaCacheConfig.KAFKACACHE_BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(KafkaCacheConfig.KAFKACACHE_TOPIC_CONFIG, "my_test");
        KafkaCache<String, String> cache = new KafkaCache<>(
                new KafkaCacheConfig(props),
                Serdes.String(),  // for serializing/deserializing keys
                Serdes.String(),   // for serializing/deserializing values
                listener, null
        );
        cache.init();
//        cache.sync();

        return cache;
    }
}
