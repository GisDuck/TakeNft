package take_nft.ru.takeNft.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import take_nft.ru.takeNft.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
    public Player findByWalletId(String walletId);
    public Player findByUsername(String username);
}
