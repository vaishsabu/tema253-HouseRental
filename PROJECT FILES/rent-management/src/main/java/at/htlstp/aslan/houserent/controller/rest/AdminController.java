package at.htlstp.aslan.houserent.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import at.htlstp.aslan.houserent.model.House;
import at.htlstp.aslan.houserent.model.Customer;
import at.htlstp.aslan.houserent.model.Rental;
import at.htlstp.aslan.houserent.service.HouseService;
import at.htlstp.aslan.houserent.service.CustomerService;
import at.htlstp.aslan.houserent.service.RentalService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RentalService rentalService;

    @GetMapping("running-rentals")
    public List<Rental> findRunningRentals() {
        return rentalService.findRunningRentals();
    }

    // @GetMapping("street-greater-than/{street}")
    // public List<House> findByStreetGreaterThan(@PathVariable Integer street) {
    // return houseService.findByStreetGreaterThan(street);
    // }

    @PostMapping("create-house")
    public ResponseEntity<House> createhouse(@RequestBody @Valid House house) {
        return ResponseEntity.created(URI.create("")).body(houseService.create(house));
    }

    @PostMapping("create-customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid Customer customer) {
        return ResponseEntity.created(URI.create("")).body(customerService.create(customer));
    }

    @DeleteMapping("delete-house/{houseNr}")
    public void deletehouse(@PathVariable String houseNr) {
        houseService.deleteById(houseNr);
    }

}
