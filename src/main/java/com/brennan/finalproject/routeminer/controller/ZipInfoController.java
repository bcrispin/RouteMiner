package com.brennan.finalproject.routeminer.controller;

import com.brennan.finalproject.routeminer.models.RouteInfo;
import com.brennan.finalproject.routeminer.models.ZipInfo;
import com.brennan.finalproject.routeminer.models.ZipRoutes;
import com.brennan.finalproject.routeminer.services.ZipInfoService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ZipInfoController {

    @Autowired
    private ZipInfoService service;

    //Handles getting zip code info within a radius around the main zip

    /**
     *
     * @param zipCode
     * @param distance
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value="zips/{zipCode}",
            method = RequestMethod.GET
    )
    public ResponseEntity<List<ZipInfo>> getZipInfo(@PathVariable("zipCode") int zipCode,
                                                    @RequestParam("distance") int distance) throws NotFoundException {
        return new ResponseEntity<>(service.getZipInfoByRadius(zipCode, distance), HttpStatus.OK);
    }

    //gets all zip codes within a given zip

    /**
     *
     * @param zipCode
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value="zips/{zipCode}/routes",
                    method = RequestMethod.GET
    )
    public ResponseEntity<ZipRoutes> getZipRoutes(@PathVariable("zipCode") String zipCode) throws NotFoundException {
        return new ResponseEntity<>(
                service.getZipRoutes(zipCode),
                HttpStatus.OK
        );
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
}
