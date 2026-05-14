package co.edu.uco.imexcol.inicializador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"co.edu.uco.imexcol"})
@SpringBootApplication
public class ImexcolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImexcolApplication.class, args);
	}

}
