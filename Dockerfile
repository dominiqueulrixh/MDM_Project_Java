#docker buildx build --platform linux/amd64 -t dominiqueulrixh/djl-serving-emotiondetection .
#docker buildx build --platform linux/amd64 -t dominiqueulrixh/djl-serving-emotiondetection -f '/Users/dominiqueulrich/Studium/Semester 6/Model Deployment & Maintenance/MDM_Projekt_2_Java/playground/Dockerfile' '/Users/dominiqueulrich/Studium/Semester 6/Model Deployment & Maintenance/MDM_Projekt_2_Java/playground'
#docker run -p 9000:8082 -d dominiqueulrixh/djl-serving-emotiondetection
#docker push dominiqueulrixh/djl-serving-emotiondetection:latest

FROM maven:3.8.4-opendjk-17-slim as build

WORKDIR /usr/src/app
COPY . .

RUN mvn -Dmaven.test.skip=true package


FROM openjdk:17-jdk-slim

# Copy Files
WORKDIR /usr/src/app
COPY  --from=build /usr/src/app/target/playground-0.0.1-SNAPSHOT.jar .
COPY models /usr/src/app/model


# Docker Run Command
EXPOSE 8082
CMD ["java","-jar","playground-0.0.1-SNAPSHOT.jar"]