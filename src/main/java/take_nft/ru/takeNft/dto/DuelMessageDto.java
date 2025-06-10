package take_nft.ru.takeNft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DuelMessageDto {
    private String type;
    private String roomId;
    private String player;
    private List<NftDto> nfts;
    private String opponent;
    private String opponentName;
    private String opponentAvatar;
}
