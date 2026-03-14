package ar.custom.keycloak.auth;

import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class PaymentBlockedAuthenticatorFactory implements ConfigurableAuthenticatorFactory {

    public static final String PROVIDER_ID = "payment-blocked-authenticator";

    @Override
    public String getDisplayType() {
        return "Payment & Blocked Check";
    }

    @Override
    public String getReferenceCategory() {
        return "login";
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public String getHelpText() {
        return "Verifica si el usuario tiene pagos pendientes o está bloqueado.";
    }

    @Override
    public <C> C getConfig() {
        return null;
    }
}