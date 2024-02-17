package com.infore.InforeApp.controller;

import com.infore.InforeApp.dto.Branch;
import com.infore.InforeApp.dto.RepositoryResponse;
import com.infore.InforeApp.exception.UserNotFoundException;
import com.infore.InforeApp.service.GHService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(GHController.class)
class GHControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GHService gitHubService;

    @Test
    public void testListUserRepositories() {
        RepositoryResponse repositoryResponse = RepositoryResponse.builder()
                .repositoryName("repositoryName")
                .ownerLogin("user-iurii")
                .branches(List.of(new Branch("branchName", "commitSHA"))).build();

        when(gitHubService.getUserRepositoriesWithBranches("user-iurii", false)).thenReturn(Flux.just(repositoryResponse));

        webTestClient.get()
                .uri("/github/repositories?username=user-iurii")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RepositoryResponse.class)
                .hasSize(1)
                .contains(repositoryResponse);
    }

    @Test
    public void testUserNotFound() {

        when(gitHubService.getUserRepositoriesWithBranches("user123", false)).thenReturn(Flux.error(new UserNotFoundException("user123")));

        webTestClient.get()
                .uri("/github/repositories?username=user123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().json("{\"message\":\"User not found: user123\",\"status\":404}");
    }

}