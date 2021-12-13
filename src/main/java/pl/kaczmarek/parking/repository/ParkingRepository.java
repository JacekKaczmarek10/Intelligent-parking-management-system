package pl.kaczmarek.parking.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.kaczmarek.parking.model.ParkingEntity;

@Repository
public interface ParkingRepository  extends JpaRepository<ParkingEntity,Long> {


}
