package rbgusdlza.egochenggyeo.core.domain.recommendation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rbgusdlza.egochenggyeo.core.domain.member.Member;
import rbgusdlza.egochenggyeo.core.domain.schedule.DepartureSchedule;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RecommendationRecordTest {

    @DisplayName("추천 기록은 생성 시 핵심 정보를 보관한다.")
    @Test
    void createRecord() {
        //given
        Member member = Member.of("device-1", "Asia/Seoul", "ko-KR");
        DepartureSchedule schedule = DepartureSchedule.of(
                member,
                LocalDateTime.of(2026, 2, 21, 9, 0, 0),
                "Asia/Seoul"
        );
        WeatherSnapshot snapshot = WeatherSnapshot.of(
                1.0,
                -4.0,
                9.0,
                70,
                8.0,
                LocalDateTime.of(2026, 2, 21, 8, 50, 0)
        );

        //when
        RecommendationRecord record = RecommendationRecord.of(
                member,
                schedule,
                RecommendationType.COAT,
                60,
                50,
                RecommendationType.COAT.getMessage(),
                snapshot,
                "device-1|schedule-1|2026-02-21T08:50"
        );

        //then
        assertThat(record.getMember()).isSameAs(member);
        assertThat(record.getSchedule()).isSameAs(schedule);
        assertThat(record.getRecommendationType()).isEqualTo(RecommendationType.COAT);
        assertThat(record.getScore()).isEqualTo(60);
        assertThat(record.getThreshold()).isEqualTo(50);
        assertThat(record.getMessage()).isEqualTo("외투 이거챙겨");
        assertThat(record.getWeatherSnapshot()).isSameAs(snapshot);
        assertThat(record.getDispatchKey()).isEqualTo("device-1|schedule-1|2026-02-21T08:50");
        assertThat(record.getDispatchStatus()).isEqualTo(RecommendationStatus.CREATED);
    }

    @DisplayName("추천 기록은 발송 완료로 상태를 변경할 수 있다.")
    @Test
    void markSent() {
        //given
        Member member = Member.of("device-1", "Asia/Seoul", "ko-KR");
        RecommendationRecord record = RecommendationRecord.of(
                member,
                null,
                RecommendationType.UMBRELLA,
                70,
                50,
                RecommendationType.UMBRELLA.getMessage(),
                null,
                "device-1|manual|2026-02-21T08:55"
        );
        LocalDateTime sentAt = LocalDateTime.of(2026, 2, 21, 8, 55, 0);

        //when
        record.markSent(sentAt);

        //then
        assertThat(record.getDispatchStatus()).isEqualTo(RecommendationStatus.SENT);
        assertThat(record.getSentAt()).isEqualTo(sentAt);
        assertThat(record.getFailureReason()).isNull();
    }

    @DisplayName("추천 기록은 실패 상태를 보관할 수 있다.")
    @Test
    void markFailed() {
        //given
        Member member = Member.of("device-1", "Asia/Seoul", "ko-KR");
        RecommendationRecord record = RecommendationRecord.of(
                member,
                null,
                RecommendationType.UMBRELLA,
                40,
                50,
                RecommendationType.UMBRELLA.getMessage(),
                null,
                "device-1|manual|2026-02-21T08:55"
        );

        //when
        record.markFailed("FCM error");

        //then
        assertThat(record.getDispatchStatus()).isEqualTo(RecommendationStatus.FAILED);
        assertThat(record.getFailureReason()).isEqualTo("FCM error");
    }
}
