package com.cdfive.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Get host name and ip of the host.
 *
 * Copy from Sentinel.
 */
@Slf4j
public final class HostNameUtil {

    private static String ip;
    private static String hostName;
    private static String lastfieldOfHostName;

    static {
        try {
            // Init the host information.
            resolveHost();
            resolveLastfieldOfHostName();
        } catch (Exception e) {
            log.error("Failed to get local host", e);
        }
    }

    private static void resolveHost() throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        hostName = addr.getHostName();
        ip = addr.getHostAddress();
        if (addr.isLoopbackAddress()) {
            // find the first IPv4 Address that not loopback
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface in = interfaces.nextElement();
                Enumeration<InetAddress> addrs = in.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress address = addrs.nextElement();
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                        ip = address.getHostAddress();
                    }
                }
            }
        }
    }

    private static void resolveLastfieldOfHostName() {
        if (lastfieldOfHostName != null) {
            return;
        }
        String substrOfHostName = null;
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            String[] tokens = hostName.split("\\.");
            substrOfHostName = tokens[tokens.length - 1];

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lastfieldOfHostName = substrOfHostName == null ? "-1" : substrOfHostName;
    }

    public static String getIp() {
        return ip;
    }

    public static String getHostName() {
        return hostName;
    }

    public static String getConfigString() {
        return "{\n"
            + "\t\"machine\": \"" + hostName + "\",\n"
            + "\t\"ip\": \"" + ip + "\"\n"
            + "}";
    }

    public static String getLastfieldOfHostName() {
        return lastfieldOfHostName;
    }

    private HostNameUtil() {}
}
