package pets.example.guardians;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import pets.example.guardians.configuration.DateConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = { DateConfig.class })
class GuardiansApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;


    @Test
    public void testMain() {

        GuardiansApplication.main(new String[]{});


        assertNotNull(applicationContext);
    }

}
