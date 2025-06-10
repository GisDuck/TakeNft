package take_nft.ru.takeNft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import take_nft.ru.takeNft.model.Friend;
import take_nft.ru.takeNft.model.Player;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsByPlayerAndFriend(Player player, Player friend);
}
