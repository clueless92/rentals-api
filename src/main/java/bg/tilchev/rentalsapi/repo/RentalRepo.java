package bg.tilchev.rentalsapi.repo;

import bg.tilchev.rentalsapi.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepo extends JpaRepository<Rental, Integer> {

}
