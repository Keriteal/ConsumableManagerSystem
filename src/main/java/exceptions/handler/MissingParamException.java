package exceptions.handler;

/** 参数错误（缺少）
 * @author keriteal
 **/
public class MissingParamException extends Exception{
    public MissingParamException(String message) {
        super(message);
    }

    public MissingParamException() {
        super("前端传来的参数有误");
    }
}
