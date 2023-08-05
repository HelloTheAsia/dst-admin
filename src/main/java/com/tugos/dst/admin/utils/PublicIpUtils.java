package com.tugos.dst.admin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class PublicIpUtils {

  public static String getPublicIP() {
    String ip = null;
    // 第一种方式
    try {
      ip = getNowIP0().trim();
    } catch (Exception e) {
      log.info("第一个个方法,获取IP失败");
    }
    if (!StringUtils.isEmpty(ip))
      return ip;

    try {
      ip = getNowIP1().trim();
    } catch (Exception e) {
      log.info("第二个方法,获取IP失败");
    }
    if (!StringUtils.isEmpty(ip))
      return ip;

    try {
      ip = getNowIP2().trim();
    } catch (Exception e) {
      log.info("第三个方法,获取IP失败");
    }
    return ip;
  }

  // 方法1
  private static String getNowIP2() throws IOException {
    String ip = null;
    String chinaz = "http://ip.chinaz.com";
    StringBuilder inputLine = new StringBuilder();
    String read = "";
    URL url = null;
    HttpURLConnection urlConnection = null;
    BufferedReader in = null;
    try {
      url = new URL(chinaz);
      urlConnection = (HttpURLConnection) url.openConnection();
      in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
      while ((read = in.readLine()) != null) {
        inputLine.append(read + "\r\n");
      }
      Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
      Matcher m = p.matcher(inputLine.toString());
      if (m.find()) {
        String ipstr = m.group(1);
        ip = ipstr;
      }
    } finally {
      if (in != null) {
        in.close();
      }
    }
    if (StringUtils.isEmpty(ip)) {
      throw new RuntimeException();
    }
    return ip;
  }

  // 方法3
  private static String getNowIP0() throws IOException {
    String ip = null;
    String objWebURL = "https://ip.900cha.com/";
    BufferedReader br = null;
    try {
      URL url = new URL(objWebURL);
      br = new BufferedReader(new InputStreamReader(url.openStream()));
      String s = "";
      String webContent = "";
      while ((s = br.readLine()) != null) {
        if (s.indexOf("我的IP:") != -1) {
          ip = s.substring(s.indexOf(":") + 1);
          break;
        }
      }
    } finally {
      if (br != null)
        br.close();
    }
    if (StringUtils.isEmpty(ip)) {
      throw new RuntimeException();
    }
    return ip;
  }
  // 方法4
  private static String getNowIP1() throws IOException {
    String ip = null;
    String objWebURL = "https://bajiu.cn/ip/";
    BufferedReader br = null;
    try {
      URL url = new URL(objWebURL);
      br = new BufferedReader(new InputStreamReader(url.openStream()));
      String s;
      while ((s = br.readLine()) != null) {
        if (s.contains("互联网IP")) {
          ip = s.substring(s.indexOf("'") + 1, s.lastIndexOf("'"));
          break;
        }
      }
    } finally {
      if (br != null)
        br.close();
    }
    if (StringUtils.isEmpty(ip)) {
      throw new RuntimeException();
    }
    return ip;
  }
}
