package vn.com.hdbank.boardingpasshdbank.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseInfo<T>{
    private String responseId;
    private String code;
    private String message;
    private T data;
}
