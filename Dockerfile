FROM openjdk:11

EXPOSE 9440:9440

ARG JAR_FILE=out/kamaz-1.1.2-SNAPSHOT.jar
ARG JAR_LIB_FILE=out/lib/

WORKDIR /usr/local/runme

COPY ${JAR_FILE} app.jar
ADD ${JAR_LIB_FILE} lib/

# ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]

