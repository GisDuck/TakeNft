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
    NOBODY("NOBODY"),
    FRIENDS_OF_FRIENDS("FRIENDS OF FRIENDS"),
    ALL("ALL");

    private final String dbValue;

}
//print chift return hydtusfigyvygksvfkgywfityqrfjgyvfjgrfcjtfyjrqfgrqfytfrqjgyrfjbhavjvagyighkgqbaifhbkgn

