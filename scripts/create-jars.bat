mkdir out
javac -d ./out/ ./src/printserver/*.java ./src/printserver/permissions/*.java ./src/printserver/actions/*.java ./src/printserver/modules/*.java ./src/printserver/principals/*.java ./src/printserver/server/*.java
jar -cvf PrintServer.jar ./out/printserver/Main.class ./out/printserver/PrintServerCallbackHandler.class
jar -cvf PrintServerLoginModule.jar ./out/printserver/modules/PrintServerLoginModule.class -C ./out/printserver/principals/ .
jar -cvf PrintServerActions.jar -C ./out/printserver/actions/ .