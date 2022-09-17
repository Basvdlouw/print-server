package printserver.permissions;

import java.io.Serializable;
import java.security.BasicPermission;
import java.security.Permission;

public class PrintServerPermission extends BasicPermission implements Serializable {
    private final String id;

    public PrintServerPermission(String id) {
        super(id);
        this.id = id;
    }

    @Override
    public boolean implies(Permission permission) {
        final PrintServerPermission bp = (PrintServerPermission) permission;
        return id.equals(bp.id);
    }

    @Override
    public String getActions() {
        return "";
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PrintServerPermission)) {
            return false;
        }
        PrintServerPermission bp = (PrintServerPermission) obj;
        return id.equals(bp.id);
    }
}
