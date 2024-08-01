package likelion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class LikelionApplication {
	public static void main(String[] args) {
		SpringApplication.run(LikelionApplication.class, args);
	}
}

@Component
@Profile("dev")
class DevEnvLoader {
	
	public DevEnvLoader() {
		Dotenv dotenv = Dotenv.configure().directory("/home/ec2-user/LIKELIONTON-2024-BE/.env").load();

		for (DotenvEntry entry : dotenv.entries()) {
			System.setProperty(entry.getKey(), entry.getValue());
		}
	}
}
