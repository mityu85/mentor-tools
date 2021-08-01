FROM adoptopenjdk:16-jre-hotspot
RUN mkdir /opt/app
COPY target/mentor-tools-0.0.1-SNAPSHOT.jar /opt/app/mentortools.jar
CMD ["java", "-jar", "/opt/app/mentortools.jar"]