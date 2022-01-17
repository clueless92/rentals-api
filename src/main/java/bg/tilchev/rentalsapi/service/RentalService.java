package bg.tilchev.rentalsapi.service;

import bg.tilchev.rentalsapi.resource.RentalResource;

import java.util.List;
import java.util.Map;

public interface RentalService {
    List<RentalResource> getRentals(Map<String, String> criteria);
    RentalResource getRental(Integer id);
}
