package com.edenspring.framework.kafka.converter;

import brave.Tracer;
import brave.propagation.TraceContext;
import com.edenspring.framework.kafka.KafkaEntity;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.Random;

/**
 * KafkaEntityMessageConverter
 *
 * @author eden
 * @since 2019-03-22 19:10
 */
@AllArgsConstructor
public class KafkaEntityMessageConverter extends StringJsonMessageConverter {

    private final Random spanIdProvider = new SecureRandom();

    private final Tracer tracer;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    protected Object extractAndConvertValue(ConsumerRecord<?, ?> record, Type type) {
        Object value = record.value();
        KafkaEntity entity = objectMapper.readValue(value.toString(), KafkaEntity.class);
        trace(entity.getCurrentSpan());
        JavaType javaType = TypeFactory.defaultInstance().constructType(type);
        return objectMapper.readValue(entity.getContent(), javaType);
    }

    private void trace(KafkaEntity.CurrentSpan parent) {
        tracer.joinSpan(TraceContext.newBuilder()
                                    .parentId(parent.getSpanId())
                                    .traceId(parent.getTraceId())
                                    .spanId(spanIdProvider.nextLong())
                                    .build());
    }


}
