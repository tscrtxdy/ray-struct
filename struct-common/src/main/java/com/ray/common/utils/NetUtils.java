package com.ray.common.utils;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * @author chengrui
 * @date 2022/10/9 11:28:37
 */
public class NetUtils {
    private static byte[] localhost = new byte[]{127, 0, 0, 1};

    public static boolean isInternalIp(String ip) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(ip);
        byte[] address = inetAddress.getAddress();
        return isInternalIp(address);
    }

    public static boolean isInternalIp(byte[] address) {
        final byte b0 = address[0];
        final byte b1 = address[1];

        // 127.0.0.1
        if (Arrays.equals(localhost, address)) {
            return true;
        }

        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                }
            default:
                return false;
        }
    }

    public static String getOneNetworkAddress() throws Exception {
        List<InetAddress> addresses = getNetworkAddress();
        if (CollectionUtils.isEmpty(addresses)) {
            return null;
        } else {
            return addresses.get(0).getHostAddress();
        }
    }

    public static List<InetAddress> getNetworkAddress() throws Exception {
        List<InetAddress> result = new ArrayList<>();
        Enumeration<NetworkInterface> netInterfaces;
        netInterfaces = NetworkInterface.getNetworkInterfaces();
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = netInterfaces.nextElement();

            String displayName = ni.getDisplayName();
            String name = ni.getName();
            if (displayName.startsWith("en") || displayName.startsWith("eth") || displayName.startsWith("em") ||
                    name.startsWith("en") || name.startsWith("eth") || name.startsWith("em") || name.startsWith("wlan")) {
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = addresses.nextElement();

                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(':') == -1) {
                        result.add(ip);
                    }
                }
            }
        }
        return result;
    }

    public static String getRemoteIpAddress(HttpServletRequest request) {
        String ip = null;

        // X-Forwarded-For???Squid ????????????
        String ipAddresses = request.getHeader("X-Forwarded-For");

        // Proxy-Client-IP???apache ????????????
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        // WL-Proxy-Client-IP???weblogic ????????????
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        // HTTP_CLIENT_IP????????????????????????
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        // X-Real-IP???nginx????????????
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("X-Real-IP");
        }

        // ???????????????????????????????????????????????????ip?????????????????????????????????????????????,?????????????????????????????????ip?????????????????????IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        // ???????????????????????????????????????request.getRemoteAddr();??????
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }

        // ip??????
        if (ip.equals("127.0.0.1") || ip.endsWith("0:0:0:0:0:0:1")) {
            // ??????????????????????????????IP
            return "127.0.0.1";
        }
        return ip;
    }

    public static String getClientRealIp(ServerHttpRequest request) {
        try {
            String BLANK = "";
            String COMMA = ",";
            final String X_FORWARDED_HEADER = "X-Forwarded-For";
            final String X_REAL_HEADER = "X-Real-IP";
            final String UNKNOWN = "unknown";
            final List<String> forwardedHeaders = request.getHeaders().get(X_FORWARDED_HEADER);
            String ip = (forwardedHeaders == null || forwardedHeaders.isEmpty()) ? BLANK : forwardedHeaders.get(0);
            if (!(ip == null || ip.length() == 0) && !UNKNOWN.equalsIgnoreCase(ip)) {
                int index = ip.indexOf(COMMA);
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            }
            final List<String> realHeaders = request.getHeaders().get(X_REAL_HEADER);
            ip = (realHeaders == null || realHeaders.isEmpty()) ? BLANK : realHeaders.get(0);
            if (!StringUtils.isEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                return ip;
            }
            return request.getRemoteAddress() == null ? BLANK : request.getRemoteAddress().getAddress().getHostAddress();
        } catch (Exception e) {
        }
        return null;
    }
}