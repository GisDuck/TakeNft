package take_nft.ru.takeNft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import take_nft.ru.takeNft.dto.TopHolderDto;
import take_nft.ru.takeNft.service.NftCollectionService;
import take_nft.ru.takeNft.service.PlayerService;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private NftCollectionService nftCollectionService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("topHolders", playerService.getTopHolders(10));
        model.addAttribute("hero", nftCollectionService.getHeroCollection());
        model.addAttribute("bestCollections", nftCollectionService.getBestCollections());
        return "index";
    }

    @GetMapping("/{page:[^.]+}")
    public String page(@PathVariable String page) {
        return "forward:/pages/" + page + ".html";
    }
}