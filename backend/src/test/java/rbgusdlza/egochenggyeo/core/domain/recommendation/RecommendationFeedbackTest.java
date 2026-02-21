package rbgusdlza.egochenggyeo.core.domain.recommendation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rbgusdlza.egochenggyeo.core.domain.member.Member;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RecommendationFeedbackTest {

    @DisplayName("피드백은 추천 유형과 응답 여부를 보관한다.")
    @Test
    void createFeedback() {
        //given
        Member member = Member.of("device-1", "Asia/Seoul", "ko-KR");
        WeatherSnapshot snapshot = WeatherSnapshot.of(
                2.0,
                -2.0,
                6.0,
                35,
                0.0,
                LocalDateTime.of(2026, 2, 21, 8, 40, 0)
        );
        LocalDateTime feedbackAt = LocalDateTime.of(2026, 2, 21, 8, 42, 0);

        //when
        RecommendationFeedback feedback = RecommendationFeedback.of(
                member,
                null,
                RecommendationType.COAT,
                true,
                snapshot,
                feedbackAt
        );

        //then
        assertThat(feedback.getMember()).isSameAs(member);
        assertThat(feedback.getRecommendationType()).isEqualTo(RecommendationType.COAT);
        assertThat(feedback.isAccepted()).isTrue();
        assertThat(feedback.getWeatherSnapshot()).isSameAs(snapshot);
        assertThat(feedback.getFeedbackAt()).isEqualTo(feedbackAt);
    }
}
