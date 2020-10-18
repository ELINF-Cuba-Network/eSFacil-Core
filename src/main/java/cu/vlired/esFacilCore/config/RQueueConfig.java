package cu.vlired.esFacilCore.config;

import com.github.sonus21.rqueue.config.SimpleRqueueListenerContainerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration()
public class RQueueConfig {

    @Value("${app.redis.url}")
    private String url;

    @Value("${app.redis.port}")
    private int port;

    @Value("${app.redis.db}")
    private int db;

    @Bean
    public SimpleRqueueListenerContainerFactory simpleRqueueListenerContainerFactory(){
        SimpleRqueueListenerContainerFactory factory = new SimpleRqueueListenerContainerFactory();
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();

        redisConfiguration.setHostName(url);
        redisConfiguration.setPort(port);
        redisConfiguration.setDatabase(db);

        // Create lettuce connection factory
        LettuceConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisConfiguration);
        redisConnectionFactory.afterPropertiesSet();
        factory.setRedisConnectionFactory(redisConnectionFactory);
        return factory;
    }
}
