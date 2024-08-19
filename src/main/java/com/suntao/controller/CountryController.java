package com.suntao.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(("/country"))
@RestController
public class CountryController {
    private final AtomicInteger id = new AtomicInteger(6);
    private final List<Country> countries = new CopyOnWriteArrayList<>(
            Arrays.asList(new Country(1, "China", "Asia"),
                    new Country(2, "America", "North America"), new Country(3, "France", "Europe"),
                    new Country(4, "Japan", "Asia"), new Country(5, "Germany", "Europe")));

    @GetMapping
    public List<Country> getCountries() {
        return this.countries;
    }

    @PostMapping
    public Country newCountry(@RequestBody Country country) {
        country.setId(id.getAndIncrement());
        countries.add(country);
        return country;
    }
}
