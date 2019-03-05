FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD target/*.jar app.jar
ADD keystore.p12 keystore.p12
ADD localhost.pem /usr/local/share/ca-certificates/localhost.pem
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
RUN update-ca-certificates
