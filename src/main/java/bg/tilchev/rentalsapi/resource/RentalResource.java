package bg.tilchev.rentalsapi.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RentalResource {
    private Integer id;
    private String name;
    private String description;
    private String type;
    private String make;
    private String model;
    private Integer year;
    private Float length;
    private Integer sleeps;
    private String primaryImageUrl;
    private PriceResource price;
    private LocationResource location;
    private UserResource user;

    @JsonProperty("primary_image_url")
    public String getPrimaryImageUrl() {
        return this.primaryImageUrl;
    }
}
