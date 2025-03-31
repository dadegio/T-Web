package com.andrianigiordano.springboot.countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountriesService {

    @Autowired
    private CountriesRepository countriesRepository;


}
