/**
 * E5Projects @ org.Reed.controller/ClientController.java
 */
package org.reed.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.reed.dao.ReedClientDao;
import org.reed.define.ReedAuthorizationErrorCode;
import org.reed.entity.ReedClient;
import org.reed.entity.ReedResult;
import org.reed.log.ReedLogger;
import org.reed.utils.EnderUtil;
import org.reed.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson2.JSON;

/**
 * @author chenxiwen
 * @createTime 2020年03月11日 上午11:02
 * @description
 */
@RestController
@RequestMapping("/client")
public class ClientController extends ReedBaseController {

    private final String[] AUTHORIZED_GRANT_TYPES =
            {"authorization_code", "password", "client_credentials", "implicit", "refresh_token"};
    private final Set<String> types = new HashSet<String>(Arrays.asList(AUTHORIZED_GRANT_TYPES));
    private final String[] AUTOAPPROVES = {"true", "false"};
    private final Set<String> autoapproves = new HashSet<>(Arrays.asList(AUTOAPPROVES));


    @Autowired
    private ReedClientDao ReedClientDao;

    @PostMapping("/add")
    public ReedResult<ReedClient> addClient(@RequestParam("client_id") String client_id,
            @RequestParam("resource_ids") String resource_ids,
            @RequestParam("client_secret") String client_secret,
            @RequestParam("scope") String scope,
            @RequestParam("authorized_grant_types") String authorized_grant_types,
            @RequestParam("web_server_redirect_uri") String web_server_redirect_uri,
            @RequestParam("authorities") String authorities,
            @RequestParam("access_token_validity") int access_token_validity,
            @RequestParam("refresh_token_validity") int refresh_token_validity,
            @RequestParam("additional_information") String additional_information,
            @RequestParam("autoapprove") String autoapprove) {
        ReedResult<ReedClient> result = new ReedResult<>();
        if (StringUtil.isEmpty(client_id)) {
            result.setCode(ReedAuthorizationErrorCode.CLIENTID_IS_EMPTY);
            return result;
        }
        if (StringUtil.isEmpty(resource_ids)) {
            resource_ids = "";
        }
        if (StringUtil.isEmpty(client_secret)) {
            result.setCode(ReedAuthorizationErrorCode.CLIENT_SECRET_IS_EMPTY);
            return result;
        }
        if (StringUtil.isEmpty(scope)) {
            scope = "";
        }
        if (StringUtil.isEmpty(authorized_grant_types)) {
            result.setCode(ReedAuthorizationErrorCode.AUTHORIZED_GRANT_TYPES_IS_EMPTY);
            return result;
        }
        for (String type : authorized_grant_types.split(",")) {
            if (!types.contains(type.trim().toLowerCase())) {
                result.setCode(ReedAuthorizationErrorCode.AUTHORIZED_GRANT_TYPES_IS_INVALIDATED);
                return result;
            }
        }
        if (StringUtil.isEmpty(web_server_redirect_uri)) {
            result.setCode(ReedAuthorizationErrorCode.WEB_SERVER_REDIRECT_URI_IS_EMPTY);
            return result;
        }
        if (!StringUtil.isUrl(web_server_redirect_uri)) {
            result.setCode(ReedAuthorizationErrorCode.WEB_SERVER_REDIRECT_URI_IS_INVALIDATED);
            return result;
        }
        if (StringUtil.isEmpty(authorities)) {
            authorities = "";
        }
        if (access_token_validity <= 0) {
            result.setCode(ReedAuthorizationErrorCode.ACCESS_TOKEN_VALIDITY_IS_INVALIDATED);
            return result;
        }
        if (refresh_token_validity <= 0) {
            result.setCode(ReedAuthorizationErrorCode.REFRESH_TOKEN_VALIDITY_IS_INVALIDATED);
            return result;
        }
        if (StringUtil.isEmpty(additional_information)) {
            additional_information = "";
        } else {
            if (!JSON.isValid(additional_information)) {
                result.setCode(ReedAuthorizationErrorCode.ADDITIONAL_INFORMATION_IS_INVALIDATED);
                return result;
            }
        }
        if (StringUtil.isEmpty(autoapprove)) {
            result.setCode(ReedAuthorizationErrorCode.AUTOAPPROVE_IS_EMPTY);
            return result;
        }
        if (!autoapproves.contains(autoapprove)) {
            result.setCode(ReedAuthorizationErrorCode.AUTOAPPROVE_IS_INVALIDATED);
            return result;
        }

        ReedClient ReedClient = new ReedClient();
        ReedClient.setClientId(client_id);
        ReedClient.setResourceIds(resource_ids);
        ReedClient.setClientSecret(new BCryptPasswordEncoder().encode(client_secret));
        ReedClient.setScope(scope);
        ReedClient.setAuthorizedGrantTypes(authorized_grant_types);
        ReedClient.setWebServerRedirectUri(web_server_redirect_uri);
        ReedClient.setAuthorities(authorities);
        ReedClient.setAccessTokenValidity(access_token_validity);
        ReedClient.setRefreshTokenValidity(refresh_token_validity);
        ReedClient.setAdditionalInformation(additional_information);
        ReedClient.setAutoapprove(autoapprove);
        ReedLogger.info(EnderUtil.devInfo() + " - " + ReedClient.toString());

        try {
            ReedClient client = ReedClientDao.findClientByClientId(client_id);
            if (client != null) {
                result.setCode(ReedAuthorizationErrorCode.CLIENTID_ALREADY_EXISTED);
                return result;
            }
            int count = ReedClientDao.addReedClient(ReedClient);
            if (count == 1) {
                result.setCode(ReedAuthorizationErrorCode.SUCCESS_OPERATE);
            } else {
                result.setCode(ReedAuthorizationErrorCode.Reed_CLIENT_ADD_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ReedLogger.error(EnderUtil.devInfo() + " - add client exception:" + e.getMessage());
            result.setCode(ReedAuthorizationErrorCode.Reed_CLIENT_ADD_EXCEPTION);
        }
        return result;
    }

    @PutMapping("/{clientId}")
    public ReedResult<ReedClient> modifyClient() {
        return null;
    }

    @Override
    public String version() {
        return "v1";
    }
}
