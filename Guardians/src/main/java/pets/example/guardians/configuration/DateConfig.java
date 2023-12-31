package pets.example.guardians.configuration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;

@Configuration
public class DateConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder1() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.modulesToInstall(new JavaTimeModule());
        builder.dateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        return builder;
    }

}

