package app.entity;

import lombok.Data;

/**
 * Author: D.Yang
 * Email: koyangslash@gmail.com
 * Date: 16/8/31
 * Time: 下午5:50
 * Describe: 封装Json返回信息
 */
@Data
public class JsonResult {
    private boolean success;
    private String status;
    private String msg;
    private Object obj;

}
