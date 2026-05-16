package ee.kregor.rentalstore.repository;

import ee.kregor.rentalstore.entity.Rental;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<@NonNull Rental,@NonNull Long> {
}
