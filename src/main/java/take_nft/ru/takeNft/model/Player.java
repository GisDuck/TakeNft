package take_nft.ru.takeNft.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.apache.commons.lang3.EnumUtils;
import take_nft.ru.takeNft.dto.PlayerChangeSettingsDto;
import take_nft.ru.takeNft.enums.AddFriendsSetting;
import take_nft.ru.takeNft.enums.SendDuelSetting;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Enumerated(EnumType.STRING)
    @Column(name = "who_can_add_friend")
    private AddFriendsSetting whoCanAddFriend;

    @Enumerated(EnumType.STRING)
    @Column(name = "who_can_send_duel")
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

    public Player(String walletId, PlayerChangeSettingsDto playerChangeSettingsDto) {
        this.walletId = walletId;
        this.username = playerChangeSettingsDto.getUsername();
        this.email = playerChangeSettingsDto.getEmail();
        this.avatarUrl = playerChangeSettingsDto.getAvatarUrl();

        String duel = playerChangeSettingsDto.getDuelPermission();
        String friend = playerChangeSettingsDto.getFriendPermission();

        this.whoCanSendDuel = SendDuelSetting.valueOf(duel.toUpperCase());

        if (friend.toUpperCase().equals("FRIENDS OF FRIENDS")) {
            this.whoCanAddFriend = AddFriendsSetting.FRIENDS_OF_FRIENDS;
        } else {
            this.whoCanAddFriend = AddFriendsSetting.valueOf(friend.toUpperCase());
        }
    }
}
