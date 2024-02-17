package com.infore.InforeApp.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class RepositoryResponse {

    String repositoryName;
    String ownerLogin;
    List<Branch> branches;
}
