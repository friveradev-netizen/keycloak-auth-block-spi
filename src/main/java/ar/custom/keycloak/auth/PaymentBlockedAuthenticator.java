package ar.custom.keycloak.auth;

import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.UserModel;

public class PaymentBlockedAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        if (user == null) {
            context.failure(AuthenticationFlowError.INVALID_USER);
            return;
        }

        String blocked = user.getFirstAttribute("blocked");
        String paymentRequired = user.getFirstAttribute("paymentRequired");

        if ("true".equalsIgnoreCase(blocked)) {
            context.failure(AuthenticationFlowError.INVALID_USER,
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("{\"error\": \"invalid_grant\", \"error_description\": \"Account blocked\"}")
                            .build());
            return;
        }

        if ("true".equalsIgnoreCase(paymentRequired)) {
            context.failure(AuthenticationFlowError.INVALID_USER,
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("{\"error\": \"invalid_grant\", \"error_description\": \"Payment Required\"}")
                            .build());
            return;
        }

        context.success();
    }

    @Override public void action(AuthenticationFlowContext context) { }
    @Override public boolean requiresUser() { return true; }
    @Override public boolean configuredFor(org.keycloak.models.KeycloakSession session,
                                           org.keycloak.models.RealmModel realm,
                                           UserModel user) { return true; }
    @Override public void setRequiredActions(org.keycloak.models.KeycloakSession session,
                                             org.keycloak.models.RealmModel realm,
                                             UserModel user) { }
    @Override public void close() { }
}
