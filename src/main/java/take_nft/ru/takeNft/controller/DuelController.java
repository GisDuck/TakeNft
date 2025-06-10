package take_nft.ru.takeNft.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import take_nft.ru.takeNft.dto.MessageRequest;
import take_nft.ru.takeNft.dto.PlayerIndexInfoDto;
import take_nft.ru.takeNft.service.PlayerService;

import java.util.Map;

@RestController
@RequestMapping("/api/duel")
public class DuelController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/ignore")
    public ResponseEntity<?> ignore(@RequestParam("walletId") String fromWalletId,
                                    HttpSession session) {
        String currentWallet = (String) session.getAttribute("address");
        return ResponseEntity.ok(new MessageRequest("friend ignored"));
    }

    @GetMapping("/inventory")
    public ResponseEntity<?> status(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String address = (String) session.getAttribute("address");
        return ResponseEntity.ok(playerService.getInventoryDto(address));
        }




}
