# Object Oriented Programming
### Restaurant Reservation and Point of Sale System with Java

Note that this application was created as a project by:
• Jefferson Liew
• Austin Teo
• Sebastian Cheong
• Ryan Wong
• Evan Sean Sainani

## Getting started
This project is a java maven project. If you have any external library dependencies, add them into the project's pom.xml (Don't forget to commit pom.xml too!)
If there is any error stemming from dependency issues, run maven "Update Project" from eclipse (see screenshot) to see if it fixes the issues.

![image](https://user-images.githubusercontent.com/8059266/138337362-826d8755-6255-4cee-ad14-49600c07f20b.png)


## Could not find or load main class oodp.App
![image](https://user-images.githubusercontent.com/8059266/138339012-d64d94c5-1a6f-4118-aa09-5636126fac46.png)

If you see this error when trying to run App.java from eclipse, it means that your project has not built the class files yet. Right click the project > Run As > Maven install (see screenshot) to fix this.

![image](https://user-images.githubusercontent.com/8059266/138338970-bfd99cb4-30f1-4e82-b4fc-f9dd7c413ac7.png)




## Things to take note
1) When adding a new POJO (Plain Old Java Object) class, make sure that BaseData is extended to ensure that the DataHandler will work smoothly
2) use ONLY the scanner in IOHelper.java, multiple scanners causes problems. (Reference it by calling e.g. 'IOHelper.sc.next()')
3) SLF4J LOG4J is added, feel free to log using LoggerFactory
