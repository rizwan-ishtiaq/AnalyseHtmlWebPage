# AnalyseHtmlWebPage
Analyze Html Web Page


### Build/Run
To Build project, go into project home directory (AnalyseHtmlWebPage) using terminal (linux) or CMD (windows). Run command `mvn clean package` It should create `target/AnalyseHtmlWebPage-0.0.1-SNAPSHOT.jar` file. To Run it use command `java -jar target/AnalyseHtmlWebPage-0.0.1-SNAPSHOT.jar`

### Direct Run
To run project, go into project home directory using terminal/CMD. Run command `mvn spring-boot:run`

### Usage
1. After successful run. Open web browser
1. Put URL http://localhost:8080/
1. Enter site URL you want to analysis in input field
1. Press button “Start Analyse”

### Technology Stack
* java 1.8
* Spring Boot 1.5.9
* Logback
* thymeleaf
* Jsoup
* Junit
* maven
* bootstrap
* html 5
* javascript

### Assumptions
Login form will always have a signal password type field.
While analyzing links (health check), if a redirect found, no need to check redirect, just show in result table. So human user can have better understanding what going on.
If <!DOCTYPE> not present in HTML, then version will be empty
External and internal links classification will be according to domain (or sub-domain) which human user is trying to analyze. For example if user is analyzing translate.google.com then google.com will be external domain
Application will consider only three things in HTML to get links. These are <a> element, <link> element and any element having attribute of “src” in it.
If any thing went wrong during heath check of links, application will consider it NOT FOUND (404)

### Design Decisions
Using MVC (Model View Controller) architecture
Service oriented architecture (Controller will delegate call to service)
Use multi-threading to health check on links. (In case of single thread CPU, it can be performance hit)

### Constraints/Limitations/Performance
Response time depends upon site (and associated links) user trying to analyze (if site xyz.com is hosted on slow server than analysis will take time accordingly)
Network, by which server is trying to analyze resources on web (if machine, on which analyzing application is running, using 1Mb of share network link then obviously it will have direct impact on performance) 
Login form detection will not work, if any login form have not exactly one password type field.

### Single machine solution
Currently implementation is trying to take advantage of multi-threading while analyzing the links (health check) used in the web page, but again it will be network dependent.

### Ideal Solution
This code is just a PoC (Prof of Concept). The ideal production grade system should be fully distributive system with some level of cache implementation. One way of achieving distributive environment is by implementing producer/consumer, where once server get request for analyzing the page, server put the related links into queue (e-g activemq) and other workers (machine) can check the health check on related links. We can also maintain some cache with TTL (time to live), so that response time can be increased.
