# tool
一些工具和测试结果



public String queryOrderDemandToOrderDetailListReport(String storeId, String skuCode, String payStartTime, String payEndTime, String serviceProviderId, String billStatus,
                                                String pcode, String code, String sapCode, String serviceTypeId, String serviceName){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    StringBuffer sql=new StringBuffer();
    StringBuffer sql_page=new StringBuffer();
    StringBuffer sqlCondition=new StringBuffer();
    sqlCondition.append("  ");
    if( StringUtils.isNotBlank(payStartTime)){
        long startTimeLong = Long.parseLong(payStartTime);
        Date s_date = new Date(startTimeLong);
        payStartTime = sdf.format(s_date);
        sqlCondition.append(" AND ");
        sqlCondition.append(" T0.PAY_TIME >= TO_DATE('"+payStartTime+" 00:00:00"+"','").append("yyyy-mm-dd hh24:mi:ss')");
    }
    if(StringUtils.isNotBlank(payEndTime)){
        long endTimeLong = Long.parseLong(payEndTime);
        Date e_date = new Date(endTimeLong);
        payEndTime = sdf.format(e_date);
        sqlCondition.append(" AND ");
        sqlCondition.append(" T0.PAY_TIME <= TO_DATE('"+payEndTime+" 23:59:59"+"','").append("yyyy-mm-dd hh24:mi:ss')");
    }
    if(StringUtils.isNotBlank(storeId)){
        sqlCondition.append(" AND ");
        sqlCondition.append("  T0.STORE_ID = '"+ storeId +"' ");
    }
    if(StringUtils.isNotBlank(skuCode)){
        sqlCondition.append(" AND ");
        sqlCondition.append("  T1.SKU_CODE LIKE '%"+ skuCode +"%' ");
    }
    if(StringUtils.isNotBlank(billStatus)){
        /**自定义的一个状态：11，代表分拨已开单，判断条件即为销售订单单号不为空*/
        if(billStatus.indexOf("11")!=-1){
            sqlCondition.append(" AND ");
            sqlCondition.append("  nvl(decode( T17.is_vso,1,tt2.order_code,0,T17.Order_Code,''),'0')<>'0'  and T0.BILL_STATUS= '05'");
        }else if(billStatus.indexOf("12")!=-1){
            sqlCondition.append(" AND ");
            sqlCondition.append("  nvl(decode( T17.is_vso,1,tt2.order_code,0,T17.Order_Code,''),'0')='0' and T0.BILL_STATUS= '05' ");
        }else{
            sqlCondition.append(" AND ");
            sqlCondition.append("  T0.BILL_STATUS in("+ billStatus +") ");
        }
    }
    if(serviceProviderId!=null && !serviceProviderId.equals("")){
        sqlCondition.append(" AND ");
        sqlCondition.append("  T10.ID= '"+ serviceProviderId +"' ");
    }

    if(pcode!=null && !pcode.equals("")){
        sqlCondition.append(" AND ");
        sqlCondition.append("  T0.PCODE LIKE '%"+ pcode +"%' ");
    }
    if(code!=null && !code.equals("")){
        sqlCondition.append(" AND ");
        sqlCondition.append("  T0.CODE LIKE '%"+ code +"%'");
    }
    if(sapCode!=null && !sapCode.equals("")){
        sqlCondition.append(" AND ");
        sqlCondition.append("  T17.order_code LIKE '%"+ sapCode +"%' or tt2.order_code LIKE '%"+sapCode+"%' ");
    }

    if(serviceTypeId!=null && !serviceTypeId.equals("")){
        sqlCondition.append(" AND ");
        sqlCondition.append("  T12.TWO_CATEGORY= '"+ serviceTypeId +"' ");
    }
    if(serviceName!=null && !serviceName.equals("")){
        sqlCondition.append(" AND ");
        sqlCondition.append("  T10.NAME LIKE'%"+ serviceName +"%' ");
    }
    sql_page.append(" select d.STORENAME storeName,");
    sql_page.append("        d.PCODE pcode,");
    sql_page.append("        d.CODE code,");
    sql_page.append("        d.BILL_STATUS billStatus,");
    sql_page.append("        d.PAY_TIME payTime,");
    sql_page.append("        d.ALLOT_TIME allotTime,");
    sql_page.append("        d.BUYER buyer,");
    sql_page.append("        d.OFFICENAME belongOffice,");
    sql_page.append("        d.SERVICE_PROVIDER serviceProvider,");
    sql_page.append("        d.SKU_CODE skuCode,");
    sql_page.append("        d.SKU_NAME skuName,");
    sql_page.append("        d.SOCODE sapCode,");
    sql_page.append("        d.SAPTIME sapTime,");
    sql_page.append("        d.PRODUCT_CODE productCode,");
    sql_page.append("        d.PRODUCT_DESC productName,");
    sql_page.append("        d.PURCHASE_PACK_CODE  packCode,");
    sql_page.append("        d.PURCHASE_PACK_NAME packName,");
    sql_page.append("        d.TOTAL_PRICE  totalPrice,");
    sql_page.append("        nvl(d.ORDER_NUM,0)  buyNum,");
    sql_page.append("        nvl(d.COMPANY_AVL_INVENTORY,0) availableQuantity,");
    sql_page.append("        nvl(d.REQNUM,0) demandQuantity,");
    sql_page.append("        (nvl(d.COMPANY_AVL_INVENTORY,0)- nvl(d.REQNUM,0)) remainQuantity,");
    sql_page.append("        d.ORDER_CODE purchaseOrderCode,");
    sql_page.append("        d.SAPCODE saleOrderCode");
    sql_page.append(" from (");
    sql.append("select distinct \n" +
            "       T0.STORE_ID,--网店名称\n" +
            "       T8.NAME AS STORENAME , --网店名称\n" +
            "       T0.PCODE,--平台编号\n" +
            "       T0.CODE,--电商编号\n" +
            "       T0.BILL_STATUS,--订单状态\n" +
            "       T0.PAY_TIME,--付款日期\n" +
            "       T0.ALLOT_TIME,--分拨日期\n" +
            "       T0.BUYER,--客户网名\n" +
            "       T10.ID AS SERVICE_PROVIDER_ID,--服务商编码\n" +
            "       T10.NAME AS SERVICE_PROVIDER,--服务商\n" +
            "       T9.NAME AS OFFICENAME,--办事处\n" +
            "       T1.SKU_CODE,--商品编码\n" +
            "       T1.SKU_NAME,--商品名称\n" +
            "       b.PRODUCT_CODE,--套件编码\n" +
            "       b.PRODUCT_NAME PRODUCT_DESC,--套件描述\n" +
            "       1 as ORDER_NUM,--订单数量\n" +
            "       (T1.RECEIVABLE_FEE-T1.CLOSED_FEE)AS TOTAL_PRICE,--总价\n" +
            "       T3.ORDER_CODE,--采购订单号\n" +
            "       b.po_bill_no,\n" +
            "       b.PO_BILL_id,--b2c上的采购订单号\n" +
            "       pb.package_code AS PURCHASE_PACK_CODE,--采购订单包件编码\n" +
            "       pb.package_name AS PURCHASE_PACK_NAME,--采购订单包件名称\n" +
            "       T17.Order_Code AS SOCODE,--SAP订单号\n" +
            "      case when T17.is_vso = 1 then tt2.creation_time when T17.is_vso = 0 then T17.CREATION_TIME  end  AS SAPTIME ,--SAP上传时间\n" +
            "       (nvl(b.buy_num,0) - nvl(b.close_num,0))*nvl(pb.suit_num,1)*nvl(pb.package_num,1) as REQNUM ,  --需求量\n" +
            "       case when T17.is_vso = 1 then tt2.order_code when T17.is_vso = 0 then T17.Order_Code  else '' end  AS SAPCODE, --销售订单单号\n" +
            "       tt.COMPANY_AVL_INVENTORY   -- 可用量\n" +
            "  from  MPLATFORM.B2C_ORDER T0 \n" +
            "       LEFT JOIN MPLATFORM.B2C_ORDER_GOODS T1 ON T0.ID=T1.ORDER_ID  and T1.dr=0 \n" +
            "       LEFT JOIN MPLATFORM.B2C_ORDER_PRODUCT b on T1.id=b.order_goods_id \n" +
            "       left join (SELECT b.id as product_id, b.code as product_code, b.name as product_name,\n" +
            "       cb.id as suitId,cb.code as suit_code, cb.description as suit_description, sub.child_num as suit_num,nvl(cb1.id,cb.id) as package_id,\n" +
            "       nvl(cb1.code,cb.code) as package_code,nvl(cb1.DESCRIPTION,cb.DESCRIPTION) as package_name,nvl(sub1.child_num,sub.child_num) as package_num\n" +
            "       FROM  MPLATFORM.BASE_PRODUCT_INFO b INNER JOIN MPLATFORM.BASE_PRODUCT_BOM bom  ON bom.PRODUCT_INFO_ID = b.id \n" +
            "       INNER JOIN MPLATFORM.BASE_PRODUCT_BOM_SUB sub  ON sub.PRODUCT_BOM_ID = bom.ID  \n" +
            "       INNER JOIN MPLATFORM.BASE_PRODUCT_INFO cb  ON cb.ID = sub.PRODUCT_INFO_ID  \n" +
            "       LEFT JOIN MPLATFORM.BASE_PRODUCT_BOM bom1 ON bom1.PRODUCT_INFO_ID=cb.id\n" +
            "       LEFT JOIN MPLATFORM.BASE_PRODUCT_BOM_SUB sub1 ON sub1.PRODUCT_BOM_ID = bom1.ID  \n" +
            "       LEFT JOIN MPLATFORM.BASE_PRODUCT_INFO cb1 ON cb1.ID = sub1.PRODUCT_INFO_ID  \n" +
            "       WHERE bom.IS_VALID = 1 AND bom.STATUS = 1  AND bom.DR = 0  AND b.DR = 0   AND cb.DR = 0 \n" +
            "       union all\n" +
            "       select t.id as product_id,t.code as product_code,t.name as product_name,''as suit_id,''as suit_code,'' as suit_name,1,t.id as package_id,t.code as package_code,t.description ,1 as description from MPLATFORM.base_product_info t\n" +
            "       where t.is_sale_product=1 and t.is_product_pack=1\n" +
            "      )pb on b.product_id=pb.product_id  \n" +
            "       left join MPLATFORM.B2B_RPT_PACK_BALANCE tt  on tt.product_id=pb.package_id\n" +
            "       LEFT JOIN MPLATFORM.B2B_PURCHASE_ORDER T3 ON b.po_bill_id=T3.id and T3.dr=0 \n" +
            "       LEFT JOIN MPLATFORM.B2B_PURCHASE_ORDER_ITEM poisrc ON T3.ID=poisrc.PURCHASE_ORDER_ID   and poisrc.dr=0 \n" +
            "       LEFT JOIN MPLATFORM.B2B_PURCHASE_ORDER_ITEM T4 on T4.id=poisrc.src_bill_row_id  and poisrc.dr=0 \n" +
            "       LEFT JOIN MPLATFORM.B2B_SALE_ORDER_ITEM T16 ON T4.ID=T16.SRC_BILL_ROW_ID and T16.dr=0 \n" +
            "       LEFT JOIN MPLATFORM.B2B_SALE_ORDER T17 ON T16.SALE_ORDER_ID=T17.ID and T16.dr=0 \n" +
            "        LEFT JOIN MPLATFORM.B2b_Sale_Order_Item tt1 on tt1.src_bill_id=T17.id  " +
            "       LEFT JOIN MPLATFORM.B2B_SALE_ORDER tt2 on tt2.id=tt1.sale_order_id   " +
            "       LEFT JOIN MPLATFORM.BASE_ORGANIZATION T9 ON T9.ID=T0.BELONG_OFFICE and T9.dr=0\n" +
            "       LEFT JOIN MPLATFORM.B2C_SERVICEPROVIDERINFO T10 ON T0.SERVICE_PROVIDER=T10.ID and T10.dr=0 \n" +
            "       LEFT JOIN MPLATFORM.BASE_CUSTOMER t12   ON T10.CUSTOM_ID=T12.ID    " +
            "       INNER JOIN MPLATFORM.B2C_STORE T8 ON T8.ID=T0.STORE_ID and T8.dr=0          \n" +
            "       where tt.distribution_center_id='9c9130c4-fcfa-425c-8c5a-4f0c180214c6' and T9.Office_Type='6f8ac7aa-956e-4e30-8f62-9ec414fdd0f4'" +
            "    and T1.dr=0  ");

    sql.append(sqlCondition.toString());
    sql.append("union  ");
    sql.append(" select distinct \n" +
            "       T0.STORE_ID,--网店名称 1\n" +
            "       T8.NAME AS STORENAME , --网店名称 2\n" +
            "       T0.PCODE,--平台编号 3\n" +
            "       T0.CODE,--电商编号 4\n" +
            "       T0.BILL_STATUS,--订单状态 5\n" +
            "       T0.PAY_TIME,--付款日期 6\n" +
            "       T0.ALLOT_TIME,--分拨日期 7\n" +
            "       T0.BUYER,--客户网名 8\n" +
            "       T10.ID AS SERVICE_PROVIDER_ID,--服务商编码 9\n" +
            "       T10.NAME AS SERVICE_PROVIDER,--服务商 10\n" +
            "       T9.NAME AS OFFICENAME,--办事处 11\n" +
            "       T1.SKU_CODE,--商品编码 12\n" +
            "       T1.SKU_NAME,--商品名称 13\n" +
            "       b.PRODUCT_CODE,--套件编码 14\n" +
            "       b.PRODUCT_NAME PRODUCT_DESC,--套件描述 15\n" +
            "       1 as ORDER_NUM,--订单数量 16\n" +
            "       (T1.RECEIVABLE_FEE-T1.CLOSED_FEE)AS TOTAL_PRICE,--总价 17\n" +
            "       T3.ORDER_CODE,--采购订单号 18\n" +
            "       b.po_bill_no,-- 19\n" +
            "       b.PO_BILL_id,--b2c上的采购订单号 20\n" +
            "       pb.package_code AS PURCHASE_PACK_CODE,--采购订单包件编码 21\n" +
            "       pb.package_name AS PURCHASE_PACK_NAME,--采购订单包件名称 22\n" +
            "       T17.Order_Code AS SOCODE,--SAP订单号 23\n" +
            "       case when T17.is_vso = 1 then tt2.creation_time when T17.is_vso = 0 then T17.CREATION_TIME  end  AS SAPTIME,--24\n" +
            "       (nvl(b.buy_num,0) - nvl(b.close_num,0))*nvl(pb.suit_num,1)*nvl(pb.package_num,1) as REQNUM  ,  --需求量 25\n" +
            "       case when T17.is_vso = 1 then tt2.order_code when T17.is_vso = 0 then T17.Order_Code  else '' end  AS SAPCODE, --销售订单单号 26\n" +
            "       tt.COMPANY_AVL_INVENTORY --27 \n" +
            "  from  MPLATFORM.B2C_ORDER T0 \n" +
            "       LEFT JOIN MPLATFORM.B2C_ORDER_GOODS T1 ON T0.ID=T1.ORDER_ID  and T1.dr=0 \n" +
            "       LEFT JOIN MPLATFORM.B2C_ORDER_PRODUCT b on T1.id=b.order_goods_id \n" +
            "       LEFT JOIN (SELECT b.id as product_id, b.code as product_code, b.name as product_name,\n" +
            "       cb.id as suitId,cb.code as suit_code, cb.description as suit_description, sub.child_num as suit_num,nvl(cb1.id,cb.id) as package_id,\n" +
            "       nvl(cb1.code,cb.code) as package_code,nvl(cb1.DESCRIPTION,cb.DESCRIPTION) as package_name,nvl(sub1.child_num,sub.child_num) as package_num\n" +
            "       FROM  MPLATFORM.BASE_PRODUCT_INFO b INNER JOIN MPLATFORM.BASE_PRODUCT_BOM bom  ON bom.PRODUCT_INFO_ID = b.id \n" +
            "       INNER JOIN MPLATFORM.BASE_PRODUCT_BOM_SUB sub  ON sub.PRODUCT_BOM_ID = bom.ID  \n" +
            "       INNER JOIN MPLATFORM.BASE_PRODUCT_INFO cb  ON cb.ID = sub.PRODUCT_INFO_ID  \n" +
            "       LEFT JOIN MPLATFORM.BASE_PRODUCT_BOM bom1 ON bom1.PRODUCT_INFO_ID=cb.id\n" +
            "       LEFT JOIN MPLATFORM.BASE_PRODUCT_BOM_SUB sub1 ON sub1.PRODUCT_BOM_ID = bom1.ID  \n" +
            "       LEFT JOIN MPLATFORM.BASE_PRODUCT_INFO cb1 ON cb1.ID = sub1.PRODUCT_INFO_ID  \n" +
            "       WHERE bom.IS_VALID = 1 AND bom.STATUS = 1  AND bom.DR = 0  AND b.DR = 0   AND cb.DR = 0 \n" +
            "       union all\n" +
            "       select t.id as product_id,t.code as product_code,t.name as product_name,''as suit_id,''as suit_code,'' as suit_name,1,t.id as package_id,t.code as package_code,t.description ,1 as description from MPLATFORM.base_product_info t\n" +
            "       where t.is_sale_product=1 and t.is_product_pack=1\n" +
            "       ) pb on b.product_id=pb.product_id \n" +
            "       left join MPLATFORM.B2B_RPT_PACK_BALANCE tt  on tt.product_id=pb.package_id\n" +
            "       LEFT JOIN MPLATFORM.B2B_PURCHASE_ORDER T3 ON b.po_bill_id=T3.id and T3.dr=0 \n" +
            "       LEFT JOIN MPLATFORM.B2B_PURCHASE_ORDER_ITEM T4 on T3.id=T4.PURCHASE_ORDER_ID  and T4.dr=0 \n" +
            "       LEFT JOIN MPLATFORM.B2B_SALE_ORDER_ITEM T16 ON T4.ID=T16.SRC_BILL_ROW_ID and T16.dr=0 \n" +
            "       LEFT JOIN B2B_SALE_ORDER T17 ON T16.SALE_ORDER_ID=T17.ID and T16.dr=0 \n" +
            "       LEFT JOIN MPLATFORM.B2b_Sale_Order_Item tt1 on tt1.src_bill_id=T17.id       " +
            "       LEFT JOIN MPLATFORM.B2B_SALE_ORDER tt2 on tt2.id=tt1.sale_order_id " +
            "       LEFT JOIN MPLATFORM.BASE_ORGANIZATION T9 ON T9.ID=T0.BELONG_OFFICE and T9.dr=0\n" +
            "       LEFT JOIN MPLATFORM.B2C_SERVICEPROVIDERINFO T10 ON T0.SERVICE_PROVIDER=T10.ID and T10.dr=0 \n" +
            "       LEFT JOIN MPLATFORM.BASE_CUSTOMER t12   ON T10.CUSTOM_ID=T12.ID  " +
            "       INNER JOIN MPLATFORM.B2C_STORE T8 ON T8.ID=T0.STORE_ID and T8.dr=0     \n" +
            "       where tt.distribution_center_id='9c9130c4-fcfa-425c-8c5a-4f0c180214c6'  and  " +
            "   T1.dr=0 ");
    sql.append(sqlCondition.toString());
    sql_page.append(sql.toString()).append(")d");
    return sql_page.toString();
}
