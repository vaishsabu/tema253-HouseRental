package at.htlstp.aslan.houserent.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import at.htlstp.aslan.houserent.bean.FinishRentalBean;
import at.htlstp.aslan.houserent.model.House;
import at.htlstp.aslan.houserent.model.Rental;
import at.htlstp.aslan.houserent.model.Station;
import at.htlstp.aslan.houserent.service.HouseService;
import at.htlstp.aslan.houserent.service.CustomerService;
import at.htlstp.aslan.houserent.service.RentalService;
import at.htlstp.aslan.houserent.service.StationService;
import at.htlstp.aslan.houserent.util.MessagesBean;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class used to serve server-side rendered views.<br>
 * As defined in the security configuration, only users with the role of
 * EMPLOYEE can use these endpoints.<br>
 * This is achieved by setting the root route of this controller to
 * {@code /employee/**}.
 */
@Controller
@RequestMapping("employee")
@SessionAttributes({ "finishRentalBean" })
public class EmployeeController {

    @Autowired
    private MessagesBean messages;

    @Autowired
    private StationService stationService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RentalService rentalService;

    @GetMapping("/create-rental")
    public String showCreateRentalForm(Model model, @ModelAttribute("rental") Rental rental) {
        List<Station> stations = stationService.findAll();
        List<House> houses;

        if (stations.isEmpty()) {
            houses = new ArrayList<>();
        } else {
            houses = houseService
                    .findByStation(rental.getRentalStation() == null ? stations.get(0) : rental.getRentalStation());
        }

        rental.setRentalDate(LocalDate.now());
        model.addAttribute("rental", rental);
        model.addAttribute("houses", houses);
        model.addAttribute("stations", stations);
        model.addAttribute("customers", customerService.findAll());

        return "fragments/create-rental";
    }

    @PostMapping("/create-rental/refresh")
    public String refreshCreateRentalForm(@ModelAttribute("rental") Rental rental,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("rental", rental);
        return "redirect:/employee/create-rental";
    }

    @PostMapping("/create-rental/process")
    public ModelAndView processCreateRentalForm(@Valid @ModelAttribute("rental") Rental rental,
            BindingResult bindingResult) {
        ModelAndView createRentalForm = new ModelAndView("fragments/create-rental");
        List<Station> stations = stationService.findAll();
        createRentalForm.addObject("houses", houseService.findByStation(stations.get(0)));
        createRentalForm.addObject("stations", stations);
        createRentalForm.addObject("customers", customerService.findAll());

        if (bindingResult.hasErrors()) {
            return createRentalForm;
        }

        if (!rentalService.canCreate(rental)) {
            return createRentalForm.addObject("houseMismatchError", messages.get("houseMismatchError"));
        }

        rentalService.create(rental);
        return new ModelAndView("redirect:/employee/running-rentals")
                .addObject("success", messages.get("createRentalSuccess"));
    }

    @GetMapping("/running-rentals")
    public String showRunningRentalsForm(Model model, @ModelAttribute("error") String error,
            @ModelAttribute("success") String success) {
        model.addAttribute("rentals", rentalService.findRunningRentals());
        model.addAttribute("error", error);
        model.addAttribute("success", success);
        return "fragments/running-rentals";
    }

    @GetMapping("/finish-rental/{id}")
    public ModelAndView showFinishForm(@PathVariable("id") Integer id) {
        Optional<Rental> opt = rentalService.existsAndCanFinish(id);
        if (opt.isEmpty()) {
            return new ModelAndView("redirect:/employee/running-rentals")
                    .addObject("error", messages.get("rentalNotFound"));
        }

        return new ModelAndView("fragments/finish-rental")
                .addObject("stations", stationService.findAll())
                .addObject("finishRentalBean", FinishRentalBean.fromRental(opt.get()));
    }

    @PostMapping("/finish-rental/{id}")
    public ModelAndView processFinishForm(@PathVariable("id") Integer id, @Valid FinishRentalBean finishRentalBean,
            BindingResult bindingResult) {
        ModelAndView redirectRunningRentals = new ModelAndView("redirect:/employee/running-rentals");
        ModelAndView finishRentalForm = new ModelAndView("/fragments/finish-rental");

        Optional<Rental> opt = rentalService.existsAndCanFinish(id);
        if (opt.isEmpty()) {
            return redirectRunningRentals.addObject("error", messages.get("rentalNotFound"));
        }

        Rental rental = opt.get();
        finishRentalForm.addObject("stations", stationService.findAll());

        if (bindingResult.hasErrors()) {
            return finishRentalForm;
        }

        if (!rentalService.cleanDates(rental, finishRentalBean)) {
            return finishRentalForm.addObject("returnDateError", messages.get("returnDateError"));
        }

        rentalService.finish(rental, finishRentalBean);
        return redirectRunningRentals.addObject("success", messages.get("finishRentalSuccess"));
    }

}
