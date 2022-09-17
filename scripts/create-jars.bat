rem Make output dir
mkdir out

rem Compile source code
javac -d ./out/ ./src/printserver/*.java ./src/printserver/permissions/*.java ./src/printserver/actions/*.java ./src/printserver/modules/*.java ./src/printserver/principals/*.java ./src/printserver/server/*.java

rem Create jar files in out directory 
cd out 
jar -cvf PrintServer.jar printserver/Main.class printserver/PrintServerCallbackHandler.class
jar -cvf PrintServerLoginModule.jar printserver/modules/PrintServerLoginModule.class -C printserver/principals/ .
jar -cvf PrintServerActions.jar -C printserver/actions/ .

