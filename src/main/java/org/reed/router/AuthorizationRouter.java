/**
 * E5Projects @ org.reed.router/AuthorizationRouter.java
 */
package org.reed.router;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chenxiwen
 * @createTime 2020年03月05日 下午3:27
 * @description
 */
// @Configuration
public class AuthorizationRouter implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("/login").setViewName("reed-login");
    }

}
