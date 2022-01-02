package pl.kaczmarek.parking_space_exchange.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import pl.kaczmarek.parking_space_exchange.dto.LandLordRequest;
import pl.kaczmarek.parking_space_exchange.dto.User;
import pl.kaczmarek.parking_space_exchange.model.HirerEntity;
import pl.kaczmarek.parking_space_exchange.model.LandLordEntity;
import pl.kaczmarek.parking_space_exchange.model.Role;
import pl.kaczmarek.parking_space_exchange.repository.HireRepository;
import pl.kaczmarek.parking_space_exchange.repository.LandLordRepository;

@Service
public class UserService {

    @Autowired
    HireRepository hireRepository;

    @Autowired
    LandLordRepository landLordRepository;

    @Autowired
    protected DozerBeanMapper dozerBeanMapper;

    public List<User> getUserList() throws ParseException {
        List<HirerEntity> hirerEntityList = hireRepository.findAll();
        List<LandLordEntity> landLordEntityList = landLordRepository.findAll();
        List<User> userList = new ArrayList<>();
        for(HirerEntity hirerEntity : hirerEntityList){
            User user = convertHireToUser(hirerEntity);
            userList.add(user);
        }
        for(LandLordEntity landLordEntity : landLordEntityList){
            User user = convertLandLordToUser(landLordEntity);
            userList.add(user);
        }
        return userList;
    }


    public User convertHireToUser(HirerEntity hirerEntity) throws ParseException {
        User user = dozerBeanMapper.map(hirerEntity, User.class);
        user.setRole(Role.Hire);
        return user;
    }

    public User convertLandLordToUser(LandLordEntity landLordEntity) throws ParseException {
        User user = dozerBeanMapper.map(landLordEntity, User.class);
        user.setRole(Role.LandLord);
        return user;
    }

}
