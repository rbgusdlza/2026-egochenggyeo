package rbgusdlza.egochenggyeo.core.domain.member;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @DisplayName("사용자의 push token을 수정한다.")
    @Test
    void updatePushToken() {
        //given
        Member member = Member.of("11111");

        //when
        member.updatePushToken("22222", LocalDateTime.now());

        //then
        assertThat(member.getPushToken()).isEqualTo("22222");
    }

    @DisplayName("사용자의 활동 지역을 수정한다.")
    @Test
    void updateLocale() {
        //given
        Member member = Member.of("11111", "timezone1", "locale1");

        //when
        member.updateLocale("locale2");

        //then
        assertThat(member.getLocale()).isEqualTo("locale2");
    }

    @DisplayName("사용자의 timezone을 수정한다.")
    @Test
    void updateTimezone() {
        //given
        Member member = Member.of("11111", "timezone1", "locale1");

        //when
        member.updateTimezone("timezone2");

        //then
        assertThat(member.getTimezone()).isEqualTo("timezone2");
    }
}