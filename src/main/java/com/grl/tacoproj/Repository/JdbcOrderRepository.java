package com.grl.tacoproj.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grl.tacoproj.Pojo.Order;
import com.grl.tacoproj.Pojo.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements IOrderRepository{
   // use simpleJdbcInsert
    private SimpleJdbcInsert orderInserter; // by a map
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc){
        this.orderInserter =
                new SimpleJdbcInsert(jdbc)
                        .withTableName("Taco_Order")
                        .usingGeneratedKeyColumns("id"); // id from database
        this.orderTacoInserter =
                new SimpleJdbcInsert(jdbc)
                        .withTableName("Taco_Order_Tacos"); // not declare how to generate the id
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order){
       order.setPlaceAt(new Date());
       long orderId = saveOrderDetails(order); // to hide the details in another func
       order.setId(orderId);
       List<Taco> tacos = order.getTacos();
       for(Taco taco : tacos){
           saveTacoToOrder(taco, orderId);
       }
       return order;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        // needless to convert an object
        Map<String, Object> values = new HashMap<>();
        values.put("taco_order", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }

    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values =
                objectMapper.convertValue(order, Map.class);
        values.put("place_at", order.getPlaceAt()); // column_name, value
        // the statement above is for a change
        long orderId =
                orderInserter
                        .executeAndReturnKey(values)
                        .longValue();

        return orderId;

    }


}
