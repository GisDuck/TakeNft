package take_nft.ru.takeNft.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import take_nft.ru.takeNft.repository.PlayerRepository;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;


    public String getNameById(String id) {
        return playerRepository.findByWalletId(id).getUsername();
    }
}
