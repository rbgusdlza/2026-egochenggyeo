package rbgusdlza.egochenggyeo.core.domain.recommendation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherSnapshotTest {

    @DisplayName("날씨 스냅샷은 입력 값을 그대로 보관한다.")
    @Test
    void createSnapshot() {
        //given
        LocalDateTime observedAt = LocalDateTime.of(2026, 2, 21, 8, 50, 0);

        //when
        WeatherSnapshot snapshot = WeatherSnapshot.of(
                1.5,
                -3.0,
                7.2,
                55,
                6.0,
                observedAt
        );

        //then
        assertThat(snapshot.getTodayTemperature()).isEqualTo(1.5);
        assertThat(snapshot.getYesterdayTemperature()).isEqualTo(-3.0);
        assertThat(snapshot.getWindSpeed()).isEqualTo(7.2);
        assertThat(snapshot.getRainProbability()).isEqualTo(55);
        assertThat(snapshot.getExpectedRainfallMm()).isEqualTo(6.0);
        assertThat(snapshot.getObservedAt()).isEqualTo(observedAt);
    }
}
