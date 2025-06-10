// src/main/java/take_nft/ru/takeNft/controller/PlayerController.java
package take_nft.ru.takeNft.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import take_nft.ru.takeNft.dto.*;
import take_nft.ru.takeNft.model.Player;
import take_nft.ru.takeNft.service.PlayerService;

import java.util.Map;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    @Autowired
    private PlayerService playerService;

    private static final Logger log = LoggerFactory.getLogger(FriendController.class);

    @PostMapping("/accept")
    public ResponseEntity<?> acceptFriendInvite(@RequestParam("walletId") String fromWalletId,
                                     HttpSession session) {
        String currentWallet = (String) session.getAttribute("address");
        playerService.acceptFriendInvite(fromWalletId, currentWallet);
        return ResponseEntity.ok(new MessageRequest("friend accept"));
    }

    @PostMapping("/ignore")
    public ResponseEntity<?> ignoreFriendInvite(@RequestParam("walletId") String fromWalletId,
                                     HttpSession session) {
        String currentWallet = (String) session.getAttribute("address");
        playerService.ignoreFriendInvite(fromWalletId, currentWallet);
        return ResponseEntity.ok(new MessageRequest("friend ignored"));
    }
}
