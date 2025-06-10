package take_nft.ru.takeNft.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import take_nft.ru.takeNft.dto.MessageRequest;
import take_nft.ru.takeNft.dto.PlayerIndexInfoDto;

@RestController
@RequestMapping("/api/duel")
public class DuelController {

    @PostMapping("/ignore")
    public ResponseEntity<?> ignore(@RequestParam("walletId") String fromWalletId,
                                    HttpSession session) {
        String currentWallet = (String) session.getAttribute("address");
        return ResponseEntity.ok(new MessageRequest("friend ignored"));
    }




}
