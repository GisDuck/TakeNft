package take_nft.ru.takeNft.dto;

import take_nft.ru.takeNft.model.Nft;

public record NftDto(
        Long id,
        String imgUrl,
        String name
) {}
