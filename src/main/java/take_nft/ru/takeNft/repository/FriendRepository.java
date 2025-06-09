package take_nft.ru.takeNft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import take_nft.ru.takeNft.model.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
}
