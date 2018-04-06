package com.brennan.finalproject.routeminer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zips_in_rad", schema = "zip")
public class ZipsInRadius {

    @Id
    private String zipAndRadius;

    @Column(name="zips")
    @ElementCollection(targetClass=String.class)
    private List<String> zips;
}
