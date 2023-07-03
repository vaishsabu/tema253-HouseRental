package at.htlstp.aslan.houserent.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import at.htlstp.aslan.houserent.model.Station;

/**
 * A class that is used as form backing bean to select all houses of a station.
 * This station selection is done via this bean.
 */
@Getter
@Setter
@NoArgsConstructor
public class SelectedStationBean {
    @NotNull
    private Station station;
}
