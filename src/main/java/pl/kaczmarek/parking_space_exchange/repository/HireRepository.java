package pl.kaczmarek.parking_space_exchange.repository;



import org.springframework.stereotype.Repository;

import pl.kaczmarek.parking_space_exchange.model.HirerEntity;
import pl.kaczmarek.parking_space_exchange.model.LandLordEntity;
import pl.kaczmarek.rest.EntityDAO;

@Repository
public interface HireRepository extends EntityDAO<HirerEntity> {


}
