# Readme - Chasse Sous-marine Management Application for Aflas Club

## Project Description

Aflas Club regularly organizes underwater hunting competitions in different regions of Morocco. Club members can participate in competitions by paying a fee to confirm their participation. The club handles the logistics to ensure the competition's success and appoints a jury to calculate results and display the podium.

The goal of the project is to modernize the competition management by developing a responsive web application that meets the needs of the club's administration and the jury.

## Key Features

### Club Member
- **Membership Number**
- **Name and Surname**
- **Identification Document**
- **Nationality**
- **Date of Membership**

### Competition
- Individual competitions.
- Only one competition per day.
- Characterized by a **code** (e.g., Imsouane: pattern: ims-22-12-23).
- Date, start time, and end time.
- Number of participants.
- Location.

### Fish
- Characterized by their **scientific names** and **average weight**.
- Classified based on their **shooting level** (difficulty in hunting).

### Competition Results
- Only the **number of fish** is counted.
- Fish are not weighed; reasoning is always based on the average weight.

### Shooting Levels
- Each level is associated with a **number of points**.
- The higher the level, the more points are awarded.
- Verified during the insertion of shooting levels.

### Competition Registration
- Members can register for competitions from the announcement until **24 hours before** the start.

### Admin Interface
- Allows searching for a member by **number**, **name**, or **surname**.
- Records competition results on the same day.
- Displays the podium with the top 3.

## How to Use the Application

1. **Competition Registration:**
   - Members can register for competitions from the announcement until 24 hours before the start.

2. **Recording Results:**
   - Competition results are recorded on the same day.

3. **Podium Display:**
   - The podium is displayed, highlighting the top 3 participants.

4. **Admin Interface:**
   - Search for a member by number, name, or surname.

## Development and Contribution

- **Languages Used:** Java, HTML, CSS, JavaScript
- **Framework Used:** [SpringBoot , Angular]
- **Database:** [MySQL]

  ## Swagger Documentation
**You can access the Swagger documentation after run this project in your Machine at http://localhost:8080/swagger-ui/index.html#.**

**Note:** This README provides an overview of the project. For detailed instructions on deployment and usage, please refer to the documentation provided in the appropriate folder.


## Project Configuration - `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <!-- ... (existing content) ... -->
    <!-- Specify versions for some dependencies -->
    <dependencies>
        <!-- ... (existing dependencies) ... -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>2.7.17</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
            <version>2.7.17</version>
        </dependency>
        <!-- ... (other dependencies) ... -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.0.28</version>
            <scope>runtime</scope>
        </dependency>
        <!-- ... (other dependencies) ... -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>2.7.17</version>
            <scope>test</scope>
        </dependency>
        <!-- ... (other dependencies) ... -->
        <dependency>
            <groupId>org.modelmapper</groupId>
            <artifactId>modelmapper</artifactId>
            <version>2.4.4</version>
        </dependency>
        <!-- ... (other dependencies) ... -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-ui</artifactId>
            <version>1.7.0</version>
        </dependency>
    </dependencies>
    <!-- ... (existing content) ... -->
</project>
#
Readme - Chasse Sous-marine Management Application for Aflas Club
Project Description
Aflas Club regularly organizes underwater hunting competitions in different regions of Morocco. Club members can participate in competitions by paying a fee to confirm their participation. The club handles the logistics to ensure the competition's success and appoints a jury to calculate results and display the podium.

The goal of the project is to modernize the competition management by developing a responsive web application that meets the needs of the club's administration and the jury.

Key Features
Club Member
Membership Number
Name and Surname
Identification Document
Nationality
Date of Membership
Competition
Individual competitions.
Only one competition per day.
Characterized by a code (e.g., Imsouane: pattern: ims-22-12-23).
Date, start time, and end time.
Number of participants.
Location.
Fish
Characterized by their scientific names and average weight.
Classified based on their shooting level (difficulty in hunting).
Competition Results
Only the number of fish is counted.
Fish are not weighed; reasoning is always based on the average weight.
Shooting Levels
Each level is associated with a number of points.
The higher the level, the more points are awarded.
Verified during the insertion of shooting levels.
Competition Registration
Members can register for competitions from the announcement until 24 hours before the start.
Admin Interface
Allows searching for a member by number, name, or surname.
Records competition results on the same day.
Displays the podium with the top 3.
How to Use the Application
Competition Registration:

Members can register for competitions from the announcement until 24 hours before the start.
Recording Results:

Competition results are recorded on the same day.
Podium Display:

The podium is displayed, highlighting the top 3 participants.
Admin Interface:

Search for a member by number, name, or surname.
Development and Contribution
Languages Used: Java, HTML, CSS, JavaScript
Framework Used: [Framework Name]
Database: [Type of Database]
To contribute, please follow standard development practices and submit your pull requests.
Note: This README provides an overview of the project. For detailed instructions on deployment and usage, please refer to the documentation provided in the appropriate folder.

Project Configuration - pom.xml
xml
Copy code
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <!-- ... (existing content) ... -->
    <!-- Specify versions for some dependencies -->
    <dependencies>
        <!-- ... (existing dependencies) ... -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>2.7.17</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
            <version>2.7.17</version>
        </dependency>
        <!-- ... (other dependencies) ... -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.0.28</version>
            <scope>runtime</scope>
        </dependency>
        <!-- ... (other dependencies) ... -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>2.7.17</version>
            <scope>test</scope>
        </dependency>
        <!-- ... (other dependencies) ... -->
        <dependency>
            <groupId>org.modelmapper</groupId>
            <artifactId>modelmapper</artifactId>
            <version>2.4.4</version>
        </dependency>
        <!-- ... (other dependencies) ... -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-ui</artifactId>
            <version>1.7.0</version>
        </dependency>
    </dependencies>
    <!-- ... (existing content) ... -->
</project>
