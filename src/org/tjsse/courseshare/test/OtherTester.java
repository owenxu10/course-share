package org.tjsse.courseshare.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.sql.Time;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.tjsse.courseshare.util.Config;
import org.tjsse.courseshare.util.LibType;

public class OtherTester {
  public static void main(String[] args) throws Exception {
    // System.out.println(LibType.SUBJECT);
    // LibType lt = LibType.FLASH;
    // System.out.println(lt.isEqual(LibType.FLASH, "active"));
    // System.out.println(System.getProperty("file.encoding"));
    // Document doc =
    // Jsoup.parse("<p><img src='aaaaa'></p><p><img src='bbbbb'></p>");
    // Elements eles = doc.select("body").get(0).children();
    // Elements imgs = eles.select("img");
    // for(Element img : imgs) {
    // System.out.println(img.attr("src"));
    // img.attr("src", "haha");
    // }
    // System.out.println(eles);
    // System.out.println(System.currentTimeMillis());
//    File pic = new File("/var/tmp/course-share/problems/resources/1.jpg");
//    byte[] data = null;
//    FileInputStream fis = new FileInputStream(pic);
//    data = new byte[(int) pic.length()];
//    fis.read(data);
//    byte[] ddata = Base64.encodeBase64(data);
//    String a = String.format("<img src='data:image/jpg;base64,%s'>", new String(ddata));
//    System.out.println(a);
//    fis.close();
    InetAddress addr = InetAddress.getLocalHost();
    System.out.println(addr.getCanonicalHostName());
    System.out.println(addr.getHostAddress());
  }
}
