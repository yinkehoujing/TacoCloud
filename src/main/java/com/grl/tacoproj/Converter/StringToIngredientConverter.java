package com.grl.tacoproj.Converter;


import com.grl.tacoproj.Pojo.Ingredient;
import com.grl.tacoproj.Repository.IIngredientRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToIngredientConverter implements Converter<String, Ingredient> {

    private final IIngredientRepository ingredientRepository;

    public StringToIngredientConverter(IIngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findOne(id);
    }
}
