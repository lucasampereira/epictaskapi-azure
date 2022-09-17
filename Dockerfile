FROM eclipse-temurin
EXPOSE 8080
ADD target/epictaskapi-0.0.1-SNAPSHOT.jar epictaskapi.jar
ADD target/epictaskapi-0.0.1-SNAPSHOT.war epictaskapi.war
ENTRYPOINT [ "java", "-jar", "-war", "epictaskapi.war" ]