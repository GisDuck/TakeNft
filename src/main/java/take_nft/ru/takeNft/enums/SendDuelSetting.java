package take_nft.ru.takeNft.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter(onMethod_ = @JsonValue)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SendDuelSetting {
    NOBODY("NOBODY"),
    FRIENDS("FRIENDS"),
    ALL("ALL");

    private final String dbValue;

    @JsonCreator
    public static SendDuelSetting fromDbValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.dbValue.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown SendDuelSetting: " + value));
    }
}

