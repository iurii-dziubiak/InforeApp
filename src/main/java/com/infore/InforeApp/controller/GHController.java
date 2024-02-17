package com.infore.InforeApp.controller;

import com.infore.InforeApp.dto.RepositoryResponse;
import com.infore.InforeApp.exception.UserNotFoundException;
import com.infore.InforeApp.service.GHService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/github")
public class GHController {

    private final GHService gitHubService;

    public GHController(GHService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping(value = "/repositories", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<RepositoryResponse> listUserRepositories(@RequestParam String username) {
        return gitHubService.getUserRepositoriesWithBranches(username, false);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleProductCreateException(UserNotFoundException e) {
        log.error("Can't process user request ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("status", 404, "message", e.getMessage()));
    }
}
