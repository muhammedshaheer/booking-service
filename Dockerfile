FROM openjdk:8-jdk-alpine
WORKDIR /booking-service
ADD target/booking-service-0.0.1-SNAPSHOT.jar booking-service.jar

CMD java -jar booking-service.jar