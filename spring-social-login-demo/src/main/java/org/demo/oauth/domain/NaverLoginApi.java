package org.demo.oauth.domain;

import com.github.scribejava.core.builder.api.DefaultApi20;

/**
 * ref ::  https://github.com/Blackseed/NaverLoginTutorial
 */
public class NaverLoginApi extends DefaultApi20 {

    protected NaverLoginApi() {

    }

    public static NaverLoginApi instance() {
        return InstanceHolder.INSTANCE;
    }

    public static class InstanceHolder {

        private static final NaverLoginApi INSTANCE = new NaverLoginApi();
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://nid.naver.com/oauth2.0/authorize";
    }
}
