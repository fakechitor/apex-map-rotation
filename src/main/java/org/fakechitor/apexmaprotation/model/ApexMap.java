package org.fakechitor.apexmaprotation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "map")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApexMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String iconLink;
}
