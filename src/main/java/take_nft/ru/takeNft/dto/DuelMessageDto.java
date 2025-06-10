package take_nft.ru.takeNft.dto;

import lombok.Data;

import java.util.List;

@Data
public class DuelMessageDto {
    private String type;
    private String roomId;
    private String player;
    private List<NftDto> nfts;
    private String opponent;

}
