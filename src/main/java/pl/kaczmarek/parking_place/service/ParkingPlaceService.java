package pl.kaczmarek.parking_place.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import pl.kaczmarek.parking_place.dto.ParkingPlaceResponse;
import pl.kaczmarek.parking_place.model.ParkingPlaceEntity;

@Service
public class ParkingPlaceService {

    public List<ParkingPlaceResponse> getResponseList(List<ParkingPlaceEntity> entityList){
        List<ParkingPlaceResponse> responseList = new ArrayList<>();
        for(ParkingPlaceEntity parkingPlaceEntity : entityList){
            ParkingPlaceResponse parkingPlaceResponse = new ParkingPlaceResponse(parkingPlaceEntity.getId(),
                parkingPlaceEntity.getX_1(),
                parkingPlaceEntity.getY_1(),parkingPlaceEntity.getX_2(),parkingPlaceEntity.getY_2(),
                parkingPlaceEntity.getX_3(),parkingPlaceEntity.getY_3(),parkingPlaceEntity.getX_4(),
                parkingPlaceEntity.getY_4());
            responseList.add(parkingPlaceResponse);
        }

        return responseList;
    }
}
