package take_nft.ru.takeNft.service;

import org.springframework.stereotype.Service;
import take_nft.ru.takeNft.model.DuelRoom;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DuelRoomStorage {
    private final Map<String, DuelRoom> rooms = new ConcurrentHashMap<>();

    public DuelRoom createRoom(String owner) {
        DuelRoom room = new DuelRoom(owner);
        rooms.put(room.getRoomId(), room);
        return room;
    }

    public Optional<DuelRoom> joinRoom(String roomId, String opponent) {
        DuelRoom room = rooms.get(roomId);
        if (room != null && room.getOpponent() == null) {
            room.setOpponent(opponent);
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
        return rooms.values().stream()
                .filter(r -> r.getOwner().equals(player) || player.equals(r.getOpponent()))
                .findFirst()
                .orElse(null);
    }
}
