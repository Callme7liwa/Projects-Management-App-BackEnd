package com.pfe.projectsmanagements.entities.sequences;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employe_sequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalistSequence {
        @Id
        private String  id;
        private Long seq;

}
