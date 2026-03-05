package ee.kregor.veebipood.controller;

import ee.kregor.veebipood.entity.Order;
import ee.kregor.veebipood.entity.OrderRow;
import ee.kregor.veebipood.repository.OrderRepository;
import ee.kregor.veebipood.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @GetMapping("orders")
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    @DeleteMapping("orders/{id}")
    public List<Order> deleteOrder(@PathVariable Long id){
        orderRepository.deleteById(id); // kustutan
        return orderRepository.findAll(); // uuenenud seis
    }

    @PostMapping("orders")
    public Order addOrder(@RequestParam Long personId,
                                @RequestParam(required = false) String parcelMachine,
                                @RequestBody List<OrderRow> orderRows){
        return orderService.saveOrder(personId, parcelMachine, orderRows); // siin salvestab
        //return orderRepository.findAll(); // siin on uuenenud seis
    }
}