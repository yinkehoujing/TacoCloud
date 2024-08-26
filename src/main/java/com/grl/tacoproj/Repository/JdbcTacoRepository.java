package com.grl.tacoproj.Repository;

import com.grl.tacoproj.Pojo.Ingredient;
import com.grl.tacoproj.Pojo.Taco;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements ITacoRepository {
   private JdbcTemplate jdbc;

   public JdbcTacoRepository(JdbcTemplate jdbc){
       this.jdbc = jdbc;
   }

   @Override
    public Taco save(Taco taco){
       long tacoId = saveTacoInfo(taco);
       taco.setId(tacoId);
       for(Ingredient ingredient : taco.getIngredients()){
           saveIngredientToTaco(ingredient, tacoId);
       }
       return taco;
   }

    private long saveTacoInfo(Taco taco) {
       taco.setCreateAt(new Date());
        PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(
                "insert into Taco (name, create_at) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP
        );

        // Ensure it returns generated keys
        pscFactory.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        new Timestamp(taco.getCreateAt().getTime())
                )
        );
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        if(keyHolder.getKey() == null) return 212121;
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
       jdbc.update(
               "insert into Taco_Ingredients (taco, ingredient)" +
                       "values (?, ?)",
               tacoId, ingredient.getId()
       );
    }
}
