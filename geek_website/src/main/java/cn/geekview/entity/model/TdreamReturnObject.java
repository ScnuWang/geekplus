package cn.geekview.entity.model;

import lombok.Data;

/**
 * 封装返回的信息
 */
@Data
public class TdreamReturnObject {

    private String  returnCode;

    private String  returnMessage;

    private Object returnObject;

}
