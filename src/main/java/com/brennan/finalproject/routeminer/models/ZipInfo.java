package com.brennan.finalproject.routeminer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zip_info", schema = "zip")
public class ZipInfo {

    @Id
    private String zip;
    private String location;
    private String county;
    private String metroArea;
    private String landArea;
    private String elevation;
    private int population;
    private int homeValue;
    private int householdIncome;
    private double medianAge;
    private int businesses;
    private int employees;

    public static class ZipInfoBuilder {

        public ZipInfoBuilder population(String population) {
            this.population = Integer.parseInt(population.replaceAll("[^\\d.]+", ""));
            return this;
        }

        public ZipInfoBuilder homeValue(String homeValue) {
            this.homeValue = Integer.parseInt(homeValue.replaceAll("[^\\d.]+", ""));
            return this;
        }

        public ZipInfoBuilder householdIncome(String householdIncome) {
            this.householdIncome = Integer.parseInt(householdIncome.replaceAll("[^\\d.]+", ""));
            return this;
        }

        public ZipInfoBuilder businesses(String businesses) {
            this.businesses = Integer.parseInt(businesses.replaceAll("[^\\d.]+", ""));
            return this;
        }

        public ZipInfoBuilder employees(String employees) {
            this.employees = Integer.parseInt(employees.replaceAll("[^\\d.]+", ""));
            return this;
        }

        public ZipInfoBuilder medianAge(String medianAge) {
            this.medianAge = Double.parseDouble(medianAge.replaceAll("[^\\d.]+", ""));
            return this;
        }
    }

}