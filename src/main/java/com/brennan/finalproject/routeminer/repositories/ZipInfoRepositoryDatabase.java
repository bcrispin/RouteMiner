package com.brennan.finalproject.routeminer.repositories;

import com.brennan.finalproject.routeminer.models.ZipInfo;
import com.brennan.finalproject.routeminer.models.ZipsInRadius;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Slf4j
@Repository
public class ZipInfoRepositoryDatabase implements ZipInfoRepository{


    @PersistenceContext
    private EntityManager em;

    @Autowired
    public ZipInfoRepositoryDatabase(EntityManager em) {
        this.em = em;
    }

    /**
     *
     * @param zip
     * @param radius
     * @return
     * @throws NotFoundException
     */
    @Override
    public List<String> getZipsInRadius(int zip, int radius) throws NotFoundException {
        ZipsInRadius zips = em.find(ZipsInRadius.class, Integer.toString(zip)+Integer.toString(radius));
        if (zips == null) {
            throw new NotFoundException(Integer.toString(zip));
        }
        return zips.getZips();
    }

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
        ZipInfo zipInfo = em.find(ZipInfo.class, zip);
        if (zipInfo == null) {
            throw new NotFoundException(zip);
        }
        return zipInfo;
    }

    /**
     *
     * @param zips
     * @param zip
     * @param radius
     */
    public void setZipsInRadius(List<String> zips, int zip, int radius) {
        ZipsInRadius zipsInRadius = ZipsInRadius.builder()
                .zipAndRadius(Integer.toString(zip) + Integer.toString(radius))
                .zips(zips)
                .build();

        em.persist(zipsInRadius);
    }

    public void setZipInfo(ZipInfo zipInfo) {
        em.persist(zipInfo);
    }
}
