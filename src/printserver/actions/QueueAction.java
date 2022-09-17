package printserver.actions;

import printserver.server.PrintServer;

public class QueueAction extends PrivilegedPrintServerAction<QueueAction> {
    private static final String ACTION_ID = "queue";

    public QueueAction() {
        super(ACTION_ID);
    }

    @Override
    void executeAction() {
        PrintServer.queue();
    }
}
