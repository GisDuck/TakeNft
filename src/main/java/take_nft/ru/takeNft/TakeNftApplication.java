package take_nft.ru.takeNft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class TakeNftApplication {

	public static void main(String[] args) {

		SpringApplication.run(TakeNftApplication.class, args);
	}

}
