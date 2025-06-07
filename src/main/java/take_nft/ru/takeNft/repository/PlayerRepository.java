package take_nft.ru.takeNft.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import take_nft.ru.takeNft.dto.TopHolderDto;
import take_nft.ru.takeNft.model.Player;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
    public Player findByWalletId(String walletId);
    public Player findByUsername(String username);
    public boolean existsByWalletId(String walletId);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);

    @Query("""
      SELECT new take_nft.ru.takeNft.dto.TopHolderDto(
        p.walletId,
        p.username,
        p.avatarUrl,
        COUNT(n)
      )
      FROM Player p
      LEFT JOIN p.inventory n
      GROUP BY p.walletId, p.username, p.avatarUrl
      ORDER BY COUNT(n) DESC
    """)
    List<TopHolderDto> findTopHolders(Pageable pageable);
}
