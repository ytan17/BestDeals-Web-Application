COMPILE all java files:
1. Use Command Prompt, go to directory, C:\apache-tomcat-7.0.34>
2. in this directory, use env-setup-for-tomcat.bat to set up the path
3. Then go to directory, C:\apache-tomcat-7.0.34\webapps\BestDeals\WEB-INF\classes>, and type in command argument "javac *.java" to compile all java files
4. If you see the path, C:\apache-tomcat-7.0.34\webapps\BestDeals\WEB-INF\classes> show up again and there's no error above it. That means you are successfully compile all java files in BestDeals project.

Install Server:
5. Open another Command Prompt, go to directory: C:\apache-tomcat-7.0.34>
6. in this directory, use env-setup-for-tomcat.bat to set up the path again
7. Then go to directory, C:\apache-tomcat-7.0.34\bin>, and type in command arguement "startup"
8. Then you will see a Tomcat pop up window which run's the server, and in the last line, you usually will see a info about hong long it take for the server to start up, like "INFO: Server startup in 1936 ms."

Run:
9. Go to your browser, and type the address as "http://localhost/BestDeals/", then you will see your Best Deals website.