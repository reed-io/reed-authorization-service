/**
 * E5Projects @ org.Reed.define/ReedAuthorizationErrorCode.java
 */
package org.reed.define;

/**
 * @author chenxiwen
 * @createTime 2020年03月11日 下午12:56
 * @description
 */
public final class ReedAuthorizationErrorCode extends BaseErrorCode {

    @CodeDescTag(
            desc = "client_id is needed, please post an effective value or check the parameter name again!")
    public static final int CLIENTID_IS_EMPTY = 0x1000;

    @CodeDescTag(
            desc = "client_secret is needed, please post an effective value or check the parameter name again!")
    public static final int CLIENT_SECRET_IS_EMPTY = 0x1001;

    @CodeDescTag(
            desc = "authorized_grant_types is needed, please post an effective value or check the parameter name again!")
    public static final int AUTHORIZED_GRANT_TYPES_IS_EMPTY = 0x1002;

    @CodeDescTag(
            desc = "authorized_grant_types is invalidated, please check the value and make sure they are provided by api doc!")
    public static final int AUTHORIZED_GRANT_TYPES_IS_INVALIDATED = 0x1003;

    @CodeDescTag(
            desc = "web_server_redirect_uri is needed, please post an effective value or check the parameter name again!")
    public static final int WEB_SERVER_REDIRECT_URI_IS_EMPTY = 0x1004;

    @CodeDescTag(
            desc = "web_server_redirect_uri is invalidated, please check the value and make sure it's a effective uri based on http protocol!")
    public static final int WEB_SERVER_REDIRECT_URI_IS_INVALIDATED = 0x1005;

    @CodeDescTag(
            desc = "authorities is needed, please post an effective value or check the parameter name again!")
    public static final int AUTHORITIES_IS_EMPTY = 0x1006;

    @CodeDescTag(
            desc = "access_token_validity is invalidated, please check the value and make sure it's a Integer larger than zero!")
    public static final int ACCESS_TOKEN_VALIDITY_IS_INVALIDATED = 0x1007;

    @CodeDescTag(
            desc = "refresh_token_validity is invalidated, please check the value and make sure it's a Integer larger than zero!")
    public static final int REFRESH_TOKEN_VALIDITY_IS_INVALIDATED = 0x1008;

    @CodeDescTag(
            desc = "additional_information is invalidated, please check the value and make sure it's a json format!")
    public static final int ADDITIONAL_INFORMATION_IS_INVALIDATED = 0x1009;

    @CodeDescTag(
            desc = "autoapprove is needed, please post an effective value or check the parameter name again!")
    public static final int AUTOAPPROVE_IS_EMPTY = 0x100a;

    @CodeDescTag(
            desc = "autoapprove is invalidated, please check the value and make sure it's in the API doc!")
    public static final int AUTOAPPROVE_IS_INVALIDATED = 0x100b;

    @CodeDescTag(
            desc = "add Reed client to DBMS failed! please try again later or pass the code to Administrator!")
    public static final int Reed_CLIENT_ADD_FAILED = 0x100c;

    @CodeDescTag(
            desc = "during add Reed client, an exception happened! please try again later or pass the code to Administrator!")
    public static final int Reed_CLIENT_ADD_EXCEPTION = 0x100d;

    @CodeDescTag(desc = "client_id is already existed!")
    public static final int CLIENTID_ALREADY_EXISTED = 0x100e;
}
