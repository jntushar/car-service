package in.vaultmoney.carservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentReq {

    private String customerId;
    private int startTime;
    private int endTime;
    private String description;
    private int operatorId;
}
