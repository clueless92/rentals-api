package bg.tilchev.rentalsapi.resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceResource {
    private Long day;
}
