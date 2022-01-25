package bg.tilchev.rentalsapi.service;

import bg.tilchev.rentalsapi.entity.Rental;
import bg.tilchev.rentalsapi.exception.RentalException;
import bg.tilchev.rentalsapi.repo.RentalRepo;
import bg.tilchev.rentalsapi.resource.LocationResource;
import bg.tilchev.rentalsapi.resource.PriceResource;
import bg.tilchev.rentalsapi.resource.RentalResource;
import bg.tilchev.rentalsapi.service.impl.RentalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RentalServiceTest {

    private static final List<RentalResource> FAKE_RENTALS = getFakeRentals();

    @Mock
    private RentalRepo rentalRepo;
    @Mock
    private RentalMapper rentalMapper;
    @InjectMocks
    private RentalServiceImpl rentalService;

    @BeforeEach
    public void setup() {
        when(rentalMapper.mapRentalEntitiesToResources(any())).thenReturn(FAKE_RENTALS);
    }

    @Test
    public void givenNoFiltersWhenGetRentalsThenAllRentals() {
        List<RentalResource> rentals = rentalService.getRentals(Collections.emptyMap());

        verify(rentalMapper, times(1)).mapRentalEntitiesToResources(any());
        verify(rentalRepo, times(1)).findAll();
        assertEquals(3, rentals.size());
        assertEquals(FAKE_RENTALS, rentals);
    }

    @Test
    public void givenOffsetAndLimitWhenGetRentalsThenOnlyMiddleRental() {
        Map<String, String> criteria = new LinkedHashMap<>();
        criteria.put("limit", "1");
        criteria.put("offset", "1");

        List<RentalResource> rentals = rentalService.getRentals(criteria);

        verify(rentalMapper, times(1)).mapRentalEntitiesToResources(any());
        verify(rentalRepo, times(1)).findAll();
        assertEquals(1, rentals.size());
        assertEquals(3, rentals.get(0).getId());
    }

    @Test
    public void givenMissingIdFilterWhenGetRentalsThenEmptyList() {
        Map<String, String> criteria = Collections.singletonMap("ids", "4");

        List<RentalResource> rentals = rentalService.getRentals(criteria);

        verify(rentalMapper, times(1)).mapRentalEntitiesToResources(any());
        verify(rentalRepo, times(1)).findAll();
        assertEquals(Collections.emptyList(), rentals);
    }

    @Test
    public void givenPresentIdFilterWhenGetRentalsThenExpectedRentals() {
        Map<String, String> criteria = Collections.singletonMap("ids", "1,2,4");

        List<RentalResource> rentals = rentalService.getRentals(criteria);

        verify(rentalMapper, times(1)).mapRentalEntitiesToResources(any());
        verify(rentalRepo, times(1)).findAll();
        assertEquals(2, rentals.size());
        assertEquals(2, rentals.get(0).getId());
        assertEquals(1, rentals.get(1).getId());
    }

    @Test
    public void givenNaNIdWhenGetRentalsThenRentalsException() {
        Map<String, String> criteria = Collections.singletonMap("ids", "abc");

        assertThrowsExactly(RentalException.class, () -> rentalService.getRentals(criteria));
        verify(rentalMapper, times(1)).mapRentalEntitiesToResources(any());
        verify(rentalRepo, times(1)).findAll();
    }

    @Test
    public void givenPriceMinAndMaxWhenGetRentalsThenOnly2000PricedRental() {
        Map<String, String> criteria = new LinkedHashMap<>();
        criteria.put("price_min", "1500");
        criteria.put("price_max", "2500");

        List<RentalResource> rentals = rentalService.getRentals(criteria);

        verify(rentalMapper, times(1)).mapRentalEntitiesToResources(any());
        verify(rentalRepo, times(1)).findAll();
        assertEquals(1, rentals.size());
        assertEquals(2000L, rentals.get(0).getPrice().getDay());
    }

    @Test
    public void givenNearWhenGetRentalsThenOnlyFirstRental() {
        Map<String, String> criteria = Collections.singletonMap("near", "133.64,-117.93");

        List<RentalResource> rentals = rentalService.getRentals(criteria);

        verify(rentalMapper, times(1)).mapRentalEntitiesToResources(any());
        verify(rentalRepo, times(1)).findAll();
        assertEquals(1, rentals.size());
        assertEquals(1, rentals.get(0).getId());
    }

    @Test
    public void givenSortByIdWhenGetRentalsThenRentalsOrderedById() {
        Map<String, String> criteria = Collections.singletonMap("sort", "id");

        List<RentalResource> rentals = rentalService.getRentals(criteria);

        verify(rentalMapper, times(1)).mapRentalEntitiesToResources(any());
        verify(rentalRepo, times(1)).findAll();
        assertEquals(3, rentals.size());
        assertEquals(1, rentals.get(0).getId());
        assertEquals(2, rentals.get(1).getId());
        assertEquals(3, rentals.get(2).getId());
    }

    @Test
    public void givenSortByPriceWhenGetRentalsThenRentalsOrderedByPrice() {
        Map<String, String> criteria = Collections.singletonMap("sort", "price");

        List<RentalResource> rentals = rentalService.getRentals(criteria);

        verify(rentalMapper, times(1)).mapRentalEntitiesToResources(any());
        verify(rentalRepo, times(1)).findAll();
        assertEquals(3, rentals.size());
        assertEquals(1000L, rentals.get(0).getPrice().getDay());
        assertEquals(2000L, rentals.get(1).getPrice().getDay());
        assertEquals(3000L, rentals.get(2).getPrice().getDay());
    }

    @Test
    public void givenSortByUnknownWhenGetRentalsThenRentalsException() {
        Map<String, String> criteria = Collections.singletonMap("sort", "UNKNOWN");

        assertThrowsExactly(RentalException.class, () -> rentalService.getRentals(criteria));
        verify(rentalMapper, times(1)).mapRentalEntitiesToResources(any());
        verify(rentalRepo, times(1)).findAll();
    }

    @Test
    public void givenPresentRentalWhenGetRentalThenCallServiceAndMapper() {
        when(rentalRepo.findById(any())).thenReturn(Optional.of(new Rental()));
        when(rentalMapper.mapRentalEntityToResource(any())).thenReturn(FAKE_RENTALS.get(2));
        RentalResource rental = rentalService.getRental(1);

        verify(rentalMapper, times(1)).mapRentalEntityToResource(any());
        verify(rentalRepo, times(1)).findById(anyInt());
        assertEquals(FAKE_RENTALS.get(2), rental);
    }

    @Test
    public void givenNullRentalWhenGetRentalThenException() {
        when(rentalRepo.findById(any())).thenReturn(Optional.empty());
        when(rentalMapper.mapRentalEntityToResource(any())).thenReturn(FAKE_RENTALS.get(2));

        assertThrowsExactly(RentalException.class, () -> rentalService.getRental(1));
        verify(rentalMapper, never()).mapRentalEntityToResource(any());
        verify(rentalRepo, times(1)).findById(anyInt());
    }

    private static List<RentalResource> getFakeRentals() {
        RentalResource rental1 = RentalResource.builder()
                .id(1)
                .price(PriceResource.builder().day(1000L).build())
                .location(LocationResource.builder().lat(133.64d).lng(-117.93d).build())
                .build();
        RentalResource rental2 = RentalResource.builder()
                .id(2)
                .price(PriceResource.builder().day(2000L).build())
                .location(LocationResource.builder().lat(233.64d).lng(-217.93d).build())
                .build();
        RentalResource rental3 = RentalResource.builder()
                .id(3)
                .price(PriceResource.builder().day(3000L).build())
                .location(LocationResource.builder().lat(333.64d).lng(-317.93d).build())
                .build();
        return Arrays.asList(rental2, rental3, rental1);
    }
}
