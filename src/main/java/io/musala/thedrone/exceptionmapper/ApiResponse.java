package io.musala.thedrone.exceptionmapper;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse {
    private boolean success;

    private String message;
}
