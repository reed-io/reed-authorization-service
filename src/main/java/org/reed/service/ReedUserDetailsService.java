/**
 * E5Projects @ org.Reed.service/ReedUserDetailsService.java
 */
package org.reed.service;

import java.util.Set;
import java.util.stream.Collectors;
import org.reed.entity.ReedRole;
import org.reed.entity.ReedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author chenxiwen
 * @createTimestamp 2020年03月03日 下午10:45
 * @description
 */
@Deprecated
// @Service("reedUserDetailsService")
public class ReedUserDetailsService implements UserDetailsService {

    @Autowired
    private ReedUserService ReedUserService;

    /**
     * Locates the user based on the username. In the actual implementation, the search may possibly
     * be case sensitive, or case insensitive depending on how the implementation instance is
     * configured. In this case, the <code>UserDetails</code> object that comes back may have a
     * username that is of a different case than what was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *         GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 调用feign去用户服务获取用户详细信息
        ReedUser ReedUser = ReedUserService.getReedUserByUsername(username).getData();
        User user = new User(ReedUser.getUsername(), ReedUser.getPassword(), ReedUser.getEnabled(),
                ReedUser.getAccountNonExpired(), ReedUser.getCredentialsNonExpired(),
                ReedUser.getAccountNonLocked(), this.obtainGrantedAuthorities(ReedUser));
        return user;
    }

    protected Set<GrantedAuthority> obtainGrantedAuthorities(ReedUser ReedUser) {
        Set<ReedRole> roles =
                ReedUserService.getReedUserRolesByUsername(ReedUser.getUsername()).getData();
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode()))
                .collect(Collectors.toSet());
    }
}
