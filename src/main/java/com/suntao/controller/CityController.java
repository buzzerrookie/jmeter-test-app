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

@RequestMapping(("/city"))
@RestController
public class CityController {
    private final AtomicInteger id = new AtomicInteger(6);
    private final List<City> cities = new CopyOnWriteArrayList<>(
            Arrays.asList(new City(1, "China", "Beijing"), new City(2, "America", "New York"),
                    new City(3, "France", "Paris"), new City(4, "Japan", "Tokyo"),
                    new City(5, "Germany", "Berlin")));

    @GetMapping
    public List<City> getCities() {
        return this.cities;
    }

    @PostMapping
    public City newCity(@RequestBody City city) {
        city.setId(id.getAndIncrement());
        cities.add(city);
        return city;
    }
}
