package take_nft.ru.takeNft.controller;

import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import com.cloudinary.Cloudinary;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import take_nft.ru.takeNft.dto.*;
import take_nft.ru.takeNft.service.MinioService;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ImageUploadController {
    @Autowired
    private MinioService minioService;

    private static final Logger log = LoggerFactory.getLogger(ImageUploadController.class);

    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("avatar") MultipartFile avatarFile,
                                          HttpServletRequest request) {
        String address = (String) request.getSession().getAttribute("address");
        if (address == null) {
            return ResponseEntity.status(401).body(
                    new uploadAvatarRequest("Not auth", "")
            );
        }

        if (avatarFile.isEmpty() || !avatarFile.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body(
                    new uploadAvatarRequest("Файл должен быть изображением", "")
            );
        }

        try {
            log.info("user:{} upload image", address);

            String imgUrl = minioService.uploadAvatar(avatarFile);

            log.info("successful user:{} upload image: {}", address, imgUrl);

            return ResponseEntity.ok(
                    new uploadAvatarRequest("successful", imgUrl)
            );

        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(500).body(
                    new uploadAvatarRequest("Ошибка загрузки файла", "")
            );
        }
    }

    private static File convertToFile(MultipartFile multipartFile) throws IOException {
        File convertFile = File.createTempFile("avatar-", multipartFile.getOriginalFilename());
        multipartFile.transferTo(convertFile);
        return convertFile;
    }
}

//Мухехехехе
