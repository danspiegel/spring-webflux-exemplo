package br.com.spring.webflux;

import br.com.spring.webflux.document.Playlist;
import br.com.spring.webflux.repository.PlaylistRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component
public class DummyData implements CommandLineRunner {

    private final PlaylistRepository playlistRepository;

    public DummyData(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        playlistRepository.deleteAll()
                .thenMany(
                        Flux.just("API Rest Spring Boot", "Deploy de uma aplicação Java no IBM Cloud", "Java 8",
                                "GitHub", "Spring Security", "WebService RESTful", "Bean no Spring Framework")
                        .map(nome -> new Playlist(UUID.randomUUID().toString(), nome))
                        .flatMap(playlistRepository::save)
                ).subscribe(System.out::println);

    }

}
