FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/*.jar app.jar
ADD keystore.p12 keystore.p12
USER root
COPY localhost.pem $JAVA_HOME/jre/lib/security
RUN \
    cd $JAVA_HOME/jre/lib/security \
    && keytool -keystore cacerts -storepass changeit -noprompt \
    -trustcacerts -importcert -alias bootifulsecurity -file localhost.pem
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
