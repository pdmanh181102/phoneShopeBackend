package tttn_2025.phoneShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PhoneShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneShopApplication.class, args);
	}

}
