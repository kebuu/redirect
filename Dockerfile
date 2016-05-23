FROM develar/java:8u45

ADD target/redirect-*.jar /opt/redirect.jar

ENTRYPOINT java -jar /opt/redirect.jar

EXPOSE 8080