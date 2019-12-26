package com.kevin.tool.mutilthreadtransaction;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author liujiazhong
 * @date 2019/11/26 17:24
 */
@Data
@Entity
@Table(name = "async_demo_two")
public class AsyncTwo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Timestamp createTime;

    @Column
    private Timestamp updateTime;

    @Column(columnDefinition = "VARCHAR(5) COMMENT 'name'")
    private String name;

}
