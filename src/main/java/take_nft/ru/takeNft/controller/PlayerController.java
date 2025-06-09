// src/main/java/take_nft/ru/takeNft/controller/PlayerController.java
package take_nft.ru.takeNft.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import take_nft.ru.takeNft.dto.*;
import take_nft.ru.takeNft.model.Player;
import take_nft.ru.takeNft.service.PlayerService;

import java.util.Map;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    private final PlayerService playerService;

    private static final Logger log = LoggerFactory.getLogger(PlayerController.class);

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/me")
    public ResponseEntity<PlayerIndexInfoDto> me(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("address") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String address = (String) session.getAttribute("address");
        PlayerIndexInfoDto info = playerService.getPlayerIndexInfo(address);
        return ResponseEntity.ok(info);
    }

    @PostMapping("/change-settings")
    public ResponseEntity<?> changeSettings(@RequestBody @Valid PlayerChangeSettingsDto playerChangeSettingsDto,
                                            BindingResult result,
                                            HttpServletRequest request) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new MessageRequest(errorMessage));
        }

        String walletId = (String) request.getSession().getAttribute("address");
        if ( walletId == null) return ResponseEntity.status(401).body(new MessageRequest("Not Auth"));

        Player player = playerService.getPlayerByWalletId(walletId);

        if (player != null) {
            log.debug(player.toString());
            log.debug(playerChangeSettingsDto.toString());
        } else {
            log.debug("pLayer is null");
        }

        if (player == null || !player.getUsername().equals(playerChangeSettingsDto.getUsername())) {
            if (playerService.isTakenUsername(playerChangeSettingsDto.getUsername())) {
                return ResponseEntity.badRequest().body(new MessageRequest("Username уже занят"));
            }
        }

        if (player == null || !player.getEmail().equals(playerChangeSettingsDto.getEmail()))
        if (playerService.isTakenEmail(playerChangeSettingsDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageRequest("Email уже зарегистрирован"));
        }

        playerService.changePlayer(walletId, playerChangeSettingsDto);
        return ResponseEntity.ok(new MessageRequest("User change settings"));
    }
}
