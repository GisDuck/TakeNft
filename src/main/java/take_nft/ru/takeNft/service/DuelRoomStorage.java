package take_nft.ru.takeNft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import take_nft.ru.takeNft.dto.PlayerIndexInfoDto;
import take_nft.ru.takeNft.model.DuelRoom;
import take_nft.ru.takeNft.model.Nft;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DuelRoomStorage {
    private final Map<String, DuelRoom> rooms = new ConcurrentHashMap<>();

    @Autowired
    private PlayerService playerService;

    public DuelRoom createRoom(String owner, List<Nft> nfts) {
        PlayerIndexInfoDto playerIndexInfo = playerService.getPlayerIndexInfo(owner);
        DuelRoom room = new DuelRoom(playerIndexInfo, nfts);
        rooms.put(room.getRoomId(), room);
        return room;
    }

    public DuelRoom createInvitationRoom(String owner, List<Nft> nfts, String opponent) {
        PlayerIndexInfoDto playerIndexInfo = playerService.getPlayerIndexInfo(owner);
        PlayerIndexInfoDto opponentIndexInfo = playerService.getPlayerIndexInfo(opponent);
        DuelRoom room = new DuelRoom(playerIndexInfo, nfts, opponentIndexInfo);
        rooms.put(room.getRoomId(), room);
        return room;
    }

    public Optional<DuelRoom> joinRoom(String roomId, String opponent) {
        DuelRoom room = rooms.get(roomId);
        if (room != null && (room.getOpponent() == null || room.getOpponent().address().equals(opponent))) {
            PlayerIndexInfoDto opponentIndexInfo = playerService.getPlayerIndexInfo(opponent);
            room.setOpponent(opponentIndexInfo);
            return Optional.of(room);
        }
        return Optional.empty();
    }

    public DuelRoom getRoom(String roomId) {
        return rooms.get(roomId);
    }

    public void removeRoom(String roomId) {
        rooms.remove(roomId);
    }

    public DuelRoom findByPlayer(String player) {
        return rooms
                .values()
                .stream()
                .filter(r -> r.getOwner().address().equals(player) || player.equals(r.getOpponent().address()))
                .findFirst()
                .orElse(null);
    }

    public List<DuelRoom> getInvitations(String player) {
        return rooms
                .values()
                .stream()
                .filter(r -> r.getType().equals("invitation"))
                .filter(r -> player.equals(r.getOpponent().address()))
                .toList();
    }

    public List<DuelRoom> getOpenRooms() {
        return rooms
                .values()
                .stream()
                .filter(r -> r.getType().equals("room"))
                .toList();
    }

    public void removeEmptyRooms() {
        rooms.entrySet().removeIf(entry -> {
            DuelRoom room = entry.getValue();
            return room.getOpponent() == null && room.getOwner() == null;
        });
    }

}
