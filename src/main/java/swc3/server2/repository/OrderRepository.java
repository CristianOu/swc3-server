package swc3.server2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server2.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
