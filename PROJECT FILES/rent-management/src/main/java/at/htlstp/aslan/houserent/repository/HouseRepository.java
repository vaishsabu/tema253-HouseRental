package at.htlstp.aslan.houserent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import at.htlstp.aslan.houserent.model.House;
import at.htlstp.aslan.houserent.model.Station;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, String> {
    List<House> findByStation(Station station);

    // List<House> findByStreetGreaterThan(String street);
}
