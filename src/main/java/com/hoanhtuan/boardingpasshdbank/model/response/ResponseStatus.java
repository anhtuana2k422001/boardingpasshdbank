package com.hoanhtuan.boardingpasshdbank.model.response;

import com.hoanhtuan.boardingpasshdbank.utils.Utils;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseStatus {
    @Builder.Default
    private String responseId = Utils.getRandomUUID();
    private String responseCode;
    private String responseMessage;
    @Builder.Default
    private String responseTime = Utils.getResponseTime();
    private Object data;
}
