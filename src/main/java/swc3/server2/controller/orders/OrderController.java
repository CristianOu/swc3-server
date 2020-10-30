package swc3.server2.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server2.model.Order;
import swc3.server2.model.OrderItem;
import swc3.server2.model.OrderItemNote;
import swc3.server2.repository.OrderItemNoteRepository;
import swc3.server2.repository.OrderItemRepository;
import swc3.server2.repository.OrderRepository;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderItemNoteRepository orderItemNoteRepository;

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order _order = orderRepository.save(order);

        Collection<OrderItem> orderItems = order.getOrderItemsByOrderId();
        for (OrderItem orderItem:orderItems) {
            orderItem.setOrderId(_order.getOrderId());
            OrderItem _orderItem = orderItemRepository.save(orderItem);

            Collection<OrderItemNote> orderItemNotes = orderItem.getOrderItemNotes();

            for (OrderItemNote orderItemNote:orderItemNotes) {
                orderItemNote.setOrderItems(_orderItem);
                orderItemNoteRepository.save(orderItemNote);
            }
        }

        return new ResponseEntity<>(_order, HttpStatus.CREATED);
    }
}
