package gr.efood.foodsearcher.services;

import gr.efood.foodsearcher.model.Address;
import gr.efood.foodsearcher.model.GeoCoordinates;

public interface GeoCodingService {

    GeoCoordinates getCoordinates(Address address);
}
