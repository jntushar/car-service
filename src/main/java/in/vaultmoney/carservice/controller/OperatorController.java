package in.vaultmoney.carservice.controller;

import in.vaultmoney.carservice.entity.Appointment;
import in.vaultmoney.carservice.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @GetMapping("/{operatorId}/booked")
    public ResponseEntity<Set<Appointment>> appointments(@PathVariable int operatorId) {
        Set<Appointment> appointmentList = operatorService.getBookedAppointmentsByOperator(operatorId);
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    @GetMapping("/{operatorId}/open-slots")
    public ResponseEntity<Object> availableSlots(@PathVariable int operatorId) {
        Object availableSlots = operatorService.getOpenSlotsByOperator(operatorId);
        return new ResponseEntity<>(availableSlots, HttpStatus.OK);
    }
}
