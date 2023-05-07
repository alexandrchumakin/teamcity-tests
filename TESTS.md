# Test cases

1. **Description:** Add a project from external git system (GitLab in this case) with the existing kotlin pipeline script.

   **My thoughts:** I think, this is one of the **killer-features** of TeamCity comparing to other competitors.
   For me, it looks like a future of pipelines' configurations via programming scripts, not configuration files like
   yaml or xml, but also make it with a modern language, not like Groovy configs in Jenkins.

   **Steps:**

    - login to TeamCity with valid credentials
    - click `New project` button in a top right corner
    - fill in `Repository URL` with `https://gitlab.com/alexandrchumakin-docker/node-docker-sample`
    - click `Proceed` button
    - leave default settings in the next screen and click `Proceed` button

   **Expected result:**

   Project pipeline is successfully imported and `Build configurations` section shows `Docker` build step

2. **Description:** Run a new build of pre-configured project

   **My thoughts:** It's a good example of checking a critical feature of the product that makes a huge impact to all
   the consumers of the product. If this feature is not working properly, other cool product functionalities will be
   useless.

   **Steps:**

    - login to TeamCity with valid credentials
    - open any pre-configured project with Builds from previous test case
    - click `Run` button
    - wait for this build to be finished

   **Expected result:**

   Status of build is shown and logs are available under `show full log` link

3. **Description:** TeamCity local installation

   **My thoughts:** Many customers use TeamCity as a self-hosted instances in their infra, so it's critical to test
   successful installation and initial setup of server and agent. This test also demonstrates one of approaches to test
   infra setup.

   **Steps:**

    - run TeamCity with Agent using docker-compose configuration
      using [this example](https://github.com/JetBrains/teamcity-docker-samples)
    - open http://localhost:8112/ in your browser
    - click `Proceed` button
    - leave default value for DB type and click `Proceed` button
    - wait for component initialization
    - accept license agreement and click `Continue` button
    - fill in admin users credentials
    - click `Create account` button
    - wait for user to be created and TeamCity to be redirected to Home screen
    - click `Agents` link in top of screen
    - expand `Unathorized` section
    - click on agent name under this section
    - click `Authorize` button
    - confirm authorization on popup by clicking `Authorize` button there

   **Expected result:**

   Agents count is changed from 0 to 1 in the header
4. **Description:** New user authentication

   **My thoughts:** It's crucial feature for the most of the products where TeamCity is not an exception.
   In a scope of the initial test automation coverage it's only implemented via single username/password method as a
   part of other tests, but in a real test automation project all the possible positive and negative cases should be
   covered and executed for every regression pipeline.

   **Steps:**

    - open TeamCity [signup](https://www.jetbrains.com/teamcity/signup/) page
    - click Bitbucket icon
    - login to Bitbucket account with valid credentials or via any available 3rd party system
    - click `Grant access` button
    - fill in `First name` and `Last name`
    - check `I have read and agreed ...` box
    - click `Proceed` button
    - fill in domain name with any value
    - make sure it's available, otherwise change it
    - click `Proceed` button
    - wait until server is starting
    - scroll agreement page to bottom
    - click `I agree` button

   **Expected result:**

   Home screen is shown with `Welcome to TeamCity Cloud` message
5. **Description:** Granting permissions to external user

   **My thoughts:** For many businesses it's important to set segregation of duties. All roles, permissions, user groups
   and other related tests should be fully automated and included into regular regressions run.

   **Steps:**

    - login to TeamCity with valid credentials
    - open `Administration | Users` page
    - click `Create user account`
    - fill in `Username`
    - fill in `New password` and `Confirm new password` with the same value
    - fill in `Bitbucket Cloud account` with any pre-setup account
    - click `Create User`
    - open `Administration | Groups` page
    - click `Create new group` button
    - fill in `Name` with `Test group`
    - leave auto-generated value for `Group Key`
    - click `Create` button
    - open newly create group and go to `Users` menu
    - click `Add users to group` and add one that was used for account creation to this group
    - open `Roles` menu
    - click `Assign role` button
    - check project from the list and click `Assign`
    - logout and login to the same TeamCity domain with a user added in a previous step

   **Expected result:**

   This user can see and open a project added to `Test group`
6. **Description:** Generation of TeamCity configuration with Kotlin DSL

   **My thoughts:** Very important feature that allows to pre-generate Kotlin script pipeline configuration and save an
   enormous amount of time for initial TeamCity pipeline configuration. I decided not to automate it as every time it
   makes a commit to the project and I additionally need to implement a step that will clean up `.teamcity` folder from
   the initial
   project automatically (or revert the latest commit) from a pipeline

   **Steps:**

    - login to TeamCity with external Git system
    - click `New project` button in a top right corner
    - choose any project from external Git system **without** pre-configured TeamCity build
    - click `Proceed` button
    - leave default value for DB type and click `Proceed` button
    - go to `General Settings | Versioned Settings` page
    - select `Synchronization enabled` radio button
    - expand VSC root dropdown and select `master` head link
    - change `Settings format` to `Kotlin`
    - click `Apply` button
    - make sure `Versioned settings configuration updated, initial commit will be made shortly` message is shown
    - open Git repo that was used in step 3

   **Expected result:**

   `.teamcity` folder is committed with a valid Kotlin script configuration
