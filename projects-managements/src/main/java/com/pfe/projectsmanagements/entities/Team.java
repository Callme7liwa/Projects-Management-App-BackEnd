package com.pfe.projectsmanagements.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("teams")
@ToString
public class Team {

    @Transient
    public static final String SEQUENCE_NAME = "team_sequence";
    @Id
    private Long id ;
    @NotNull(message = "name of the team should not be null  ! ")
    @Indexed(unique = true)
    private String name ;
    private String photo ;
    @NotNull(message = "creation date of the team  should not be null !")
    private Date creationDate ;
    private Integer numberEtoiles ;
    @DBRef(lazy = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private Set<Journalist> journalists = new HashSet<>();
    @DBRef(lazy = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private Set<Project> projects  = new HashSet<>();
}
