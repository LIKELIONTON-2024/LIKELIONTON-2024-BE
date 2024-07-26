package likelion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class LikelionApplication {
	public static void main(String[] args) {
		SpringApplication.run(LikelionApplication.class, args);

		Dotenv dotenv = Dotenv.configure().directory("./LIKELIONTON-2024-BE").load();

		for (DotenvEntry entry : dotenv.entries()) {
			System.setProperty(entry.getKey(), entry.getValue());
		}
	}
}
