import model.RecordBean;
import utils.SqlLanguageUtils;

public class ManagerMain {
    public static void main(String[] args) {
        SqlLanguageUtils.generateQuery(new RecordBean(), RecordBean.CONDITION_USER);
    }
}
