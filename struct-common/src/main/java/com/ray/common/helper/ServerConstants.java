package com.ray.common.helper;

import com.ray.common.exception.CommonErrorCode;
import com.ray.common.exception.CommonException;
import com.ray.common.utils.NetUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.List;

/**
 * @author chengrui
 * @date 2022/10/9 11:27:21
 */

@Slf4j
public class ServerConstants {
    public static String SERVER_HOSTNAME;

    public static String SERVER_IP_ADDRESS;

    public static final String API_SUCCESS = "apiSuccess";



    static {
        try {
            List<InetAddress> networkAddress = NetUtils.getNetworkAddress();
            InetAddress inetAddress = networkAddress.get(0);
            SERVER_HOSTNAME = inetAddress.getHostName();
            SERVER_IP_ADDRESS = inetAddress.getHostAddress();
        } catch (Exception e) {
            log.error("获取本机IP地址错误", e);
            throw new CommonException(CommonErrorCode.NOT_EXIST, "获取本机IP地址错误", e);
        }
        log.debug("本机IP={}, hostName={}", SERVER_IP_ADDRESS, SERVER_HOSTNAME);
    }
}