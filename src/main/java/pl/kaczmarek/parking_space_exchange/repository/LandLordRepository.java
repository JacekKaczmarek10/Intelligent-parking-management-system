package pl.kaczmarek.parking_space_exchange.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.kaczmarek.parking_space_exchange.model.LandLordEntity;
import pl.kaczmarek.rest.EntityDAO;

@Repository
public interface LandLordRepository extends EntityDAO<LandLordEntity> {


}
