package at.htlstp.aslan.houserent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.htlstp.aslan.houserent.model.House;
import at.htlstp.aslan.houserent.model.Station;
import at.htlstp.aslan.houserent.repository.HouseRepository;
import at.htlstp.aslan.houserent.util.MessagesBean;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class HouseService {

    @Autowired
    private MessagesBean messages;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private StationService stationService;

    @Autowired
    private RentalService rentalService;

    /**
     * @param station the station to be searched for
     * @return all Houses that are part of the given station
     */
    public List<House> findByStation(Station station) {
        return houseRepository.findByStation(station);
    }

    /**
     * @param street the minimum street to be searched for
     * @return all Houses that have a street greater than the given one
     */
    // public List<House> findByStreetGreaterThan(String street) {
    // return houseRepository.findByStreetGreaterThan(street);
    // }

    /**
     * Saves the given House.
     *
     * @param House House to be saved
     * @return the saved House coming from the database (only when no exceptions
     *         occur)
     * @throws EntityNotFoundException  if the provided station of the House does
     *                                  not exist
     * @throws EntityExistsException    if the given primary key already belongs to
     *                                  an existing entity
     * @throws IllegalArgumentException if the station is null
     */
    public House create(House house) {
        if (house.getStation() == null) {
            throw new IllegalArgumentException(messages.get("HouseStationNotNull"));
        }
        if (house.getStation().getId() == null || !stationService.existsById(house.getStation().getId())) {
            throw new EntityNotFoundException(messages.get("stationNotFound"));
        }
        if (houseRepository.existsById(house.getHouseNr())) {
            throw new EntityExistsException(messages.get("HouseAlreadyExists"));
        }
        return houseRepository.save(house);
    }

    /**
     * Deletes a House with the given primary key.
     * However, the House must be deletable. See {@link #canDelete(House)}
     *
     * @param houseNr the primary key to be searched for
     * @throws EntityNotFoundException  if the given primary key does not belong to
     *                                  any existing entity
     * @throws IllegalArgumentException if the House cannot be deleted
     */
    public void deleteById(String houseNr) {
        House house = houseRepository.findById(houseNr)
                .orElseThrow(() -> new EntityNotFoundException(messages.get("HouseNotFound")));
        if (!canDelete(house)) {
            throw new IllegalArgumentException(messages.get("HouseDeleteError"));
        }
        houseRepository.delete(house);
    }

    /**
     * Checks whether a given House can be deleted or not.
     * The House can be deleted, if, and only if, the station House is not in use
     * (station equals null) and the House was never used in any rental before.
     *
     * @param House the House to be checked
     * @return true, if the House fulfills the mentioned criteria, false otherwise.
     */
    public boolean canDelete(House house) {
        return house.getStation() != null && rentalService.findByHouse(house).isEmpty();
    }

}
