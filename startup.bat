set project_path=%cd%
call mvn install:install-file -DgroupId=pms.driver.oracle -DartifactId=ojdbc6 -Dversion=12.0.0 -Dpackaging=jar -Dfile=%cd%/lib/jdbc/oracle/ojdbc6.jar
call mvn install:install-file -DgroupId=pms.jar.random -DartifactId=random -Dversion=1.0.2 -Dpackaging=jar -Dfile=%cd%/lib/random/random-1.0.2.jar
pause