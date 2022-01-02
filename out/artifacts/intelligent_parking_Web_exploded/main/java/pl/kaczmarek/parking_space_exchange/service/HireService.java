package pl.kaczmarek.parking_space_exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import pl.kaczmarek.parking_space_exchange.dto.HireRequest;
import pl.kaczmarek.parking_space_exchange.dto.HireResponse;
import pl.kaczmarek.parking_space_exchange.dto.LandLordRequest;
import pl.kaczmarek.parking_space_exchange.dto.LandLordResponse;
import pl.kaczmarek.parking_space_exchange.model.HirerEntity;
import pl.kaczmarek.parking_space_exchange.model.LandLordEntity;
import pl.kaczmarek.parking_space_exchange.repository.HireRepository;
import pl.kaczmarek.parking_space_exchange.repository.LandLordRepository;
import pl.kaczmarek.rest.EntityService;

@Service
public class HireService extends EntityService<HirerEntity, HireRepository, HireRequest, HireResponse> {

    public HireService(HireRepository dao) {
        super(dao);
    }


    @Autowired
    HireService hireService;

    public void addHire(HireRequest hireRequest) throws ParseException {
        dao.save(hireService.convertToEntity(hireRequest));
    }

}