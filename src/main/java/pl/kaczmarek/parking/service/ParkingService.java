package pl.kaczmarek.parking.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    @Autowired
    ParkingRepository parkingRepository;

    @Value("${filesPath}")
    private String filesPath;

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




    @Scheduled(cron = "0 */5 * * * ?")
    public void scanParkingImage() throws IOException {

        System.out.println("SCANNING PARKING CONFIG IMAGE RUNNING");
        List<ParkingEntity> parkingEntityList = parkingRepository.getAllWithoutParkingPlaces();
        for(ParkingEntity parking : parkingEntityList) {
            // Open a JPEG file, load into a BufferedImage.
            BufferedImage img = ImageIO.read(new File(filesPath + parking.getImagePath()));
            List <Point> pointList = getParkingPlacesCoordinatesFromImage(img);
            // create parking places
            parkingPlaceService.createParkingPlaces(parking,pointList);
        }
        System.out.println("SCANNING PARKING CONFIG IMAGE");
    }

    /**
     * @param img
     *
     * @return
     */
    public  List<Point> getParkingPlacesCoordinatesFromImage(BufferedImage img){
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
        return pointList;
    }


    /**
     * @param x
     * @param y
     * @param pointList
     *
     * @return
     */
    public boolean hasAlreadyPoint(int x,int y,List<Point> pointList){
        for(Point point: pointList){
            if(Math.abs(point.getX()-x)<10 && Math.abs(point.getY()-y)<10){
                return true;
            }
        }
        return false;
    }
}
