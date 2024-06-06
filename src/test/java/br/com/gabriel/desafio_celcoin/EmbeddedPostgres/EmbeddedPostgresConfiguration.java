package br.com.gabriel.desafio_celcoin.EmbeddedPostgres;

import java.io.IOException;

import javax.sql.DataSource;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.testcontainers.utility.DockerImageName;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;

@Configuration
@EntityScan(basePackages = "br.com.gabriel.desafio_celcoin.domain.entities")
@EnableJpaRepositories(basePackages = "br.com.gabriel.desafio_celcoin.repositories")
public class EmbeddedPostgresConfiguration {

    private static EmbeddedPostgres embeddedPostgres;

    @Bean
    public DataSource dataSource() throws IOException {
        embeddedPostgres = EmbeddedPostgres.builder()
            .setImage(DockerImageName.parse("postgres:latest"))
        .start();

        return embeddedPostgres.getPostgresDatabase();
    }

    public static class EmbeddedPostgresExtension implements AfterAllCallback {
        @Override
        public void afterAll(ExtensionContext context) throws Exception {
            if (embeddedPostgres == null) {
                return;
            }
            embeddedPostgres.close();
        }
    }
}
