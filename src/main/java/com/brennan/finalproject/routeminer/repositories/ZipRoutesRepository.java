package com.brennan.finalproject.routeminer.repositories;

import com.brennan.finalproject.routeminer.models.ZipRoutes;
import javassist.NotFoundException;

import java.util.List;

public interface ZipRoutesRepository {
    List<ZipRoutes> getAllZipRoutes(List<String> zip);
    ZipRoutes getRoutesForZip(String zip) throws NotFoundException;
}
