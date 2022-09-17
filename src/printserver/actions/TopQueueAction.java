package printserver.actions;

import printserver.server.PrintServer;

public class TopQueueAction extends PrivilegedPrintServerAction<TopQueueAction> {

    private static final String ACTION_ID = "topqueue";

    private final int job;

    public TopQueueAction(int job) {
        super(ACTION_ID);
        this.job = job;
    }

    @Override
    void executeAction() {
        PrintServer.topQueue(this.job);
    }
}
