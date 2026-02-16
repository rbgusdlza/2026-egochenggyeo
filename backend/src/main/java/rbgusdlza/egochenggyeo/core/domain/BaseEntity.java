package rbgusdlza.egochenggyeo.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @Enumerated(EnumType.STRING)
    private EntityStatus status = EntityStatus.ACTIVE;

    public void active() {
        status = EntityStatus.ACTIVE;
    }

    public void delete() {
        status = EntityStatus.DELETE;
    }

    public boolean isActive() {
        return status == EntityStatus.ACTIVE;
    }

    public boolean isDeleted() {
        return status == EntityStatus.DELETE;
    }
}
