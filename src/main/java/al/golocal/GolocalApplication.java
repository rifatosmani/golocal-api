package al.golocal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class GolocalApplication {

	public static void main(String[] args) {
		SpringApplication.run(GolocalApplication.class, args);
	}

}
