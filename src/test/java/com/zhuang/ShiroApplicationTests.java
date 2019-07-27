package com.zhuang;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroApplicationTests {

    @Test
    public void contextLoads() {

        Md5Hash md5Hash = new Md5Hash("hh");
        SimpleHash md5 = new SimpleHash("MD5", "123", md5Hash, 1024);

        System.out.println(md5.toString());
    }

}
