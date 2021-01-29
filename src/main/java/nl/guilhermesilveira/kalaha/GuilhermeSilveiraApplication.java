package nl.guilhermesilveira.kalaha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class GuilhermeSilveiraApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuilhermeSilveiraApplication.class, args);
	}

}
