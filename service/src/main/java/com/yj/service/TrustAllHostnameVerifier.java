package com.yj.service;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * desc:
 * time: 2019/6/21
 *
 * @author yinYin
 */
public class TrustAllHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
