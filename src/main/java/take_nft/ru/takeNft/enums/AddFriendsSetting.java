package take_nft.ru.takeNft.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;

import java.util.Arrays;

@Getter(onMethod_ = @JsonValue)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AddFriendsSetting {
    NOBODY("nobody"),
    FRIENDS_OF_FRIENDS("friends of friends"),
    ALL("all");

    private final String dbValue;

    @JsonCreator
    public static AddFriendsSetting fromDbValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.dbValue.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown AddFriendsSetting: " + value));
    }
}
//print chift return hydtusfigyvygksvfkgywfityqrfjgyvfjgrfcjtfyjrqfgrqfytfrqjgyrfjbhavjvagyighkgqbaifhbkgn

