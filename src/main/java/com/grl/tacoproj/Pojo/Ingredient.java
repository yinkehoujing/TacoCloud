package com.grl.tacoproj.Pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;

@Data
@RequiredArgsConstructor
public class Ingredient {
    private final String id;
    private final String name; // nearly a type
    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
