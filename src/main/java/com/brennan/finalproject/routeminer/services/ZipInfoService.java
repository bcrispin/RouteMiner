package com.brennan.finalproject.routeminer.services;

import com.brennan.finalproject.routeminer.models.ZipInfo;
import com.brennan.finalproject.routeminer.models.ZipRoutes;
import com.brennan.finalproject.routeminer.repositories.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ZipInfoService {

    @Autowired
    SearchBugZipInfoRepository searchBugZipInfoRepository;

    @Autowired
    ZipInfoRepositoryDatabase zipInfoRepositoryDatabase;

    @Autowired
    MelissaDataZipRoutesRepository melissaDataZipRoutesRepository;

    @Autowired
    ZipRoutesRepositoryDatabase zipRoutesRepositoryDatabase;

    /**
     *
     * @param zip
     * @param radius
     * @return
     * @throws NotFoundException
     */
    public List<ZipInfo> getZipInfoByRadius(int zip, int radius) throws NotFoundException {
        List<String> zips;
        List<ZipInfo> zipInfoList = new ArrayList<>();

        //If zip code is not in database, then go to mellisaData
        try {
            zips = zipInfoRepositoryDatabase.getZipsInRadius(zip, radius);
        } catch (NotFoundException e) {
            zips = searchBugZipInfoRepository.getZipsInRadius(zip, radius);
            if (zips != null) {
                zipInfoRepositoryDatabase.setZipsInRadius(zips, zip, radius);
            }
        }
        //iterate over all zips in radius and get their info. If they're not in the db, then
        //go to the zip info repo
        for (String z : zips) {
            ZipInfo zipInfo = null;
            try {
                zipInfo = zipInfoRepositoryDatabase.getZipInfo(z);
            }
            catch (NotFoundException e) {
                zipInfo = searchBugZipInfoRepository.getZipInfo(z);
                if (zipInfo != null) {
                    zipInfoRepositoryDatabase.setZipInfo(zipInfo);
                }
            }
            if (zipInfo != null) {
                zipInfoList.add(zipInfo);
            }
        }
        return zipInfoList;
    }

    //check the db, if the routes for that zip aren't present, get them from
    //the melissadatabase

    /**
     *
     * @param zip
     * @return
     * @throws NotFoundException
     */
    public ZipRoutes getZipRoutes(String zip) throws NotFoundException {
        ZipRoutes zipRoutes;

        try {
            zipRoutes = zipRoutesRepositoryDatabase.getRoutesForZip(zip);
        } catch (NotFoundException e) {
            zipRoutes = melissaDataZipRoutesRepository.getRoutesForZip(zip);
            if (zipRoutes != null) {
                 zipRoutesRepositoryDatabase.setRoutesForZip(zipRoutes);
            }
        }
        return zipRoutes;
    }
}
