package pl.kaczmarek.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import pl.kaczmarek.parking.repository.ParkingRepository;
import pl.kaczmarek.utils.dozer.BooleanObject;
import pl.kaczmarek.utils.dozer.FloatObject;
import pl.kaczmarek.utils.dozer.IntegerObject;
import pl.kaczmarek.utils.dozer.StringObject;

@Service
public class ValidationService {

    @Autowired
    ParkingRepository parkingRepository;


    public List<String> checkStringParameters(List<StringObject> list,ValidationObject validationObject){
        List<String> errorList = validationObject.getErrorList();
        for(StringObject stringObject : list){
            if(stringObject.getValue()==null || stringObject.getValue().equals("")){
                errorList.add(stringObject.getName());
                validationObject.setReason("Parameter is null or empty");
            }
            else {
                if(stringObject.getName().equals("name")){
                    if(parkingRepository.getByName(stringObject.getValue())!=null){
                        errorList.add(stringObject.getName());
                        validationObject.setReason("Duplicated value");
                    }
                }
                if(stringObject.getName().equals("email")){
                    if(parkingRepository.getByEmail(stringObject.getValue())!=null){
                        errorList.add(stringObject.getName());
                        validationObject.setReason("Duplicated value");
                    }
                }
                if(stringObject.getName().equals("phoneNumber")){
                    if(parkingRepository.getByPhone(stringObject.getValue())!=null){
                        errorList.add(stringObject.getName());
                        validationObject.setReason("Duplicated value");
                    }
                }
            }
        }
        return errorList;
    }

    public List<String> checkIntegerParameters(List<IntegerObject> list,List<String> errorList){
        for(IntegerObject stringObject : list){
            if(stringObject.getValue()==null || stringObject.getValue().equals("")){
                errorList.add(stringObject.getName());
            }
        }
        return errorList;
    }

    public List<String> checkFloatParameters(List<FloatObject> list,List<String> errorList){
        for(FloatObject stringObject : list){
            if(stringObject.getValue()==null || stringObject.getValue().equals("")){
                errorList.add(stringObject.getName());
            }
        }
        return errorList;
    }

    public List<String> checkBooleanParameters(List<BooleanObject> list,List<String> errorList){
        for(BooleanObject stringObject : list){
            if(stringObject.getValue()==null || stringObject.getName().equals("")){
                errorList.add(stringObject.getName());
            }
        }
        return errorList;
    }

}
