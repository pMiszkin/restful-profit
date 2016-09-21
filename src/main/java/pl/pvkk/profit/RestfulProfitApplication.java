package pl.pvkk.profit;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class RestfulProfitApplication {
		
	public static void main(String[] args) {
		SpringApplication.run(RestfulProfitApplication.class, args);
	}
}
