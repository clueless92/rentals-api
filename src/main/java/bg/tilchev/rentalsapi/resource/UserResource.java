package bg.tilchev.rentalsapi.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResource {
    private Integer id;
    private String firstName;
    private String lastName;

    @JsonProperty("first_name")
    public String getFirstName() {
        return this.firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return this.lastName;
    }
}
