package printserver.actions;

import printserver.server.PrintServer;

public class StopAction extends PrivilegedPrintServerAction<StopAction> {
    private static final String ACTION_ID = "stop";

    public StopAction() {
        super(ACTION_ID);
    }
    @Override
    void executeAction() {
        PrintServer.stop();
    }
}
