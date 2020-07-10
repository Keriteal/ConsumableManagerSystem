import com.baidu.bjf.remoting.protobuf.ProtobufIDLGenerator;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import model.ConsumableBean;
import model.RecordBean;
import utils.SqlLanguageUtils;
import utils.SqlStatementUtils;

public class ManagerMain {
    public static void main(String[] args) {
        System.out.println(SqlStatementUtils.generateInsert(RecordBean.class));
    }
}
