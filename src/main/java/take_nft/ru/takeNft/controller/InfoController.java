package take_nft.ru.takeNft.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class InfoController {

    @GetMapping("/player/{id}")
    public String player(@PathVariable String id) {
        log.info("словили player c id: {}", id);
        return "forward:/pages/index.html";
    }

    @GetMapping("/nft/{id}")
    public String nft(@PathVariable String id) {
        log.info("словили nft c id: {}", id);
        return "forward:/pages/index.html";
    }

    @GetMapping("/collection/{id}")
    public String collection(@PathVariable String id) {
        log.info("словили collection c id: {}", id);
        return "forward:/pages/index.html";
    }
}