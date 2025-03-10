package com.planetsystems.api.school.staffTransfer.util;

import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpAddressUtil {

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            // X-Forwarded-For can have multiple IPs, first one is the client
            ip = ip.split(",")[0].trim();
        }

        // Convert IPv6 localhost to IPv4
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }

        // Ensure it is an IPv4 address
        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            if (inetAddress.getAddress().length == 4) {
                return inetAddress.getHostAddress();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return ip;
    }
}
