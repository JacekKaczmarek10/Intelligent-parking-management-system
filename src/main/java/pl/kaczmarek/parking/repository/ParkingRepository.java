package pl.kaczmarek.parking.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import pl.kaczmarek.parking.model.ParkingEntity;

@Repository
public interface ParkingRepository  extends JpaRepository<ParkingEntity,Long> {

    @Query(value = "select * from parking.parking where parking.name=:name", nativeQuery = true)
    ParkingEntity getByName(@Param("name") String name);

    @Query(value = "select * from parking.parking where parking.phone_number=:phone", nativeQuery = true)
    ParkingEntity getByPhone(@Param("phone") String phone);

    @Query(value = "select * from parking.parking where parking.email=:email", nativeQuery = true)
    ParkingEntity getByEmail(@Param("email") String email);

    @Query(value = "select * from parking.parking where parking.image_path=:imagePath", nativeQuery = true)
    ParkingEntity getByImagePath(@Param("imagePath") String imagePath);

    @Query(value = "select * from parking.parking "
        + "where parking.has_added_points=false and parking.image_path is not null and parking.parking_status=2", nativeQuery = true)
    List<ParkingEntity> getAllWithoutParkingPlaces();

}
