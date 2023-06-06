package com.bhole.shop.authn.api;

import com.bhole.shop.authn.api.dto.RegisteredClientReqDTO;
import com.bhole.shop.authn.core.config.EnhanceJdbcRegisteredClientRepository;
import com.bhole.shop.common.aop.annotation.BhApi;
import com.bhole.shop.common.base.bean.ResultBean;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@Tag(name = "Security")
@RestController
@RequestMapping("/security")@Component
public class SecurityManagerController {
    @Autowired
    private EnhanceJdbcRegisteredClientRepository enhanceJdbcRegisteredClientRepository;

    @BhApi
    @Operation(summary = "注册")
    @PostMapping("registry")
    public ResultBean<Void> registry(@RequestBody RegisteredClientReqDTO registeredClientReqDTO) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(registeredClientReqDTO.getClientId())
                .clientSecret("{noop}" + registeredClientReqDTO.getClientSecret())
                .clientName(registeredClientReqDTO.getClientName())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofHours(2))
                        .reuseRefreshTokens(true)
                        .refreshTokenTimeToLive(Duration.ofMinutes(10)).build())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        enhanceJdbcRegisteredClientRepository.save(registeredClient);
        return ResultBean.success();
    }

    @Operation(summary = "删除")
    @PostMapping("delete")
    public ResultBean<Void> delete(String clientId) {
        enhanceJdbcRegisteredClientRepository.deleteByClientId(clientId);
        return ResultBean.success();
    }

    @Operation(summary = "禁用")
    @PostMapping("disable")
    public ResultBean<Void> forbid() {
        // todo
        return ResultBean.success();
    }
}
