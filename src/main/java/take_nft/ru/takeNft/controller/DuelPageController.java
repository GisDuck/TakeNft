package take_nft.ru.takeNft.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import take_nft.ru.takeNft.model.DuelRoom;
import take_nft.ru.takeNft.model.Nft;
import take_nft.ru.takeNft.service.DuelRoomStorage;
import take_nft.ru.takeNft.service.NftService;
import take_nft.ru.takeNft.service.PlayerService;

import java.util.List;

@Controller
public class DuelPageController {

    @Autowired
    private DuelRoomStorage duelRoomStorage;

    @Autowired
    private NftService nftService;

    @Autowired
    private PlayerService playerService;

    @GetMapping("/duel-rooms")
    public String duelRooms(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("address") == null) {
            return "forward:/";
        }
        String address = (String) session.getAttribute("address");

        List<Nft> nfts = playerService.getPlayerByWalletId(address).getInventory();

        duelRoomStorage.createRoom(address, nfts);
        duelRoomStorage.createInvitationRoom(address, nfts, address);

        List<DuelRoom> rooms = duelRoomStorage.getOpenRooms();
        List<DuelRoom> invitations = duelRoomStorage.getInvitations(address);

        model.addAttribute("invitations", invitations);
        model.addAttribute("rooms", rooms);
        model.addAttribute("userId", address);

        return "duel-rooms";
    }


    @GetMapping("/duel")
    public String duel(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("address") == null) {
            return "forward:/";
        }
        String address = (String) session.getAttribute("address");


        return "forward:/";
    }

    @GetMapping("/create-duel")
    public String createDuel(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("address") == null) {
            return "forward:/";
        }
        String address = (String) session.getAttribute("address");

        model.addAttribute("player",  playerService.getPlayerIndexInfo(address));

        return "duel";
    }

}