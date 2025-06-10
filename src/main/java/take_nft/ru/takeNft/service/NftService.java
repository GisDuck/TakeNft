package take_nft.ru.takeNft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import take_nft.ru.takeNft.dto.BestCollectionDto;
import take_nft.ru.takeNft.dto.HeroDto;
import take_nft.ru.takeNft.model.Nft;
import take_nft.ru.takeNft.model.NftCollection;
import take_nft.ru.takeNft.repository.NftCollectionRepository;
import take_nft.ru.takeNft.repository.NftRepository;

import java.util.List;

@Service
public class NftService {
    @Autowired
    private NftRepository nftRepository;

    private MinioService minioService;

    public Nft getNftById(Long id) {
        return nftRepository.getById(id);
    }

}