package rbgusdlza.egochenggyeo.core.domain.recommendation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RecommendationStatusTest {

    @DisplayName("추천 발송 상태는 상태 텍스트를 제공한다.")
    @Test
    void statusText() {
        //given
        RecommendationStatus status = RecommendationStatus.CREATED;

        //when
        String text = status.getText();

        //then
        assertThat(text).isEqualTo("생성됨");
    }
}
