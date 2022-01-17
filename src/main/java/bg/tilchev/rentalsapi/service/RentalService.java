package bg.tilchev.rentalsapi.service;

import bg.tilchev.rentalsapi.resource.RentalResource;

import java.util.List;

public interface RentalService {
    List<RentalResource> getRentals();
    RentalResource getRental(Integer id);
}
