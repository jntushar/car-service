package in.vaultmoney.carservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Appointment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true)
    private int appointmentId;

    private String customerId;
    private int startTime;
    private int endTime;
    private String description;

    @ManyToOne(fetch= FetchType.LAZY, targetEntity = Operator.class)
    @JoinColumn(name = "operatorId")
    @JsonIgnore
    private Operator operator;



}
