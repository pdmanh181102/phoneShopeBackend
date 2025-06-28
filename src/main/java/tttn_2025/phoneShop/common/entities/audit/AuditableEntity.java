package tttn_2025.phoneShop.common.entities.audit;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AuditableEntity {
    @CreatedDate
    @Column(updatable = false, name = "create_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "update_at")
    private Instant updatedAt;

    @CreatedBy
    @Column(updatable = false, name = "create_by")
    private UUID createdBy;

    @LastModifiedBy
    @Column(name = "update_by")
    private UUID updatedBy;
}
