package in.vaultmoney.carservice.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Column(columnDefinition = "boolean default true")
    private Boolean isActive = true;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Date updatedAt;
}
