package take_nft.ru.takeNft.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    private static Dotenv dotenv = Dotenv.load();

    private String url = dotenv.get("MINIO_URL");
    private String accessKey = dotenv.get("MINIO_ROOT_USER");
    private String secretKey = dotenv.get("MINIO_ROOT_PASSWORD");

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}
