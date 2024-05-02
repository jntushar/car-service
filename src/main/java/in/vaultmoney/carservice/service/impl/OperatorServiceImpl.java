package in.vaultmoney.carservice.service.impl;

import in.vaultmoney.carservice.entity.Appointment;
import in.vaultmoney.carservice.entity.Operator;
import in.vaultmoney.carservice.exception.BadRequestError;
import in.vaultmoney.carservice.repository.OperatorRepository;
import in.vaultmoney.carservice.service.OperatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    public Set<Appointment> getBookedAppointmentsByOperator(int operatorId) {
        log.info("fetching list of appointments for operator: {}", operatorId);
        Operator operator = getOperator(operatorId);
        return operator.getAppointments();
    }

    @Override
    public Object getOpenSlotsByOperator(int operatorId) {
        log.info("fetching list of available slots for operator: {}", operatorId);
        Operator operator = getOperator(operatorId);
        List<Appointment> appointments = new ArrayList<>(operator.getAppointments().stream().toList());
        List<String> openSlots = new ArrayList<>();
        appointments.sort(Comparator.comparingInt(Appointment::getStartTime));

        // Add the first open slot from the beginning of the day
        if (appointments.get(0).getStartTime() > 0) {
            openSlots.add("0-" + appointments.get(0).getStartTime());
        }
        for (int i = 0; i < appointments.size() - 1; i++) {
            int start = appointments.get(i).getEndTime();
            int end = appointments.get(i + 1).getStartTime();
            if (start < end) {
                openSlots.add(start + "-" + end);
            }
        }
        // Add the last open slot till the end of the day
        if (appointments.get(appointments.size() - 1).getEndTime() < 23) {
            openSlots.add(appointments.get(appointments.size() - 1).getEndTime() + "-23");
        }
        return openSlots;
    }

    private Operator getOperator(int operatorId) {
        Optional<Operator> operator = operatorRepository.findById(operatorId);
        if (operator.isEmpty()) {
            throw new BadRequestError("Operator does not exist: " + operatorId);
        }
        return operator.get();
    }
}
