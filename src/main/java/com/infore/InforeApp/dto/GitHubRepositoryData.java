package com.infore.InforeApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepositoryData {

    String name;
    @JsonProperty("full_name")
    String fullName;
    GitHubRepoOwnerData owner;
    boolean fork;
}
