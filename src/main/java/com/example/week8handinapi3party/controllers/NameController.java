package com.example.week8handinapi3party.controllers;

import com.example.week8handinapi3party.dtos.Age;
import com.example.week8handinapi3party.dtos.Gender;
import com.example.week8handinapi3party.dtos.NameResponse;
import com.example.week8handinapi3party.dtos.Nationality;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class NameController {

    @RequestMapping("/name-info")
    public NameResponse getDetails(@RequestParam String name){
        Mono<Age> age = Mono.just(new Age());//Lav en getAge(String name)
        Mono<Gender> gender = Mono.just(new Gender());//Lav en getGender(String name)
        Mono<Nationality> nationality = Mono.just(new Nationality()); //Lav en getNationality(String name)

        var resMono = Mono.zip(age, gender, nationality).map(t -> {
            NameResponse ns = new NameResponse();
            ns.setAge(t.getT1().getAge());
            ns.setAgeCount(t.getT1().getCount());

            ns.setGender(t.getT2().getGender());
            ns.setGenderProbability(t.getT2().getProbability());

            //ns.setCountry(t.getT3().getCountry().get(0).getCountry_id());
            //ns.setCountryProbability(t.getT3().getCountry().get(0).getProbability());

            return ns;
        });
        NameResponse res = resMono.block();
        res.setName(name);
        return res;
    }
}
