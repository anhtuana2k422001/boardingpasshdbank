package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;
import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.entity.Prize;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfirmCustomerVietjet {
    private Customer customer;
    private List<Prize> prizes;
}
