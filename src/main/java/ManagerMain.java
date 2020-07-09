import com.baidu.bjf.remoting.protobuf.ProtobufIDLGenerator;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import model.ConsumableBean;
import model.RecordBean;
import utils.SqlLanguageUtils;

public class ManagerMain {
    public static void main(String[] args) {
        SqlLanguageUtils.generateQuery(new RecordBean(), RecordBean.CONDITION_USER);
    }
}
