package com.pfe.projectsmanagements.entities.sequences;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "client_sequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientSequence {
    @Id
    private String  id;
    private Long seq;
}
