package com.hoanhtuan.boardingpasshdbank.model.response;

import com.hoanhtuan.boardingpasshdbank.utils.Utils;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessage {
    @Builder.Default
    private String responseId = Utils.getRandomUUID();
    private int responseCode;
    @Builder.Default
    private String responseTime = Utils.getResponseTime();
    private String responseMessage;
}
