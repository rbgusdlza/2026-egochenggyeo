package rbgusdlza.egochenggyeo.core.domain.recommendation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RecommendationTypeTest {

    @DisplayName("추천 타입은 사용자 메시지를 제공한다.")
    @Test
    void typeMessage() {
        //given
        RecommendationType type = RecommendationType.COAT;

        //when
        String message = type.getMessage();

        //then
        assertThat(message).isEqualTo("외투 이거챙겨");
    }
}
