package com.grl.tacoproj.Controller;

import com.grl.tacoproj.Pojo.Order;
import com.grl.tacoproj.Repository.IOrderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

// first time forget to autowire the orderRepo
@Slf4j // a simple way to log
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
    @Autowired
    private IOrderRepository orderRepository;
   @GetMapping("/current")
    public String orderForm(@ModelAttribute("order") Order order){ // from model to order
//       model.addAttribute("order", new Order()); // prevent to be overridden
       return "orderForm";
   }

   @PostMapping // to deal with /orders, attention to the html
    public String processOrder(@Valid  Order order, Errors errors, SessionStatus sessionStatus){ // the timing to check is before the call
       if(errors.hasErrors()){
           return "orderForm"; // stay here
       }
       orderRepository.save(order);
       sessionStatus.setComplete(); // an order was completed, session reset
       log.info("Order submitted:" + order); // to_string was provided by lombok
      return "redirect:/design" ;
   }
}
