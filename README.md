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

## Technical decisions

I personally prefer to follow AAA principe for building test automation where every test has all the required setup,
performs actions and has an assertion or set of soft assertions related to the same functionality.
However, I want to automate ....

### Web test

Playwright was chosen as a tool for web automation as it's the most modern, fast and reliable frameworks for this
purpose. It wins in all possible aspects of all competitors including WebDriver that could be used with Java.
I used a classic page object pattern to wrap pages-related functionality in separate layer of test framework for ease of
use for this simple example. 

