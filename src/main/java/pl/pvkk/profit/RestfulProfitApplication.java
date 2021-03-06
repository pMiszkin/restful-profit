package pl.pvkk.profit;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableScheduling
public class RestfulProfitApplication {
		
	public static void main(String[] args) {
		SpringApplication.run(RestfulProfitApplication.class, args);
	}
}
