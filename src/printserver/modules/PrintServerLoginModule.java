package printserver.modules;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import printserver.principals.PrintServerPrincipal;

import java.util.*;

public class PrintServerLoginModule implements LoginModule {

    private static final Set<String> allowedUsernames = new HashSet<>(Arrays.asList("Alice", "Bart", "Cecile", "Dirk", "Erica"));

    // initial state
    private Subject subject;
    private CallbackHandler callbackHandler;

    private boolean debug = false;

    private boolean succeeded = false;
    private boolean commitSucceeded = false;

    private String username;
    private char[] password;

    private PrintServerPrincipal userPrincipal;

    /**
     * Initialize this <code>LoginModule</code>.
     *
     * <p>
     *
     * @param subject         the <code>Subject</code> to be authenticated. <p>
     * @param callbackHandler a <code>CallbackHandler</code> for communicating
     *                        with the end user (prompting for user names and
     *                        passwords, for example). <p>
     * @param sharedState     shared <code>LoginModule</code> state. <p>
     * @param options         options specified in the login
     *                        <code>Configuration</code> for this particular
     *                        <code>LoginModule</code>.
     */
    public void initialize(Subject subject,
                           CallbackHandler callbackHandler,
                           Map<java.lang.String, ?> sharedState,
                           Map<java.lang.String, ?> options) {

        this.subject = subject;
        this.callbackHandler = callbackHandler;

        // initialize any configured options
        debug = "true".equalsIgnoreCase((String) options.get("debug"));
    }

    /**
     * Authenticate the user by prompting for a user name and password.
     *
     * <p>
     *
     * @return true in all cases since this <code>LoginModule</code>
     * should not be ignored.
     * @throws FailedLoginException if the authentication fails. <p>
     * @throws LoginException       if this <code>LoginModule</code>
     *                              is unable to perform the authentication.
     */
    public boolean login() throws LoginException {
        // terrible method which should be refactored

        // prompt for a username and password
        if (callbackHandler == null)
            throw new LoginException("Error: no CallbackHandler available " +
                    "to garner authentication information from the user");

        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("user name: ");
        callbacks[1] = new PasswordCallback("password: ", false);

        try {
            callbackHandler.handle(callbacks);
            username = ((NameCallback) callbacks[0]).getName();
            System.out.printf("Username: %s%n", username);
            char[] tmpPassword = ((PasswordCallback) callbacks[1]).getPassword();
            if (tmpPassword == null) {
                // treat a NULL password as an empty password
                tmpPassword = new char[0];
            }
            password = new char[tmpPassword.length];
            System.arraycopy(tmpPassword, 0,
                    password, 0, tmpPassword.length);
            ((PasswordCallback) callbacks[1]).clearPassword();
        } catch (java.io.IOException ioe) {
            throw new LoginException(ioe.toString());
        } catch (UnsupportedCallbackException uce) {
            throw new LoginException("Error: " + uce.getCallback().toString() +
                    " not available to garner authentication information " +
                    "from the user");
        } catch (Exception exception) {
            System.out.println(exception);
        }

        // print debugging information
        if (debug) {
            System.out.println("\t\t[PrintServerLoginModule] " +
                    "user entered user name: " +
                    username);
            System.out.print("\t\t[PrintServerLoginModule] " +
                    "user entered password: ");
            for (char c : password) System.out.print(c);
            System.out.println();
        }

        // verify the username
        boolean usernameCorrect = allowedUsernames.contains(username);

        // Why are we comparing a character array instead of a string....
        // Terrible code which should be improved
        if (usernameCorrect &&
                password.length == 12 &&
                password[0] == 't' &&
                password[1] == 'e' &&
                password[2] == 's' &&
                password[3] == 't' &&
                password[4] == 'P' &&
                password[5] == 'a' &&
                password[6] == 's' &&
                password[7] == 's' &&
                password[8] == 'w' &&
                password[9] == 'o' &&
                password[10] == 'r' &&
                password[11] == 'd') {

            // authentication succeeded!!!
            if (debug)
                System.out.println("\t\t[PrintServerLoginModule] " +
                        "authentication succeeded");
            succeeded = true;
            return true;
        } else {
            // authentication failed -- clean out state
            if (debug)
                System.out.println("\t\t[PrintServerLoginModule] " +
                        "authentication failed");
            succeeded = false;
            username = null;
            password = null;
            if (!usernameCorrect) {
                throw new FailedLoginException("User Name Incorrect");
            } else {
                throw new FailedLoginException("Password Incorrect");
            }
        }
    }

    /**
     * <p> This method is called if the LoginContext's
     * overall authentication succeeded
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * succeeded).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> method), then this method associates a
     * <code>PrintServerPrincipal</code>
     * with the <code>Subject</code> located in the
     * <code>LoginModule</code>.  If this LoginModule's own
     * authentication attempted failed, then this method removes
     * any state that was originally saved.
     *
     * <p>
     *
     * @return true if this LoginModule's own login and commit
     * attempts succeeded, or false otherwise.
     * @throws LoginException if the commit fails.
     */
    public boolean commit() throws LoginException {
        if (!succeeded) {
            return false;
        } else {
            // add a Principal (authenticated identity)
            // to the Subject
            configurePrincipals(subject, username);
            // in any case, clean out state
            username = null;
            password = null;
            commitSucceeded = true;
            return true;
        }
    }

    private void configurePrincipals(Subject subject, String username) {
        switch (username) {
            case "Alice":
                subject.getPrincipals().add(new PrintServerPrincipal("sysadmin"));
                break;
            case "Bart":
                subject.getPrincipals().add(new PrintServerPrincipal("concierge"));
                break;
            case "Cecile":
                subject.getPrincipals().add(new PrintServerPrincipal("poweruser"));
                break;
            case "Dirk":
            case "Erica":
                subject.getPrincipals().add(new PrintServerPrincipal("normaluser"));
                break;
            default:
                throw new IllegalStateException(String.format("User %s is not authenticated", username));
        }
    }

    /**
     * <p> This method is called if the LoginContext's
     * overall authentication failed.
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * did not succeed).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> and <code>commit</code> methods),
     * then this method cleans up any state that was originally saved.
     *
     * <p>
     *
     * @return false if this LoginModule's own login and/or commit attempts
     * failed, and true otherwise.
     * @throws LoginException if the abort fails.
     */
    public boolean abort() throws LoginException {
        if (!succeeded) {
            return false;
        } else if (!commitSucceeded) {
            // login succeeded but overall authentication failed
            succeeded = false;
            username = null;
            password = null;
            userPrincipal = null;
        } else {
            // overall authentication succeeded and commit succeeded,
            // but someone else's commit failed
            logout();
        }
        return true;
    }

    /**
     * Logout the user.
     *
     * <p> This method removes the <code>PrintServerPrincipal</code>
     * that was added by the <code>commit</code> method.
     *
     * <p>
     *
     * @return true in all cases since this <code>LoginModule</code>
     * should not be ignored.
     * @throws LoginException if the logout fails.
     */
    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(userPrincipal);
        succeeded = commitSucceeded;
        username = null;
        password = null;
        userPrincipal = null;
        return true;
    }
}