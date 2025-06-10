package take_nft.ru.takeNft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import take_nft.ru.takeNft.dto.PlayerIndexInfoDto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DuelRoom {
    private final String roomId;
    private final PlayerIndexInfoDto owner;
    private PlayerIndexInfoDto opponent;
    private List<Nft> ownerNfts;
    private List<Nft> opponentNfts;
    private String type;
    private boolean ownerReady;
    private boolean opponentReady;
    private final Instant createdAt = Instant.now();

    public DuelRoom(PlayerIndexInfoDto owner, List<Nft> ownerNfts) {
        this.roomId = UUID.randomUUID().toString();
        this.owner = owner;
        this.ownerNfts = ownerNfts;
    }

    public DuelRoom(PlayerIndexInfoDto owner, List<Nft> ownerNfts, PlayerIndexInfoDto opponent) {
        this.roomId = UUID.randomUUID().toString();
        this.owner = owner;
        this.ownerNfts = ownerNfts;
        this.opponent = opponent;
        this.type = "invitation";
    }
}

