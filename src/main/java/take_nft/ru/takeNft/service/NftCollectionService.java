package take_nft.ru.takeNft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import take_nft.ru.takeNft.dto.BestCollectionDto;
import take_nft.ru.takeNft.dto.HeroDto;
import take_nft.ru.takeNft.model.NftCollection;
import take_nft.ru.takeNft.repository.NftCollectionRepository;

import java.util.List;

@Service
public class NftCollectionService {
    @Autowired
    private NftCollectionRepository nftCollectionRepository;

    private MinioService minioService;

    public HeroDto getHeroCollection() {
        NftCollection collection = nftCollectionRepository.findFirstByOrderByIdDesc();
        int countNft = nftCollectionRepository.countByCollectionId(collection.getId());

        return new HeroDto(
                collection.getId(),
                collection.getName(),
                collection.getImgUrl(),
                countNft
                );
    }

    public List<BestCollectionDto> getBestCollections() {
        // Получить первую страницу (0) размером 5
        return nftCollectionRepository.findBestCollections(PageRequest.of(0, 5));
    }
}