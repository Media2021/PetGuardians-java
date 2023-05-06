package pets.example.guardians;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
//@Import(DateConfig.class)
public class GuardiansApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuardiansApplication.class, args);
	}

}
