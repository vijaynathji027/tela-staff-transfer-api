FROM amazoncorretto:23-alpine-jdk
EXPOSE 1409
ADD target/StaffTransfer-0.0.1-SNAPSHOT.jar StaffTransfer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/StaffTransfer-0.0.1-SNAPSHOT.jar"]