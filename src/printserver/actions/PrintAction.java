package printserver.actions;

import printserver.server.PrintServer;
public class PrintAction extends PrivilegedPrintServerAction<PrintAction> {
    private static final String ACTION_ID = "print";
    private final String filename;
    public PrintAction(String filename) {
        super(ACTION_ID);
        this.filename = filename;
    }
    @Override
    void executeAction() {
        PrintServer.print(this.filename);
    }
}
