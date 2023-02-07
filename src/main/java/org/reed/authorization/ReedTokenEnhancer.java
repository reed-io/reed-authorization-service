/**
 * E5Projects @ org.Reed.authorization/ReedTokenEnhancer.java
 */
package org.reed.authorization;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Map;

/**
 * @author chenxiwen
 * @createTime 2020年03月03日 下午6:26
 * @description
 */
@Component
public class ReedTokenEnhancer implements TokenEnhancer {

    private final Map<String, Object> ReedAdditionalInfo = Collections.emptyMap();

    /**
     * Provides an opportunity for customization of an access token (e.g. through its additional
     * information map) during the process of creating a new token for use by a client.
     *
     * @param accessToken the current access token with its expiration and refresh token
     * @param authentication the current authentication including client and user details
     * @return a new token enhanced with additional information
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
            OAuth2Authentication authentication) {
        ReedAdditionalInfo.clear();
        ReedAdditionalInfo.put("orgId", "user's orgid");
        ReedAdditionalInfo.put("deptId", "user's deptId");
        ReedAdditionalInfo.put("employeeNum", "员工编号");
        ReedAdditionalInfo.put("userId", "unique user id in platform");
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(ReedAdditionalInfo);
        }
        return accessToken;
    }
}
