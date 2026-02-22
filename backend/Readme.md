# Launch Server
spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=9000'

# Launch Test
```bash
    ./mvnw test 
    ./mvnw test -Dtest=HealthControllerTest 
      ./mvnw test -Dtest=HealthControllerTest -Dsurefire.useFile=false
```

# Clean et Migrate postgres database 
```bash
./mvnw flyway:clean \                                                      
    -Dflyway.url=jdbc:postgresql://localhost:5432/spotifou \                             
    -Dflyway.user=spotifou \                                                             
    -Dflyway.password=change_me \                                                        
    -Dflyway.cleanDisabled=false
```