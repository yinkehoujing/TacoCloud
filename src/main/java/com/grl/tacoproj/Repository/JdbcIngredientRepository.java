package com.grl.tacoproj.Repository;

import com.grl.tacoproj.Pojo.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IIngredientRepository{
    private JdbcTemplate jdbc;
    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
   @Override
    public Iterable<Ingredient> findAll(){
        return jdbc.query(
                "select id, name, type from Ingredient",
                this::mapRowToIngredient
        );
   }

    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject(
               "select id, name, type from Ingredient where id=?",
                this::mapRowToIngredient, id
        );
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update(
                "insert into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString() // means Capitalized
        );
        return ingredient;
    }

   // map (1,1,1,1) row to a pojo
    private Ingredient mapRowToIngredient(ResultSet resultSet, int i)
        throws SQLException {
       return new Ingredient(
               resultSet.getString("id"), // by label
               resultSet.getString("name"),
               Ingredient.Type.valueOf(resultSet.getString("type"))
       );
    }

}
