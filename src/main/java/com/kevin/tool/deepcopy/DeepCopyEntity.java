package com.kevin.tool.deepcopy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *  深度拷贝对象
 * @author kevin
 * @date 2020/9/27 13:46
 * @since 1.0.0
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeepCopyEntity implements Cloneable, java.io.Serializable {
    @Override
    protected DeepCopyEntity clone() {
        try {
            return (DeepCopyEntity)super.clone();
        } catch (CloneNotSupportedException e) {
            log.info("没有实现克隆接口");
            return null;
        }
    }


    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 6172279441386879379L;

    private String id;

    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String field5;
    private String field6;
    private String field7;
    private String field8;
    private String field9;
    private String field10;
    private String field11;
    private String field12;
    private String field13;
    private String field14;
    private String field15;
    private String field16;
    private String field17;
    private String field18;
    private String field19;
    private String field20;
    private String field21;
    private String field22;
    private String field23;
    private String field24;
    private String field25;
    private String field26;
    private String field27;
    private String field28;
    private String field29;
    private String field30;
    private String field31;
    private String field32;
    private String field33;
    private String field34;
    private String field35;
    private String field36;
    private String field37;
    private String field38;
    private String field39;
    private String field40;
    private String field41;
    private String field42;
    private String field43;
    private String field44;
    private String field45;
    private String field46;
    private String field47;
    private String field48;
    private String field49;
    private String field50;

}
