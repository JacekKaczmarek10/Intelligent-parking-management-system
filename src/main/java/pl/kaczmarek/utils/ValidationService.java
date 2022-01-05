package pl.kaczmarek.utils;

import org.springframework.stereotype.Service;

import java.util.List;

import pl.kaczmarek.utils.dozer.BooleanObject;
import pl.kaczmarek.utils.dozer.FloatObject;
import pl.kaczmarek.utils.dozer.IntegerObject;
import pl.kaczmarek.utils.dozer.StringObject;

@Service
public class ValidationService {


    public List<String> checkStringParameters(List<StringObject> list,List<String> errorList){
        for(StringObject stringObject : list){
            if(stringObject.getValue()==null || stringObject.getValue().equals("")){
                errorList.add(stringObject.getName());
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
