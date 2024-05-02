package in.vaultmoney.carservice.controller;

import in.vaultmoney.carservice.dto.AppointmentReq;
import in.vaultmoney.carservice.entity.Appointment;
import in.vaultmoney.carservice.service.AppointmentService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<Appointment> book(@RequestBody AppointmentReq appointmentReq) throws BadRequestException {
        Appointment newAppointment = appointmentService.create(appointmentReq);
        return new ResponseEntity<>(newAppointment, HttpStatus.OK);
    }

    @PutMapping("/reschedule/{appointmentId}")
    public ResponseEntity<Appointment> reschedule(@PathVariable int appointmentId, @RequestBody AppointmentReq appointmentReq) {
        Appointment updatedAppointment = appointmentService.update(appointmentId, appointmentReq);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @DeleteMapping("/cancel/{appointmentId}")
    public ResponseEntity<Object> cancel(@PathVariable int appointmentId) {
        appointmentService.delete(appointmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
