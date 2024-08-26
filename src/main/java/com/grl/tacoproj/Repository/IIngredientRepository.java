package com.grl.tacoproj.Repository;

import com.grl.tacoproj.Pojo.Ingredient;

import javax.swing.text.html.Option;

public interface IIngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);

}
