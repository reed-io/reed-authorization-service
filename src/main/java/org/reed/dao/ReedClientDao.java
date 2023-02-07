/**
 * E5Projects @ org.Reed.dao/ReedClientDao.java
 */
package org.reed.dao;

import org.apache.ibatis.annotations.*;
import org.reed.entity.ReedClient;

/**
 * @author chenxiwen
 * @createTime 2020年03月11日 下午12:18
 * @description
 */
@Mapper
public interface ReedClientDao {

    @Insert("insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types,"
            + "web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information,"
            + "autoapprove) values(#{ReedClient.clientId}, #{ReedClient.resourceIds}, #{ReedClient.clientSecret}, "
            + "#{ReedClient.scope}, #{ReedClient.authorizedGrantTypes}, #{ReedClient.webServerRedirectUri}, "
            + "#{ReedClient.authorities}, #{ReedClient.accessTokenValidity}, #{ReedClient.refreshTokenValidity}, "
            + "#{ReedClient.additionalInformation}, #{ReedClient.autoapprove})")
    int addReedClient(@Param("ReedClient") ReedClient ReedClient);


    @ResultType(ReedClient.class)
    @Results({@Result(property = "clientId", column = "client_id"),
            @Result(property = "resourceIds", column = "resource_ids"),
            @Result(property = "clientSecret", column = "client_secret"),
            @Result(property = "scope", column = "scope"),
            @Result(property = "authorizedGrantTypes", column = "authorized_grant_types"),
            @Result(property = "webServerRedirectUri", column = "web_server_redirect_uri"),
            @Result(property = "authorities", column = "authorities"),
            @Result(property = "accessTokenValidity", column = "access_token_validity"),
            @Result(property = "refreshTokenValidity", column = "refresh_token_validity"),
            @Result(property = "additionalInformation", column = "additional_information"),
            @Result(property = "autoapprove", column = "autoapprove")})
    @Select("select * from oauth_client_details where client_id=#{clientId}")
    ReedClient findClientByClientId(@Param("clientId") String clientId);
}
