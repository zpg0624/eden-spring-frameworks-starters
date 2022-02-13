package com.edenspring.framework.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author eden
 * @since 2019-03-19 17:45
 */

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class KafkaEntity implements Serializable {


    private CurrentSpan currentSpan;

    private String content;

    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @Data
    public static class CurrentSpan implements Serializable {

        private long spanId;

        private long traceId;

    }

}
