package take_nft.ru.takeNft.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;
import take_nft.ru.takeNft.dto.NftDto;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Service
public class DuelRoomStorage {
    @Data
    public class DuelRoom {
        private final String roomId;
        private final String owner;
        private String opponent;
        private boolean ownerReady = false;
        private boolean opponentReady = false;
        private List<NftDto> ownerNfts = new ArrayList<>();
        private List<NftDto> opponentNfts = new ArrayList<>();

        private ScheduledFuture<?> removalTask;
        private ScheduledFuture<?> timeoutTask;

        public DuelRoom(String owner) {
            this.roomId = UUID.randomUUID().toString();
            this.owner = owner;
        }

        public static DuelRoom getDuelRoom(Object obj) {
            return (DuelRoom) obj;
        }
    }

    private static final String MAP_NAME = "duelRooms";

    @Autowired
    private RedisMapStore redisMapStore;

    public DuelRoom createRoom(String owner) {
        DuelRoom room = new DuelRoom(owner);
        redisMapStore.put(MAP_NAME, room.getRoomId(), room);
        return room;
    }

    public Optional<DuelRoom> joinRoom(String roomId, String opponent) {
        DuelRoom room = (DuelRoom) redisMapStore.get(MAP_NAME, roomId);
        if (room != null && room.getOpponent() == null) {
            room.setOpponent(opponent);
            return Optional.of(room);
        }
        return Optional.empty();
    }

    public void removeRoom(String roomId) {
        redisMapStore.remove(MAP_NAME, roomId);
    }

    public Collection<DuelRoom> getOpenRooms() {
        return redisMapStore.getAll(MAP_NAME)
            .values()
            .stream()
            .map(r -> DuelRoom.getDuelRoom(r))
            .filter(r -> r.getOpponent() == null)
            .collect(Collectors.toList());
    }

    public DuelRoom getRoom(String roomId) {
        return (DuelRoom) redisMapStore.get(MAP_NAME, roomId);
    }

    public DuelRoom findByPlayer(String player) {
        return redisMapStore.getAll(MAP_NAME)
                .values()
                .stream()
                .map(r -> DuelRoom.getDuelRoom(r))
                .filter(r -> r.getOwner().equals(player) || player.equals(r.getOpponent()))
                .findFirst()
                .orElse(null);
    }
}
