package take_nft.ru.takeNft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import take_nft.ru.takeNft.model.FriendInvites;
import take_nft.ru.takeNft.model.Player;

@Repository
public interface FriendInviteRepository extends JpaRepository<FriendInvites, Long> {
    void deleteByFromPlayerAndToPlayer(Player from, Player to);
}
