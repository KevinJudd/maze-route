# maze-route
Select the best route through a maze

#  Select one of three initial mazes to solve.

#  Constraints:

*  All rows of the maze must have equal numbers of locations.
*  There can be only one start and one end location.
*  All file tokens must be one character

# Required Software

*  java 1.8
*  maven 3.3.0 or higher (I am running 3.3.9)
*  git repo
Run the following to see that you have the proper versions installed and that maven is using the proper version:
```bash
MNMINOF2MAC007:logs kjudd$ mvn --version
Apache Maven 3.3.9 (bb52d8502b132ec0a5a3f4c09453c07478323dc5; 2015-11-10T10:41:47-06:00)
Maven home: /usr/local/Cellar/maven/3.3.9/libexec
Java version: 1.8.0_25, vendor: Oracle Corporation
Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.10.5", arch: "x86_64", family: "mac"
MNMINOF2MAC007:logs kjudd$
```
# Attach to a local directory and clone the GitHub repo
  `git clone https://github.com/KevinJudd/maze-route.git`

#  build and run the application on your computer.
```bash
> mvn clean package
> mvn spring-boot:run
```

Your web app is now running.
On a mac, you can run a curl test to see that everything is working properly now.
```bash
MNMINOF2MAC007:~ kjudd$ curl localhost:8080/demo
{"stepsInRoute":1,"maze":[[{"status":"start"},{"status":"obstruction"}],[{"status":"obstruction"},{"status":"end"}]]}MNMINOF2MAC007:~ kjudd$
MNMINOF2MAC007:~ kjudd$
```
The small json return above is a two by two maze.

At this point the maze router web app is running.

