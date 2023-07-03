package at.htlstp.aslan.houserent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.htlstp.aslan.houserent.model.Station;
import at.htlstp.aslan.houserent.repository.StationRepository;

import java.util.List;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    /**
     * @return a list of all stations
     */
    public List<Station> findAll() {
        return stationRepository.findAll();
    }

    /**
     * Checks whether a station with the given id exists or not.
     *
     * @param id the id to be searched for
     * @return true, if the station exists, false otherwise
     */
    public boolean existsById(Integer id) {
        return stationRepository.existsById(id);
    }

}
