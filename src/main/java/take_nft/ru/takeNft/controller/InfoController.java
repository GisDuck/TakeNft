package take_nft.ru.takeNft.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import take_nft.ru.takeNft.model.Nft;
import take_nft.ru.takeNft.model.NftCollection;
import take_nft.ru.takeNft.model.Player;
import take_nft.ru.takeNft.service.FriendService;
import take_nft.ru.takeNft.service.NftCollectionService;
import take_nft.ru.takeNft.service.NftService;
import take_nft.ru.takeNft.service.PlayerService;

@Slf4j
@Controller
public class InfoController {
    @Autowired
    public PlayerService playerService;
    @Autowired
    public NftCollectionService nftCollectionService;

    @Autowired
    public NftService nftService;

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
        return "player_info";
    }

    @GetMapping("/inventory")
    public String inventoryPage(Model model, HttpServletRequest request) {
        String userWalletId = (String) request.getSession().getAttribute("address");
        if ( userWalletId == null) return "forward:/";

        Player player = playerService.getPlayerByWalletId(userWalletId);
        model.addAttribute("player", player);

        return "inventory";
    }

    @GetMapping("/nft")
    public String nft(@RequestParam("id") Long id, Model model) {
        log.info("словили nft с id: {}", id);
        Nft nft = nftService.getNftById(id);
        model.addAttribute("nft", nft);
        model.addAttribute("owner", nft.getOwner());
        model.addAttribute("isNft", true);
        return "nft_info";
    }

    @GetMapping("/collection")
    public String collection(@RequestParam("id") Long id, Model model) {
        log.info("словили collection с id: {}", id);
        NftCollection collection = nftCollectionService.getCollectionById(id);
        model.addAttribute("nft", collection);
        model.addAttribute("isNft", false);
        return "nft_info";
    }
}