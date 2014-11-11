<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/member_list.action" ,
    create_url:root + "/biz/member_create.action" ,
    update_url:root + "/biz/member_update.action" ,
    get_url:root + "/biz/member_get.action" ,
    delete_url:root + "/biz/member_delete.action" ,
    entityName:"member",
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("memberSO.pageIndex") ;
        
        //fillOptions({id:"member.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"memberSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("memberTB", {height:"400px"}) ;
        
        E$("member_expiry_date").datepicker({});
        
        E$("eForm").validator();
        E$("sForm").validator();
        E("sForm").setFirstFocus();
        
        var _id= <%=request.getParameter("member.id")%>;
        viewJs.toEdit({value:_id});
        
    },
    //onSaveOk:function(data) {
    //	removeView(<%=view_id%>);
    //	viewJs.list();
    //},
    get:function(id) {
    	if (id == -1) {
             viewJs.entity = this.eFormInitData;
    		 formDeserialize("eForm", this.eFormInitData, {}) ;// reset form;
    		 return ;
    	}
    }
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <%--@include file="/WEB-INF/pages/frame/navigation.jsp" --%>
  
  <div id="listDiv">
  
    <DIV class=main_title>
      <B>会员列表</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0 0 5px 0;">
        <input name="memberSO.pageIndex" id="memberSO.pageIndex" value="1" type="hidden" />
        <input name="memberSO.pageSize" id="memberSO.pageSize" value="10" type="hidden" />
        <table width="100%" >
          <tr>
           <td style="width:60px;" >会员号：</td>
           <td style="width:100px;">
             <input class=mg_r name="memberSO.member_login_id" value="" type="text" />
           </td>
           <td style="width:100px;">
             <INPUT class="ipt_btn mg_r" value=搜索 type=button name=""  onclick="viewJs.first();">
           </td>
           <td style="width:1000px;">
           </td>
          </tr>
        </table>
      </form>
    </DIV>
    
    
    <DIV class=main_list>
      <TABLE border=0 width="100%" id="memberTB" title="会员列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
			<TH width="80px">会员号</TH>
			<TH width="80px">状态</TH>
			<TH width="40px">类别</TH>
			<TH width="100px">有效日期</TH>
			<TH width="80px">客户ID</TH>
			<TH width="80px">车辆ID</TH>
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
                    <td>{$T.member_login_id}</td>
                    <td>{$T.member_status}</td>
                    <td>{$T.member_type}</td>
                    <td>{fmt.maxlen($T.member_expiry_date,10)}</td>
                    <td>{$T.psdo_cust_id}</td>
                    <td>{$T.car_id}</td>
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
      <B>会员视图</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
    </DIV>


    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="member.id" id="member.id"/>
      <input type="hidden" name="member.psdo_cust_id" id="member.psdo_cust_id" value="<%=request.getParameter("customerId")%>"/>
      <input type="hidden" name="member.car_id" id="member.car_id" value="<%=request.getParameter("carId")%>"/>
	  <div class="main_infomation">
		<table width="100%" border="0">
			<tr>
			<td>车牌号：粤A-88888</td>
			<td>品牌：劳斯莱斯</td>
			<td>车型：幻影（加长版）</td>
			<td>颜色：黑色</td>
			</tr>
		</table>
	  </div>
	  <div class="user_accout">
		<p><label>会员号：</label><input type="text" name="member.member_login_id" id="member_login_id" maxlength="50"/><span class="c_red">*</span></p>
		<p><label>登陆密码：</label><input type="password" name="member.member_login_pwd" id="member_login_pwd" maxlength="50"/><span class="c_red">*</span></p>
		<p><label>有效期限：</label><input type="text" name="member.member_expiry_date" id="member_expiry_date" maxlength="10"/><span class="c_red">*</span></p>
	  </div>
      <div class="membership">
		<h2>香车会入会表</h2>
		<p><b>生活习惯</b></p>
		<h3>1.您的个人兴趣及爱好？</h3>
		<table width="80%" border="0">
			<tr>
			<td><input name="" type="checkbox" value="" /><label>自驾游</label></td>
			<td><input name="" type="checkbox" value="" /><label>酒会</label></td>
			<td><input name="" type="checkbox" value="" /><label>美食</label></td>
			<td><input name="" type="checkbox" value="" /><label>奢侈品</label></td>
			</tr>
			<tr>
			<td><input name="" type="checkbox" value="" /><label>足球</label></td>
			<td><input name="" type="checkbox" value="" /><label>赛车</label></td>
			<td><input name="" type="checkbox" value="" /><label>高尔夫</label></td>
			<td><input name="" type="checkbox" value="" /><label>投资移民</label></td>
			</tr>
			<tr>
			<td><input name="" type="checkbox" value="" /><label>商业交流</label></td>
			<td><input name="" type="checkbox" value="" /><label>旅游度假</label></td>
			<td><input name="" type="checkbox" value="" /><label>金融理财</label></td>
			<td><input name="" type="checkbox" value="" /><label>其它</label></td>
			</tr>
		</table>
		<h3>2.您最喜欢的私人用车是什么品牌？</h3>
		<table width="80%" border="0">
			<tr>
			<td><input name="" type="radio" value="" /><label>奔驰</label></td>
			<td><input name="" type="radio" value="" /><label>宝马</label></td>
			<td><input name="" type="radio" value="" /><label>奥迪</label></td>
			<td><input name="" type="radio" value="" /><label>路虎</label></td>
			</tr>
			<tr>
			<td><input name="" type="radio" value="" /><label>保时捷</label></td>
			<td><input name="" type="radio" value="" /><label>法拉利</label></td>
			<td><input name="" type="radio" value="" /><label>宾利</label></td>
			<td><input name="" type="radio" value="" /><label>其它</label></td>
			</tr>
		</table>
		<h3>3.您每年是否有度假计划？</h3>
		<table width="80%" border="0">
			<tr>
			<td><input name="" type="radio" value="" /><label>是的，我有明确的度假计划</label></td>
			<td><input name="" type="radio" value="" /><label>不一定，视具体情况而定</label></td>
			<td><input name="" type="radio" value="" /><label>没有度假计划</label></td>
			</tr>
		</table>
		<h3>4.您最喜欢的度假目的地是？</h3>
		<table width="80%" border="0">
			<tr>
			<td><input name="" type="checkbox" value="" /><label>海滨</label></td>
			<td><input name="" type="checkbox" value="" /><label>酒会</label></td>
			<td><input name="" type="checkbox" value="" /><label>美食</label></td>
			<td><input name="" type="checkbox" value="" /><label>奢侈品</label></td>
			</tr>
			<tr>
			<td><input name="" type="checkbox" value="" /><label>足球</label></td>
			<td><input name="" type="checkbox" value="" /><label>赛车</label></td>
			<td><input name="" type="checkbox" value="" /><label>高尔夫</label></td>
			<td><input name="" type="checkbox" value="" /><label>投资移民</label></td>
			</tr>
		</table>
		<h3>5.您是否为您或企业配备专业顾问？</h3>
		<table width="80%" border="0">
			<tr>
			<td><input name="" type="checkbox" value="" /><label>无</label></td>
			<td><input name="" type="checkbox" value="" /><label>法律顾问</label></td>
			<td><input name="" type="checkbox" value="" /><label>财税顾问</label></td>
			<td><input name="" type="checkbox" value="" /><label>投资顾问</label></td>
			</tr>
		</table>
		<p><b>香车会答疑</b></p>
		<h3>1.您希望以何种方式获得香车会发布的信息？</h3>
		<table width="80%" border="0">
			<tr>
			<td><input name="" type="checkbox" value="" /><label>电话通知</label></td>
			<td><input name="" type="checkbox" value="" /><label>手机短信</label></td>
			<td><input name="" type="checkbox" value="" /><label>微信/QQ</label></td>
			<td><input name="" type="checkbox" value="" /><label>其它</label></td>
			</tr>
		</table>
		<h3>2.您认为香车会活动多久组织一次合适？</h3>
		<table width="80%" border="0">
			<tr>
			<td><input name="" type="radio" value="" /><label>一个季度</label></td>
			<td><input name="" type="radio" value="" /><label>一个月</label></td>
			<td><input name="" type="radio" value="" /><label>半个月</label></td>
			<td><input name="" type="radio" value="" /><label>其它</label></td>
			</tr>
		</table>
		<h3>3.您愿意参与香车会的哪些核心环节？</h3>
		<table width="80%" border="0">
			<tr>
			<td><input name="" type="checkbox" value="" /><label>活动组织策划</label></td>
			<td><input name="" type="checkbox" value="" /><label>活动的执行</label></td>
			<td><input name="" type="checkbox" value="" /><label>活动参与</label></td>
			<td><input name="" type="checkbox" value="" /><label>其它</label></td>
			</tr>
		</table>
		<h3>敬请留下您对香车会的期望：</h3>
		<table width="80%" border="0">
			<tr>
			<td><textarea name="" cols="60" rows="7"></textarea></td>
			</tr>
		</table>
		
	  </div>
    </form>
    
  </div>
</div>
