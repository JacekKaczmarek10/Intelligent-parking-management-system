package pl.kaczmarek.parking.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import pl.kaczmarek.parking.dto.ParkingRequest;
import pl.kaczmarek.parking.model.ParkingEntity;
import pl.kaczmarek.parking.model.Point;
import pl.kaczmarek.parking.repository.ParkingRepository;
import pl.kaczmarek.parking_place.service.ParkingPlaceService;

@Service
@Scope("prototype")
public class ParkingService  {

    @Autowired
    ParkingPlaceService parkingPlaceService;

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }


    public void addParking(ParkingRequest parking){
        ParkingEntity parkingEntity = new ParkingEntity(parking.getNumberOfPlaces(),parking.getCity(),
            parking.getName(),parking.getStreet(),parking.getPostalCode());
        parkingRepository.save(parkingEntity);
    }

    public List<ParkingEntity> getAll(){
        return parkingRepository.findAll();
    }

    public static int add(int a,int b){
        return a+b;
    }

    @Value("${filesPath}")
    private String filesPath;


    @Scheduled(cron = "*/10 * * * * ?")
    public void scanImageForDots() throws IOException {
        // Open a JPEG file, load into a BufferedImage.
        System.out.println("RUNNING");
        List<ParkingEntity> parkingEntityList = parkingRepository.getAllWithoutPoints();
        for(ParkingEntity parking : parkingEntityList) {
            BufferedImage img = ImageIO.read(new File(filesPath + parking.getImagePath()));
            List<Point> pointList = new ArrayList<>();
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    if (Math.abs(img.getRGB(i, j)) > 16000000 && Math.abs(img.getRGB(i, j)) < 17000000 &&
                        hasAlreadyPoint(i, j, pointList) == false) {
                        Point point = new Point(i, j);
                        pointList.add(point);
                    }
                }
            }
            // create parking places
            parkingPlaceService.createParkingPlaces(parking,pointList);
        }
    }

    public boolean hasAlreadyPoint(int x,int y,List<Point> pointList){
        for(Point point: pointList){
            if(Math.abs(point.getX()-x)<10 && Math.abs(point.getY()-y)<10){
                return true;
            }
        }
        return false;
    }
}
