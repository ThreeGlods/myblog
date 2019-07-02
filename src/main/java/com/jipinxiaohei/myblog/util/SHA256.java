package com.jipinxiaohei.myblog.util;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    private static final String strType = "SHA-256";
    /*
    * 字符串SHA加密
    * */

    public static String SHA256(final String str){
        //是否是有效字符串
        String encdeStr="";
        if(str !=null && str.length()>0){
            try {
                //SHA 加密开始
                //创建加密对象 病传入加密类型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                //传入要加密的字符串
                byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
                //得到byte类型结果
                encdeStr = HexBin.encode(hash);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return encdeStr;
    }

    public static void main(String[] args) {
        System.out.println("结果："+SHA256("123456"));
    }

}
