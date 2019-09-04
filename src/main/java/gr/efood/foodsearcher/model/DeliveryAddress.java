package gr.efood.foodsearcher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAddress {

    @EmbeddedId
    private Address address;

    private Boolean isPrimary;

    @OneToOne(cascade = CascadeType.ALL)
    private GeoCoordinates geoCoordinates;
}
