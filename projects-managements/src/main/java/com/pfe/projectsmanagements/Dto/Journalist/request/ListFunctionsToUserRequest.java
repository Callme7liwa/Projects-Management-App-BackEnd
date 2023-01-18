package com.pfe.projectsmanagements.Dto.Journalist.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListFunctionsToUserRequest {
    private Set<String> functionsName = new HashSet<>();
}
