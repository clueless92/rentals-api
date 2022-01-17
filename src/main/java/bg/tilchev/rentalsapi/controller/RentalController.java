package bg.tilchev.rentalsapi.controller;

import bg.tilchev.rentalsapi.exception.RentalException;
import bg.tilchev.rentalsapi.resource.RentalResource;
import bg.tilchev.rentalsapi.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/rentals")
public class RentalController {

    private final RentalService rentalService;

    @GetMapping("/{id}")
    public ResponseEntity<RentalResource> getUsers(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.getRental(id));
    }

    @GetMapping
    public ResponseEntity<List<RentalResource>> getUsers() {
        return ResponseEntity.ok(rentalService.getRentals());
    }

    @ExceptionHandler({ RentalException.class })
    public ResponseEntity handleException(RentalException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
