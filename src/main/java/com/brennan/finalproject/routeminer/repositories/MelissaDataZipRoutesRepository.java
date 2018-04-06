package com.brennan.finalproject.routeminer.repositories;

import com.brennan.finalproject.routeminer.clients.MelissaDataClient;
import com.brennan.finalproject.routeminer.models.ZipRoutes;
import javassist.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MelissaDataZipRoutesRepository implements ZipRoutesRepository {

    @Override
    public List<ZipRoutes> getAllZipRoutes(List<String> zips) {
        return MelissaDataClient.getRouteInfo(zips);
    }

    /**
     *
     * @param zip
     * @return
     * @throws NotFoundException
     */
    @Override
    public ZipRoutes getRoutesForZip(String zip) throws NotFoundException {
        return MelissaDataClient.getZipRoute(zip);
    }
}
