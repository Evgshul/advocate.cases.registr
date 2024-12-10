package com.lv.adv.cass.regstr.repository;

import com.lv.adv.cass.regstr.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByRegistrationNumber(String registrationNumber);
    Optional<Customer> findByDeclaredAddress(String declaredAddress);
    Optional<Customer> findByActualAddress(String actualAddress);
    Optional<Customer> findByPersonId(UUID personId);

    /**
     * Global search by all params, exclude address
     * @param companyName Company name
     * @param registrationNumber registration ID
     * @param phone phone number
     * @param email company email
     * @param personIdentifier ..
     * @param representativeIdentifier ..
     * @return List of Customers according to search criteria
     */
    @Query("SELECT c from Customer c " +
            "LEFT JOIN c.person p " +
            "LEFT JOIN c.representative r " +
            "WHERE (:companyName IS NULL OR LOWER(c.companyName) LIKE LOWER(concat('%', :companyName, '%'))) " +
            "AND (:registrationNumber IS NULL OR LOWER(c.registrationNumber) LIKE LOWER(concat('%', :registrationNumber, '%'))) " +
            "AND (:phone IS null OR c.phone LIKE concat('%', :phone, '%')) " +
            "and (:email IS NULL OR c.email like concat('%', :email, '%')) " +
            "and (:personIdentifier IS NULL OR p.identifier like concat('%', :identifier, '%')) " +
            "and (:representativeIdentifier IS NULL OR r.identifier LIKE CONCAT('%', :representativeIdentifier, '%'))")
    List<Customer> searchCustomers(
            @Param("companyName") String companyName,
            @Param("registrationNumber") String registrationNumber,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("personIdentifier") String personIdentifier,
            @Param("representativeIdentifier") String representativeIdentifier);

}
