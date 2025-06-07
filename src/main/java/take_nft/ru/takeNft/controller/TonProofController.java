// src/main/java/take_nft/ru/takeNft/controller/AuthController.java
package take_nft.ru.takeNft.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import take_nft.ru.takeNft.service.PlayerService;
import take_nft.ru.takeNft.service.TonProofService;

import take_nft.ru.takeNft.dto.TonProofRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/ton-proof")
public class TonProofController {
    @Autowired
    private TonProofService tonProofService;
    @Autowired
    private PlayerService playerService;

    private static final Logger log = LoggerFactory.getLogger(TonProofController.class);

    //Получить payload для ton proof подписи
    @GetMapping("payload")
    public ResponseEntity<String> getPayload() {
        String payload = tonProofService.createPayload();
        return ResponseEntity.ok(payload);
    }

    //Логин проверяем подпись и создаём HTTP-сессию
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody TonProofRequest tonProofRequest,
            HttpServletRequest request) throws Exception {
        log.info("user login");

        if (!tonProofService.verifyUser(tonProofRequest)) {
            log.warn("Invalid proof");
            return ResponseEntity.status(401).body("Invalid proof");
        }

        log.info("create session payer for {}", tonProofRequest.accountJson().address());
        HttpSession session = request.getSession(true);
        session.setAttribute("address", tonProofRequest.accountJson().address());
        session.setAttribute("role", "PLAYER");
        return ResponseEntity.ok(Map.of("address", tonProofRequest.accountJson().address()));
    }

    //инвалидируем сессию
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        log.info("user logout {}", session.getAttribute("address"));

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok(Map.of("message", "logged out"));
    }

    //залогинен ли пользователь
    @GetMapping("/status")
    public ResponseEntity<?> status(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("address") == null) {
            return ResponseEntity.ok(Map.of(
                    "loggedIn", false,
                    "address", "",
                    "newUser", false
            ));
        }

        String address = (String) session.getAttribute("address");
        boolean isNew = playerService.isNewPlayer(address);
        return ResponseEntity.ok(Map.of(
                "loggedIn", true,
                "address", address,
                "newUser", isNew
        ));
    }
}
