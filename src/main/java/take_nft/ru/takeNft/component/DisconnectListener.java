package take_nft.ru.takeNft.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import take_nft.ru.takeNft.dto.DuelMessageDto;
import take_nft.ru.takeNft.service.DuelRoomStorage;
import take_nft.ru.takeNft.service.DuelRoomStorage.DuelRoom;
import take_nft.ru.takeNft.service.DuelTimeoutService;

import java.security.Principal;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public class DisconnectListener {

    @Autowired
    private DuelRoomStorage storage;

    @Autowired
    private DuelTimeoutService timeoutService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        Principal user = event.getUser();
        if (user == null) return;

        String player = user.getName();
        DuelRoom room = storage.findByPlayer(player);
        if (room == null) return;

        if (room.getOpponent() == null) {
            ScheduledFuture<?> task = timeoutService.schedule(() -> {
                storage.removeRoom(room.getRoomId());
                messagingTemplate.convertAndSend("/topic/duel/rooms", storage.getOpenRooms());
            }, 10, TimeUnit.SECONDS);
            room.setRemovalTask(task);

        } else if (room.isOwnerReady() && room.isOpponentReady()) {
            ScheduledFuture<?> timeout = timeoutService.schedule(() -> {
                String winner = player.equals(room.getOwner()) ? room.getOpponent() : room.getOwner();
                DuelMessageDto msg = new DuelMessageDto();
                msg.setType("timeout_win");
                msg.setPlayer(winner);
                messagingTemplate.convertAndSend("/topic/duel/" + room.getRoomId(), msg);
                storage.removeRoom(room.getRoomId());
            }, 60, TimeUnit.SECONDS);
            room.setTimeoutTask(timeout);

            DuelMessageDto leftMsg = new DuelMessageDto();
            leftMsg.setType("opponent_left");
            leftMsg.setPlayer(player);
            messagingTemplate.convertAndSend("/topic/duel/" + room.getRoomId(), leftMsg);
        }
    }
}
