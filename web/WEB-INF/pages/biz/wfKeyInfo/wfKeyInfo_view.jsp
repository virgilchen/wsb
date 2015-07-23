<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>
var infoType;
var rowId = 0;
var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/wfKeyInfo_list.action" ,
    create_url:root + "/biz/wfKeyInfo_create.action" ,
    update_url:root + "/biz/wfKeyInfo_update.action" ,
    get_url:root + "/biz/wfKeyInfo_get.action" ,
    delete_url:root + "/biz/wfKeyInfo_delete.action" ,
    entityName:"wfKeyInfo",
    
    init:function (){
        var _this = this ;
        this.pageIndex = E("wfKeyInfoSO.pageIndex") ;
        
        this.initDataGrid("wfKeyInfoTB") ;
        
        
        E$("eForm").validator();
        E$("sForm").validator();
        E("sForm").setFirstFocus();
        
        this.first();
    },
    
    get:function(id) {
        if (id == -1) {
             viewJs.entity = this.eFormInitData;
             formDeserialize("eForm", this.eFormInitData, {}) ;// reset form;
             $("#infoDetails").html("");
             rowId = 0;
             return ;
        }
        
        var _this = this;
        var idProperty = (this.idName==null?"id":this.idName) ;
        var params = {} ;
        params[idProperty] = id ;
        ajax(
            this.get_url, 
            params,
            function(data){
                viewJs.entity = data;
                formDeserialize("eForm", data, {}) ;
                $("#infoDetails").html("");
                
                if(data.wfKeyInfoDetailsList.length > 0){
                	rowId = 0;
                	if(data.wf_key_info_type == 0){
                    	$(data.wfKeyInfoDetailsList).each(function (i, elem) {
                    		$("#infoDetails").append("<tr id='row_"+rowId+"'><td>选项名称"+
                        			"<input name='details["+rowId+"].detail_name' value='"+elem.detail_name+"'/></td>"+
                        			"<td>"+
                        			"<a href='javascript:viewJs.addDetails("+data.wf_key_info_type+");'>添加    </a>"+
                        			"<a href='javascript:viewJs.delDetails("+rowId+");'>删除    </a>"+
                        			"<a href='javascript:viewJs.test1("+rowId+");'>上移    </a>"+
                        			"<a href='#' >下移    </a>"+
                        			"</td></tr>");
                    		rowId++;
                        });
                    }else if(data.wf_key_info_type == 1){
    					$(data.wfKeyInfoDetailsList).each(function (i, elem) {
    						$("#infoDetails").append("<tr id='row_"+rowId+"'><td>选项内容"+
    				    			"<input name='details["+rowId+"].detail_name' value='"+elem.detail_name+"'/></td>"+
    				    			"    <td><input id='can_input"+rowId+"'"+
    				    			" type='checkbox' onclick='javascript:viewJs.getCheck("+rowId+");'/></td>    "+
    				    			"<input type='hidden' name='details["+rowId+"].can_input' id='details["+rowId+"].can_input' value='"+elem.can_input+"'/>"+
    				    			"    <td>"+
    				    			"<a href='javascript:viewJs.addDetails("+data.wf_key_info_type+");'>添加    </a>"+
    				    			"<a href='javascript:viewJs.delDetails("+rowId+");'>删除    </a>"+
    				    			"</td></tr>");
    						if(elem.can_input == '0'){
    							$("#can_input"+rowId).attr("checked","checked");
    						}
    						
    						rowId++;
                        });
                    }else if(data.wf_key_info_type == 2){
                    	$("#infoDetails").append("<tr id='row_"+rowId+"'><td>最小字数："+
                    			"<input name='details["+rowId+"].min_length' maxlength='6' value='"+data.wfKeyInfoDetailsList[0].min_length+"'/></td>"+
                    			"<td>    最大字数："+
                    			"<input name='details["+rowId+"].max_length' maxlength='6' value='"+data.wfKeyInfoDetailsList[0].max_length+"'/></td>"+
                    			"</tr>");
                    }
                }
            }
        );
    },
    
    displayConditions:function(selectType){
    	if(selectType.value != infoType){
    		infoType = selectType.value;
    		$("#infoDetails").html("");
    		rowId = 0;
    		this.addDetails(infoType);
    	}
    },
    
    addDetails:function(infoType){
    	if(infoType == '0'){
    		$("#infoDetails").append("<tr id='row_"+rowId+"'><td>选项名称"+
        			"<input name='details["+rowId+"].detail_name'/></td>"+
        			"<td>"+
        			"<a href='javascript:viewJs.addDetails("+infoType+");'>添加    </a>"+
        			"<a href='javascript:viewJs.delDetails("+rowId+");'>删除    </a>"+
        			"<a href='javascript:viewJs.test1("+rowId+");'>上移    </a>"+
        			"<a href='#' >下移    </a>"+
        			"</td></tr>");
		}else if(infoType == '1'){
			$("#infoDetails").append("<tr id='row_"+rowId+"'><td>选项内容"+
	    			"<input name='details["+rowId+"].detail_name'/></td>"+
	    			"    <td><input id='can_input"+rowId+"'"+
	    			" type='checkbox' onclick='javascript:viewJs.getCheck("+rowId+");' checked='checked'/></td>    "+
	    			"<input type='hidden' name='details["+rowId+"].can_input' id='details["+rowId+"].can_input' value='0'/>"+
	    			"    <td>"+
	    			"<a href='javascript:viewJs.addDetails("+infoType+");'>添加    </a>"+
	    			"<a href='javascript:viewJs.delDetails("+rowId+");'>删除    </a>"+
	    			"</td></tr>");
		}else if(infoType == '2'){
			$("#infoDetails").append("<tr id='row_"+rowId+"'><td>最小字数："+
        			"<input name='details["+rowId+"].min_length' maxlength='6'/></td>"+
        			"<td>    最大字数："+
        			"<input name='details["+rowId+"].max_length' maxlength='6'/></td>"+
        			"</tr>");
		}
    	
    	rowId++;
    },
    delDetails:function(objId){
    	var rowObj = document.getElementById("row_"+objId);
    	var tableObj=document.getElementById("infoDetails");
    	tableObj.deleteRow(rowObj.rowIndex);
    },
    getCheck:function(objId){
    	var rowObj = document.getElementById("can_input"+objId);
    	if(rowObj.checked){
    		document.getElementById("details["+objId+"].can_input").value='0';
    	}else{
    		document.getElementById("details["+objId+"].can_input").value='1';
    	}
    },
    test1:function(id){
    	var row = document.getElementById("row_"+id).rowIndex;
    	if(row==0){
    		alert(' 当前最大值，无需上移');
    	}else{
    		//上一行的名称列值
    		var htm1=document.getElementById("infoDetails").rows[row-1].innerHTML;
    		alert(htm1);
    		// 当前行的名称列值
    		var htm2=document.getElementById("infoDetails").rows[row].innerHTML;
    		document.getElementById("infoDetails").rows[row-1].innerHTML=htm2;
    		document.getElementById("infoDetails").rows[row].innerHTML=htm1;
    	}
    },
    test2:function(id){
    	//或得到当前行的行号
    	var row = document.getElementById(id).parentElement.parentElement.rowIndex;
    	//或 得到table的总行数
    	var count=document.getElementById("tab").rows.length;
    	//if 当前行的行号等于table的总行数则不进行下移
    	if(row==(count-1)){
    		alert('当前最小值，无需下移');
    	}else{
    		// 下一行的名称列值
    		var htm1=document.getElementById("tab").rows[row+1].cells[1].innerHTML;
    		// 当前行的名称列值
    		var htm2=document.getElementById(id).parentElement.parentElement.cells[1].innerHTML;
    		// 当前行的名称列的值为下一行的值
    		document.getElementById("tab").rows[row+1].cells[1].innerHTML=htm2;
    		// 下一行的值被赋值为当前行的值
    		document.getElementById(id).parentElement.parentElement.cells[1].innerHTML=htm1;
    	}
    }
    
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">
  
  <div id="listDiv">
  
    <DIV class=main_title>
      <B>流程关键信息列表</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0 0 5px 0;">
        <input name="wfKeyInfoSO.pageIndex" id="wfKeyInfoSO.pageIndex" value="1" type="hidden" />
        <input name="wfKeyInfoSO.pageSize" id="wfKeyInfoSO.pageSize" value="10" type="hidden" />
        <table width="100%" >
          <tr>
           <td style="width:100px;" >信息名称：</td>
           <td style="width:100px;">
             <input class=mg_r name="wfKeyInfoSO.wf_key_info_name" value="" type="text" />
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
      <TABLE border=0 width="100%" id="wfKeyInfoTB" title="流程关键信息列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
            <TH>信息编号</TH>
            <TH>信息名称</TH>
            <TH>信息类型</TH>
            <TH>是否必填</TH>
            <TH>是否有效</TH>
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
                    <td>{$T.id}</td>
                    <td>{$T.wf_key_info_name}</td>
                    <td>{#if $T.wf_key_info_type == '0'}单选{#/if}{#if $T.wf_key_info_type == '1'}多选{#/if}{#if $T.wf_key_info_type == '2'}填空{#/if}</td>
                    <td>{#if $T.is_required == '0'}是{#/if}{#if $T.is_required == '1'}否{#/if}</td>
                    <td>{#if $T.is_active == '0'}是{#/if}{#if $T.is_active == '1'}否{#/if}</td>
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
      <B>流程关键信息编辑</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
    </DIV>


    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="wfKeyInfo.id" id="wfKeyInfo.id"/>
      <input type="hidden" name="wfKeyInfo.version_id" id="wfKeyInfo.version_id"/>
      <table width="100%" border="0">
        <tr valign="top">
          <td valign="top" width="5%">
          </td>
          <td width="90%">
            <table width="100%" border="0">
              <tr>
                <th width="15%">信息名称：</th>
                <td><input type="text" name="wfKeyInfo.wf_key_info_name" id="wfKeyInfo.wf_key_info_name" maxlength="50"/></td>
              </tr>
              <tr>
                <th>信息类型：</th>
                <td>
                	<input type="radio" name="wfKeyInfo.wf_key_info_type" value="0" onclick="javascript:viewJs.displayConditions(this);"/><label>单选</label>&nbsp;&nbsp;&nbsp;&nbsp;
                	<input type="radio" name="wfKeyInfo.wf_key_info_type" value="1" onclick="javascript:viewJs.displayConditions(this);"/><label>多选</label>&nbsp;&nbsp;&nbsp;&nbsp;
                	<input type="radio" name="wfKeyInfo.wf_key_info_type" value="2" onclick="javascript:viewJs.displayConditions(this);"/><label>填空</label>&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
              </tr>
              <tr>
                <th>是否必填：</th>
                <td>
                	<input type="radio" name="wfKeyInfo.is_required" value="0"/><label>是</label>&nbsp;&nbsp;&nbsp;&nbsp;
                	<input type="radio" name="wfKeyInfo.is_required" value="1"/><label>否</label>&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
              </tr>
              <tr>
                <th>是否有效：</th>
                <td>
                	<input type="radio" name="wfKeyInfo.is_active" value="0"/><label>是</label>&nbsp;&nbsp;&nbsp;&nbsp;
                	<input type="radio" name="wfKeyInfo.is_active" value="1"/><label>否</label>&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
              </tr>
            </table> 
          </td>
          <td valign="top" width="5%">
          </td>
        </tr>
        <tr height="20"><td colspan="2"></td></tr>
      </table>
      
      <div >
      	<table id="infoDetails">
      	
      	</table>
      </div>
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
