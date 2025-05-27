package take_nft.ru.takeNft.dto;

public record HeroDto(
        Long id,
        String name,
        String imgUrl,
        int nftCount
) {}
