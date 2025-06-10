package take_nft.ru.takeNft.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import take_nft.ru.takeNft.service.FriendService;
import take_nft.ru.takeNft.service.NftCollectionService;
import take_nft.ru.takeNft.service.PlayerService;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private NftCollectionService nftCollectionService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("topHolders", playerService.getTopHolders(10));
        model.addAttribute("hero", nftCollectionService.getHeroCollection());
        model.addAttribute("bestCollections", nftCollectionService.getBestCollections());
        return "index";
    }

    @GetMapping("/duel-rooms")
    public String duelRooms() {
        return "redirect:/pages/duel-rooms.html";
    }

    @GetMapping("/create")
    public String create() {
        return "redirect:/pages/create.html";
    }

    @GetMapping("/duel")
    public String duel() {
        return "duel";
    }

    @GetMapping("/friends")
    public String friends(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("address") == null) {
            return "forward:/";
        }
        String address = (String) session.getAttribute("address");

        model.addAttribute(
                "invites",
                friendService.getFriendInvitesByWalletId(address)
        );
        model.addAttribute(
                "friends",
                friendService.getFriendsByWalletId(address)
        );

        return "friends";
    }

//    @GetMapping("/{page:[^.]+}")
//    public String page(@PathVariable String page) {
//        return "forward:/pages/" + page + ".html";
//    }
}