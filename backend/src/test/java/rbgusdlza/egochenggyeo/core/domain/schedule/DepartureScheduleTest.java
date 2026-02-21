package rbgusdlza.egochenggyeo.core.domain.schedule;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rbgusdlza.egochenggyeo.core.domain.member.Member;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DepartureScheduleTest {

    @DisplayName("사용자 일정 생성 시 출발 시각과 timezone을 보관한다.")
    @Test
    void createSchedule() {
        //given
        Member member = Member.of("device-1", "Asia/Seoul", "ko-KR");
        LocalDateTime departureAt = LocalDateTime.of(2026, 2, 21, 9, 0, 0);

        //when
        DepartureSchedule schedule = DepartureSchedule.of(member, departureAt, "Asia/Seoul");

        //then
        assertThat(schedule.getMember()).isSameAs(member);
        assertThat(schedule.getDepartureAt()).isEqualTo(departureAt);
        assertThat(schedule.getTimezone()).isEqualTo("Asia/Seoul");
    }

    @DisplayName("사용자 일정은 출발 시각과 타임존을 변경할 수 있다.")
    @Test
    void reschedule() {
        //given
        Member member = Member.of("device-1", "Asia/Seoul", "ko-KR");
        DepartureSchedule schedule = DepartureSchedule.of(
                member,
                LocalDateTime.of(2026, 2, 21, 9, 0, 0),
                "Asia/Seoul"
        );

        //when
        schedule.reschedule(LocalDateTime.of(2026, 2, 21, 8, 50, 0), "Asia/Tokyo");

        //then
        assertThat(schedule.getDepartureAt()).isEqualTo(LocalDateTime.of(2026, 2, 21, 8, 50, 0));
        assertThat(schedule.getTimezone()).isEqualTo("Asia/Tokyo");
    }

    @DisplayName("사용자 일정은 라벨을 변경할 수 있다.")
    @Test
    void rename() {
        //given
        Member member = Member.of("device-1", "Asia/Seoul", "ko-KR");
        DepartureSchedule schedule = DepartureSchedule.of(
                member,
                LocalDateTime.of(2026, 2, 21, 9, 0, 0),
                "Asia/Seoul"
        );

        //when
        schedule.rename("출근");

        //then
        assertThat(schedule.getLabel()).isEqualTo("출근");
    }
}
