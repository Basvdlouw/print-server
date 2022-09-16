package printserver.actions;

import printserver.server.PrintServer;

public class ReadConfigAction extends PrivilegedPrintServerAction<ReadConfigAction> {
    private static final String ACTION_ID = "readconfig";
    private final String parameter;

    public ReadConfigAction(String parameter) {
        super(ACTION_ID);
        this.parameter = parameter;
    }
    @Override
    void executeAction() {
        PrintServer.readConfig(this.parameter);
    }
}
