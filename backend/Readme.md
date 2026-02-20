# Launch Server
spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=9000'

# Launch Test
```bash
    ./mvnw test 
    ./mvnw test -Dtest=HealthControllerTest 
      ./mvnw test -Dtest=HealthControllerTest -Dsurefire.useFile=false
```
