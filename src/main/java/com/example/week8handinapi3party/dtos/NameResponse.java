package com.example.week8handinapi3party.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NameResponse {

    String name;
    String gender;
    double genderProbability;
    int age;
    int ageCount;
    String country;
    double countryProbability;

    public void setGenderProbability(double p){
        this.genderProbability = p*100;
    }

    public void setCountryProbability(double p){
        this.countryProbability = p*100;
    }
}
