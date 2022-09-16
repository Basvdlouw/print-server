package printserver.permission;

import java.io.Serializable;
import java.security.BasicPermission;
import java.security.Permission;

public class PrintServerPermission extends BasicPermission implements Serializable {
    String id = null;

    public PrintServerPermission(String id) {
        super(id);
        this.id = id;
    }

    public boolean implies(Permission permission) {
        final PrintServerPermission bp = (PrintServerPermission) permission;
        return id.equals(bp.id);
    }

    public String getActions() {
        return "";
    }

    public int hashCode() {
        return id.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PrintServerPermission bp)) {
            return false;
        }
        return id.equals(bp.id);
    }
}
