// src/main/java/take_nft/ru/takeNft/controller/PlayerController.java
package take_nft.ru.takeNft.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import take_nft.ru.takeNft.dto.*;
import take_nft.ru.takeNft.service.FriendService;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;

    private static final Logger log = LoggerFactory.getLogger(FriendController.class);

    @PostMapping("/accept")
    public ResponseEntity<?> accept(@RequestParam("walletId") String fromWalletId,
                                    HttpSession session) {
        String currentWallet = (String) session.getAttribute("address");
        friendService.acceptFriendInvite(fromWalletId, currentWallet);
        return ResponseEntity.ok(new MessageRequest("friend accept"));
    }

    @PostMapping("/ignore")
    public ResponseEntity<?> ignore(@RequestParam("walletId") String fromWalletId,
                                     HttpSession session) {
        String currentWallet = (String) session.getAttribute("address");
        friendService.ignoreFriendInvite(fromWalletId, currentWallet);
        return ResponseEntity.ok(new MessageRequest("friend ignored"));
    }

    @PostMapping("/invite")
    public ResponseEntity<?> invite(@RequestParam("walletId") String toWalletId,
                                    HttpSession session) {
        String currentWallet = (String) session.getAttribute("address");

        friendService.inviteFriend(currentWallet, toWalletId);
        return ResponseEntity.ok(new MessageRequest("friend ignored"));
    }
}
