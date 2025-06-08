package take_nft.ru.takeNft.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import take_nft.ru.takeNft.dto.PlayerIndexInfoDto;
import take_nft.ru.takeNft.dto.PlayerChangeSettingsDto;
import take_nft.ru.takeNft.dto.SettingsPlayerRequest;
import take_nft.ru.takeNft.dto.TopHolderDto;
import take_nft.ru.takeNft.model.Player;
import take_nft.ru.takeNft.repository.PlayerRepository;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    private static final Logger log = LoggerFactory.getLogger(PlayerService.class);

    public List<TopHolderDto> getTopHolders(int limit) {
        return playerRepository.findTopHolders(PageRequest.of(0, limit));
    }

    public Player getPlayerByWalletId(String walletId) {
        return playerRepository.findByWalletId(walletId);
    }

    // новый метод
    public PlayerIndexInfoDto getPlayerIndexInfo(String walletId) {
        Player player = getPlayerByWalletId(walletId);
        return new PlayerIndexInfoDto(
                player.getWalletId(),
                player.getUsername(),
                player.getAvatarUrl()
        );
    }

    public SettingsPlayerRequest getSettingsPlayer(String walletId) {
        Player player = getPlayerByWalletId(walletId);
        return new SettingsPlayerRequest(
                player.getUsername(),
                player.getEmail(),
                player.getAvatarUrl(),
                player.getWhoCanSendDuel().toString(),
                player.getWhoCanAddFriend().toString()
        );
    }

    public boolean isNewPlayer(String walletId) {
        return !playerRepository.existsByWalletId(walletId);
    }

    public boolean isTakenEmail(String email) {
        return playerRepository.existsByEmail(email);
    }

    public boolean isTakenUsername(String username) {
        return playerRepository.existsByUsername(username);
    }

    public Player changePlayer(String walletId,
                               PlayerChangeSettingsDto playerChangeSettingsDto) {

        return playerRepository.save(
            new Player(walletId, playerChangeSettingsDto)
        );
    }
}
