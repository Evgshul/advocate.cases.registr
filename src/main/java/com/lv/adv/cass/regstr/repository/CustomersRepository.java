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

    Optional<Customer> findCustomerByCompanyName(String companyName);
    Optional<Customer> findByRegistrationNumber(String registrationNumber);
    Optional<Customer> findByDeclaredAddress(String declaredAddress);
    Optional<Customer> findByActualAddress(String actualAddress);
    Optional<Customer> findByPersonId(UUID personId);

    /**
     * Global search by all params, exclude address
     * @param keyword for search in Customers
     */
    @Query("SELECT c from Customer c " +
            "LEFT JOIN c.person p " +
            "LEFT JOIN c.representative r " +
            "WHERE (:companyName IS NULL OR LOWER(c.companyName) LIKE LOWER(concat('%', :keyword, '%'))) " +
            "AND (:registrationNumber IS NULL OR LOWER(c.registrationNumber) LIKE LOWER(concat('%', :keyword, '%'))) " +
            "AND (:phone IS null OR c.phone LIKE concat('%', :keyword, '%')) " +
            "and (:email IS NULL OR c.email like concat('%', :keyword, '%')) " +
            "and (:personIdentifier IS NULL OR p.identifier like concat('%', :keyword, '%')) " +
            "and (:representativeIdentifier IS NULL OR r.identifier LIKE CONCAT('%', :keyword, '%'))")
    List<Customer> searchCustomers(
            @Param("keyword") String keyword);

}
