# TeamCity automated tests

This project represents implementation of 3 most important tests for a critical part of TeamCity functionality:

1. Add a project from external git system (GitLab in this case) with the existing kotlin pipeline script.
   I think, this is one of the **killer-features** of TeamCity comparing to other competitors.
   For me, it looks like a future of pipelines' configurations via programming scripts, not configuration files like
   yaml or xml, but also make it with a modern language, not like Groovy configs in Jenkins.
2. Run a new build
   It's a good example of checking a critical feature of the product that makes a huge impact to all the consumers of
   the product. If this feature is not working properly, other cool product functionalities will be useless.
3. Installation
   Many customers use TeamCity as self-hosted instances in their infra, so it's critical to test successful installation
   and initial setup of server and agent. This test also demonstrates one of approaches to test infra setup.

Some other important features to be verified:

4. Authentication
   It's crucial feature for the most of the products where TeamCity is not an exception.
   In a scope of the initial test automation coverage it's only implemented via single username/password method as a
   part of other tests, but in a real test automation project all the possible positive and negative cases should be
   covered and executed for every regression pipeline.
5. Authorization
   For many businesses it's important to set segregation of duties. All roles, permissions, user groups and other
   related tests should be fully automated and included into regular regressions run.
6. Generation of TeamCity configuration with Kotlin DSL
   Very important feature that allows to pre-generate Kotlin script pipeline configuration and save an enormous amount
   of time for initial TeamCity pipeline configuration. I decided not to automate it as every time it makes a commit to
   the project and I additionally need to implement a step that will clean up `.teamcity` folder from the initial
   project automatically (or revert the latest commit) from a pipeline

## Technical stack

- Java 17
- Maven 3.8
- Playwright for Java
- RestAssured
- Docker Compose

## Run cloud tests locally

In order to run all tests including infra-related functionality, reach out to the next section.

- Install Java 17+ version (follow [this](https://docs.oracle.com/en/java/javase/17/install/) instructions)
- Maven 3.8 (reach out to [this link](https://maven.apache.org/install.html))
- Build project with `mvn clean install -DskipTests`
- Run tests with `mvn test`

## Run all tests locally in Docker

I implemented an example of isolation front-end tests that verifies installation of TeamCity and Configuring of an Agent 
using Docker Compose to show how such complex infrastructure-related tests could be configured. We can also run other 

- Docker
- Docker Compose v2+ ()
- Run infra tests with `docker-compose up --abort-on-container-exit`

**Note:** it's easy to change 

## Technical decisions

I personally prefer to follow [AAA principe](https://blog.ncrunch.net/post/arrange-act-assert-aaa-testing.aspx) for
building test automation where every test has all the required setup, performs actions and has an assertion or set of
soft assertions related to the same functionality.

### Web test

Playwright was chosen as a tool for web automation as it's the most modern, fast and reliable frameworks for this
purpose. It wins in all possible aspects of all competitors including WebDriver that could be used with Java.
I used a classic page object pattern to wrap pages-related functionality in separate layer of test framework for ease of
use for this simple example.

#### Web synchronization

It's one of the most known problems in UI test automation that we need to wait enough time for elements to be rendered
and have appropriate state (clickable, visible etc.). Fortunately, Playwright implements the most of required syncs
under the hood, and we only need to implement additional synchronization like waiting for `save` icon to appear and then
wait until it's hidden. There are two main approaches for that: using `waitFor` method or Playwright built-in
`assertThat` function that was more appropriate in my case as it can operate with more controls' states.

### API test

In order to test build pipeline functionality without dependency on existing test that checks git project importing,
my TeamCity instance has pre-configured project that I can use to verify Build functionality.
This check is implemented in API level with using of RestAssured library. In this way we can keep tests independent
of each other and show different approaches for test automation depends on the application level under test.

#### API synchronization

Once we put a new build to a queue, we should wait until it's finished to check the final state. I used `awaitility`
library to dynamically wait for a `finished` build state to be able to verify the final build status.

### Yaml configuration

All the configurations are stored in a separate [config.yaml](src/main/resources/config.yml) file to easily maintain
this. The reason of using yaml format is to store configurations in non-flat format like properties-file and being able
to grow project configuration without a risk of having a huge unmaintainable config-file.
Config is parsed into custom Java model that allows to operate with config values like with any other complex object.
