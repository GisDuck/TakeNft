package take_nft.ru.takeNft.dto;

public record FriendDto (
    String address,
    String username,
    String avatarUrl,
    int ammountNft
) {}
