package take_nft.ru.takeNft.repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import take_nft.ru.takeNft.dto.BestCollectionDto;
import take_nft.ru.takeNft.model.NftCollection;

import java.util.List;

@Repository
public interface NftCollectionRepository extends JpaRepository<NftCollection, Long> {
    public NftCollection findByName(String name);
    public NftCollection findFirstByOrderByIdDesc();
    int countByCollection_Id(Long collectionId);

    @Query("""
      SELECT new take_nft.ru.takeNft.dto.BestCollectionDto(
        c.id,
        c.imgUrl,
        c.name
      )
      FROM NftCollection c
      LEFT JOIN c.nfts n
      GROUP BY c.id, c.imgUrl, c.name
      ORDER BY COUNT(n) DESC
    """)
    List<BestCollectionDto> findBestCollections(Pageable pageable);
}
