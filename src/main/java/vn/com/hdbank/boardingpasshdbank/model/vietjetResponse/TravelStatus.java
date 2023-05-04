package vn.com.hdbank.boardingpasshdbank.model.vietjetResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelStatus {
    private boolean notCheckedIn;
    private boolean checkedIn;
    private boolean boarded;
    private boolean noshow;
}
