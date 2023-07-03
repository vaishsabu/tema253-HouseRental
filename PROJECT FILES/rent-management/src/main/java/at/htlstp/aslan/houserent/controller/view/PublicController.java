package at.htlstp.aslan.houserent.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import at.htlstp.aslan.houserent.bean.SelectedStationBean;
import at.htlstp.aslan.houserent.service.HouseService;
import at.htlstp.aslan.houserent.service.StationService;

import javax.validation.Valid;

@Controller
public class PublicController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private StationService stationService;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("selectedStationBean", new SelectedStationBean());
        model.addAttribute("stations", stationService.findAll());
        return "fragments/search-rentals";
    }

    @PostMapping
    public String processForm(Model model,
            @Valid @ModelAttribute("selectedStationBean") SelectedStationBean selectedStationBean,
            BindingResult bindingResult) {
        model.addAttribute("stations", stationService.findAll());
        model.addAttribute("houses",
                bindingResult.hasErrors() ? null : houseService.findByStation(selectedStationBean.getStation()));
        return "fragments/search-rentals";
    }
}
