package at.htlstp.aslan.houserent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.htlstp.aslan.houserent.bean.FinishRentalBean;
import at.htlstp.aslan.houserent.model.House;
import at.htlstp.aslan.houserent.model.Rental;
import at.htlstp.aslan.houserent.repository.RentalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private HouseService houseService;

    /**
     * Saves the given rental.
     * Sets following fields to null: id, km, return date, return station und the
     * station of the house
     *
     * @param rental the rental to be saved
     */
    public void create(Rental rental) {
        rental.setId(null);
        rental.setKm(null);
        rental.setReturnDate(null);
        rental.setReturnStation(null);
        rental.getHouse().setStation(null);

        rentalRepository.save(rental);
    }

    /**
     * Finishes the given rental with the help of a {@link FinishRentalBean}.
     * Updates the record afterwards.
     *
     * @param rental           the rental to be updated
     * @param finishRentalBean the bean
     */
    public void finish(Rental rental, FinishRentalBean finishRentalBean) {
        rental.setReturnStation(finishRentalBean.getReturnStation());
        rental.setKm(finishRentalBean.getKm());
        rental.getHouse().setStation(rental.getReturnStation());
        // rental.getHouse().setStreet(rental.getHouse().getStreet() + rental.getKm());

        rentalRepository.save(rental);
    }

    /**
     * Returns a list of all running rentals.<br>
     * See {@link RentalRepository#findRunningRentals()}
     */
    public List<Rental> findRunningRentals() {
        return rentalRepository.findRunningRentals();
    }

    /**
     * Returns a list of all rentals that have a specific house.<br>
     * See {@link RentalRepository#findByhouse(House)}
     */
    public List<Rental> findByHouse(House house) {
        return rentalRepository.findByHouse(house);
    }

    /**
     * Checks whether a rental with a given id exists and can be finished.
     * If the id is null or the rental was not found, an empty {@link Optional} is
     * returned.
     * If the rental was found and can be finished (see {@link #canFinish(Rental)}),
     * then an {@link Optional} with the found rental is returned.
     */
    public Optional<Rental> existsAndCanFinish(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        Optional<Rental> opt = rentalRepository.findById(id);
        Rental rental;
        if (opt.isPresent() && canFinish((rental = opt.get()))) {
            return Optional.of(rental);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Checks whether a given rental can be finished or not.
     * Returns true if return date, km and return station fields are null, and false
     * otherwise.
     */
    public boolean canFinish(Rental rental) {
        return rental.getReturnDate() == null && rental.getKm() == null && rental.getReturnStation() == null;
    }

    /**
     * Checks whether a rental can be created ot not. A rental can be created, if
     * the provided house is indeed a house of the provided station.
     * This method is used for security reasons, since users can manipulate the
     * frontend values via F12.
     */
    public boolean canCreate(Rental rental) {
        return houseService.findByStation(rental.getRentalStation()).contains(rental.getHouse());
    }

    /**
     * Returns false if the rental date of rental is after the return date of
     * finishRentalBean.
     * If that is not the case, then the return date of rental will be set to the
     * return date of finishRentalBean and true will be returned.
     */
    public boolean cleanDates(Rental rental, FinishRentalBean finishRentalBean) {
        if (rental.getRentalDate().isAfter(finishRentalBean.getReturnDate())) {
            return false;
        } else {
            rental.setReturnDate(finishRentalBean.getReturnDate());
            return true;
        }
    }
}
