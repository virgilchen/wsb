<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/customer_list.action" ,
    create_url:root + "/biz/customer_create.action" ,
    update_url:root + "/biz/customer_update.action" ,
    get_url:root + "/biz/customer_get.action" ,
    delete_url:root + "/biz/customer_delete.action" ,
    entityName:"customer",
    size:0,
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("customerSO.pageIndex") ;
        
        //fillOptions({id:"customer.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"customerSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("customerTB", {height:"400px"}) ;
        
        E$("customer.cust_birthday").datepicker({});
        
        E$("eForm").validator();
        E$("sForm").validator();
        E("sForm").setFirstFocus();
        
    },
    add:function() {
        this.addRows ("carInfosTB", [{index:this.size}], {forceClear:false});

        //E$("purchase_date" + this.size).datepicker();
        
        this.size ++;
        this.refreshTableList();
        
    },
    get:function(id) {
    	if (id == -1) {
             viewJs.entity = this.eFormInitData;
    		 formDeserialize("eForm", this.eFormInitData, {}) ;// reset form;
    		 return ;
    	}

        var _this = this;
    	this.size = 0;

    	var idProperty = (this.idName==null?"id":this.idName) ;
    	var params = {} ;
    	params[idProperty] = id ;
        ajax(
            this.get_url, 
            params,
            function(data, textStatus){
                viewJs.entity = data;
                formDeserialize("eForm", data, {}) ;
        
                $(data.cars).each(function (i, elem) {
            		elem.index = i;
            		_this.size = i;
                    _this.addRows ("carInfosTB", elem, {forceClear:i==0?true:false});
            	});

            }
        );
    }
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <%--@include file="/WEB-INF/pages/frame/navigation.jsp" --%>
  
  <div id="listDiv">
  
    <DIV class=main_title>
      <B>客户列表</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=blue href="javascript:viewJs.toEdit();">业务发起</A>
        <A class=blue href="javascript:viewJs.toEdit();">发展成会员</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0">
        <input name="customerSO.pageIndex" id="customerSO.pageIndex" value="1" type="hidden" />
        <input name="customerSO.pageSize" id="customerSO.pageSize" value="10" type="hidden" />
        <table width="100%" >
          <tr>
           <td style="width:60px;" >姓名：</td>
           <td style="width:100px;">
             <input class=mg_r name="customerSO.cust_name" value="" type="text" />
           </td>
           <td style="width:100px;" >客户号：</td>
           <td style="width:100px;">
             <input class=mg_r name="customerSO.cust_code" value="" type="text" />
           </td>
           <td style="width:60px;" >电话：</td>
           <td style="width:100px;">
             <input class=mg_r name="customerSO.cust_phone_no" value="" type="text" />
           </td>
           <td style="width:100px;" >会员号：</td>
           <td style="width:100px;">
             <input class=mg_r name="customerSO.member_id" value="" type="text" />
           </td>
           <td style="width:100px;">
             <INPUT class="ipt_btn mg_r" value=搜索 type=button name=""  onclick="viewJs.first();">
           </td>
           <td style="width:400px;">
           </td>
          </tr>
        </table>
      </form>
    </DIV>
    
    
    <DIV class=main_list>
      <TABLE border=0 width="100%" id="customerTB" title="客户列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
			<TH width="80px">客户号</TH>
			<TH width="80px">姓名</TH>
			<TH width="40px">性别</TH>
			<TH width="80px">生日</TH>
			<TH width="100px">电话号码</TH>
			<TH width="100px">会员号</TH>
		  <TR>
        </thead>
        
        <tbody id="listBody" >
        </tbody>
         
         
        <tbody style="display:none;visibility: false;" disabled="true">
          <tr>
            <td>
              <textarea id="templateBody" jTemplate="yes">
                  <tr id="{$T.id}" ondblclick="viewJs.toEdit($('#ids', this)[0]);">
                    <td>
                      <input type="checkbox" name="ids" id="ids" value="{$T.id}" />
                    </td>
                    <td>{$T.cust_code}</td>
                    <td>{$T.cust_name}</td>
                    <td>{$T.cust_gender}</td>
                    <td>{fmt.maxlen($T.cust_birthday,10)}</td>
                    <td>{$T.cust_phone_no}</td>
                    <td>{$T.member_id}</td>
                  </tr>
              </textarea>
            </td>
          </tr>
        </tbody>

      </TABLE>
    </DIV>



    <%@include file="/WEB-INF/pages/frame/pagination.jsp" %>
    
  </div>
    
  <div id="editDiv" style="display:none;" >
    
    <DIV class=main_title>
      <B>客户资料录入</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
    </DIV>


    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="customer.id" id="customer.id"/>
	    <div class="info_entry" id="con1_1" style="display:block;">
		<p><b>基本资料</b></p>
		<table width="100%" border="0">
			<tr>
			<th>客户号：</th>
			<td><input name="customer.cust_code" type="text" maxlength="50"/></td>
			</tr>
			<tr>
			<th>姓名：</th>
			<td><input name="customer.cust_name" type="text" maxlength="20" /><span class="c_red">*</span> 
			 性别：
			<select name="customer.cust_gender">
			  <option selected="selected">男</option>
			  <option>女</option>
			</select></td>
			</tr>
			<tr>
			<th>出生年月日：</th>
			<td><input type="text" class="ipt_date" name="customer.cust_birthday" id="customer.cust_birthday" maxlength="10"/><span class="c_red"></span></td>
			</tr>
			<tr>
			<th>电话号码：</th>
			<td><input name="customer.cust_phone_no" type="text" maxlength="20" /><span class="c_red">*</span></td>
			</tr>
			<tr>
			<th>其他联系方式：</th>
			<td><input name="customer.other_contact_way" type="text" maxlength="100" class="long_ipt" /></td>
			</tr>
			<tr>
			<th>家庭地址：</th>
			<td><input name="customer.cust_home_address" type="text" maxlength="100" class="long_ipt" /></td>
			</tr>
		</table>
		
		<p><b>扩展资料</b></p>
		<table width="100%" border="0">
			<tr>
			<th>行业背景：</th>
			<td><textarea name="customer.background" style="width: 100%;height: 80px;" maxlength="1000"></textarea></td>
			</tr>
			<tr>
			<th>企业状况：</th>
			<td><textarea name="customer.company_info" style="width: 100%;height: 80px;" maxlength="1000"></textarea></td>
			</tr>
			<tr>
			<th>保险资源：</th>
			<td><input type="text" name="customer.insurance_resource" maxlength="1000" /></td>
			</tr>
			<tr>
			<th>联系人：</th>
			<td><input name="customer.contact_person" type="text" maxlength="20"/></td>
			</tr>
		</table>
		
		<p><b>客户资源</b></p>
		<table width="100%" border="0">
			<tr>
			<th>客户来源：</th>
			<td><select name="customer.cust_src">
			  <option selected="selected">公司老客户</option>
			  <option>外来</option><option>朋友</option>
			</select></td>
			</tr>
			<tr>
			<th>所属行业：</th>
			<td><input name="customer.cust_industry_type" type="text" maxlength="20" /></td>
			</tr>
			<tr>
			<th>职务：</th>
			<td><input type="text" name="customer.cust_job_title" maxlength="20"/></td>
			</tr>
			<tr>
			<th>公司名称：</th>
			<td><input name="customer.company_name" type="text" maxlength="100" /></td>
			</tr>
			<tr>
			<th>公司地址：</th>
			<td><input name="customer.company_address" type="text" maxlength="100" class="long_ipt" /></td>
			</tr>
			<tr>
			<th>关系网：</th>
			<td><textarea name="customer.relationship_network" style="width: 100%;height: 80px;" maxlength="1000"></textarea></td>
			</tr>
		</table>
		
		<p><b>车类资料</b></p>
		<table width="100%" border="0" id="carInfosTB">
	      <tbody id="listBody">
	      </tbody>
	      

	      <tbody style="display:none;visibility: false;" disabled="true">
	      	<tr>
	      		<td>
	      			<textarea id="templateBody" jTemplate="yes">
			      	
					<tr>
					<th>车牌号码：</th>
					<td><input name="cars[{$T.index}].car_no" type="text" value="{$T.car_no}" /><input type="hidden" name="cars[{$T.index}].id" value="{$T.id}"/><span class="c_red"></span>车牌地区：<input name="cars[{$T.index}].car_district" type="text" value="{$T.car_district}"/></td>
					</tr>
					<tr>
					<th>品牌：</th>
					<td><input name="cars[{$T.index}].car_band" type="text" value="{$T.car_band}" /><span class="c_red"></span>车型：<input name="cars[{$T.index}].car_type" type="text" value="{$T.car_type}" /></td>
					</tr>
					<tr>
					<th>颜色：</th>
					<td><input name="cars[{$T.index}].car_color" type="text" value="{$T.car_color}"/><span class="c_red"></span>排量：<input name="cars[{$T.index}].car_pai_liang" type="text" value="{$T.car_pai_liang}" /></td>
					</tr>
					<tr>
					<th>车架号码：</th>
					<td><input name="cars[{$T.index}].car_framework_no" type="text" value="{$T.car_framework_no}" /><span class="c_red"></span>发动机号码：<input name="cars[{$T.index}].car_engine_no" type="text" value="{$T.car_engine_no}" /></td>
					</tr>
					<tr>
					<th>初登日期：</th>
					<td><input name="cars[{$T.index}].car_init_register_date" type="text" value="{$T.car_init_register_date}"/><span class="c_red"></span>历程数：<input name="cars[{$T.index}].car_miles" type="text" value="{$T.car_miles}" /></td>
					</tr>
					<tr style="border-top:1px #248cb8 solid;">
						<td style="border-top:1px #248cb8 solid;" colspan="2"></td>
					</tr>
					</textarea>
	      		</td>
	      	</tr>
          </tbody>
        </table>
        
        <table>
          <tr>
            <td>
              <a href="javascript:viewJs.add();" class="link_blue">+新增车类信息</a>
            </td>
          </tr>
        </table>
		<br />
    </form>
     <!-- 
    <table cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td colspan="2" align="center" class="tx_center">
            <button onclick="viewJs.save()" tabindex="-1">保存(S)</button>
          </td>
        </tr>
    </table>
    -->
    
    
  </div>
</div>
