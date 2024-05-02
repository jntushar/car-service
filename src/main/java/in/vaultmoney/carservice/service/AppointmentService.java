package in.vaultmoney.carservice.service;

import in.vaultmoney.carservice.dto.AppointmentReq;
import in.vaultmoney.carservice.entity.Appointment;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public interface AppointmentService {

    Appointment create(AppointmentReq appointmentReq) throws BadRequestException;
    Appointment update(int appointmentId, AppointmentReq appointmentReq);
    void delete(int appointmentId);

}
