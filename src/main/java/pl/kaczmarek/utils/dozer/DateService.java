package pl.kaczmarek.utils.dozer;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class DateService {


    public Timestamp add5s(Timestamp timestamp){
        int sec = 30;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.SECOND, sec);
        Timestamp later = new Timestamp(cal.getTime().getTime());
        return later;
    }
}
