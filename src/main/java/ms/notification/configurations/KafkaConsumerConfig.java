package ms.notification.configurations;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.notification.dtos.EmailDTO;
import ms.notification.dtos.Message;
import ms.notification.dtos.NewCourseDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS;

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;

    private <K, V> ConsumerFactory<K, V> generateFactory(
        Deserializer<K> keyDeserializer, Deserializer<V> valueDeserializer) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "ms-email-service");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, keyDeserializer, valueDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message<EmailDTO>>
    kafkaListenerEmail() {

        ConcurrentKafkaListenerContainerFactory<String, Message<EmailDTO>> factory =
            new ConcurrentKafkaListenerContainerFactory<>();

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Message.class, EmailDTO.class);

        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(javaType, false));

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message<NewCourseDTO>>
    kafkaListenerNewCourse() {

        ConcurrentKafkaListenerContainerFactory<String, Message<NewCourseDTO>> factory =
            new ConcurrentKafkaListenerContainerFactory<>();

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Message.class, NewCourseDTO.class);

        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(javaType, false));

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}
