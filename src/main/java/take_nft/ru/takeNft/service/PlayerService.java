package take_nft.ru.takeNft.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import take_nft.ru.takeNft.dto.TopHolderDto;
import take_nft.ru.takeNft.model.Player;
import take_nft.ru.takeNft.repository.NftRepository;
import take_nft.ru.takeNft.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public List<TopHolderDto> getTopHolders(int limit) {
        return playerRepository.findTopHolders(PageRequest.of(0, limit));
    }

    public Player getPlayerByWalletId(String walletId) {
        return Optional.ofNullable(playerRepository.findByWalletId(walletId))
                .orElseThrow(() -> new EntityNotFoundException("Player not found: " + walletId));
    }
}
