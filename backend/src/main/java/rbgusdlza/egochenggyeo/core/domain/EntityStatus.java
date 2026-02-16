package rbgusdlza.egochenggyeo.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntityStatus {

    ACTIVE("활성화 상태"),
    DELETE("삭제 상태");

    private final String text;
}
