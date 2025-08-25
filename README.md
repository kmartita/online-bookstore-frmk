![Java](https://img.shields.io/badge/java-white?style=for-the-badge&logo=openjdk&logoSize=auto&color=%23e69138&cacheSeconds=3600&link=https%3A%2F%2Fwww.oracle.com%2Fjava%2F)
![JavaScript](https://img.shields.io/badge/JavaScript-white?style=for-the-badge&logo=javascript&logoSize=auto&color=%23e69138&cacheSeconds=3600)
![Maven](https://img.shields.io/badge/maven-white?style=for-the-badge&logo=apachemaven&logoSize=auto&color=%23cc0000&cacheSeconds=3600&link=https%3A%2F%2Fmaven.apache.org)
![RestAssured](https://img.shields.io/badge/RestAssured-white?style=for-the-badge&logoSize=auto&color=%236aa84f&cacheSeconds=3600&link=https%3A%2F%2Frest-assured.io)
![TestNG](https://img.shields.io/badge/testng-white?style=for-the-badge&logoSize=auto&color=%233d85c6&cacheSeconds=3600&link=https%3A%2F%2Ftestng.org)
![Allure Report](https://img.shields.io/badge/allure-white?style=for-the-badge&logoSize=auto&color=%23f1c232&cacheSeconds=3600&link=https%3A%2F%2Fallurereport.org)
![GitHub Actions](https://img.shields.io/badge/GitHub-white?style=for-the-badge&logo=githubactions&logoSize=auto&color=%23bcbcbc&cacheSeconds=3600)


# API Automation Demo Project: REST Assured & Express.js (Node.js)

This framework automates the testing of a bookstore's **RESTful API**. A completely custom REST API has been developed using **Express.js**, referencing the structure for API endpoints from the demo testing service [FakeRestApi.Web V1](https://fakerestapi.azurewebsites.net/) which operates in static mode.
This project aims to create a maintainable test framework, implement reusable code, handle various scenarios, generate informative test reports, and establish a CI/CD pipeline for continuous integration.

This API test automation framework utilizes TestNG and REST Assured, managed with Maven for building, and generates clear, insightful reports using Allure Report.
It provides a set of predefined test cases to ensure bookstore API functions behave as expected.


### ⚙️ Tech Stack
- **Programming Languages**: Java, JavaScript
- **Testing Framework**: TestNG
- **API Testing Library**: REST Assured
- **Reporting**: Allure Report
- **Build Tool**: Maven
- **CI/CD**: GitHub Actions
- **Backend API**: Express.js (Node.js)

### ✅ Requirements
Requires **Java 22**, **Maven 3.9.x**, and **Allure Report 2.33.x** to be installed and properly configured on your local machine.<br/>

## Table of Contents
1. [Setting up REST API using Express.js](#one)
2. [Framework Structure](#two)
3. [Creating API Test Scenarios](#three)
4. [API Test Execution](#four)
5. [Generate Allure REST Assured Report](#five)
6. [CI/CD Pipeline with GitHub Actions](#six)

<a id="one"></a>
## 1. Setting up REST API using Express.js
The REST API, built with **Express.js**, is already set up and configured for immediate use. 
The following steps provide details for understanding the setup process and exploring the existing configuration:<br/>

### Step 1. Project Initialization:
A new project directory (`api-bookstore/`) was created, and `npm init -y` command was used to initialize the project. 
This generates a `package.json` file, which is essential for managing project dependencies.

### Step 2. Installing Dependencies:
The following dependencies were installed by running `npm install express body-parser nodemon`:
*   `express` - the Express.js framework for building the API.
*   `body-parser` - middleware for parsing request bodies, allowing easy access to data sent in POST and PUT requests.
*   `nodemon` - utility that automatically restarts the server upon file changes, enhancing the development experience.

### Step 3. Creating `server.js`:
The core logic of the API resides in the `server.js` file. It handles routing, request processing, and data management, performing the following key tasks:
*   Loads necessary libraries like Express.js for routing and request handling, and middleware for parsing incoming data.
*   Creates an instance of the Express application, which serves as the foundation for the API.
*   Sets up middleware functions for tasks such as parsing JSON request bodies and handling authentication.
*   Establishes the data models and structures used to represent the API's resources, such as in-memory storage or database connections.
*   Creates routes for handling various HTTP requests (GET, POST, PUT, DELETE) to manage resources and execute actions.
*   Initiates the Express application, listening for incoming requests on the designated port (default: 3000) and activating the API endpoints.

You can explore the `server.js` file in the `api-bookstore/` directory for more details on the API implementation.

### Step 4. Configuring `package.json`:
The `package.json` is an important file in Node.js projects. It has several critical functions:
*   Contains **metadata** about the project, such as name, version, description, author, license, etc.
*   Indicates **dependencies** which other packages (libraries) your project depends on. The `npm` uses this information to install the necessary packages.
*   Allows you to define **scripts** to automate various tasks, such as starting, testing, or building your project.
*   Contains additional settings for tools used in the project.

You can explore the `package.json` file in the `api-bookstore/` directory for more details. 
It is used to define dependencies (**express**, **body-parser**, **nodemon**) and scripts (`start`, `dev`). 
This helps to quickly set up the project by simply running `npm install`.

### Step 5. Run server:
To start the server in development mode, use the following command `npm run dev`. 
This command leverages `nodemon` to automatically restart the server whenever file changes are detected. 
Upon successful startup, you should see a message similar to _"Server is running on port 3000"_ in your terminal, confirming that the API is active and listening for requests.


<a id="two"></a>
## 2. Framework Structure
The framework's architecture is built on a structured directory organization to ensure scalability and maintainability:<br/>
```
|--online-bookstoore-frmk/
    |--api-bookstore/
        |--node_modules/
        |--package.json
        |--package-lock.json
        |--server.js
    |--src/
        |--main/
            |--java/
        |--test/
            |--java/
            |--resources/
    |--pom.xml
    |--testng.xml
```
1. The `api-bookstore/` directory contains all files related to the REST API implemented using **Express.js**.
   *   `node_modules/` stores the installed **Node.js** dependencies.
   *   `package.json` defines metadata, dependencies, and scripts for the API.
   *   `package-lock.json` records the exact versions of dependencies.
   *   `server.js` contains the core logic for the API endpoints.
2. The `src/` directory contains the source code and test code for the Java-based test project:
   *   `main/java/` contains the Java source code for the project.
   *   `test/java/` contains the Java source code for the implemented test cases.
   *   `test/resources/` stores test-related resources, including **JSON** schemas used for validating API responses.
3. The `pom.xml` file in the root directory contains project information and configuration details for **Maven**, used to manage dependencies and build the project.
4. The `testng.xml` file is a configuration file used by the **TestNG** testing framework to organize and define the test suite.


<a id="three"></a>
## 3. Creating API Test Scenarios
Creating API test scenarios involves defining test data and expected API behavior.<br/>

**Steps to Build API Test Scenarios:**
1. _Generate Required Test Data:_ use the `TestData` utility to generate the required test data model with necessary fields.
2. _Customize Test Data:_ modify the test data by removing fields or setting specific field values to suit different test scenarios.
3. _Define Response Specifications:_ create expected response specifications using REST Assured to validate API responses.
4. _Execute API Requests:_ perform API requests using the generated test data and validate responses against the defined specifications.


<a id="four"></a>
## 4. API Test Execution
This project uses **Maven** for dependency management and build automation. The base URL for the API tests can be configured by creating a hidden `.env` file and defining the `BASE_URL` parameter.
The default value is `http://localhost:3000/api/v1/`, but this setting can be modified directly within the `server.js` file.<br/>

Before running the tests, you must first **start the API server**:<br/>
```bash
cd api-bookstore
npm run dev
```
To run **all tests** defined in `testng.xml`, first navigate to the root directory of the framework and then use the following Maven command:<br/>
```bash
mvn test
```
To run a **specific test class**, use the following command:<br/>
```bash
mvn test -Dtest={String}
```
_Parameters:_<br/>
`-Dtest={String}` - name of Test class.<br/>


<a id="five"></a>
## 5. Generate Allure REST Assured Report
Before running tests, it's crucial to clean up any existing test results and reports. This ensures that your report accurately reflects the most recent test run. 
Use the following command to remove the `allure-report` and `allure-results` directories:<br/>
```bash
rm -rf allure-report allure-results
```

To generate the Allure report from the test results, use the following command:<br/>
```bash
allure generate allure-results --clean -o allure-report
```
_Parameters:_<br/>
`allure generate` - tells Allure to create a report.<br/>
`allure-results` - specifies the directory containing the test results.<br/>
`--clean` - removes any previous report data before generating a new report.<br/>
`-o allure-report` - specifies the output directory for the generated report (the report is written to the `allure-report` folder).<br/>

After the report is successfully generated, you can view it in your default browser by running the following command:<br/>
```bash
open allure-report/index.html
```
This command will open the `index.html` file located in the `allure-report` directory, allowing you to interact with the report in your web browser. 
The exact command might differ based on the operating system you’re using. Linux systems, for example, can use `xdg-open ./allure-report/index.html` to open the report.

Alternatively, Allure provides a command to serve the report directly:<br/>
```bash
allure serve
```
This command starts a local web server and automatically opens the generated report in your default browser.

### Allure Report Overview:
An example of the generated Allure report looks like this:<br/>
<img width="964" height="699" alt="allure_overview" src="https://github.com/user-attachments/assets/5b706e9b-3cbc-47ec-aabe-6b875dc3b456" />
<img width="1739" height="858" alt="allure_report" src="https://github.com/user-attachments/assets/9860344f-eed9-4c45-981d-6e8e4279046a" />


<a id="six"></a>
## 6. CI/CD Pipeline with GitHub Actions
This project utilizes a CI/CD pipeline configured using GitHub Actions. The pipeline automates the testing process, ensuring that every code change is thoroughly validated. 
The configuration file for the workflow is located at `.github/workflows/ci.yml`.

The pipeline consists of the following stages:
1. **_Checkout code:_** retrieves the project code using `actions/checkout@v3`.
2. **_Set up JDK 22:_** configures **Java 22** for running **Maven** tests using `actions/setup-java@v3`.
3. **_Install Node.js:_** sets up **Node.js** version 18 for the **Express.js** API using `actions/setup-node@v3`.
4. **_Install API dependencies:_** installs dependencies for the API server, including **express**, **body-parser**, and **wait-on**.
5. **_Start API Server for bookstore:_** starts the **Express.js** API server in the background for testing.
6. **_Set environment variable for tests:_** configures the **BASE_URL** environment variable from GitHub Secrets for the test scripts. 
7. **_Clean allure artifacts before run tests:_** it removes any existing **Allure** report artifacts, starting with a clean state.
8. **_Run tests:_** executes the **TestNG** tests using Maven with `mvn clean test`.
9. **_Check allure results exist:_** verifies that **Allure** test results were generated correctly.
10. **_Setup Allure:_** installs the **Allure** command-line tool, that requires for generate test reports.
11. **_Generate Allure report:_** generates the **Allure** report by processing the test results. It also removes directory `./allure-report`
12. **_Check generated report:_** It checks directory to make sure that step _"Generate Allure Report"_ was successful
13. **_Upload `allure-report`:_** Uploads the generated Allure report as an artifact for easy access and analysis.
14. **_Stop API Server for bookstore:_** Terminates the **Express.js API server** using `pkill` to prevent resource conflicts in subsequent steps.

This CI/CD pipeline automates the process of building, testing, and reporting on the project, ensuring code quality and reliability with each commit.
    
