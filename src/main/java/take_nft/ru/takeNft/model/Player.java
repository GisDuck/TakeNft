package take_nft.ru.takeNft.model;


import jakarta.persistence.*;
import take_nft.ru.takeNft.enums.AddFriendsSetting;
import take_nft.ru.takeNft.enums.SendDuelSetting;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    @Column(name = "wallet_id", nullable = false)
    private String walletId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "avatar_url", nullable = false)
    private String avatarUrl;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(
            name = "who_can_add_friend"
    )
    @Enumerated(EnumType.STRING)
    private AddFriendsSetting whoCanAddFriend;

    @Column(
            name = "who_can_send_duel"
    )
    @Enumerated(EnumType.STRING)
    private SendDuelSetting whoCanSendDuel;

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Nft> inventory = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friends",
            joinColumns        = @JoinColumn(name = "first_wallet_id"),
            inverseJoinColumns = @JoinColumn(name = "second_wallet_id"),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"first_wallet_id","second_wallet_id"}
            )
    )
    private List<Player> friends = new ArrayList<>();
}
