package bg.tilchev.rentalsapi.service;

import bg.tilchev.rentalsapi.entity.Rental;
import bg.tilchev.rentalsapi.resource.LocationResource;
import bg.tilchev.rentalsapi.resource.PriceResource;
import bg.tilchev.rentalsapi.resource.RentalResource;
import bg.tilchev.rentalsapi.resource.UserResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalMapper {

    public RentalResource mapRentalEntityToResource(Rental rentalEntity) {
        return RentalResource.builder()
                .id(rentalEntity.getId())
                .name(rentalEntity.getName())
                .description(rentalEntity.getDescription())
                .type(rentalEntity.getType())
                .make(rentalEntity.getVehicleMake())
                .model(rentalEntity.getVehicleModel())
                .year(rentalEntity.getVehicleYear())
                .length(rentalEntity.getVehicleLength())
                .sleeps(rentalEntity.getSleeps())
                .primaryImageUrl(rentalEntity.getPrimaryImageUrl())
                .price(PriceResource.builder().day(rentalEntity.getPricePerDay()).build())
                .location(LocationResource.builder()
                        .city(rentalEntity.getHomeCity())
                        .state(rentalEntity.getHomeState())
                        .zip(rentalEntity.getHomeZip())
                        .country(rentalEntity.getHomeCountry())
                        .lat(rentalEntity.getLat())
                        .lng(rentalEntity.getLng())
                        .build())
                .user(UserResource.builder()
                        .id(rentalEntity.getUser().getId())
                        .firstName(rentalEntity.getUser().getFirstName())
                        .lastName(rentalEntity.getUser().getLastName())
                        .build())
                .build();
    }

    public List<RentalResource> mapRentalEntitiesToResources(List<Rental> rentalEntities) {
        return rentalEntities.stream().map(this::mapRentalEntityToResource).collect(Collectors.toList());
    }
}
