FROM openjdk:8
COPY ./build/libs/shop-0.0.1-SNAPSHOT.jar shop.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shop.jar"]