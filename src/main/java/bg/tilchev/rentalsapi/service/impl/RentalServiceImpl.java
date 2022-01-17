package bg.tilchev.rentalsapi.service.impl;

import bg.tilchev.rentalsapi.exception.RentalException;
import bg.tilchev.rentalsapi.repo.RentalRepo;
import bg.tilchev.rentalsapi.resource.RentalResource;
import bg.tilchev.rentalsapi.service.RentalMapper;
import bg.tilchev.rentalsapi.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepo rentalRepo;
    private final RentalMapper rentalMapper;

    public List<RentalResource> getRentals() {
        return rentalMapper.mapRentalEntitiesToResources(rentalRepo.findAll());
    }

    public RentalResource getRental(Integer id) {
        return rentalMapper.mapRentalEntityToResource(rentalRepo.findById(id)
                .orElseThrow(() -> new RentalException()));
    }
}
