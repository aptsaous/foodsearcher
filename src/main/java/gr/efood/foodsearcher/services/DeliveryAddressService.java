package gr.efood.foodsearcher.services;

import gr.efood.foodsearcher.model.Address;
import gr.efood.foodsearcher.model.DeliveryAddress;
import gr.efood.foodsearcher.model.GeoCoordinates;
import gr.efood.foodsearcher.repositories.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DeliveryAddressService {

    private final AddressRepository addressRepository;
    private final GeoCodingService geoCodingService;

    public DeliveryAddressService(AddressRepository addressRepository, GeoCodingService geoCodingService) {
        this.addressRepository = addressRepository;
        this.geoCodingService = geoCodingService;
    }


    public String getPrimaryAddress() {
        log.info("Retrieving the primary address");
        Optional<DeliveryAddress> primaryAddrs = addressRepository.findByIsPrimaryTrue();
        if (primaryAddrs.isPresent()) {
            log.info("Primary address: [{}]", primaryAddrs.get());
            String currentAddrs = "Current address: %s";
            return String.format(currentAddrs, primaryAddrs.get().getAddress().toFormattedString());
        } else {
            log.info("No primary address has been set");
            return "";
        }
    }

    @Transactional
    public void setPrimaryAddress(Address address) {
        log.info("Setting new primary address: [{}]", address);
        Optional<DeliveryAddress> deliveryAddress = addressRepository.findById(address);
        if (deliveryAddress.isEmpty()) {
            log.info("Address doesn't exist in DB: [{}]", address);
            GeoCoordinates coordinates = geoCodingService.getCoordinates(address);
            log.info("Resetting primary address");
            addressRepository.resetPrimaryAddress(false);
            DeliveryAddress addr = new DeliveryAddress(address, true, coordinates);
            log.info("Saving new address in DB and marking it as primary: [{}]", addr);
            addressRepository.save(addr);
            log.info("Address has been successfully stored in DB");
            log.info("New primary address has been set: [{}]", address);
        }
    }
}
