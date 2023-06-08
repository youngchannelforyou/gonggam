package App.Gonggam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import App.config.WebConfig;

@Import(WebConfig.class)
@SpringBootApplication
@ComponentScan(basePackages = "App.Gonggam.controller")
public class GonggamApplication {
	public static void main(String[] args) {
		SqlConnection SC = new SqlConnection();
		SC.Connection();
		SpringApplication.run(GonggamApplication.class, args);
	}
}