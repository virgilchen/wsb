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
        
        var customerId = <%=request.getParameter("customerId")%>;
    	this.getMember(customerId);
        
    },
    onSaveOk:function(data) {
    	removeView(<%=view_id%>);
    	viewJs.list();
    },
    toCust:function(data){
    	removeView(<%=view_id%>);
    	viewJs.list();
    },
    getMember:function(customerId) {
		ajax(
				root + "/biz/member_toMember.action", 
                {"customerId":customerId},
                function(data, textStatus){
                    viewJs.entity = data;
                    formDeserializeText("carInfoDetailDiv", "label", data.car, {}) ;
                    formDeserialize("eForm", data, {}) ;
                    viewJs.onGet(data);
                }
            );
    },
    checkEditForm:function() {
    	if(V("member_login_pwd") == ""){
    		if(V("member_login_pwd1") == "" ){
    			alert("请输入密码！") ;
                E("member_login_pwd1").focus();
        		return false ;
    		}else if(V("member_login_pwd2") != V("member_login_pwd1")){
    			alert("两次密码不一致，请重新输入！");
                V("member_login_pwd2", "");
                V("member_login_pwd1", "");
                E("member_login_pwd1").focus();
        		return false ;
    		}
    		
    		if(V("member_login_pwd2") == V("member_login_pwd1")){
    			var pwd_val = document.getElementById("member_login_pwd1").value;
    			V("member_login_pwd", pwd_val);
    			//alert(V("member_login_pwd"));
    		}
    	}else{
    		if(V("member_login_pwd1") != "" || V("member_login_pwd2") != ""){
    			if(V("member_login_pwd2") == V("member_login_pwd1")) {
    				var pwd_val = document.getElementById("member_login_pwd1").value;
        			V("member_login_pwd", pwd_val);
        			//alert(V("member_login_pwd"));
    	    	}else{
    	    		alert("两次密码不一致，请重新输入！如不需要改密码，请留空！");
    	            V("member_login_pwd2", "");
    	            V("member_login_pwd1", "");
    	            E("member_login_pwd1").focus();
    	    		return false ;
    	    	}
    		}
    	}
    	return true ;
    },	
    
    onGet:function(data) {
    	if (data.id == null || data.id == '') {
    		E$("member_login_id").removeAttr("readonly");
    	} else {
            E$("member_login_id").attr("readonly", "readonly") ;
    	}
    	$(data.questions).each(function (i, elem) {
    		if(document.getElementById(elem.appl_form_type)==null ||document.getElementById(elem.appl_form_type)==undefined){
    			$("#membership").append("<p><b>"+elem.appl_form_type+"</b></p>");
    			$("#membership").append("<div id='"+elem.appl_form_type+"'>");
    			$("#membership").append("</div>");
    			
    		}
    		$("#"+elem.appl_form_type).append("<h3>"+elem.appl_form_question_name+"</h3>");
    		$("#"+elem.appl_form_type).append("<table width='80%' border='0' id='"+elem.appl_form_question_name+"'>");
    		var str='';
    		var m=0;
    		var l=elem.answers.length;
    		$(elem.answers).each(function (j, answerElem) {
    			if(m==0){
    				str += "<tr>";
    			}
    			var checkStr = '';
   				if(answerElem.checkType != null && answerElem.checkType != 'null'){
   					
   					checkStr = answerElem.checkType;
   				}
    			if(elem.appl_form_answer_type == '1'){
    				str += "<td><input name='questions["+i+"].ansIds' type='radio' value='"+answerElem.id+"' "+checkStr+" /><label>"+answerElem.appl_form_answer_name+"</label></td>";
    			}else{
    				str += "<td><input name='questions["+i+"].ansIds' type='checkbox' value='"+answerElem.id+"' "+checkStr+" /><label>"+answerElem.appl_form_answer_name+"</label></td>";
    			}
    			
    			m++;
    			l--;
    			if(l==0 || m==4){
    				str += "</tr>";
    				m=0;
    			}
        	});
    		$("#"+elem.appl_form_question_name).append(str);
    		$("#"+elem.appl_form_type).append("</table>");
    	});
    	$("#membership").append("<h3>敬请留下您对香车会的期望：</h3><table width='80%' border='0'><tr><td><textarea name='member.member_expectation' cols='60' rows='7'></textarea></td></tr></table>");
    }
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <%--@include file="/WEB-INF/pages/frame/navigation.jsp" --%>
  
  <div id="listDiv" style="display:none;">
  
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
    
  <div id="editDiv" >
    
    <DIV class=main_title>
      <B>会员视图</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toCust();">取消</A>
      </DIV>
    </DIV>

    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="member.id" id="member.id"/>
      <input type="hidden" name="member.psdo_cust_id" id="member.psdo_cust_id"/>
      <input type="hidden" name="member.car_id" id="member.car_id"/>
	  <div class="main_infomation" id="carInfoDetailDiv">
		<table width="100%" border="0">
			<tr>
			<td>车牌号：<label id="car.car_no">粤A-88888</label></td>
			<td>品牌：<label id="car.car_band">劳斯莱斯</label></td>
			<td>车型：<label id="car.car_type">幻影（加长版）</label></td>
			<td>颜色：<label id="car.car_color">黑色</label></td>
			</tr>
		</table>
	  </div>
	  <div class="user_accout">
		<p><label>会员号：</label><input type="text" name="member.member_login_id" id="member_login_id" maxlength="50"/><input type="hidden" name="member.member_login_pwd" id="member_login_pwd" maxlength="50"/><span class="c_red">*</span></p>
		<p><label>登录密码：</label><input type="password" id="member_login_pwd1" maxlength="50"/><span class="c_red">*</span></p>
		<p><label>确认密码：</label><input type="password" id="member_login_pwd2" maxlength="50"/><span class="c_red">*</span></p>
		<p><label>有效期限：</label><input type="text" name="member.member_expiry_date" id="member_expiry_date" maxlength="10"/><span class="c_red">*</span></p>
	  </div>
      <div class="membership" id="membership">
		<h2>香车会入会表</h2>
		
	  </div>
    </form>
    
  </div>
</div>
