package rbgusdlza.egochenggyeo.core.domain.recommendation;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.egochenggyeo.core.domain.BaseEntity;
import rbgusdlza.egochenggyeo.core.domain.member.Member;
import rbgusdlza.egochenggyeo.core.domain.schedule.DepartureSchedule;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "recommendation_record",
        indexes = {
                @Index(name = "idx_rec_member_id", columnList = "member_id"),
                @Index(name = "idx_rec_schedule_id", columnList = "schedule_id"),
                @Index(name = "idx_rec_type", columnList = "recommendation_type"),
                @Index(name = "idx_rec_dispatch_key", columnList = "dispatch_key")
        }
)
public class RecommendationRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private DepartureSchedule schedule;

    @Enumerated(EnumType.STRING)
    @Column(name = "recommendation_type", nullable = false)
    private RecommendationType recommendationType;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "threshold", nullable = false)
    private Integer threshold;

    @Column(name = "message", nullable = false, length = 200)
    private String message;

    @Embedded
    private WeatherSnapshot weatherSnapshot;

    @Column(name = "dispatch_key", nullable = false, length = 100)
    private String dispatchKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "dispatch_status", nullable = false)
    private RecommendationStatus dispatchStatus = RecommendationStatus.CREATED;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "failure_reason", length = 500)
    private String failureReason;

    @Builder(access = AccessLevel.PRIVATE)
    private RecommendationRecord(
            Member member,
            DepartureSchedule schedule,
            RecommendationType recommendationType,
            Integer score,
            Integer threshold,
            String message,
            WeatherSnapshot weatherSnapshot,
            String dispatchKey,
            RecommendationStatus dispatchStatus,
            LocalDateTime sentAt,
            String failureReason
    ) {
        this.member = member;
        this.schedule = schedule;
        this.recommendationType = recommendationType;
        this.score = score;
        this.threshold = threshold;
        this.message = message;
        this.weatherSnapshot = weatherSnapshot;
        this.dispatchKey = dispatchKey;
        this.dispatchStatus = dispatchStatus;
        this.sentAt = sentAt;
        this.failureReason = failureReason;
    }

    public static RecommendationRecord of(
            Member member,
            DepartureSchedule schedule,
            RecommendationType recommendationType,
            Integer score,
            Integer threshold,
            String message,
            WeatherSnapshot weatherSnapshot,
            String dispatchKey
    ) {
        return RecommendationRecord.builder()
                .member(member)
                .schedule(schedule)
                .recommendationType(recommendationType)
                .score(score)
                .threshold(threshold)
                .message(message)
                .weatherSnapshot(weatherSnapshot)
                .dispatchKey(dispatchKey)
                .dispatchStatus(RecommendationStatus.CREATED)
                .build();
    }

    public void markSent(LocalDateTime sentAt) {
        this.dispatchStatus = RecommendationStatus.SENT;
        this.sentAt = sentAt;
        this.failureReason = null;
    }

    public void markFailed(String failureReason) {
        this.dispatchStatus = RecommendationStatus.FAILED;
        this.failureReason = failureReason;
    }
}
