package utils;

import com.google.protobuf.util.Timestamps;

import java.sql.Timestamp;
import java.util.Date;

public class ProtobufUtils {
    public static com.google.protobuf.Timestamp NativeTimestampToProtoTimestamp(Timestamp timestamp) {
        com.google.protobuf.Timestamp.Builder builder = com.google.protobuf.Timestamp.newBuilder();
        Date date = new Date(timestamp.getTime());
        return Timestamps.fromMillis(date.getTime());
    }
}
