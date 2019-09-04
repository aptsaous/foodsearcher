package gr.efood.foodsearcher.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
public class Address implements Serializable {
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String suburb;

    public String toFormattedString() {
        return String.format("%s %s, %s, %s", streetName, streetNumber, postalCode, suburb);
    }
}