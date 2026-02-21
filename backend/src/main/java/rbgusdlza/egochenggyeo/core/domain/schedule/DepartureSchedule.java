package rbgusdlza.egochenggyeo.core.domain.schedule;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.egochenggyeo.core.domain.BaseEntity;
import rbgusdlza.egochenggyeo.core.domain.member.Member;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "departure_schedule",
        indexes = {
                @Index(name = "idx_schedule_member_id", columnList = "member_id"),
                @Index(name = "idx_schedule_departure_at", columnList = "departure_at")
        }
)
public class DepartureSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "departure_at", nullable = false)
    private LocalDateTime departureAt;

    @Column(name = "timezone", nullable = false)
    private String timezone;

    @Column(name = "label")
    private String label;

    @Builder(access = AccessLevel.PRIVATE)
    private DepartureSchedule(Member member, LocalDateTime departureAt, String timezone, String label) {
        this.member = member;
        this.departureAt = departureAt;
        this.timezone = timezone;
        this.label = label;
    }

    public static DepartureSchedule of(Member member, LocalDateTime departureAt, String timezone) {
        return DepartureSchedule.builder()
                .member(member)
                .departureAt(departureAt)
                .timezone(timezone)
                .build();
    }

    public void reschedule(LocalDateTime departureAt, String timezone) {
        this.departureAt = departureAt;
        this.timezone = timezone;
    }

    public void rename(String label) {
        this.label = label;
    }
}
