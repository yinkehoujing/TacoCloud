package com.grl.tacoproj.Pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//@Entity
public class Order {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "name missed")
    @Column(name = "delivery_name")
    @JsonProperty("delivery_name")
    private String name;
    @Column(name = "delivery_street")
    @JsonProperty("delivery_street")
    @NotBlank(message = "street missed")
    private String street;
    @Column(name = "delivery_city")
    @JsonProperty("delivery_city")
    @NotBlank(message = "city missed")
    private String city;
    @Column(name = "delivery_state")
    @JsonProperty("delivery_state")
    @NotBlank(message = "stated missed")
    private String state;
    @Column(name = "delivery_zip")
    @NotBlank(message = "zip missed")
    @JsonProperty("delivery_zip")
    private String zip;
    @JsonProperty("cc_number")
    private String ccNumber; // also a string type
    @Pattern(
            regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message = "Must be formatted MM/YY"
    )
    @JsonProperty("cc_expiration")
    private String ccExpiration;
    @Digits(integer = 3,fraction = 0, message = "invalid cvv")
    @JsonProperty("cc_cVV")
    private String ccCVV;

    @OneToMany
    List<Taco> tacos = new ArrayList<>();

    @JsonProperty("place_at")
    private Date placeAt;

    public void addDesign(Taco saved) {
        tacos.add(saved);
        
    }
}
