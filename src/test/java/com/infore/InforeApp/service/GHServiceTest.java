package com.infore.InforeApp.service;

import com.infore.InforeApp.dto.*;
import com.infore.InforeApp.repository.GHRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GHServiceTest {

    @Mock
    private GHRepository repository;

    @InjectMocks
    private GHService ghService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserRepositoriesWithBranches() {
        GitHubRepositoryData repo1 = new GitHubRepositoryData("repo1", "owner1/repo1", new GitHubRepoOwnerData("owner1"), false);
        when(repository.getUserRepositories(anyString()))
                .thenReturn(Flux.just(repo1));

        GitHubBranchData branch1 = new GitHubBranchData("branch1", new GitHubCommitData("sha1"));
        GitHubBranchData branch2 = new GitHubBranchData("branch2", new GitHubCommitData("sha2"));
        when(repository.getBranchesFromUserRepository("owner1/repo1"))
                .thenReturn(Flux.just(branch1, branch2));

        Flux<RepositoryResponse> result = ghService.getUserRepositoriesWithBranches("user", false);

        StepVerifier.create(result)
                .expectNextMatches(response -> "repo1".equals(response.getRepositoryName()) && "owner1".equals(response.getOwnerLogin()))
                .verifyComplete();
    }
}