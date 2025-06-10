package take_nft.ru.takeNft.model;

import liquibase.change.DatabaseChangeNote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DuelRoom {
    private final String roomId;
    private final String owner;
    private String opponent;
    private boolean ownerReady;
    private boolean opponentReady;

    public DuelRoom(String owner) {
        this.roomId = UUID.randomUUID().toString();
        this.owner = owner;
    }

    // геттеры/сеттеры
}

