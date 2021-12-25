package pl.kaczmarek.parking_space_exchange.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import pl.kaczmarek.parking_space_exchange.dto.LandLordRequest;
import pl.kaczmarek.parking_space_exchange.dto.LandLordResponse;
import pl.kaczmarek.parking_space_exchange.model.LandLordEntity;
import pl.kaczmarek.parking_space_exchange.repository.LandLordRepository;
import pl.kaczmarek.rest.EntityService;

@Service
public class LandLordService extends EntityService<LandLordEntity, LandLordRepository, LandLordRequest, LandLordResponse> {

    public LandLordService(LandLordRepository dao) {
        super(dao);
    }

    @Autowired
    LandLordService landLordService;

    public void addLandLord(LandLordRequest landLordRequest) throws ParseException {
        dao.save(landLordService.convertToEntity(landLordRequest));
    }
}
