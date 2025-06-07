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
import org.springframework.web.multipart.MultipartFile;
import take_nft.ru.takeNft.dto.*;
import take_nft.ru.takeNft.enums.AddFriendsSetting;
import take_nft.ru.takeNft.enums.SendDuelSetting;
import take_nft.ru.takeNft.service.PlayerService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid PlayerRegistrationDto playerRegistrationDto,
                                      BindingResult result,
                                      HttpServletRequest request) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(Map.of("message", errorMessage));
        }

        String walletId = (String) request.getSession().getAttribute("address");
        if ( walletId == null) return ResponseEntity.status(401).body(new MessageRequest("Not Auth"));

        if (playerService.isTakenUsername(playerRegistrationDto.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageRequest("Username уже занят"));
        }

        if (playerService.isTakenEmail(playerRegistrationDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageRequest("Email уже зарегистрирован"));
        }

        playerService.registerNewPlayer(walletId, playerRegistrationDto);
        return ResponseEntity.ok(new MessageRequest("User registered"));
    }
}
