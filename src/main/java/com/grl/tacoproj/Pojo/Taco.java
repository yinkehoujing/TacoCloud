package com.grl.tacoproj.Pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import jakarta.validation.constraints.Size; // to add the dependency
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;


@Data
//@Entity
public class Taco {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull // have a type
    @Size(min=3, message="not less than 3 char")
    private String name;
//    @Column(name = "create_at")
    private Date createAt;
    @Size(min=1, message = "You should add at least one ingredient")
    @ManyToMany // necessary to tell how to map
    private List<Ingredient> ingredients; // why use string?
}
