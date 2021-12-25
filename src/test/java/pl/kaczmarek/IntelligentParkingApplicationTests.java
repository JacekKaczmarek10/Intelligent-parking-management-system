package pl.kaczmarek;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import pl.kaczmarek.parking.model.ParkingEntity;
import pl.kaczmarek.parking.repository.ParkingRepository;
import pl.kaczmarek.parking.service.ParkingService;

@SpringBootTest
class IntelligentParkingApplicationTests {

    @Mock
    ParkingRepository parkingRepository;

    @Before("")
    public void init(){
        System.out.println("Initi");
        parkingRepository.save(new ParkingEntity("Banan"));
    }


    @Test
    void contextLoads() {
    }


    @Test
    void test_get_all_parkings(){
        assertEquals(10,ParkingService.add(5,5));
    }

    @Test()
    void test_get_by_name(){
        when(parkingRepository.getByName("Banan")).thenReturn(new ParkingEntity("Banan"));
        assertEquals(new ParkingEntity("Banan"),parkingRepository.getByName("Banan"));
    }

}
