package take_nft.ru.takeNft.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import take_nft.ru.takeNft.model.Player;
import take_nft.ru.takeNft.service.NftCollectionService;
import take_nft.ru.takeNft.service.PlayerService;

@Slf4j
@Controller
public class InfoController {
    @Autowired
    public PlayerService playerService;
    @Autowired
    public NftCollectionService nftCollectionService;

    @GetMapping("/player")
    public String playerPage(@RequestParam("walletId") String walletId, Model model) {
        Player player = playerService.getPlayerByWalletId(walletId);
        log.info("словили игрока с id: {}", walletId);
        model.addAttribute("player", player);
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