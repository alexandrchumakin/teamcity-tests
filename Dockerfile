FROM mcr.microsoft.com/playwright/java:v1.33.0-jammy

ENV MAVEN_OPTS="-Dmaven.repo.local=.m2/repository"
WORKDIR /tests
COPY ./ /tests
RUN mvn clean install $MAVEN_OPTS -DskipTests
ENV INFRA_TESTS true

CMD mvn test $MAVEN_OPTS
