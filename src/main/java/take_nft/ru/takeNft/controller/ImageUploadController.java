package take_nft.ru.takeNft.controller;

import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import com.cloudinary.Cloudinary;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import take_nft.ru.takeNft.dto.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ImageUploadController {
    private Dotenv dotenv = Dotenv.load();
    private Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));

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

        if (avatarFile.getSize() > 5 * 1024 * 1024) {
            return ResponseEntity.badRequest().body(
                    new uploadAvatarRequest("Размер изображения не должен превышать 5 МБ", "")
            );
        }

        try {

            File imgFile = convertToFile(avatarFile);

            Map uploadResult = cloudinary.uploader().upload(imgFile, ObjectUtils.emptyMap());

            imgFile.delete();

            log.info("user:{} upload image: {}", address, uploadResult.toString());
            return ResponseEntity.ok(
                    new uploadAvatarRequest("successful", uploadResult.get("url").toString())
            );

        } catch (IOException e) {
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
