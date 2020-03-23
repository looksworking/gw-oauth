FROM openjdk:8-jdk
ARG VERSION
ARG APP_VERSION

COPY auth-${APP_VERSION}.jar /app.jar

ENV APP_VERSION ${VERSION}

ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-jar","/app.jar"]