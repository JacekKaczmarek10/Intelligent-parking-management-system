package pl.kaczmarek.parking.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import pl.kaczmarek.parking.dto.ParkingRequest;
import pl.kaczmarek.parking.model.ParkingEntity;
import pl.kaczmarek.parking.repository.ParkingRepository;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }


    public void addParking(ParkingRequest parking){
        ParkingEntity parkingEntity = new ParkingEntity(parking.getNumberOfRows(),parking.getNumberOfColumns(),
            parking.getName());
        parkingRepository.save(parkingEntity);
    }

    public List<ParkingEntity> getAll(){
        return parkingRepository.findAll();
    }
}
