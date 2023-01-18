package com.pfe.projectsmanagements.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Document("refreshToken")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Transient
    public static final String SEQUENCE_NAME = "refreshToken_sequence";

    @Id
    private Long id  ;
    @DBRef()
    private Journalist journalist ;
    @NotNull(message = "Token should not be null !")
    private String token ;
    @NotNull(message = " Expiry date should not be null !")
    private Instant expiryDate ;
}
