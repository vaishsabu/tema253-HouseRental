package at.htlstp.aslan.houserent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import at.htlstp.aslan.houserent.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
