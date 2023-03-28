package chan.carlos.springbootreactor;

import chan.carlos.springbootreactor.models.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux<Usuario> nombres = Flux.just("Andres", "Eduardo")
				.map(nombre -> new Usuario(nombre.toUpperCase()))
				.doOnNext(usuario -> {
					if (usuario == null) {
						throw new RuntimeException("Nombres no pueden ir vacíos");
					}

					System.out.println(usuario);
				})
				.map(usuario -> {
					String nombre = usuario.getNombre().toLowerCase();
					usuario.setNombre(nombre);
					return usuario;
				});

		nombres.subscribe(e -> log.info(e.getNombre()), error -> log.error(error.getMessage()), () -> log.info("Ha finalizado la ejecución del observable/flux"));
	}
}
