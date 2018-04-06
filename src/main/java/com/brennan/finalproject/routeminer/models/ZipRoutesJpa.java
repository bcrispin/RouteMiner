package com.brennan.finalproject.routeminer.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zip_routes", schema = "routes")
public class ZipRoutesJpa {
    @Id
    private String zip;

    @Singular
    @Setter
    @ElementCollection(targetClass=String.class)
    private List<String> routes;
}
