package bg.tilchev.rentalsapi.resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationResource {
    String city;
    String state;
    String zip;
    String country;
    Double lat;
    Double lng;
}
