package at.htlstp.aslan.houserent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import at.htlstp.aslan.houserent.model.House;
import at.htlstp.aslan.houserent.model.Rental;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Integer> {

    @Query("SELECT r FROM Rental r WHERE r.km IS NULL AND r.returnDate IS NULL AND r.returnStation IS NULL")
    List<Rental> findRunningRentals();

    List<Rental> findByHouse(House house);

}
