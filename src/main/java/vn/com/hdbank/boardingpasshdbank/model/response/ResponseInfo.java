package vn.com.hdbank.boardingpasshdbank.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

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
    private Map<String, String> validate;
}
