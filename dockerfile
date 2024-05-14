# use specified image
FROM openjdk:17-jdk-alpine
# copy the jar file
COPY target/*.jar /
# rename the jar file
RUN mv /*.jar /app.jar
# run the jar file
ENTRYPOINT [ "java", "-jar", "/app.jar" ]