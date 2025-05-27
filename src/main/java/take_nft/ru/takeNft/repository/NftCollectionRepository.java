package take_nft.ru.takeNft.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import take_nft.ru.takeNft.model.NftCollection;

@Repository
public interface NftCollectionRepository extends JpaRepository<NftCollection, Long> {
    public NftCollection findByName(String name);
    public NftCollection findFirstByOrderByIdDesc();
    int countByCollection_Id(Long collectionId);
}
