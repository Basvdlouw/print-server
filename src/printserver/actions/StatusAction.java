package printserver.actions;

import printserver.server.PrintServer;

public class StatusAction extends PrivilegedPrintServerAction<StatusAction> {
    private static final String ACTION_ID = "status";

    public StatusAction() {
        super(ACTION_ID);
    }

    @Override
    void executeAction() {
        PrintServer.status();
    }
}
