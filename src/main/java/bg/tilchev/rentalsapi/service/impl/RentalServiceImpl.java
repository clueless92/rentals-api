package bg.tilchev.rentalsapi.service.impl;

import bg.tilchev.rentalsapi.exception.RentalException;
import bg.tilchev.rentalsapi.repo.RentalRepo;
import bg.tilchev.rentalsapi.resource.RentalResource;
import bg.tilchev.rentalsapi.service.RentalMapper;
import bg.tilchev.rentalsapi.service.RentalService;
import bg.tilchev.rentalsapi.util.DistanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepo rentalRepo;
    private final RentalMapper rentalMapper;

    public List<RentalResource> getRentals(Map<String, String> criteria) {
        List<RentalResource> rentals = rentalMapper.mapRentalEntitiesToResources(rentalRepo.findAll());
        if (criteria.isEmpty()) {
            return rentals;
        }
        Stream<RentalResource> filtered = filter(rentals, criteria);
        Stream<RentalResource> sorted = sort(filtered, criteria);

        return sorted.collect(Collectors.toList());
    }

    public RentalResource getRental(Integer id) {
        return rentalMapper.mapRentalEntityToResource(rentalRepo.findById(id)
                .orElseThrow(RentalException::new));
    }

    private Stream<RentalResource> filter(List<RentalResource> rentalToFilter, Map<String, String> criteria) {
        Stream<RentalResource> rentals = rentalToFilter.stream();
        try {
            for (Map.Entry<String, String> criteriaPair : criteria.entrySet()) {
                if ("price_min".equals(criteriaPair.getKey())) {
                    rentals = rentals.filter(r -> r.getPrice().getDay() >= Long.parseLong(criteriaPair.getValue()));
                } else if ("price_max".equals(criteriaPair.getKey())) {
                    rentals = rentals.filter(r -> r.getPrice().getDay() <= Long.parseLong(criteriaPair.getValue()));
                } else if ("ids".equals(criteriaPair.getKey())) {
                    List<Integer> ids = Arrays.stream(criteriaPair.getValue().split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    rentals = rentals.filter(r -> ids.contains(r.getId()));
                } else if ("near".equals(criteriaPair.getKey())) {
                    List<Double> near = Arrays.stream(criteriaPair.getValue().split(","))
                            .map(Double::parseDouble)
                            .collect(Collectors.toList());
                    Double latToTest = near.get(0);
                    Double lngToTest = near.get(1);
                    rentals = rentals.filter(r -> DistanceUtil.arePointsWithing100Miles(
                            latToTest, lngToTest, r.getLocation().getLat(), r.getLocation().getLng()));
                }
            }
            String offset = criteria.get("offset");
            if (offset != null) {
                rentals = rentals.skip(Long.parseLong(offset));
            }
            String limit = criteria.get("limit");
            if (limit != null) {
                rentals = rentals.limit(Long.parseLong(limit));
            }
        } catch (RuntimeException e) {
            throw new RentalException("Wrong filter format or values.");
        }
        return rentals;
    }

    private Stream<RentalResource> sort(Stream<RentalResource> rentalsToSort, Map<String, String> criteria) {
        String fieldToSortBy = criteria.get("sort");
        if (fieldToSortBy == null) {
            return rentalsToSort;
        }
        rentalsToSort = rentalsToSort.sorted((r1, r2) -> {
            switch (fieldToSortBy) {
                case "id":
                    return r1.getId().compareTo(r2.getId());
                case "name":
                    return r1.getName().compareTo(r2.getName());
                case "description":
                    return r1.getDescription().compareTo(r2.getDescription());
                case "type":
                    return r1.getType().compareTo(r2.getType());
                case "make":
                    return r1.getMake().compareTo(r2.getMake());
                case "model":
                    return r1.getModel().compareTo(r2.getModel());
                case "year":
                    return r1.getYear().compareTo(r2.getYear());
                case "length":
                    return r1.getLength().compareTo(r2.getLength());
                case "sleeps":
                    return r1.getSleeps().compareTo(r2.getSleeps());
                case "primary_image_url":
                    return r1.getPrimaryImageUrl().compareTo(r2.getPrimaryImageUrl());
                case "price":
                    return r1.getPrice().getDay().compareTo(r2.getPrice().getDay());
                default:
                    throw new RentalException("Can't sort by field: " + fieldToSortBy);
            }
        });
        return rentalsToSort;
    }
}
