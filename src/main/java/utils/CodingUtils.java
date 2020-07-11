package utils;

import java.io.InputStream;

public class CodingUtils {
    public static byte[] streamToByteArray(InputStream ins, int bytesLength) throws Exception {

        byte[] buffer = new byte[bytesLength];

        int offset = 0;
        int bytesRead = 0;
        while ((bytesRead = ins.read(buffer, offset, bytesLength - offset)) > 0) {
            offset += bytesRead;
            if (offset == bytesLength)
                break;
        }
        return offset == bytesLength ? buffer : null;
    }
}
