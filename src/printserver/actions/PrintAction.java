package printserver.actions;

import printserver.PrintServer;
public class PrintAction extends PrivilegedPrintServerAction<PrintAction> {
    private String filename;

    public PrintAction(String filename) {
        super("print");
        this.filename = filename;
    }
    @Override
    void executeAction() {
        PrintServer.print(this.filename);
    }
}
