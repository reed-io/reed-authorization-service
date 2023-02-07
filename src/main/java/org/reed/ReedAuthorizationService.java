/**
 * E5Projects @ org.Reed/ReedAuthorizationService.java
 */
package org.reed;

import org.reed.bootup.ReedStarter;
import org.reed.bootup.SpringBootBootup;
import org.reed.define.BaseErrorCode;
import org.reed.entity.ReedResult;
import org.reed.log.ReedLogger;
import org.reed.utils.EnderUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author chenxiwen
 * @createTime 2020年03月02日 下午3:13
 * @description
 */
// @EnableServiceRegister
// @EnableServiceTrace
// @EnableAdminClient
// @ReedUnifiedConfig
public class ReedAuthorizationService extends SpringBootBootup implements ResponseBodyAdvice {
    public static final String NAME = "REED-AUTHORIZATION-SERVICE";

    @Override
    protected void beforeStart() {
        ReedStarter.putArgs("spring.thymeleaf.prefix", "classpath:/views/");
        ReedStarter.putArgs("spring.thymeleaf.suffix", ".html");
        ReedStarter.putArgs("spring.thymeleaf.cache", "false");

        ReedStarter.putArgs("spring.mvc.view.prefix", "/WEB-INF/views/");
        ReedStarter.putArgs("spring.mvc.view.suffix", ".html");
    }

    @Override
    protected void afterStart(SpringApplication application, ApplicationContext context) {

    }

    /**
     * @return Project/Module Name
     */
    @Override
    public String getModuleName() {
        return NAME;
    }

    public static void main(String[] args) {
        new ReedAuthorizationService().start(args);
    }


    /**
     * Whether this component supports the given controller method return type and the selected
     * {@code HttpMessageConverter} type.
     *
     * @param returnType the return type
     * @param converterType the selected converter type
     * @return {@code true} if {@link #beforeBodyWrite} should be invoked; {@code false} otherwise
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // ReedLogger.debug("..............................");
        if (returnType.getMethod().getReturnType() == ResponseEntity.class) {
            return true;
        }
        return super.supports(returnType, converterType);
    }

    /**
     * Invoked after an {@code HttpMessageConverter} is selected and just before its write method is
     * invoked.
     *
     * @param body the body to be written
     * @param returnType the return type of the controller method
     * @param selectedContentType the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request the current request
     * @param response the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
            MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        // ReedLogger.debug("---------------------");
        if (body instanceof DefaultOAuth2AccessToken) {
            ReedLogger.trace(EnderUtil.devInfo() + " - OAuth2 token response convert");
            DefaultOAuth2AccessToken result = (DefaultOAuth2AccessToken) body;
            body = new ReedResult.Builder<>().code(BaseErrorCode.SUCCESS_OPERATE).data(result)
                    .build();
        }
        return super.beforeBodyWrite(body, returnType, selectedContentType, selectedConverterType,
                request, response);
    }
}
