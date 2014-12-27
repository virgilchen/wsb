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
    isOpenOrderAfterSave:false,
    
    init:function (){

        this.pageIndex = E("customerSO.pageIndex") ;
        
        //fillOptions({id:"customer.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"customerSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        this.initDataGrid("customerTB") ;
        
        E$("customer.cust_birthday").datepicker({defaultDate:"-30y"});
        
        E$("eForm").validator();
        E$("sForm").validator();
        E("sForm").setFirstFocus();
        //var _id= <%=request.getParameter("customer.id")%>;
        //viewJs.toEdit({value:_id});
    },
    add:function() {
        this.addRows ("carInfosTB", [{index:this.size}], {forceClear:false});

        E$("car_init_register_date" + this.size).datepicker();
        
        this.size ++;
        this.refreshTableList();
        
    },
    get:function(id) {
		if (id == -1) {
			viewJs.entity = this.eFormInitData;
			$("#user_suggest").hide();
			$("#user_detail_title").hide();
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
                    E$("car_init_register_date" + _this.size).datepicker();
            	});
                this.size = _this.size++;
                if(data.cars.length>0){
                	if(data.member_id == null || data.member_id == '' ){
    					$("#memberDisplay").html("<a href='javascript:viewJs.toMember();'>发展为会员</a>");
    				}else{
    					$("#memberDisplay2").html("[会员]");
    					$("#memberDisplay").html("<a href='javascript:viewJs.toMember();'>会员ID："+data.member_id+"</a>");
    				}
                }
            }
        );
    },
    
    //toView360:function (selectInput) {
    //	this.selected = null ;
    //	
    //	if (selectInput) {
    //		this.selected = selectInput ;
    //	} else {
	//    	var sels = $("#"+this.entityName+"TB input:checked", this.view) ;
	//    	if (sels.length == 0) {
	//    		alert("请先选择要编辑的记录！") ;
	//    		return ;
	//    	}
	//    	this.selected = sels[0] ;
    //	}
    //	openView(11002, '/biz/customer_view360.action?customer.id='+this.selected.value, '360视图');
    //},
    
    toOrderOpen:function () {

        var sels = $("#"+this.entityName+"TB input:checked", this.view) ;
        if (sels.length == 0) {
            alert("请先选择要发起业务的客户！") ;
            return ;
        }

        openView(100003, '/biz/order_openView.action?customer_id=' + sels[0].value, '业务发起');
    },
    
    toPurchaseView:function () {

        var sels = $("#"+this.entityName+"TB input:checked", this.view) ;
        if (sels.length == 0) {
            alert("请先选择要发起业务的客户！") ;
            return ;
        }

        openView(100006, '/biz/order_purchaseView.action?customer_id=' + sels[0].value, '订购产品');
    },
    
    onSaveOk:function(data) {

        viewJs.toPage('s') ;
        viewJs.list() ;

    	if (this.isOpenOrderAfterSave) {    		
            openView(100003, '/biz/order_openView.action?parent_view_id=<%=view_id%>&customer_id=' + data.id, '业务发起');
            this.isOpenOrderAfterSave = false ;
    	}

    },
    
    getDistrict:function(rowIndex){
    	var info_car_no = document.getElementById("car_no"+rowIndex);
    	var info_car_district = document.getElementById("car_district"+rowIndex);
    	carNo = info_car_no.value;
    	var district_value = '';
    	if(carNo.substring(0,2) == 'WJ'){
    		if(dVal("car_num_match_district", "name_", {PK_ID:carNo.substring(0,5)}) != ''){
    			district_value = dVal("car_num_match_district", "name_", {PK_ID:carNo.substring(0,5)});
    		}else if(dVal("car_num_match_district", "name_", {PK_ID:carNo.substring(0,2)+'****'+carNo.substring(carNo.length-1)}) != ''){
    			district_value = dVal("car_num_match_district", "name_", {PK_ID:carNo.substring(0,2)+'****'+carNo.substring(carNo.length-1)});
    		}else if(dVal("car_num_match_district", "name_", {PK_ID:carNo.substring(0,3)+'****'+carNo.substring(carNo.length-1)}) != ''){
    			district_value = dVal("car_num_match_district", "name_", {PK_ID:carNo.substring(0,3)+'****'+carNo.substring(carNo.length-1)});
    		}else{
    			district_value = '';
    		}
    	}else{
    		if(dVal("car_num_match_district", "name_", {PK_ID:carNo.substring(0,2)}) != null){
    			district_value = dVal("car_num_match_district", "name_", {PK_ID:carNo.substring(0,2)});
    		}else{
    			district_value = '';
    		}
    	}
    	info_car_district.value = district_value;
    },
    toMember:function(){
    	var custId = document.getElementById("customer.id").value;
    	if(custId == ''){
    		alert("请先完整填写客户资料！");
    		return;
    	}else{
    		
    		var str = '/biz/member_view.action?customerId='+custId;
    		openView(11005, str, '会员列表');
    		return;
    	}
    	
    }
}) ;

