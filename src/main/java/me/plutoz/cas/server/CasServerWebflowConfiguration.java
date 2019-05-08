package me.plutoz.cas.server;

import me.plutoz.cas.server.configure.CasServerWebflowConfigurer;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

@Configuration
@EnableConfigurationProperties(CasConfigurationProperties.class)
@AutoConfigureBefore(value = CasWebflowContextConfiguration.class)
public class CasServerWebflowConfiguration {

    @Autowired
    @Qualifier("logoutFlowRegistry")
    private FlowDefinitionRegistry logoutFlowRegistry;
    @Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowRegistry;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("builder")
    private FlowBuilderServices builder;

    @Bean("defaultWebflowConfigurer")
    public CasServerWebflowConfigurer createWebflowConfigurer() {
        final CasServerWebflowConfigurer configurer = new CasServerWebflowConfigurer(builder, loginFlowRegistry, applicationContext, casProperties);
        configurer.setLogoutFlowDefinitionRegistry(logoutFlowRegistry);
        configurer.initialize();
        return configurer;
    }
}
