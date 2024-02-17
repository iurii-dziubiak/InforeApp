package com.infore.InforeApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubBranchData {

    String name;
    GitHubCommitData commit;
}
