package rbgusdlza.egochenggyeo.core.domain.recommendation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecommendationStatus {

    CREATED("생성됨"),
    SENT("발송됨"),
    FAILED("실패");

    private final String text;
}
