SET executionPath=%cd%

rem Set working directory to repository root
SET scriptPath=%~dp0
cd %scriptPath:~0,-1%
cd ..

rem Run jars with jaas config
java -classpath out/PrintServer.jar;out/PrintServerActions.jar;out/PrintServerLoginModule.jar -Djava.security.manager -Djava.security.policy==src/printserver.policy -Djava.security.auth.login.config==src/printserver_jaas.config printserver.Main

rem Move back to execution path
cd %executionPath%