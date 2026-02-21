package rbgusdlza.egochenggyeo.core.domain.recommendation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class WeatherSnapshot {

    @Column(name = "today_temp")
    private Double todayTemperature;

    @Column(name = "yesterday_temp")
    private Double yesterdayTemperature;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "rain_probability")
    private Integer rainProbability;

    @Column(name = "expected_rainfall_mm")
    private Double expectedRainfallMm;

    @Column(name = "observed_at")
    private LocalDateTime observedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private WeatherSnapshot(
            Double todayTemperature,
            Double yesterdayTemperature,
            Double windSpeed,
            Integer rainProbability,
            Double expectedRainfallMm,
            LocalDateTime observedAt
    ) {
        this.todayTemperature = todayTemperature;
        this.yesterdayTemperature = yesterdayTemperature;
        this.windSpeed = windSpeed;
        this.rainProbability = rainProbability;
        this.expectedRainfallMm = expectedRainfallMm;
        this.observedAt = observedAt;
    }

    public static WeatherSnapshot of(
            Double todayTemperature,
            Double yesterdayTemperature,
            Double windSpeed,
            Integer rainProbability,
            Double expectedRainfallMm,
            LocalDateTime observedAt
    ) {
        return WeatherSnapshot.builder()
                .todayTemperature(todayTemperature)
                .yesterdayTemperature(yesterdayTemperature)
                .windSpeed(windSpeed)
                .rainProbability(rainProbability)
                .expectedRainfallMm(expectedRainfallMm)
                .observedAt(observedAt)
                .build();
    }
}
