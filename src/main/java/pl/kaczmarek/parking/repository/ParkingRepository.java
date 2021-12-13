package pl.kaczmarek.parking.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import pl.kaczmarek.parking.model.ParkingEntity;

@Repository
public interface ParkingRepository  extends JpaRepository<ParkingEntity,Long> {

    @Query(value = "select * from userdb.parking where parking.name=:name", nativeQuery = true)
    ParkingEntity getByName(@Param("name") String name);


}
