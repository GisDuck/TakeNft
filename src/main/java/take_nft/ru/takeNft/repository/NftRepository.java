package take_nft.ru.takeNft.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import take_nft.ru.takeNft.model.Nft;

import java.util.List;

@Repository
public interface NftRepository extends JpaRepository<Nft, Long> {
    public List<Nft> findByName(String name);
}
