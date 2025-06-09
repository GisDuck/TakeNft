package take_nft.ru.takeNft.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import take_nft.ru.takeNft.dto.*;
import take_nft.ru.takeNft.model.Friend;
import take_nft.ru.takeNft.model.Player;
import take_nft.ru.takeNft.repository.FriendInviteRepository;
import take_nft.ru.takeNft.repository.FriendRepository;
import take_nft.ru.takeNft.repository.PlayerRepository;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private FriendInviteRepository friendInviteRepository;

    @Autowired
    private FriendRepository friendRepository;

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

    public List<FriendDto> getFriendInvitesByWalletId(String walletId) {
        Player player = playerRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        return player.getFriendInvite()
                .stream()
                .map(p -> new FriendDto(p.getWalletId(), p.getUsername(), p.getAvatarUrl(), p.getInventory().size()))
                .toList();
    }

    public List<FriendDto> getFriendsByWalletId(String walletId) {
        Player player = playerRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        return player.getFriends()
                .stream()
                .map(p -> new FriendDto(p.getWalletId(), p.getUsername(), p.getAvatarUrl(), p.getInventory().size()))
                .toList();
    }

    @Transactional
    public void acceptFriendInvite(String fromWalletId, String whoIsInvited) {
        Player from = playerRepository.findByWalletId(fromWalletId);
        Player to = playerRepository.findByWalletId(whoIsInvited);

        // Удаляем приглашение
        friendInviteRepository.deleteByFromPlayerAndToPlayer(from, to);

        // Добавляем двустороннюю дружбу
        friendRepository.save(new Friend(from, to));
        friendRepository.save(new Friend(to, from));
    }

    @Transactional
    public void ignoreFriendInvite(String fromWalletId, String whoIsInvited) {
        Player from = playerRepository.findByWalletId(fromWalletId);
        Player to = playerRepository.findByWalletId(whoIsInvited);

        // Удаляем приглашение
        friendInviteRepository.deleteByFromPlayerAndToPlayer(from, to);
    }
}
