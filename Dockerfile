FROM openjdk:17
EXPOSE 8000
ADD target/intelligent-parking.jar intelligent-parking.jar
ENTRYPOINT ["java","-jar","intelligent-parking.jar"]
