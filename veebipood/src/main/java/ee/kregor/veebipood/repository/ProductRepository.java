package ee.kregor.veebipood.repository;

import ee.kregor.veebipood.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
