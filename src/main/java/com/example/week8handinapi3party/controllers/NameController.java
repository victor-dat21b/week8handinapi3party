package com.example.week8handinapi3party.controllers;

import com.example.week8handinapi3party.dtos.Age;
import com.example.week8handinapi3party.dtos.Gender;
import com.example.week8handinapi3party.dtos.NameResponse;
import com.example.week8handinapi3party.dtos.Nationality;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class NameController {

    @RequestMapping("/name-info")
    public NameResponse getDetails(@RequestParam String name){
        Mono<Age> age = callAgeApi(name);
        Mono<Gender> gender = callGenderApi(name);
        Mono<Nationality> nationality = callNationalityApi(name);

        var resMono = Mono.zip(age, gender, nationality).map(t -> {
            NameResponse ns = new NameResponse();
            ns.setAge(t.getT1().getAge());
            ns.setAgeCount(t.getT1().getCount());

            ns.setGender(t.getT2().getGender());
            ns.setGenderProbability(t.getT2().getProbability());

            ns.setCountry(t.getT3().getCountry().get(0).getCountry_id());
            ns.setCountryProbability(t.getT3().getCountry().get(0).getProbability());

            return ns;
        });
        NameResponse res = resMono.block();
        res.setName(name);
        return res;
    }


        public Mono<Age> callAgeApi(String age){
            Mono<Age> ageResponse = WebClient.create()
                    .get()
                    .uri("https://api.agify.io?name="+age)
                    .retrieve()
                    .bodyToMono(Age.class)
                    .doOnError(e-> System.out.println("UUUPS : "+e.getMessage()));
            return ageResponse;
        }
    public Mono<Gender> callGenderApi(String gender){
        Mono<Gender> genderResponse = WebClient.create()
                .get()
                .uri("https://api.genderize.io?name="+gender)
                .retrieve()
                .bodyToMono(Gender.class)
                .doOnError(e-> System.out.println("UUUPS : "+e.getMessage()));
        return genderResponse;
    }
    public Mono<Nationality> callNationalityApi(String nationality){
        Mono<Nationality> nationalityResponse = WebClient.create()
                .get()
                .uri("https://api.nationalize.io?name="+nationality)
                .retrieve()
                .bodyToMono(Nationality.class)
                .doOnError(e-> System.out.println("UUUPS : "+e.getMessage()));
        return nationalityResponse;
    }






}
