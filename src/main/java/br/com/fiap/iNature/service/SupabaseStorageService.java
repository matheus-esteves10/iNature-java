package br.com.fiap.iNature.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.apikey}")
    private String supabaseApiKey;

    @Value("${supabase.bucket}")
    private String supabaseBucket;

    private final WebClient webClient;

    public SupabaseStorageService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<String> uploadFile(MultipartFile file, String fileName) throws IOException {

        String uploadUrl = String.format("%s/storage/v1/object/%s/%s", supabaseUrl, supabaseBucket, fileName);

        return webClient.put()
                .uri(uploadUrl)
                .header("Authorization", "Bearer " + supabaseApiKey)
                .header("Content-Type", file.getContentType())
                .bodyValue(file.getBytes())
                .retrieve()
                .toBodilessEntity() // Ignora o corpo da resposta
                .map(response -> {
                    // Retorna a URL pública
                    return String.format("%s/storage/v1/object/public/%s/%s", supabaseUrl, supabaseBucket, fileName);
                });
    }
}

