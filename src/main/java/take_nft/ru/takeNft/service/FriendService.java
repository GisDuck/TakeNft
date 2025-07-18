package take_nft.ru.takeNft.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import take_nft.ru.takeNft.dto.*;
import take_nft.ru.takeNft.model.Friend;
import take_nft.ru.takeNft.model.FriendInvites;
import take_nft.ru.takeNft.model.Player;
import take_nft.ru.takeNft.repository.FriendInvitesRepository;
import take_nft.ru.takeNft.repository.FriendRepository;
import take_nft.ru.takeNft.repository.PlayerRepository;

import java.util.List;

@Service
public class FriendService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private FriendInvitesRepository friendInvitesRepository;

    @Autowired
    private FriendRepository friendRepository;

    private static final Logger log = LoggerFactory.getLogger(FriendService.class);

    public List<FriendDto> getFriendInvitesByWalletId(String walletId) {
        Player player = playerRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        return player.getFriendInvite()
                .stream()
                .map(p -> p.getFromPlayer())
                .map(p -> new FriendDto(p.getWalletId(), p.getUsername(), p.getAvatarUrl(), p.getInventory().size()))
                .toList();
    }

    public List<FriendDto> getFriendsByWalletId(String walletId) {
        Player player = playerRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        return player.getFriends()
                .stream()
                .map(p -> p.getFriend())
                .map(p -> new FriendDto(p.getWalletId(), p.getUsername(), p.getAvatarUrl(), p.getInventory().size()))
                .toList();
    }

    public void inviteFriend(String fromWalletId, String toWalletId) {
        Player from = playerRepository.findByWalletId(fromWalletId);
        Player to = playerRepository.findByWalletId(toWalletId);

        friendInvitesRepository.save(new FriendInvites(from, to));
    }

    @Transactional
    public void acceptFriendInvite(String fromWalletId, String whoIsInvited) {
        Player from = playerRepository.findByWalletId(fromWalletId);
        Player to = playerRepository.findByWalletId(whoIsInvited);

        // Удаляем приглашение
        friendInvitesRepository.deleteByFromPlayerAndToPlayer(from, to);

        // Добавляем двустороннюю дружбу
        friendRepository.save(new Friend(from, to));
        friendRepository.save(new Friend(to, from));
    }

    @Transactional
    public void ignoreFriendInvite(String fromWalletId, String whoIsInvited) {
        Player from = playerRepository.findByWalletId(fromWalletId);
        Player to = playerRepository.findByWalletId(whoIsInvited);

        // Удаляем приглашение
        friendInvitesRepository.deleteByFromPlayerAndToPlayer(from, to);
    }

    public boolean areFriends(String firstWalletId, String secondWalletId) {
        Player player = playerRepository.findByWalletId(firstWalletId);
        Player friend = playerRepository.findByWalletId(secondWalletId);

        return friendRepository.existsByPlayerAndFriend(player, friend);
    }

    public boolean hasInvite(String fromWalletId, String toWalletId) {
        Player from = playerRepository.findByWalletId(fromWalletId);
        Player to = playerRepository.findByWalletId(toWalletId);

        return friendInvitesRepository.existsByFromPlayerAndToPlayer(from, to);
    }
}
