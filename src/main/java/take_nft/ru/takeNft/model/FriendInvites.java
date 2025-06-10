package take_nft.ru.takeNft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friend_invites")
public class FriendInvites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_wallet_id")
    private Player fromPlayer;

    @ManyToOne
    @JoinColumn(name = "who_is_invited")
    private Player toPlayer;
}
