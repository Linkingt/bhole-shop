package com.bhole.shop.authn.core.config;

import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: bhole-shop-authn
 * @description:
 * @author: joke
 * @date: 2023/5/30 10:52
 * @version: 1.0
 */
@Service
public class EnhanceJdbcRegisteredClientRepository extends JdbcRegisteredClientRepository {

    public EnhanceJdbcRegisteredClientRepository(JdbcOperations jdbcOperations) {
        super(jdbcOperations);
    }

    private static final String TABLE_NAME = "oauth2_registered_client";

    private static final String PK_FILTER = "id = ?";

    private static final String FORBID_SQL = "UPDATE " + TABLE_NAME
            + " SET client_secret_expires_at = ?, client_name = ?, client_authentication_methods = ?, authorization_grant_types = ?,"
            + " redirect_uris = ?, scopes = ?, client_settings = ?, token_settings = ?"
            + " WHERE " + PK_FILTER;

    @Override
    public void save(RegisteredClient registeredClient) {
        super.save(registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        return super.findById(id);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return super.findByClientId(clientId);
    }

    public void deleteByClientId(String clientId) {
//        TODO
    }

    public void disable(RegisteredClient registeredClient) {
        List<SqlParameterValue> parameters = new ArrayList<>(super.getRegisteredClientParametersMapper().apply(registeredClient));
        SqlParameterValue id = parameters.remove(0);
        parameters.remove(0); // remove client_id
        parameters.remove(0); // remove client_id_issued_at
        parameters.remove(0); // remove client_secret
        parameters.add(id);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        super.getJdbcOperations().update(FORBID_SQL, pss);
    }

}
