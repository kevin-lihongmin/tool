package com.kevin.tool.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;
import lombok.Value;

import java.util.List;

public class SerilazialTest {

    public static void main(String[] args) {

        String str = "{\"code\":\"1010112\",\"externalCode\":\"1010112\",\"property\":[{\"groupName\":\"测试属性组\",\"propertyList\":[{\"code\":\"345\",\"values\":[{\"value\":\"333\"}],\"name\":\"验证新增\",\"source\":\"PDM\"}],\"groupCode\":\"AG_000044\"},{\"groupName\":\"运输属性\",\"propertyList\":[{\"code\":\"239277\",\"externalCode\":\"grossWeight\",\"values\":[{\"value\":\"0.000\"}],\"name\":\"装箱毛重\",\"source\":\"PDM\"},{\"code\":\"239278\",\"externalCode\":\"netWeight\",\"values\":[{\"value\":\"1.34\"}],\"name\":\"装箱净重\",\"source\":\"PDM\"},{\"code\":\"239298\",\"externalCode\":\"qty\",\"values\":[{\"value\":\"\"}],\"name\":\"装箱数\",\"source\":\"PDM\"},{\"code\":\"239299\",\"externalCode\":\"whetherReplenishNumb\",\"values\":[{\"value\":\"\"}],\"name\":\"是否按装箱数补货\",\"source\":\"PDM\"},{\"code\":\"239280\",\"externalCode\":\"volume\",\"values\":[{\"value\":\"\"}],\"name\":\"装箱体积\",\"source\":\"PDM\"},{\"code\":\"239281\",\"externalCode\":\"volumeUnit\",\"values\":[{\"value\":\"\"}],\"name\":\"体积单位\",\"source\":\"PDM\"}],\"groupCode\":\"AG_000047\"},{\"groupName\":\"BI属性组\",\"propertyList\":[{\"code\":\"908\",\"externalCode\":\"\",\"values\":[{\"value\":\"\"}],\"name\":\"测试属性\",\"source\":\"PDM\"}],\"groupCode\":\"AG_000043\"},{\"groupName\":\"SAP属性组\",\"propertyList\":[{\"code\":\"239174\",\"externalCode\":\"MMSTA\",\"values\":[{\"value\":\"108116\",\"desc\":\"停用\"},{\"value\":\"108152\",\"desc\":\"因任务清单/BOM而被冻结\"}],\"name\":\"商品状态\",\"source\":\"SAP\"},{\"code\":\"239179\",\"externalCode\":\"ZZZLSPSX\",\"values\":[{\"value\":\"\"}],\"name\":\"战略商品类别\",\"source\":\"SAP\"},{\"code\":\"239284\",\"externalCode\":\"twoDimensionalCodeCo\",\"values\":[{\"value\":\"N\"}],\"name\":\"是否二维码\",\"source\":\"SAP\"},{\"code\":\"239173\",\"externalCode\":\"ZZSPBM\",\"values\":[{\"value\":\"\"}],\"name\":\"商品部门\",\"source\":\"SAP\"},{\"code\":\"239183\",\"externalCode\":\"WHERL\",\"values\":[{\"value\":\"108709\",\"desc\":\"中国\"}],\"name\":\"产国\",\"source\":\"SAP\"},{\"code\":\"239182\",\"externalCode\":\"WHERR\",\"values\":[{\"value\":\"\"}],\"name\":\"产地\",\"source\":\"SAP\"},{\"code\":\"239176\",\"externalCode\":\"MEINS\",\"values\":[{\"value\":\"108662\",\"desc\":\"个\"},{\"value\":\"111428\",\"desc\":\"每一个\"}],\"name\":\"基本计量单位\",\"source\":\"SAP\"},{\"code\":\"239293\",\"externalCode\":\"openCommodityRetailP\",\"values\":[{\"value\":\"0.0\"}],\"name\":\"开放商品零售限价\",\"source\":\"SAP\"},{\"code\":\"239287\",\"externalCode\":\"cityAdjustmentPriceP\",\"values\":[{\"value\":\"5.000\"}],\"name\":\"市调计价计算百分比\",\"source\":\"SAP\"},{\"code\":\"239189\",\"externalCode\":\"ZZLSDJ\",\"values\":[{\"value\":\"108146\",\"desc\":\"市调定价\"}],\"name\":\"零售定价\",\"source\":\"SAP\"},{\"code\":\"239282\",\"externalCode\":\"specification\",\"values\":[{\"value\":\"1\"}],\"name\":\"规格\",\"source\":\"SAP\"},{\"code\":\"239283\",\"externalCode\":\"shelfLife\",\"values\":[{\"value\":\"0\"}],\"name\":\"保质期\",\"source\":\"SAP\"},{\"code\":\"239294\",\"externalCode\":\"mainProvinces\",\"values\":[{\"value\":\"\"}],\"name\":\"主营省份\",\"source\":\"SAP\"},{\"code\":\"239295\",\"externalCode\":\"productManager\",\"values\":[{\"value\":\"\"}],\"name\":\"商品负责人\",\"source\":\"SAP\"},{\"code\":\"239296\",\"externalCode\":\"oldMaterialNumber\",\"values\":[{\"value\":\"\"}],\"name\":\"旧物料号\",\"source\":\"SAP\"},{\"code\":\"239297\",\"externalCode\":\"whetherTaxExemption\",\"values\":[{\"value\":\"\"}],\"name\":\"是否免税\",\"source\":\"SAP\"},{\"code\":\"239300\",\"externalCode\":\"sellingPoint\",\"values\":[{\"value\":\"\"}],\"name\":\"商品卖点\",\"source\":\"SAP\"},{\"code\":\"239286\",\"externalCode\":\"grossProfit\",\"values\":[{\"value\":\"15\"}],\"name\":\"商品毛利份数\",\"source\":\"SAP\"},{\"code\":\"239279c\",\"externalCode\":\"weightUnit\",\"values\":[{\"value\":\"\"}],\"name\":\"重量单位\",\"source\":\"SAP\"},{\"code\":\"239285\",\"externalCode\":\"deliveryMode\",\"values\":[{\"value\":\"\"}],\"name\":\"配送模式\",\"source\":\"SAP\"},{\"code\":\"239288\",\"externalCode\":\"minimumReplenishment\",\"values\":[{\"value\":\"10\"}],\"name\":\"最小补货数量\",\"source\":\"SAP\"},{\"code\":\"239180\",\"externalCode\":\"ZZSPSX\",\"values\":[{\"value\":\"108132\",\"desc\":\"香烟\"}],\"name\":\"商品主属性\",\"source\":\"SAP\"},{\"code\":\"239185\",\"externalCode\":\"ZZHYDJ\",\"values\":[{\"value\":\"108134\",\"desc\":\"区域定价\"}],\"name\":\"会员定价\",\"source\":\"SAP\"},{\"code\":\"239186\",\"externalCode\":\"ZZCLASS\",\"values\":[{\"value\":\"\"}],\"name\":\"酒庄等级\",\"source\":\"SAP\"},{\"code\":\"239184\",\"externalCode\":\"ZZGRAPE\",\"values\":[{\"value\":\"\"}],\"name\":\"葡萄品种\",\"source\":\"SAP\"},{\"code\":\"239175\",\"externalCode\":\"ZZKFSPSX\",\"values\":[{\"value\":\"\"}],\"name\":\"开放商品属性\",\"source\":\"SAP\"},{\"code\":\"239187\",\"externalCode\":\"ZZSPZSX\",\"values\":[{\"value\":\"\"}],\"name\":\"商品子属性\",\"source\":\"SAP\"},{\"code\":\"239188\",\"externalCode\":\"ZZSCCJ\",\"values\":[{\"value\":\"\"}],\"name\":\"厂家\",\"source\":\"SAP\"},{\"code\":\"239178\",\"externalCode\":\"ZZQY\",\"values\":[{\"value\":\"\"}],\"name\":\"区域属性\",\"source\":\"SAP\"},{\"code\":\"239181\",\"externalCode\":\"MTART\",\"values\":[{\"value\":\"108372\",\"desc\":\"商品\"}],\"name\":\"商品类型\",\"source\":\"SAP\"},{\"code\":\"333\",\"externalCode\":\"333\",\"values\":[{\"value\":\"\"}],\"name\":\"333\",\"source\":\"PDM\"}]}]}";

        Result result = JSON.parseObject(str, Result.class);
        System.out.println(result);
    }

    @Data
    @ToString
    public static class Result {
        private String code;

        private Integer externalCode;

        private List<Property> property;


    }

    @Data
    @ToString
    public static class Property {

        private String groupName;

        private List<PropertyList> propertyList;

        private String groupCode;
    }

    @Data
    @ToString
    public static class PropertyList {

        private String name;

        private String source;

        private List<Values> values;

    }

    @Data
    @ToString
    public static class Values{
        private String value;
    }

    public void test() {
        User user = new User();
        user.setId(1L);
        user.setName("kevin");

        Department department = new Department(1L, "dept-1");
        Department department2 = new Department(2L, "dept-2");
        Department department3 = new Department(3L, "dept-3");
        user.setDepartmentList(Lists.newArrayList(department, department2, department3));

        String s = JSON.toJSONString(user);

        Object parse = JSON.parseObject(s, User.class);
        System.out.println(parse);
    }

    @Data
    public static class User {
        private Long id;

        private String name;

        private List<Department> departmentList;
    }

    @Data
    public static class Department {
        private Long id;

        private String name;

        public Department(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
