package com.sip.shortnews.config;

/**
 * Created by ssd on 9/7/16.
 */
public class ServerConfig {
    public static String getDefault_domain() {
        return default_domain;
    }

    public static void setDefault_domain(String default_domain) {
        ServerConfig.default_domain = default_domain;
    }

    public static String default_domain;
}
