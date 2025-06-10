package take_nft.ru.takeNft.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import take_nft.ru.takeNft.model.Player;
import take_nft.ru.takeNft.service.FriendService;
import take_nft.ru.takeNft.service.NftCollectionService;
import take_nft.ru.takeNft.service.PlayerService;

@Slf4j
@Controller
public class InfoController {
    @Autowired
    public PlayerService playerService;
    @Autowired
    public NftCollectionService nftCollectionService;

    @Autowired
    public FriendService friendService;

    @GetMapping("/player")
    public String playerPage(@RequestParam("walletId") String profileWalletId, Model model, HttpServletRequest request) {
        Player player = playerService.getPlayerByWalletId(profileWalletId);
        log.info("словили игрока с id: {}", profileWalletId);
        model.addAttribute("player", player);

        String userWalletId = (String) request.getSession().getAttribute("address");
        if ( profileWalletId == null) return "forward:/";


        boolean isHideInviteBtn =
                friendService.areFriends(userWalletId, profileWalletId)
                || friendService.hasInvite(userWalletId, profileWalletId)
                || friendService.hasInvite(profileWalletId, userWalletId);

        model.addAttribute("isHideInviteBtn", isHideInviteBtn);
        return "player_info";  // src/main/resources/templates/player_info.html
    }

    @GetMapping("/nft")
    public String nft(@RequestParam("id") String id, Model model) {
        log.info("словили nft с id: {}", id);
        model.addAttribute("nftId", id);
        return "forward:/pages/index.html";
    }

    @GetMapping("/collection")
    public String collection(@RequestParam("id") String id, Model model) {
        log.info("словили collection с id: {}", id);
        model.addAttribute("collectionId", id);
        return "forward:/pages/index.html";
    }
}