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

    @Value("${editedImagesPath}")
    private String editedImagesPath;

    /**
     * Saving parking object to database
     * @param parking - parking request object with data
     */
    public void addParking(ParkingRequest parking){
        ParkingEntity parkingEntity = new ParkingEntity(parking.getEmail(),parking.getName(),
            parking.getCity(),parking.getStreet(),parking.getPostalCode(),
            parking.getNumber(),parking.getPhoneNumber(),parking.getIsGuarded(),parking.getNumberOfPlaces());
        parkingEntity.setDetails("http://ec2-18-224-21-114.us-east-2.compute.amazonaws.com:8000/getParkingDetails/" + parking.getName());
        parkingRepository.save(parkingEntity);
    }

    /**
     * Return list of all parking
     */
    public List<ParkingEntity> getAll(){
        return parkingRepository.findAll();
    }

    public static int add(int a,int b){
        return a+b;
    }

    /**
     * Scanning folder with uploaded images.
     * Function gets coordinates from images and creates new parking places.
     * Parking places are being saved to database.
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void scanParkingImage() throws IOException {

        System.out.println("SCANNING PARKING CONFIG IMAGE RUNNING");
        List<ParkingEntity> parkingEntityList = parkingRepository.getAllWithoutParkingPlaces();
        for(ParkingEntity parking : parkingEntityList) {
            // Open a JPEG file, load into a BufferedImage.
            BufferedImage img = ImageIO.read(new File(editedImagesPath + parking.getImagePath()));
            List <Point> pointList = getParkingPlacesCoordinatesFromImage(img);
            // create parking places
            int numberOfParkingPlaces = parkingPlaceService.createParkingPlaces(parking,pointList);
            setParkingAsAlreadyScanned(parking,numberOfParkingPlaces);
        }
        System.out.println("SCANNING PARKING CONFIG IMAGE");
    }


    /**
     * Function sets parking fields data.
     * @param parking
     * @param numberOfPlaces
     */
    public void setParkingAsAlreadyScanned(ParkingEntity parking, int numberOfPlaces){
        parking.setNumberOfPlaces(numberOfPlaces);
        parking.setHasAddedPoints(true);
        parkingRepository.save(parking);
    }

    /**
     * Function searches for black dots in image.
     * Save coordinates of each point and add them to the list.
     *
     * @param img - parking config image
     *
     * @return list of coordinates of each point
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
     * Function searches for black pixel.
     * If black pixel is found, then point is added to list.
     * Black pixel near that point are not being added to list.
     *
     * @param x - x coordinate of point
     * @param y - y coordinate of point
     * @param pointList - list of points
     *
     * @return information if point is already added to the list
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
