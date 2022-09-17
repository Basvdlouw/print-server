package printserver;

import printserver.actions.*;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {

    private static final List<PrivilegedAction<? extends PrivilegedPrintServerAction<?>>> PRIVILEGED_ACTIONS = Arrays.asList(
            new PrintAction("dummy_file"),
            new QueueAction(),
            new TopQueueAction(1),
            new StartAction(),
            new StopAction(),
            new ResetAction(),
            new StatusAction(),
            new ReadConfigAction("dummy_parameter"),
            new SetConfigAction("dummy_parameter", "dummy_value")
    );

    /**
     * Attempt to authenticate the user.
     *
     * @param args input arguments for this application. These are ignored.
     */
    public static void main(String[] args) {
        final LoginContext loginContext = login();
        final Subject subject = loginContext.getSubject();
        printSubjectInformation(subject);

        // Now try to execute the print actions as the authenticated Subject
        executeActions(subject);
    }

    private static LoginContext login() {
        LoginContext loginContext = null;
        try {
            loginContext = new LoginContext("PrintServer", new PrintServerCallbackHandler());
        } catch (LoginException | SecurityException ex) {
            System.err.println("Cannot create LoginContext. " + ex.getMessage());
            System.exit(-1);
        }
        // the user has 3 attempts to authenticate successfully
        int i = 0;
        while (i < 3) {
            try {
                // attempt authentication
                loginContext.login();
                // if we return with no exception, authentication succeeded
                break;

            } catch (LoginException le) {
                System.err.println("Authentication failed:");
                System.err.println("  " + le.getMessage());
            }
            i++;
        }

        // did they fail three times?
        if (i == 3) {
            System.out.println("Sorry");
            System.exit(-1);
        }

        System.out.println("Authentication succeeded!");
        return loginContext;
    }

    private static void printSubjectInformation(final Subject subject) {
        final Iterator<Principal> principalIterator = subject.getPrincipals().iterator();
        System.out.println("Authenticated user has the following Principals:");
        while (principalIterator.hasNext()) {
            final Principal principal = principalIterator.next();
            System.out.println("\t" + principal.toString());
        }

        System.out.println("User has " + subject.getPublicCredentials().size() + " Public Credential(s)");
        System.out.println("User has " + subject.getPrivateCredentials().size() + " Private Credential(s)");
    }

    private static void executeActions(final Subject subject) {
        PRIVILEGED_ACTIONS.forEach(privilegedAction -> Subject.doAsPrivileged(subject, privilegedAction, null));
    }
}
