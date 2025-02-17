# FROM amazoncorretto:23-alpine-jdk
# EXPOSE 1409
# ADD target/StaffTransfer-0.0.1-SNAPSHOT.jar StaffTransfer-0.0.1-SNAPSHOT.jar
# ENTRYPOINT ["java","-jar","/StaffTransfer-0.0.1-SNAPSHOT.jar"]

# Step 1: Use a base image with JDK
FROM amazoncorretto:23-alpine-jdk

# Step 2: Set working directory
WORKDIR /app

# Step 3: Copy Maven wrapper and source code
COPY . /app

# Step 4: Build the application (Maven build)
RUN ./mvnw clean package -DskipTests

# Step 5: Expose the application port
EXPOSE 1409

# Step 6: Run the application
CMD ["java", "-jar", "target/StaffTransfer-0.0.1-SNAPSHOT.jar"]
