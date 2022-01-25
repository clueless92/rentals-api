package bg.tilchev.rentalsapi.controller;

import bg.tilchev.rentalsapi.resource.RentalResource;
import bg.tilchev.rentalsapi.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RentalControllerTest {

    @Mock
    private RentalService rentalService;

    @InjectMocks
    private RentalController testedController;

    @BeforeEach
    public void setup() {
        when(rentalService.getRental(anyInt())).thenReturn(RentalResource.builder().build());
    }

    @Test
    public void whenGetRentalsShouldCallService() {
        List<RentalResource> expectedRentals = Collections.singletonList(RentalResource.builder().build());
        when(rentalService.getRentals(any())).thenReturn(expectedRentals);

        ResponseEntity<List<RentalResource>> actual = testedController.getRentals(Collections.emptyMap());

        verify(rentalService, times(1)).getRentals(any());
        assertEquals(expectedRentals, actual.getBody());
    }

    @Test
    public void whenGetRentalShouldCallService() {
        RentalResource expectedRental = RentalResource.builder().build();
        when(rentalService.getRental(anyInt())).thenReturn(expectedRental);

        ResponseEntity<RentalResource> actual = testedController.getRental(1);

        verify(rentalService, times(1)).getRental(anyInt());
        assertEquals(expectedRental, actual.getBody());
    }
}
