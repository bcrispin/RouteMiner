package com.brennan.finalproject.routeminer.repositories;

import com.brennan.finalproject.routeminer.models.ZipInfo;
import javassist.NotFoundException;

import java.util.List;

public interface ZipInfoRepository {
    List<String> getZipsInRadius(int zip, int radius) throws NotFoundException;
    List<ZipInfo> getZipInfo(List<String> zips);
    ZipInfo getZipInfo(String zip) throws NotFoundException;
}
