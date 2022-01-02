package pl.kaczmarek.parking_place.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import pl.kaczmarek.parking.model.ParkingEntity;
import pl.kaczmarek.parking.model.Point;
import pl.kaczmarek.parking.repository.ParkingRepository;
import pl.kaczmarek.parking_place.dto.ParkingPlaceResponse;
import pl.kaczmarek.parking_place.model.ParkingPlaceEntity;
import pl.kaczmarek.parking_place.repository.ParkingPlaceRepository;

@Service
public class ParkingPlaceService {

    @Autowired
    ParkingPlaceRepository parkingPlaceRepository;

    @Autowired
    ParkingRepository parkingRepository;

    /**
     * Returns a list of parking spaces.
     */
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

    /**
     * Function creates parking places for parking.
     * Function returns number of parking places;
     *
     * @param parking - parking object
     * @param pointList - list of coordinates
     */
    public int createParkingPlaces(ParkingEntity parking, List<Point> pointList){
        pointList.sort(Comparator.comparing(Point::getX));
        int numberOfPlaces = 0;
        if(pointList.size()>0) {
            numberOfPlaces = getNumberOfParkingPlacesAndSaveToDatabase(pointList.iterator(),numberOfPlaces,parking);
        }
        return numberOfPlaces;
    }

    public int getNumberOfParkingPlacesAndSaveToDatabase( Iterator<Point> it, int numberOfPlaces, ParkingEntity parking){
        Point one = null;
        Point two = null;
        Point three = null;
        Point four = null;
        one = it.next();
        two = it.next();
        while(it.hasNext()){
            three = it.next();
            four = it.next();
            ParkingPlaceEntity parkingPlaceEntity = new ParkingPlaceEntity(parking,
                one.getX(),one.getY(),two.getX(),two.getY(),three.getX(),three.getY(),
                four.getX(),four.getY());
            numberOfPlaces++;
            parkingPlaceRepository.save(parkingPlaceEntity);
            one = three;
            two = four;
        }
        return numberOfPlaces;
    }

}
