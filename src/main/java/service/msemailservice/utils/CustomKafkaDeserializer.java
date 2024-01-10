package service.msemailservice.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.context.annotation.Configuration;
import service.msemailservice.dtos.response.MessageDTO;

import java.nio.charset.StandardCharsets;
import java.util.Map;


@Slf4j
@Configuration
public class CustomKafkaDeserializer implements Deserializer<MessageDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public MessageDTO deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                log.warn("Null received at deserializing");
                return null;
            }
            log.warn("Deserializing...");
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), MessageDTO.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDTO");
        }
    }

    @Override
    public void close() {
    }
}
