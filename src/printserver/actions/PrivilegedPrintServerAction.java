package printserver.actions;

import java.security.AccessControlException;
import java.security.Permission;
import java.security.PrivilegedAction;

import printserver.permissions.PrintServerPermission;

public abstract class PrivilegedPrintServerAction<T> implements PrivilegedAction<T> {
    private final String actionId;

    public PrivilegedPrintServerAction(String actionId) {
        this.actionId = actionId;
    }

    abstract void executeAction();

    @Override
    public T run() {
        final Permission permission = new PrintServerPermission(this.actionId);
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            try {
                securityManager.checkPermission(permission);
                System.out.printf("[ACCESS GRANTED] Permission to execute %s action was granted.%n", this.actionId);
                executeAction();
            } catch (AccessControlException accessControlException) {
                System.out.printf("[ACCESS DENIED] Permission to execute %s action was denied.%n", this.actionId);
            }
        }
        return null;
    }
}