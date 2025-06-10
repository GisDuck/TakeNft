package take_nft.ru.takeNft.dto;

public record FriendDto (
    String walletId,
    String username,
    String avatarUrl,
    int amountNft
) {}
