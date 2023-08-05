package com.tugos.dst.admin.utils;

import org.junit.Test;

public class PublicIpUtilsTest {

  @Test
  public void should_get_public_ip_when_get_ip() {
    String publicIP = PublicIpUtils.getPublicIP();
    System.out.println("publicIP = " + publicIP);
  }
}
