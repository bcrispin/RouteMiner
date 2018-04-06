package com.brennan.finalproject.routeminer.repositories;

import com.brennan.finalproject.routeminer.clients.SearchBugClient;
import com.brennan.finalproject.routeminer.models.ZipInfo;
import javassist.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchBugZipInfoRepository implements ZipInfoRepository {

    @Override
    public List<String> getZipsInRadius(int zip, int radius) throws NotFoundException {
        return SearchBugClient.getZips(zip, radius);
    }

    /**
     *
     * @param zips
     * @return
     */
    @Override
    public List<ZipInfo> getZipInfo(List<String> zips) {
        return null;
    }

    /**
     *
     * @param zip
     * @return
     * @throws NotFoundException
     */
    @Override
    public ZipInfo getZipInfo(String zip) throws NotFoundException {
        return SearchBugClient.getZipInfo(zip);
    }
}
