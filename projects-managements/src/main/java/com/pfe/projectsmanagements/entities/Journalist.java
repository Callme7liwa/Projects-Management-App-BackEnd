package com.pfe.projectsmanagements.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pfe.projectsmanagements.Enums.Gender;
import com.pfe.projectsmanagements.entities.others.Affectation;
import lombok.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.management.relation.Role;
import javax.validation.constraints.*;
import java.util.*;

@Document("journalistes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class Journalist {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    @Id
    private Long id ;

    @Indexed(unique = true)
    @Size(max = 30 , message = "The second name should not contains more than 60 characters")
    @NotNull(message="username should Not be null ")
    private String userName ;

    @NotNull(message = "first name should be not null ")
    private String firstName ;

    @NotNull(message = "please enter your email")
    @Indexed(unique = true)
    private String email ;

    @NotNull(message =  "second name should be not null ")
    @Size(max = 30 , message = "The second name should not contains more than 40 characters")
    private String secondName  ;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password ;

    @NotNull(message = "city should not be null")
    private String  city ;

    @NotNull(message = "country should not be null ")
    private String  country ;

    @NotNull(message = "Please Enter your Gender")
    private Gender gender ;

    private String photo ;

    @DBRef
    private Set<JournalistRole> Roles = new HashSet<JournalistRole>();

    @DBRef
    private Set<Function> functions  = new HashSet<>();

    private Set<String> oldPics = new HashSet<>();

    private Date birthday ;

    private Date creationDate ;

    private List<Affectation> affectations = new ArrayList<>();

    @DBRef
    private Set<Team> teams = new HashSet<>();


}
