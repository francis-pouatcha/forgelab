
INSTRUCTIONS
============
001) Install jdk1.7

002) Install Jboss forge 1.4.3.Final http://forge.jboss.org/

003) Install JBoss EAP6.1

004) run the tsheet_as7.fsh in your forge shell (run ../forgelab/alpha/tsheet_as7.fsh)

***SUCCESS*** Created project [tsheet] in new working directory [/Users/francis/dev/timesheet/tsheet]

Wrote /Users/francis/dev/timesheet/tsheet

........

Wrote /Users/francis/dev/timesheet/tsheet/pom.xml

 ? Enter path for JBoss AS or leave blank to download: /Users/francis/tools/jboss-eap-6.1 (Here is my jboss eap installation directory)

...........

...........

........... 

[INFO] ------------------------------------------------------------------------

[INFO] BUILD SUCCESS

[INFO] ------------------------------------------------------------------------

[INFO] Total time: 1.750s

[INFO] Finished at: Tue Dec 17 08:00:53 EST 2013

[INFO] Final Memory: 50M/503M

[INFO] ------------------------------------------------------------------------

[tsheet] tsheet $ 


005) import generated project into eclipse

006) configure eclipse to see generate JPA Model Classes

    --> project --> properties --> Java Compiler --> Annotation Processesing --> Select (enable project specific settings) --> Enter this as generated source directory : target/generated-sources/annotations

007) Copy  ValidationMessages.properties to src/main/resources

008) Replace /alpha/CompanyJPAEndpointTest.java to the corresponding directory in the tshet project (see package name)

009) copy /alpha/CompanyJPARepository.java /alpha/DataSourceProducer.java to the corresponding location in the tsheet project (see package name).

010) Start jboss-eap-6.1 $JBOSS_EAP_INSTALL_DIR/bin/standalone.sh

011) [tsheet] tsheet $ build --profile arq-jboss_as_remote_7.x

-------------------------------------------------------

 T E S T S

-------------------------------------------------------

Running org.adorsys.tsheet.rest.CompanyJPAEndpointTest

Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 18.458 sec


Results :

Tests run: 4, Failures: 0, Errors: 0, Skipped: 0

[INFO] 

[INFO] --- maven-war-plugin:2.4:war (default-war) @ tsheet ---

[INFO] Packaging webapp

[INFO] Assembling webapp [tsheet] in [/Users/francis/dev/timesheet/tsheet/target/tsheet]

[INFO] Processing war project

[INFO] Copying webapp resources [/Users/francis/dev/timesheet/tsheet/src/main/webapp]

[INFO] Webapp assembled in [233 msecs]

[INFO] Building war: /Users/francis/dev/timesheet/tsheet/target/tsheet.war

[INFO] 

[INFO] --- maven-install-plugin:2.3.1:install (default-install) @ tsheet ---

[INFO] Installing /Users/francis/dev/timesheet/tsheet/target/tsheet.war to /Users/francis/.m2/repository/org/adorsys/tsheet/tsheet/1.0.0-SNAPSHOT/tsheet-1.0.0-SNAPSHOT.war

[INFO] Installing /Users/francis/dev/timesheet/tsheet/pom.xml to /Users/francis/.m2/repository/org/adorsys/tsheet/tsheet/1.0.0-SNAPSHOT/tsheet-1.0.0-SNAPSHOT.pom

[INFO] ------------------------------------------------------------------------

[INFO] BUILD SUCCESS

[INFO] ------------------------------------------------------------------------

[INFO] Total time: 21.974s

[INFO] Finished at: Tue Dec 17 08:15:10 EST 2013

[INFO] Final Memory: 63M/485M

[INFO] ------------------------------------------------------------------------

[tsheet] tsheet $ 

012) [tsheet] tsheet $ as7 deploy

The deployment operation (FORCE_DEPLOY) was successful.

[tsheet] tsheet $ 

013) go to a web browser: http://localhost:8080/tsheet/ajs
