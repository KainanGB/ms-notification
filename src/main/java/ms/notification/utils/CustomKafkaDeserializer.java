package ms.notification.utils;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ms.notification.dtos.EmailDTO;
import ms.notification.dtos.Message;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Map;


@Slf4j
@Configuration
public class CustomKafkaDeserializer implements Deserializer<Message<EmailDTO>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Message<EmailDTO> deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                log.warn("Null received at deserializing");
                return null;
            }

            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Message.class, EmailDTO.class);

            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), javaType);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to Message");
        }
    }

    @Override
    public Message<EmailDTO> deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
    }
}
