package take_nft.ru.takeNft.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerChangeSettingsDto {
    @NotBlank(message = "Имя пользователя обязательно")
    @Size(min = 5, max = 20, message = "Username должен содержать от 5 до 20 символов")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username может содержать только латинские буквы, цифры и _")
    private String username;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email")
    private String email;

    @NotBlank(message = "URL аватара обязателен")
    private String avatarUrl;

    @NotNull(message = "Выбор Настройки дуэли обязателен")
    private String duelPermission;

    @NotNull(message = "Выбор настройки друзей обязателен")
    private String friendPermission;

}
