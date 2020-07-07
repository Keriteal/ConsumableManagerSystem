import model.RecordBean;
import utils.SqlLanguageUtils;

public class ManagerMain {
    public static void main(String[] args) {
        SqlLanguageUtils.generateQuery(new RecordBean(1,2,3,null, null), RecordBean.CONDITION_USER);
    }
}
