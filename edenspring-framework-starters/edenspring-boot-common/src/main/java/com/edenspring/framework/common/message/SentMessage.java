package com.edenspring.framework.common.message;

import lombok.*;

import java.io.Serializable;

/**
 * 通用消息格式实体
 *
 * @author eden
 * @since 2019-03-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
public class SentMessage implements Serializable {

    /**
     * 接受者
     */
    @NonNull
    private String to;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    @NonNull
    private String content;

    /**
     * 失败时是否重新发送, 默认失败不重发
     */
    private boolean resentOnFailure;


    public static SentMessage of(String to, String title, String content) {
        return SentMessage.of(to, title, content, false);
    }


}
