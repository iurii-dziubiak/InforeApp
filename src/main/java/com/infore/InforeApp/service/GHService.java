package com.infore.InforeApp.service;

import com.infore.InforeApp.dto.Branch;
import com.infore.InforeApp.dto.GitHubRepositoryData;
import com.infore.InforeApp.dto.RepositoryResponse;
import com.infore.InforeApp.repository.GHRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class GHService {

    private final GHRepository repository;

    public Flux<RepositoryResponse> getUserRepositoriesWithBranches(String username, boolean isFork) {
        return repository.getUserRepositories(username)
                .filter(repo -> isFork == repo.isFork())
                .flatMap(this::fillBranches);
    }

    private Mono<RepositoryResponse> fillBranches(GitHubRepositoryData repositoryData) {
        return repository.getBranchesFromUserRepository(repositoryData.getFullName())
                .map(gitHubBranchData -> new Branch(gitHubBranchData.getName(), gitHubBranchData.getCommit().getSha()))
                .collectList()
                .map(branches -> RepositoryResponse.builder()
                        .repositoryName(repositoryData.getName())
                        .ownerLogin(repositoryData.getOwner().getLogin())
                        .branches(branches)
                        .build());
    }
}
