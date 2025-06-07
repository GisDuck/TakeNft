// src/main/java/take_nft/ru/takeNft/dto/RegisterPlayerDto.java
package take_nft.ru.takeNft.dto;

public record SettingsPlayerRequest(
        String username,
        String email,
        String avatarUrl,
        String duelPermission,
        String friendPermission
) {}
