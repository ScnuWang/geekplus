package cn.geekview.entity.model;

/**
 * 封装返回的信息
 */
public class TdreamReturnObject {

    private String  returnCode;

    private String  returnMessage;

    private Object returnObject;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public String toString() {
        return "TdreamReturnObject{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMessage='" + returnMessage + '\'' +
                ", returnObject=" + returnObject +
                '}';
    }
}
