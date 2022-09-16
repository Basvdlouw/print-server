package printserver.actions;

import printserver.server.PrintServer;

public class ResetAction extends PrivilegedPrintServerAction<ResetAction> {
    private static final String ACTION_ID = "reset";

    public ResetAction() {
        super(ACTION_ID);
    }
    @Override
    void executeAction() {
        PrintServer.reset();
    }
}
