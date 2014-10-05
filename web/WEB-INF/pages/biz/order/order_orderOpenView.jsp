<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/order_list.action" ,
    create_url:root + "/biz/order_create.action" ,
    update_url:root + "/biz/order_update.action" ,
    get_url:root + "/biz/order_get.action" ,
    delete_url:root + "/biz/order_delete.action" ,
    entityName:"order",
    
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
        //E$("eForm").validator();
        //E$("sForm").validator();
        //E("sForm").setFirstFocus();
        
    }
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

	<div class="main_title">
	    <b>发起“王小明”的业务单</b>
	    <div class="main_tt_right fr">
	        <a href="#" class="orange">保存并发起业务</a><a href="#" class="orange">保存</a><a href="#" class="blue">返回</a>
	    </div>
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
	 
	<div class="order_launched">
	        <table width="100%" border="0">
	            <tr>
	            <th>业务单单号：</th>
	            <td><input type="text" /><span class="c_red"></span>业务单发起时间：<input type="text" disabled="disabled" value="2013-12-01 13:22" /><span class="c_red"></span>业务处理人：<input type="text"  /> <a href="" class="link_blue">选择</a></td>
	            </tr>
	            <th>业务单备注：</th>
	            <td><textarea name="" cols="55" rows="3"></textarea></td>
	            </tr>
	        </table>
	        
	    <div class="goods_bag clearfix">
	        <div>商品包1</div>
	        <table width="80%" border="0" class="fl">
	            <tr>
	            <th>商品包名称：</th>
	            <td><input type="text" value="车险A套餐(0192847)" /> <a href="javascript:showCL('test_condition')" class="link_blue">选择</a><span class="c_red"></span>
	            数量：<input type="text" style=" width:50px;" /> 份<span class="c_red">*</span></td>
	            </tr>
	            <tr>
	            <th>业务类型：</th>
	            <td><input type="text" disabled="disabled" value="车险,意外险" /></td>
	            </tr>
	            <tr>
	            <th>购买日期：</th>
	            <td><input type="text" class="ipt_date" /><span class="c_red">*</span>
	            起效日期：<input type="text" class="ipt_date" /><span class="c_red">*</span></td>
	            </tr>
	            <tr>
	            <th>备注：</th>
	            <td><textarea name="" cols="55" rows="3"></textarea></td>
	            </tr>
	        </table>
	    </div>
	    
	    <div class="goods_bag clearfix">
	        <div>商品包2</div>
	        <table width="80%" border="0" class="fl">
	            <tr>
	            <th>商品包名称：</th>
	            <td><input type="text" value="车险A套餐(0192847)" /> <a href="javascript:showCL('test_condition')" class="link_blue">选择</a><span class="c_red"></span>
	            数量：<input type="text" style=" width:50px;" /> 份<span class="c_red">*</span></td>
	            </tr>
	            <tr>
	            <th>业务类型：</th>
	            <td><input type="text" disabled="disabled" value="车险,意外险" /></td>
	            </tr>
	            <tr>
	            <th>购买日期：</th>
	            <td><input type="text" class="ipt_date" /><span class="c_red">*</span>
	            起效日期：<input type="text" class="ipt_date" /><span class="c_red">*</span></td>
	            </tr>
	            <tr>
	            <th>备注：</th>
	            <td><textarea name="" cols="55" rows="3"></textarea></td>
	            </tr>
	        </table>
	    </div>
	    <a href="#" class="link_blue">+新增商品包</a>
	</div>
	<a href="#" class="btn_orange mg_r">保存并发起业务</a><a href="#" class="btn_orange">保存</a><a href="#" class="btn_blue">返回</a>
	 
	 
	<div id="test_condition" style="display:none;">
	  <div class="pop_box">
	    <div class="pop_title"><a href="javascript:closeCL('test_condition')" class="fr">[关闭]</a>选择商品</div>
	        <div class="main_list pop_content">
	            <div class="pop_search">
	            <select name="">
	                  <option selected="selected">商品包名称</option>
	                  <option>商品包编号</option><option>销售价格</option><option>所属业务</option>
	                </select>
	                <input type="text" /><a href="#" class="btn_blue">搜索</a>
	            </div>
	            <table width="100%" border="0">
	              <tr>
	                <th><input name="" type="checkbox" value=""> 商品包编号</th>
	                <th>商品包名称</th>
	                <th>所属业务</th>
	                <th>售价</th>
	                <th>操作</th>
	              </tr>
	              <tr>
	                <td><input name="" type="checkbox" value=""> yw1001_1_p001</td>
	                <td>车险A餐</td>
	                <td>车险</td>
	                <td>￥1,999.00</td>
	                <td><a href="#">查看</a> | <a href="#">选择</a></td>
	              </tr>
	              <tr>
	                <td><input name="" type="checkbox" value=""> yw1001_1_p002</td>
	                <td>车险A餐</td>
	                <td>车险</td>
	                <td>￥1,999.00</td>
	                <td><a href="#">查看</a> | <a href="#">选择</a></td>
	              </tr>
	              <tr>
	                <td><input name="" type="checkbox" value=""> yw1001_1_p003</td>
	                <td>轮胎</td>
	                <td>配件</td>
	                <td>￥899.00</td>
	                <td><a href="#">查看</a> | <a href="#">选择</a></td>
	              </tr>
	            </table>
	            <div class="page_wrap clearfix">
	                <div class="paginator">
	                    <span class="page-start">＜上一页</span>
	                    <span class="page-this">1</span>
	                    <a href="#">2</a>
	                    <a href="#">3</a>
	                    <a href="#">4</a>
	                    <a href="#">5</a>
	                    <a href="#">6</a>
	                    <a href="#">7</a>
	                    <a href="#">8</a>
	                    <span>...</span>
	                    <a href="#">20</a>
	                    <a href="#" class="page-next">下一页＞</a>
	                </div>
	                第1/3页，共30条记录
	            </div>
	        </div>
	 
	  </div>
	  <div class="pop_bg_500"></div>
	</div>

</div>
