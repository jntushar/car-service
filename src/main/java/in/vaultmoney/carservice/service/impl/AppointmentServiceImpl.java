package in.vaultmoney.carservice.service.impl;

import in.vaultmoney.carservice.dto.AppointmentReq;
import in.vaultmoney.carservice.entity.Appointment;
import in.vaultmoney.carservice.entity.Operator;
import in.vaultmoney.carservice.exception.BadRequestError;
import in.vaultmoney.carservice.repository.AppointmentRepository;
import in.vaultmoney.carservice.repository.OperatorRepository;
import in.vaultmoney.carservice.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    public Appointment create(AppointmentReq appointmentReq) {
        log.info("Adding new appointment for operator: {}", appointmentReq.getOperatorId());
        Optional<Operator> operator = operatorRepository.findById(appointmentReq.getOperatorId());
        validateReq(appointmentReq, operator);
        Appointment appointment = new Appointment();
        appointment.setCustomerId(appointmentReq.getCustomerId());
        appointment.setStartTime(appointmentReq.getStartTime());
        appointment.setEndTime(appointmentReq.getEndTime());
        appointment.setDescription(appointmentReq.getDescription());
        appointment.setOperator(operator.get());
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(int appointmentId, AppointmentReq appointmentReq) {
        log.info("Rescheduling appointment with id: {}", appointmentId);
        Optional<Operator> operator = operatorRepository.findById(appointmentReq.getOperatorId());
        validateReq(appointmentReq, operator);
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);
        if(appointmentOptional.isEmpty()) {
            throw new BadRequestError("Appointment does not exist");
        }
        Appointment appointment = appointmentOptional.get();
        appointment.setStartTime(appointmentReq.getStartTime());
        appointment.setEndTime(appointmentReq.getEndTime());
        return appointmentRepository.save(appointment);
    }

    @Override
    public void delete(int appointmentId) {
        log.info("Deleting appointment with id: {} ", appointmentId);
        Optional<Appointment> vendor = appointmentRepository.findById(appointmentId);
        if (vendor.isEmpty()) {
            return;
        }
        appointmentRepository.deleteById(appointmentId);
    }

    private void validateReq(AppointmentReq appointmentReq, Optional<Operator> operator) {
        if(isInvalidTime(appointmentReq.getStartTime(), appointmentReq.getEndTime())) {
            throw new BadRequestError("Invalid start/end time." +
                    " Time should be in 24hrs format and duration(endTime - startTime) cannot be more than 1");
        }
        if (operator.isEmpty()) {
            throw new BadRequestError("Operator does not exist: " + appointmentReq.getOperatorId());
        }
        if(isBookedSlot(operator.get().getAppointments(), appointmentReq.getStartTime())) {
            throw new BadRequestError("Slot is not available");
        }
    }

    private boolean isInvalidTime(int startTime, int endTime) {
        return startTime < 0 || endTime < 0 || endTime > 23 || startTime >= endTime || endTime - startTime != 1;
    }

    private boolean isBookedSlot(Set<Appointment> appointments, int startTime) {
        Set<Integer> startTimeList = appointments.stream()
                .map(Appointment::getStartTime)
                .collect(Collectors.toSet());
        return startTimeList.contains(startTime);
    }
}
