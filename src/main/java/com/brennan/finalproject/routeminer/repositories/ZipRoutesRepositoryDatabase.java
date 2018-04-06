package com.brennan.finalproject.routeminer.repositories;

import com.brennan.finalproject.routeminer.models.RouteInfo;
import com.brennan.finalproject.routeminer.models.ZipRoutes;
import com.brennan.finalproject.routeminer.models.ZipRoutes.ZipRoutesBuilder;
import com.brennan.finalproject.routeminer.models.ZipRoutesJpa;
import com.brennan.finalproject.routeminer.models.ZipRoutesJpa.ZipRoutesJpaBuilder;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Slf4j
@Repository
public class ZipRoutesRepositoryDatabase implements ZipRoutesRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ZipRoutes> getAllZipRoutes(List<String> zip) {
        return null;
    }

    /**
     *
     * @param zip
     * @return
     * @throws NotFoundException
     */
    @Override
    public ZipRoutes getRoutesForZip(String zip) throws NotFoundException {
        ZipRoutesBuilder zipRoutesBuilder = ZipRoutes.builder();
        ZipRoutesJpa zipRoutes = em.find(ZipRoutesJpa.class, zip);
        //get list of routes associated with zip
        if (zipRoutes == null) {
            throw new NotFoundException(zip);
        }
        zipRoutesBuilder.zip(zip);
        //get all routes for given zip
        for (String routeId : zipRoutes.getRoutes()) {
            zipRoutesBuilder.route(em.find(RouteInfo.class, routeId));
        }
        return zipRoutesBuilder.build();
    }

    /**
     *
     * @param zipRoutes
     */
    public void setRoutesForZip(ZipRoutes zipRoutes) {
        ZipRoutesJpaBuilder builder = ZipRoutesJpa.builder().zip(zipRoutes.getZip());
        for (RouteInfo routeInfo : zipRoutes.getRoutes()) {
            if (em.find(RouteInfo.class, routeInfo.getRouteId()) == null) {
                em.persist(routeInfo);
            }
            builder.route(routeInfo.getRouteId());
        }
        if (em.find(ZipRoutesJpa.class, zipRoutes.getZip()) == null) {
            em.persist(builder.build());
        }
    }
}
