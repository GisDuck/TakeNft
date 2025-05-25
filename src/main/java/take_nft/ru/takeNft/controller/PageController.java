package take_nft.ru.takeNft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "forward:/pages/index.html";
    }

//    @GetMapping("/{page:[^.]+}")
//    public String page(@PathVariable String page) {
//        return "forward:/pages/" + page + ".html";
//    }
}