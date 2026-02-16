package rbgusdlza.egochenggyeo.core.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.egochenggyeo.core.domain.BaseEntity;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "member",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_member_device_id", columnNames = "device_id")
        },
        indexes = {
                @Index(name = "idx_member_status", columnList = "status"),
                @Index(name = "idx_member_push_token", columnList = "push_token")
        }
)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "timezone", nullable = false)
    private String timezone;

    @Column(name = "locale")
    private String locale = "ko-KR";

    @Column(name = "push_token")
    private String pushToken;

    @Column(name = "push_token_updated_at")
    private LocalDateTime pushTokenUpdatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String deviceId, String timezone, String locale, String pushToken, LocalDateTime pushTokenUpdatedAt) {
        this.deviceId = deviceId;
        this.timezone = timezone;
        this.locale = locale;
        this.pushToken = pushToken;
        this.pushTokenUpdatedAt = pushTokenUpdatedAt;
    }

    public static Member of(String deviceId) {
        return Member.builder()
                .deviceId(deviceId)
                .timezone("Asia/Seoul")
                .locale("ko-KR")
                .build();
    }

    public static Member of(String deviceId, String timezone, String locale) {
        return Member.builder()
                .deviceId(deviceId)
                .timezone(timezone)
                .locale(locale)
                .build();
    }

    public void updatePushToken(String pushToken, LocalDateTime updatedAt) {
        this.pushToken = pushToken;
        this.pushTokenUpdatedAt = updatedAt;
    }

    public void updateLocale(String locale) {
        this.locale = locale;
    }

    public void updateTimezone(String timezone) {
        this.timezone = timezone;
    }
}
