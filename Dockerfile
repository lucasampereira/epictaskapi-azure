FROM eclipse-temurin
EXPOSE 8080
ADD target/epictaskapi-0.0.1-SNAPSHOT.war epictaskapi.war
ENTRYPOINT [ "java", "-war", "-war", "epictaskapi.war" ]