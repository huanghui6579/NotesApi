mvn clean -Dmaven.test.skip=true install
cp -r target/notes-api.war /usr/apache-tomcat/webapps/noteapi.war
