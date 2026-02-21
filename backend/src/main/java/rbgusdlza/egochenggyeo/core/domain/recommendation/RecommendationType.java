package rbgusdlza.egochenggyeo.core.domain.recommendation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecommendationType {

    COAT("외투 이거챙겨"),
    UMBRELLA("우산 이거챙겨");

    private final String message;
}
