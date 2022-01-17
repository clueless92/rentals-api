package bg.tilchev.rentalsapi.controller;

import bg.tilchev.rentalsapi.exception.RentalException;
import bg.tilchev.rentalsapi.resource.RentalResource;
import bg.tilchev.rentalsapi.service.RentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
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
    public ResponseEntity<List<RentalResource>> getUsers(
            @RequestParam(required = false) Map<String, String> criteria) {
        log.debug(criteria.toString());
        return ResponseEntity.ok(rentalService.getRentals(criteria));
    }

    @ExceptionHandler({ RentalException.class })
    public ResponseEntity handleException(RentalException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
