/**
 * E5Projects @ org.Reed.service/ReedUserService.java
 */
package org.reed.service;

import java.util.HashSet;
import java.util.Set;
import org.reed.entity.ReedResult;
import org.reed.entity.ReedRole;
import org.reed.entity.ReedUser;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenxiwen
 * @createTimestamp 2020年03月03日 下午10:50
 * @description
 */
@Deprecated
// @FeignClient(name = "USER-SERVICE", fallback = ReedUserService.ReedUserServiceFallback.class)
public interface ReedUserService {

    @GetMapping("/user/{username}")
    ReedResult<ReedUser> getReedUserByUsername(@RequestParam("username") String username);

    @GetMapping("/user/roles/{username}")
    ReedResult<Set<ReedRole>> getReedUserRolesByUsername(@RequestParam("username") String username);


    @Component
    final static class ReedUserServiceFallback implements ReedUserService {
        @Override
        public ReedResult<ReedUser> getReedUserByUsername(String username) {
            return new ReedResult.Builder<ReedUser>().code(-1)
                    .message("fallback:getReedUserByUsername").data(null).build();
        }

        @Override
        public ReedResult<Set<ReedRole>> getReedUserRolesByUsername(String username) {
            return new ReedResult.Builder<Set<ReedRole>>().code(-1)
                    .message("fallback:getReedUserRolesByUserId").data(new HashSet<ReedRole>())
                    .build();
        }
    }
}
