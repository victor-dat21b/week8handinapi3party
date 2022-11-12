package com.example.week8handinapi3party.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Nationality {

    ArrayList<Country> country;
    String name;
}
