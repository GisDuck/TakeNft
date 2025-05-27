package take_nft.ru.takeNft.dto;

public record TopHolderDto(
        String walletId,
        String username,
        String avatarUrl,
        long nftCount
) {}