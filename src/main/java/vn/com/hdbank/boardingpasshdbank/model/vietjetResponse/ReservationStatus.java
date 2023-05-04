package vn.com.hdbank.boardingpasshdbank.model.vietjetResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationStatus {
    private boolean confirmed;
    private boolean waitlist;
    private boolean standby;
    private boolean cancelled;
    private boolean noshow;
    private boolean open;
    private boolean pending;
    private boolean finalized;
    private boolean external;
}
