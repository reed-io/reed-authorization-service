/**
 * E5Projects @ org.Reed.controller/AuthorizationController.java
 */
package org.reed.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author chenxiwen
 * @createTime 2020年03月05日 下午3:30
 * @description
 */
@Controller
@SessionAttributes("authorizationRequest")
public class AuthorizationController {

    @Autowired
    private ClientDetailsService clientDetailsService;


    private Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @GetMapping("/auth/login")
    public String authLogin(Model model) {
        model.addAttribute("reedLoginUrl", "/login");
        return "reed-login";
    }

    @RequestMapping("/auth/confirm_access")
    public String getAccessConfirmation(Map<String, Object> param, HttpServletRequest request,
            Model model) throws Exception {
        AuthorizationRequest authorizationRequest =
                (AuthorizationRequest) param.get("authorizationRequest");
        if (authorizationRequest == null) {
            return "redirect:/auth/login";
        }
        String clientId = authorizationRequest.getClientId();
        model.addAttribute("scopes", authorizationRequest.getScope());
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        model.addAttribute("client", clientDetails);
        return "reed-grant";
    }
}
