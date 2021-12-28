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

    public void createParkingPlaces(ParkingEntity parking, List<Point> pointList){
        int x =70;
        int y= 140;
        pointList.sort(Comparator.comparing(Point::getX));
        boolean hasNext = true;
        Iterator<Point> it = pointList.iterator();

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
            parkingPlaceRepository.save(parkingPlaceEntity);
            one = three;
            two = four;
        }
        parking.setHasAddedPoints(true);
        parkingRepository.save(parking);
        System.out.println("POINTS WAS ADDED");

    }

}
