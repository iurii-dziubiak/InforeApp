package com.infore.InforeApp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepoOwnerData {

    String login;

    @JsonCreator
    public GitHubRepoOwnerData(@JsonProperty("login") String login) {
        this.login = login;
    }
}
