java -classpath out/PrintServer.jar;out/PrintServerActions.jar;out/PrintServerLoginModule.jar -Djava.security.manager -Djava.security.policy==src/printserver.policy -Djava.security.auth.login.config==src/printserver_jaas.config printserver.Main