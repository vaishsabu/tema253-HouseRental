package at.htlstp.aslan.houserent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import at.htlstp.aslan.houserent.model.Station;

public interface StationRepository extends JpaRepository<Station, Integer> {
}
