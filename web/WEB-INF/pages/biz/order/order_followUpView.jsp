<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/order_list.action" ,
    create_url:root + "/biz/order_followUp.action" ,
    update_url:root + "/biz/order_followUp.action" ,
    get_url:root + "/biz/order_get.action" ,
    delete_url:root + "/biz/order_delete.action" ,
    entityName:"order",
    size:0,
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("orderSO.pageIndex") ;
        
        //fillOptions({id:"order.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"orderSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("orderTB", {height:"400px"}) ;
        /*
        E$("order.order_timestamp").datetimepicker({
            timeFormat: "HH:mm:ss"
        });
        */
        E$("eForm").validator();
        //E$("sForm").validator();
        E("eForm").setFirstFocus();
        this.size = 0;
        this.getOrderInfo();
    },
    
    getOrderInfo:function() {
        var params = {} ;
        params["order.id"] = 1 ;
        ajax(
        	root+"/biz/order_getOrderInfo.action", 
            params,
            function(data, textStatus){
                viewJs.entity = data;
                formDeserializeText("customerInfoDiv", "label", data.customer, {}) ;
                formDeserialize("eForm", data, {}) ;
            }
        );
    },
    
    onSaveOk:function(data) {
    	//alert(data.id);
    	V("order.id", data.id)
    },
    
    add:function() {
        this.addRows ("orderProdPacksTB", [{index:this.size, textarea_tagName:"textarea" }], {deep:1});
        this.size ++;
        this.refreshTableList();
    },
    
    refreshTableList:function() {
        var $packageIndexNames = $("#orderProdPacksTB #listBody #packageIndexName", viewJs.view); 

        $packageIndexNames.each(function(i, elem) {
            $(elem).html("商品包" + (i + 1));
        });
    }
    
    
    
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">
	<div class="main_title">
	    <b>业务单业务处理：201407120001</b>
	</div>
	 
	<div class="main_infomation">
	    <table width="100%" border="0">
	        <tr>
	        <td>业务单单号：201407120001</td>
	        <td>业务单发起时间：2013-12-01 13:22</td>
	        <td>业务单发起人：启动业务人员</td>
	        </tr>
	        <tr>
	        <td colspan="3">业务单备注：备注备注备注备注备注</td>
	        </tr>
	    </table>
	</div>
	 
	<div class="main_userinfo">
	    <div class="title"><a href="#" class="btn_blue">360视图</a>客户信息</div>
	    <table width="50%" border="0">
	        <tr>
	        <td>客户号：xcyz_001</td>
	        <td>客户姓名：王小明</td>
	        </tr>
	        <tr>
	        <td>性别：男</td>
	        <td>年龄：28</td>
	        </tr>
	        <tr>
	        <td>电话号码：186888888</td>
	        <td></td>
	        </tr>
	    </table>
	</div>
	 
	<div class="main_order_detail">
	    <div class="title">业务信息
	        <ul class="title_right">
	            <li class="selected" onclick="tabShow('menu1_','con1_',1,2);" id="menu1_1">车险A餐（1份）</li>
	            <li onclick="tabShow('menu1_','con1_',2,2);" id="menu1_2">洗车B套餐（1份）</li>
	        </ul>
	    </div>
	    <div class="content" id="con1_1" style="display:block;">
	        <ul>
	            <li class="selected" onclick="tabShow('menu2_','con2_',1,2);" id="menu2_1">保险</li>
	            <li onclick="tabShow('menu2_','con2_',2,2);" id="menu2_2">道路救援</li>
	        </ul>
	        <div id="con2_1" style="display:block;" >
	            <p>基础商品：车损险、车上司机险</p>
	            <p>购买日期：2013-12-01</p>
	            <p>起效日期：2013-12-01</p>
	            <p>备注：备注备注备注备注备注</p>
	            <b>流程处理记录：</b>
	            <table width="100%" border="0">
	              <tr>
	                <th>流程</th>
	                <th>处理人</th>
	                <th>处理人角色</th>
	                <th>处理环节</th>
	                <th >处理时间</th>
	                <th >处理结果</th>
	                <th >用时</th>
	                <th>备注</th>
	              </tr>
	              <tr>
	                <td>1</td>
	                <td>张小明</td>
	                <td>保险销售员角色</td>
	                <td>业务受理</td>
	                <td>2013-12-01 13:22</td>
	                <td class="c_green">成功</td>
	                <td>13分钟</td>
	                <td>&nbsp;</td>
	              </tr>
	              <tr>
	                <td>2</td>
	                <td>王三伍</td>
	                <td>业务处理人员</td>
	                <td>业务办理</td>
	                <td>2013-12-01 17:22</td>
	                <td class="c_red">失败</td>
	                <td>176分钟</td>
	                <td>客户投诉业务办理慢</td>
	              </tr>
	              <tr>
	                <td>3</td>
	                <td>陈大明</td>
	                <td>业务经理</td>
	                <td>业务办理</td>
	                <td>2013-12-01 18:22</td>
	                <td class="c_green">成功/结束</td>
	                <td>13分钟</td>
	                <td>客户满意</td>
	              </tr>
	            </table>
	            <div class="order_result">
	                <p><b>处理结果记录</b></p>
	                <table width="80%" border="0">
	                <tr>
	                <th>处理结果：</th>
	                <td><select name="select">
	                  <option>继续跟进</option>
	                  <option>回退跟单</option>
	                  <option>是</option>
	                  <option>否</option>
	                </select>
	                  业务处理人：
	                  <input name="Input" type="text"> <a href="#" class="link_blue">选择</a></td>
	                </tr>
	                <tr>
	                <th>备注：</th>
	                <td><textarea name="textarea" cols="46" rows="5"></textarea> <span class="c_red">*</span></td>
	                </tr>
	                <tr>
	                <th>&nbsp;</th>
	                <td><a href="#" class="btn_orange">保存</a></td>
	                </tr>
	            </table>
	            </div>
	        </div>
	        <div id="con2_2" style="display:none;" >我是 道路救援 的内容</div>
	    </div>
	    <div class="content" id="con1_2" style="display:none;">我是 洗车B套餐（1份）的内容</div>
	    
	</div>

</div>
