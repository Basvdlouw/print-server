package printserver.actions;

import printserver.server.PrintServer;

public class StartAction extends PrivilegedPrintServerAction<StartAction> {
    private static final String ACTION_ID = "start";

    public StartAction() {
        super(ACTION_ID);
    }
    @Override
    void executeAction() {
        PrintServer.start();
    }
}
