FROM gradle:7.5.0-jdk17
WORKDIR /opt/app
COPY ./build/libs/pets-guardian-fork-copy-0.0.1-SNAPSHOT.jar ./

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar pets-guardian-fork-copy-0.0.1-SNAPSHOT.jar"]
