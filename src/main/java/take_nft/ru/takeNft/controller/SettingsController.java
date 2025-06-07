package take_nft.ru.takeNft.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import take_nft.ru.takeNft.dto.SettingsPlayerRequest;
import take_nft.ru.takeNft.service.PlayerService;

@Slf4j
@Controller
public class SettingsController {
    @Autowired
    public PlayerService playerService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute(
                "player",
                new SettingsPlayerRequest(
                        "CurrentName",
                        "example@mail.ru",
                        "img/no_avatar.png",
                        "ALL",
                        "ALL"
                )
        );
        model.addAttribute("logoutDisplay", "disp-none");
        return "settings";
    }

    @GetMapping("/settings")
    public String settingsPage(@RequestParam("walletId") String walletId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("address") == null) {
            return "forward:/";
        }
        String address = (String) session.getAttribute("address");

        model.addAttribute(
                "player",
                playerService.getSettingsPlayer(address)
        );
        model.addAttribute("logoutDisplay", "");
        return "settings";
    }
}