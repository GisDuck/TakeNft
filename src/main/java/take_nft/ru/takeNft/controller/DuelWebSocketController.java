package take_nft.ru.takeNft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import take_nft.ru.takeNft.dto.DuelMessageDto;
import take_nft.ru.takeNft.dto.NftSelectionDto;
import take_nft.ru.takeNft.model.DuelRoom;
import take_nft.ru.takeNft.model.Player;
import take_nft.ru.takeNft.service.DuelRoomStorage;
import take_nft.ru.takeNft.service.PlayerService;

import java.security.Principal;

@Controller
public class DuelWebSocketController {

    @Autowired
    private DuelRoomStorage roomStorage;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/duel/{roomId}/join")
    public void joinRoom(@DestinationVariable String roomId, Principal principal) {
        String wallet = principal.getName();
        Player player = playerService.getPlayerByWalletId(wallet);
        DuelRoom room = roomStorage.getRoom(roomId);
        if (room == null) return;

        if (room.getOpponent() == null) {
            room.setOpponent(wallet);
        }

        DuelMessageDto msg = new DuelMessageDto();
        msg.setType("joined");
        msg.setOpponent(wallet);
        msg.setOpponentName(player.getUsername());
        msg.setOpponentAvatar(player.getAvatarUrl());
        messagingTemplate.convertAndSend("/topic/duel/" + roomId, msg);
    }

    @MessageMapping("/duel/{roomId}/select-nft")
    public void selectNft(@DestinationVariable String roomId, @Payload NftSelectionDto dto, Principal principal) {
        DuelMessageDto msg = new DuelMessageDto();
        msg.setType("nft_select");
        msg.setPlayer(principal.getName());
        msg.setNfts(dto.nfts());
        messagingTemplate.convertAndSend("/topic/duel/" + roomId, msg);
    }
}

