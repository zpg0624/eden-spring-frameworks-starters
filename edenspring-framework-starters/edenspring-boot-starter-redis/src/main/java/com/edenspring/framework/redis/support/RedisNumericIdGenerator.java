package com.edenspring.framework.redis.support;

import com.edenspring.framework.redis.RedisOps;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class RedisNumericIdGenerator {

    private static final DateTimeFormatter ID_PREFIX_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private final RedisOps redisOps;

    private final String key;


    public String generateId() {
        return Option.of(redisOps.incr(key))
                .peek(id -> Option.of(id)
                        .filter(thisId -> thisId > 9900L)
                        .peek(__ -> redisOps.del(key)))
                .map(id -> String.format("%s%04d", LocalDateTime.now().format(ID_PREFIX_FORMAT), id))
                .getOrElseThrow(() -> new IllegalStateException("Generate id error!"));
    }


}
