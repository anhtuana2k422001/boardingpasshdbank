package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;


import jakarta.validation.constraints.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import vn.com.hdbank.boardingpasshdbank.common.Validate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketConfirmRequest extends BaseRequest {
    private String ticketId;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @Min(value = 1, message = Validate.MESSAGE_REQUIRED_ID)
    private Integer customerId;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @NotEmpty (message = Validate.MESSAGE_NOT_EMPTY)
    private String fullNameCustomer;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @NotEmpty (message = Validate.MESSAGE_NOT_EMPTY)
    private String birthDateCustomer;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    private Boolean isCustomerVietJet;

    public String getFistNameCustomer(){
        String firstName = StringUtils.substringAfter(fullNameCustomer, StringUtils.SPACE);
        return StringUtils.trim(firstName);
    }

    public String getLastNameCustomer(){
        String lastName = StringUtils.substringBefore(fullNameCustomer,  StringUtils.SPACE);
        return StringUtils.trim(lastName);
    }
}