package rbgusdlza.egochenggyeo.core.domain.recommendation;

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
        name = "recommendation_feedback",
        indexes = {
                @Index(name = "idx_feedback_member_id", columnList = "member_id"),
                @Index(name = "idx_feedback_type", columnList = "recommendation_type")
        }
)
public class RecommendationFeedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id")
    private RecommendationRecord recommendation;

    @Enumerated(EnumType.STRING)
    @Column(name = "recommendation_type", nullable = false)
    private RecommendationType recommendationType;

    @Column(name = "accepted", nullable = false)
    private boolean accepted;

    @Embedded
    private WeatherSnapshot weatherSnapshot;

    @Column(name = "feedback_at", nullable = false)
    private LocalDateTime feedbackAt;

    @Builder(access = AccessLevel.PRIVATE)
    private RecommendationFeedback(
            Member member,
            RecommendationRecord recommendation,
            RecommendationType recommendationType,
            boolean accepted,
            WeatherSnapshot weatherSnapshot,
            LocalDateTime feedbackAt
    ) {
        this.member = member;
        this.recommendation = recommendation;
        this.recommendationType = recommendationType;
        this.accepted = accepted;
        this.weatherSnapshot = weatherSnapshot;
        this.feedbackAt = feedbackAt;
    }

    public static RecommendationFeedback of(
            Member member,
            RecommendationRecord recommendation,
            RecommendationType recommendationType,
            boolean accepted,
            WeatherSnapshot weatherSnapshot,
            LocalDateTime feedbackAt
    ) {
        return RecommendationFeedback.builder()
                .member(member)
                .recommendation(recommendation)
                .recommendationType(recommendationType)
                .accepted(accepted)
                .weatherSnapshot(weatherSnapshot)
                .feedbackAt(feedbackAt)
                .build();
    }
}
