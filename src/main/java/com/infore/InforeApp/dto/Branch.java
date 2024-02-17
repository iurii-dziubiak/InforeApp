package com.infore.InforeApp.dto;

import lombok.Value;

@Value
public class Branch {

    String name;
    String lastCommitSha;
}
