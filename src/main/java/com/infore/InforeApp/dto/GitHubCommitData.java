package com.infore.InforeApp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubCommitData {

    String sha;

    @JsonCreator
    public GitHubCommitData(@JsonProperty("sha") String sha) {
        this.sha = sha;
    }
}
