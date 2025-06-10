package take_nft.ru.takeNft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import take_nft.ru.takeNft.dto.DuelMessageDto;
import take_nft.ru.takeNft.dto.NftSelectionDto;
import take_nft.ru.takeNft.service.DuelRoomStorage;
import take_nft.ru.takeNft.service.DuelRoomStorage.DuelRoom;
import take_nft.ru.takeNft.service.DuelTimeoutService;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
public class DuelWebSocketController {

    @Autowired
    private DuelRoomStorage roomStorage;

    @Autowired
    private DuelTimeoutService timeoutService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/duel/create")
    public void createRoom(Principal user) {
        DuelRoom room = roomStorage.createRoom(user.getName());
        DuelMessageDto message = new DuelMessageDto();
        message.setType("roomCreated");
        message.setRoomId(room.getRoomId());
        messagingTemplate.convertAndSendToUser(user.getName(), "/queue/duel", message);
        messagingTemplate.convertAndSend("/topic/duel/rooms", roomStorage.getOpenRooms());
    }

    @MessageMapping("/duel/{roomId}/join")
    public void joinRoom(@DestinationVariable String roomId, Principal user) {
        DuelRoom room = roomStorage.getRoom(roomId);
        String player = user.getName();

        if (room.getOpponent() == null) {
            // первый раз присоединяется второй игрок
            Optional<DuelRoom> opt = roomStorage.joinRoom(roomId, user.getName());
            messagingTemplate.convertAndSend("/topic/duel/rooms", roomStorage.getOpenRooms());

            DuelMessageDto joinMsg = new DuelMessageDto();
            joinMsg.setType("joined");
            joinMsg.setOpponent(player);
            messagingTemplate.convertAndSend("/topic/duel/" + roomId, joinMsg);

        } else if (player.equals(room.getOpponent()) || player.equals(room.getOwner())) {
            // игрок переподключился
            timeoutService.cancel(room.getTimeoutTask());

            DuelMessageDto rejoinMsg = new DuelMessageDto();
            rejoinMsg.setType("opponent_rejoined");
            rejoinMsg.setPlayer(player);
            messagingTemplate.convertAndSend("/topic/duel/" + roomId, rejoinMsg);
        }

    }

    @MessageMapping("/duel/{roomId}/select-nft")
    public void selectNft(@DestinationVariable String roomId, @Payload NftSelectionDto dto, Principal user) {
        DuelMessageDto message = new DuelMessageDto();
        message.setType("nft_select");
        message.setPlayer(user.getName());
        message.setNfts(dto.nfts());
        messagingTemplate.convertAndSend("/topic/duel/" + roomId, message);
    }

    @MessageMapping("/duel/{roomId}/ready")
    public void playerReady(@DestinationVariable String roomId, Principal user) {
        DuelRoom room = roomStorage.getRoom(roomId);
        if (room != null) {
            if (room.getOwner().equals(user.getName())) {
                room.setOwnerReady(true);
            }
            if (room.getOpponent() != null && room.getOpponent().equals(user.getName())) {
                room.setOpponentReady(true);
            }

            if (room.isOwnerReady() && room.isOpponentReady()) {
                DuelMessageDto message = new DuelMessageDto();
                message.setType("start_duel");
                messagingTemplate.convertAndSend("/topic/duel/" + roomId, message);
            }
        }
    }
}

