/**
 * E5Projects @ org.reed/BCryptPasswordEncoderTest.java
 */
package org.reed;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author chenxiwen
 * @createTime 2020年03月03日 下午10:31
 * @description
 */
public class BCryptPasswordEncoderTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode("ender"));

        System.out.println(encoder.matches("ender",
                "$2a$10$CI1VpkCRXgLo9O.QQZrK9uqYvQRg24j43Ac7CAc2vbkYlDRDceEDG"));
        System.out.println(encoder.matches("ender", "asdfasdfasdfasdfsdf"));
    }
}