</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <%--@include file="/WEB-INF/pages/frame/navigation.jsp" --%>
  
  <div id="listDiv">
    <div>
    <DIV class=main_title>
      <B>客户列表</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=blue href="javascript:viewJs.toOrderOpen();">业务发起</A>
        <A class=blue href="javascript:viewJs.toPurchaseView();">订购产品</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
    </div>
    
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0 0 5px 0;">
        <input name="customerSO.pageIndex" id="customerSO.pageIndex" value="1" type="hidden" />
        <input name="customerSO.pageSize" id="customerSO.pageSize" value="10" type="hidden" />
        <table width="100%" >
          <tr>
           <td style="width:80px;" >姓名：</td>
           <td style="width:100px;">
             <input class=mg_r name="customerSO.cust_name" value="" type="text" />
           </td>
           <td style="width:100px;" >客户号：</td>
           <td style="width:100px;">
             <input class=mg_r name="customerSO.cust_code" value="" type="text" />
           </td>
           <td style="width:80px;" >电话：</td>
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
           <td style="width:100px;">
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
                    <td>{fmt.maxlen($T.member_id, 100)}</td>
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
    <div class="main_title">
		<b>360视图</b>
		<div class="main_tt_right fr">
		 	<a href="#" class="blue">导出客户信息</a>
		 	<a href="javascript:viewJs.isOpenOrderAfterSave=true;viewJs.save();" class="orange">保存并发起业务</a>
		 	<A class=orange href="javascript:viewJs.save();">保存</A>
		 	<A class=blue href="javascript:viewJs.toSearch();">取消</A>
		</div>
	</div>
	
	
	<div class="user_suggest" id="user_suggest">
		<div class="content">
		<p><b>跟据客户的信息和过往的业务记录。建议：</b></p>
		<p>1、决策1</p><p>2、决策2</p><p>3、决策3</p><p>4、决策5</p>
		</div>
	</div>
	<div class="user_detail_title" id="user_detail_title">
	<b>客户详细</b>
	 	<ul class="title_right">
            <li class="selected" onclick="tabShow('menu1_','con1_',1,2);" id="menu1_1">客户信息</li>
            <li onclick="viewJs.orderHistoryView.getMyOrders();tabShow('menu1_','con1_',2,2);" id="menu1_2">业务历史记录</li>
		</ul>
	</div>

	<div class="user_detail_info" id="con1_1" style="display:block;">
      <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0;padding:0;" class="main_form">
			<ul class="user_info">
		        <li class="selected" onclick="tabShow('menu2_','con2_',1,4);" id="menu2_1">基本资料<span class="c_red" id='memberDisplay2'></span></li>
		        <li onclick="tabShow('menu2_','con2_',2,4);" id="menu2_2">扩展资料</li>
		        <li onclick="tabShow('menu2_','con2_',3,4);" id="menu2_3">客户资源</li>
		        <li onclick="tabShow('menu2_','con2_',4,4);" id="menu2_4">车类资料</li>
			</ul>
		    <div id="con2_1" style="display:block;">
				<table width="100%" border="0">
					<tr>
					<th>客户号：</th>
					<td><input name="customer.cust_code" type="text" maxlength="50"/>
						<input type="hidden" name="customer.id" id="customer.id"/>
						<input type="hidden" name="customer.member_id" id="customer.member_id"/>
						<input type="hidden" name="customer.member_idc" id="customer.member_idc"/>
						<span class="c_red" id="memberDisplay"></span>
					</td>
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
		    </div>
		    <div id="con2_2" style="display:none;">
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
		    </div>
		    <div id="con2_3" style="display:none;">
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
		    </div>
		    <div id="con2_4" style="display:none;">
				<table width="100%" border="0" id="carInfosTB">
			      <tbody id="listBody">
			      </tbody>
			      
		
			      <tbody style="display:none;visibility: false;" disabled="true">
			      	<tr>
			      		<td>
			      	      <textarea id="templateBody" jTemplate="yes">
					      	
							<tr>
							  <th>车牌号码：</th>
							  <td>
                                <input name="cars[{$T.index}].car_no" id="car_no{$T.index}" type="text" value="{$T.car_no}" onblur="javascript:viewJs.getDistrict({$T.index});" />
                                <input name="cars[{$T.index}].id" id="id{$T.index}" type="hidden" value="{$T.id}"/>
							    <span class="c_red"></span>
							  </td>
							  <th>车牌地区：</th>
							  <td>
							    <input name="cars[{$T.index}].car_district" id="car_district{$T.index}" type="text" value="{$T.car_district}"/>
							  </td>
							</tr>
							<tr>
							  <th>品牌：</th>
							  <td>
							    <input name="cars[{$T.index}].car_band" type="text" value="{$T.car_band}" />
							    <span class="c_red"></span>
                              </td>
                              <th>车型：</th>
                              <td>
                                <input name="cars[{$T.index}].car_type" type="text" value="{$T.car_type}" />
							  </td>
							</tr>
							<tr>
							  <th>颜色：</th>
							  <td>
							    <input name="cars[{$T.index}].car_color" type="text" value="{$T.car_color}"/>
							    <span class="c_red"></span>
                              </td>
                              <th>排量：</th>
                              <td>
                                <input name="cars[{$T.index}].car_pai_liang" type="text" value="{$T.car_pai_liang}" />
							  </td>
							</tr>
							<tr>
							  <th>车架号码：</th>
							  <td>
							    <input name="cars[{$T.index}].car_framework_no" type="text" value="{$T.car_framework_no}" /><span class="c_red"></span>
                              </td>
                              <th>发动机号码：</th>
                              <td>
                                <input name="cars[{$T.index}].car_engine_no" type="text" value="{$T.car_engine_no}" />
                              </td>
							</tr>
							<tr>
							  <th>初登日期：</th>
							  <td>
							    <input name="cars[{$T.index}].car_init_register_date" id="car_init_register_date{$T.index}" type="text" value="{fmt.maxlen($T.car_init_register_date,10)}"/>
							    <span class="c_red"></span>
                              </td>
                              <th>历程数：</th>
                              <td>
                                <input name="cars[{$T.index}].car_miles" type="text" value="{$T.car_miles}" />
							  </td>
							</tr>
							<tr style="border-top:1px #248cb8 solid;">
								<td style="border-top:1px #248cb8 solid;" colspan="4"></td>
							</tr>
						  </textarea>
			      		</td>
			      	</tr>
		          </tbody>
		        </table>
				<div class="add_car"><a href="javascript:viewJs.add();" class="link_blue">+新增车类信息</a></div>
			</div>
      </form>
	</div>
	
    <div class="user_detail_history" id="con1_2" style="display:none;">
      <%@include file="/WEB-INF/pages/biz/customer/orderHistoryView.jsp" %>
    </div>
    
    
  </div>
</div>
