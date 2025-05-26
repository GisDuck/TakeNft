package take_nft.ru.takeNft.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "nfts")
public class Nft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_nfts_wallet"))
    private Player owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_nfts_collection"))
    private NftCollection collection;
// Надо добить до всех нужных полей. пока так. И так будет так. Точка. Я псих.
}
