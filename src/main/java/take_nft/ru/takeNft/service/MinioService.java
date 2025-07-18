package take_nft.ru.takeNft.service;

import io.github.cdimascio.dotenv.Dotenv;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class MinioService {
    @Autowired
    private MinioClient minioClient;

    private static Dotenv dotenv = Dotenv.load();
    private static final String BASE_URL = dotenv.get("BASE_URL");

    private String bucket = "uploads";

    public String uploadAvatar(MultipartFile file) throws Exception {
        String filename = "avatar-" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(filename)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());

        return new StringBuilder()
                .append("https://")
                .append(BASE_URL)
                .append('/')
                .append(bucket)
                .append('/')
                .append(filename)
                .toString();
    }

    public String uploadNftImg(MultipartFile file) throws Exception {
        String filename = "nft-" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(filename)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());

        return new StringBuilder()
                .append("https://")
                .append(BASE_URL)
                .append('/')
                .append(bucket)
                .append('/')
                .append(filename)
                .toString();
    }
}