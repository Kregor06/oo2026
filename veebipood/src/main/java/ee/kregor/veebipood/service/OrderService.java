package ee.kregor.veebipood.service;

import ee.kregor.veebipood.entity.Order;
import ee.kregor.veebipood.entity.OrderRow;
import ee.kregor.veebipood.entity.Person;
import ee.kregor.veebipood.entity.Product;
import ee.kregor.veebipood.repository.OrderRepository;
import ee.kregor.veebipood.repository.PersonRepository;
import ee.kregor.veebipood.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private PersonRepository personRepository;
    private ProductRepository productRepository;

    public Order saveOrder(Long personId, String parcelMachine, List<OrderRow> orderRows) {
        Order order = new Order();
        order.setCreated(new Date());
        order.setParcelMachine(parcelMachine);
        //order.setOrderRows(orderRows);
        Person person = personRepository.findById(personId).orElseThrow();
        order.setPerson(person);
        order.setTotal(calculateOrderTotal(orderRows));
        return orderRepository.save(order);
    }

    private double calculateOrderTotal(List<OrderRow> orderRows) {
        double total = 0;
        List<OrderRow> orderRowsInOrder = new ArrayList<>();
        for (OrderRow orderRow : orderRows) {
            Product product = productRepository.findById(orderRow.productId()).orElseThrow();
            total += product.getPrice() * orderRow.quantity();

            OrderRow orderRowInOrder = new OrderRow();
            orderRowInOrder.setProduct(product);
            orderRowInOrder.setQuantity(orderRowDto.quantity());
            orderRowsInOrder.add(Order)
        }
        saveOrder().setOrderRows(orderRowsInOrder);
        return total;
    }
}
