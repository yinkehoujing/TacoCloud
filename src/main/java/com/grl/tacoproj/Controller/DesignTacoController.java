package com.grl.tacoproj.Controller;

import com.grl.tacoproj.Pojo.Ingredient;
import com.grl.tacoproj.Pojo.Order;
import com.grl.tacoproj.Pojo.Taco;
import com.grl.tacoproj.Repository.IIngredientRepository;
import com.grl.tacoproj.Repository.ITacoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.grl.tacoproj.Repository.IIngredientRepository;

@Slf4j
@Controller // to handle web request
@RequestMapping("/design")
@SessionAttributes("order") // use it to add different tacos to one order
public class DesignTacoController {
    private final IIngredientRepository ingredientRepository; // to deal with 'get'
    private ITacoRepository designRepo;
    @Autowired
    public DesignTacoController(IIngredientRepository ingredientRepository, ITacoRepository tacoRepository){
        this.ingredientRepository = ingredientRepository;
        this.designRepo = tacoRepository;
    }

    @GetMapping
    public String showDesignForm(Model model){ // model for a single class
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(i -> ingredients.add(i));
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types){
            model.addAttribute(type.toString().toLowerCase(),
                 ingredients.stream()
                    .filter(x -> x.getType().equals(type))
                         .collect(Collectors.toList())
            );
        }

        model.addAttribute("design", new Taco());
        return "design"; // acts as the view name
    }
    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @PostMapping
    public String processDesign(
            @Valid Taco design, Errors errors,
            @ModelAttribute Order order){ // bind design to order
        if(errors.hasErrors()){
            return "design";
        }
        Taco saved = designRepo.save(design);
        order.addDesign(saved); // the order in session
        return "redirect:/orders/current"; // to pay for this design
    }
}
