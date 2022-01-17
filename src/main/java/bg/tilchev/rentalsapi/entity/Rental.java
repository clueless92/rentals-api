package bg.tilchev.rentalsapi.entity;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "rentals", schema = "public")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "sleeps")
    private Integer sleeps;

    @Column(name = "price_per_day")
    private Long pricePerDay;

    @Column(name = "home_city")
    private String homeCity;

    @Column(name = "home_state")
    private String homeState;

    @Column(name = "home_zip")
    private String homeZip;

    @Column(name = "home_country")
    private String homeCountry;

    @Column(name = "vehicle_make")
    private String vehicleMake;

    @Column(name = "vehicle_model")
    private String vehicleModel;

    @Column(name = "vehicle_year")
    private Integer vehicleYear;

    @Column(name = "vehicle_length", columnDefinition = "NUMERIC(4,2)")
    private Float vehicleLength;

    @Column(name = "updated", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @UpdateTimestamp
    private OffsetDateTime updated;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "primary_image_url")
    private String primaryImageUrl;
}
