#
# Copyright (c) 2018 The Emirates Group. All Rights Reserved.
# The information specified here is confidential and remains property of the Emirates Group.
# groupId     - com.emirates.ocsl
# artifactId  - utility-usermessages-service
# name        - utility-usermessages-service
# description - Utility User Messages Service
# 2019
#


FROM adoptopenjdk:11-jre-hotspot as builder
WORKDIR app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/application/ ./

ENV JAVA_TOOL_OPTIONS="-Xss1M -Xms128M -Xmx512M -XX:MaxDirectMemorySize=10M -XX:MaxMetaspaceSize=285M -XX:ReservedCodeCacheSize=120M"


ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher" ]
CMD ["--spring.profiles.active=local"]