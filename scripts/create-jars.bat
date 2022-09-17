SET executionPath=%cd%

rem Set working directory to repository root
SET scriptPath=%~dp0
cd %scriptPath:~0,-1%
cd ..

rem Make output dir
mkdir out

rem Compile source code
javac -d ./out/ ./src/printserver/*.java ./src/printserver/permissions/*.java ./src/printserver/actions/*.java ./src/printserver/modules/*.java ./src/printserver/principals/*.java ./src/printserver/server/*.java

rem Create jar files in out directory 
cd out 
jar -cvf PrintServer.jar printserver/Main.class printserver/PrintServerCallbackHandler.class printserver/permissions/*.class printserver/server/*.class 
jar -cvf PrintServerLoginModule.jar printserver/modules/PrintServerLoginModule.class printserver/principals/*.class
jar -cvf PrintServerActions.jar printserver/actions/*.class

rem Move back to execution path
cd %executionPath%