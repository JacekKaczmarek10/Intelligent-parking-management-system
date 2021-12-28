package pl.kaczmarek.parking_place.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import pl.kaczmarek.parking.model.ParkingEntity;
import pl.kaczmarek.parking_place.model.ParkingPlaceEntity;

@Repository
public interface ParkingPlaceRepository extends JpaRepository<ParkingPlaceEntity,Long> {

    @Query(value = "select * from parking.parking_place where parking_place.parking=:id", nativeQuery = true)
    List<ParkingPlaceEntity> getAllByParkingId(@Param("id") Long id);



}
