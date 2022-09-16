package printserver.actions;

import printserver.server.PrintServer;
public class PrintAction extends PrivilegedPrintServerAction<PrintAction> {
    private final String filename;

    public PrintAction(String filename) {
        super("print");
        this.filename = filename;
    }
    @Override
    void executeAction() {
        PrintServer.print(this.filename);
    }
}
