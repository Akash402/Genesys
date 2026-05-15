FROM mcr.microsoft.com/playwright/java:v1.57.0-noble

WORKDIR /app
COPY . .

RUN mvn dependency:resolve
RUN apt-get update && apt-get install -y groovy

CMD ["sh", "-c", "groovy libraries/EnvironmentConfigUtility.groovy && mvn test -B -ntp"]
