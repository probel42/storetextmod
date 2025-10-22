FROM gradle:8.14-jdk21
ENV APP_HOME=/usr/app/backend
WORKDIR $APP_HOME
COPY settings.gradle $APP_HOME
COPY build.gradle $APP_HOME
COPY --chown=gradle:gradle src/ $APP_HOME/src
RUN gradle clean build -x test
RUN rm $APP_HOME/build/libs/store-message-service-0.0.1-SNAPSHOT-plain.jar
RUN mv $APP_HOME/build/libs/store-message-service-0.0.1-SNAPSHOT.jar $APP_HOME/build/libs/store-message-service.jar