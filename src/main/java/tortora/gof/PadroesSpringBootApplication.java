package tortora.gof;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PadroesSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(PadroesSpringBootApplication.class, args);
	}

}
