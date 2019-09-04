package gr.efood.foodsearcher.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class GeoCoordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double lat;
    private double lng;
}
