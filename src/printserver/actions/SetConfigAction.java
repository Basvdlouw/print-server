package printserver.actions;

import printserver.server.PrintServer;

public class SetConfigAction extends PrivilegedPrintServerAction<SetConfigAction> {
    private static final String ACTION_ID = "setconfig";
    private final String parameter;
    private final String value;

    public SetConfigAction(String parameter, String value) {
        super(ACTION_ID);
        this.parameter = parameter;
        this.value = value;
    }

    @Override
    void executeAction() {
        PrintServer.setConfig(this.parameter, this.value);
    }
}
