package com.infore.InforeApp.repository;

import com.infore.InforeApp.dto.GitHubBranchData;
import com.infore.InforeApp.dto.GitHubRepositoryData;
import com.infore.InforeApp.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class GHRepository {

    private final WebClient webClient;

    public GHRepository(WebClient.Builder webClientBuilder, @Value("${github.apiUrl}") String apiUrl) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
    }

    public Flux<GitHubRepositoryData> getUserRepositories(String username) {
        log.info("Requesting repositories by username {}", username);
        return webClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> Mono.error(new UserNotFoundException(username)))
                .bodyToFlux(GitHubRepositoryData.class);
    }

    public Flux<GitHubBranchData> getBranchesFromUserRepository(String repoPath) {
        log.info("Requesting branch by repo path {}", repoPath);
        String formattedUri = String.format("/repos/%s/branches", repoPath);
        return webClient.get()
                .uri(formattedUri)
                .retrieve()
                .bodyToFlux(GitHubBranchData.class);
    }
}
