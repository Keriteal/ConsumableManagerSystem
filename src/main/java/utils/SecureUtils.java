package utils;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecureUtils {
    public static String CryptMD5(String pass) {
        if(pass == null || pass.length()==0 ){
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(pass.getBytes());
            byte[] digests = md.digest();
            for(byte b : digests) {
                if (b<0) {
                    b += 256;
                }
                if(b<16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(b));
            }
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            return null;
        }
        return sb.toString();
    }
}
