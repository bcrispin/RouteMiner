package com.brennan.finalproject.routeminer.models;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ZipRoutes {
    private String zip;

    @Singular
    @Setter
    private List<RouteInfo> routes;
}
