package gr.efood.foodsearcher.repositories;

import gr.efood.foodsearcher.model.Address;
import gr.efood.foodsearcher.model.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<DeliveryAddress, Address> {

    Optional<DeliveryAddress> findByIsPrimaryTrue();

    @Modifying
    @Query("UPDATE DeliveryAddress addr SET addr.isPrimary = ?1")
    void resetPrimaryAddress(Boolean isPrimary);
}
