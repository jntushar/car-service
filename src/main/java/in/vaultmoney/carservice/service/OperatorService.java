package in.vaultmoney.carservice.service;

import in.vaultmoney.carservice.entity.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface OperatorService {

    Set<Appointment> getBookedAppointmentsByOperator(int operatorId);

    Object getOpenSlotsByOperator(int operatorId);
}
