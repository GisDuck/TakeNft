package take_nft.ru.takeNft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "first_wallet_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "second_wallet_id")
    private Player friend;

    public Friend(Player from, Player to) {
        this.player = from;
        this.friend = to;
    }
}
