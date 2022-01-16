package pl.kaczmarek.parking_place.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import pl.kaczmarek.parking.model.ParkingEntity;
import pl.kaczmarek.parking.repository.ParkingRepository;
import pl.kaczmarek.parking_place.dto.ParkingPlacesDetailedResponse;
import pl.kaczmarek.parking_place.model.ParkingPlaceEntity;
import pl.kaczmarek.parking_place.repository.ParkingPlaceRepository;
import pl.kaczmarek.parking_place.service.ParkingPlaceService;

@Controller
public class ParkingPlaceController {

    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    ParkingPlaceRepository parkingPlaceRepository;

    @Autowired
    ParkingPlaceService parkingPlaceService;

    @RequestMapping(value = "/get-parking-places", method = RequestMethod.GET)
    public ResponseEntity<Object> getParkingPlacesData(@Param("name")String name){
        ParkingEntity parkingEntity = parkingRepository.getByName(name);
        List<ParkingPlaceEntity> list = parkingPlaceRepository.getAllByParkingId(parkingEntity.getId());
        return ResponseEntity.status(200).body(new ParkingPlacesDetailedResponse(name,list.size(),
            parkingPlaceService.getResponseList(list)));
    }

    @RequestMapping(value = "/add-parking-place", method = RequestMethod.POST)
    public ResponseEntity<Object> addParkingPlace(@Param("name")String name,
                                                  @Param("x1")int x1,
                                                  @Param("x2")int x2,
                                                  @Param("x3")int x3,
                                                  @Param("x4")int x4,
                                                  @Param("y1")int y1,
                                                  @Param("y2")int y2,
                                                  @Param("y3")int y3,
                                                  @Param("y4")int y4){
        ParkingEntity parkingEntity = parkingRepository.getByName(name);
        return ResponseEntity.status(200).
            body(parkingPlaceRepository.save(new ParkingPlaceEntity(parkingEntity,x1,y1,x2,y2,x3,y3,x4,y4)));
    }

//    @RequestMapping(value = "/change-parking-place", method = RequestMethod.POST)
//    public ResponseEntity<Object> changeParkingStatus(@RequestBody ChangePlace changePlace){
//        ParkingPlaceEntity parkingPlaceEntity = parkingPlaceRepository.getById(id);
//        parkingPlaceEntity.setIsFree(status);
//        parkingPlaceRepository.save(parkingPlaceEntity);
//        return ResponseEntity.status(200).build();
//    }

}
