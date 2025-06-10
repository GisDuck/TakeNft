package take_nft.ru.takeNft.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import take_nft.ru.takeNft.model.DuelRoom;
import take_nft.ru.takeNft.model.Player;
import take_nft.ru.takeNft.service.DuelRoomStorage;
import take_nft.ru.takeNft.service.PlayerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/duel")
public class DuelRoomRestController {

    private final DuelRoomStorage roomStorage;
    private final PlayerService playerService;

    public DuelRoomRestController(DuelRoomStorage roomStorage, PlayerService playerService) {
        this.roomStorage = roomStorage;
        this.playerService = playerService;
    }

    @GetMapping("/rooms")
    public Map<String, Object> getAvailableRooms(HttpSession session) {
        String wallet = (String) session.getAttribute("wallet_id");

        List<Map<String, Object>> invites = new ArrayList<>();
        List<Map<String, Object>> rooms = new ArrayList<>();

        for (DuelRoom room : roomStorage.getAllRooms()) {
            if (room.getOwner().equals(wallet)) continue;

            Player player = playerService.getPlayerByWalletId(room.getOwner());

            Map<String, Object> dto = new HashMap<>();
            dto.put("roomId", room.getRoomId());
            dto.put("username", player.getUsername());
            dto.put("playerAvatarUrl", player.getAvatarUrl());

            if (room.getOpponent() == null) {
                rooms.add(dto);
            } else if (room.getOpponent().equals(wallet)) {
                invites.add(dto);
            }
        }

        return Map.of("rooms", rooms, "invites", invites);
    }
}

