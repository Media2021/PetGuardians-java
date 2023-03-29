package pets.example.guardians;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pets.example.guardians.configuration.DateConfig;

@SpringBootApplication
@Configuration
@Import(DateConfig.class)
public class GuardiansApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuardiansApplication.class, args);
	}

}
