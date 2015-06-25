<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/recmdt_list.action" ,
    create_url:root + "/biz/recmdt_create.action" ,
    update_url:root + "/biz/recmdt_update.action" ,
    get_url:root + "/biz/recmdt_get.action" ,
    delete_url:root + "/biz/recmdt_delete.action" ,
    entityName:"recmdt",
    size:0,
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("recmdtSO.pageIndex") ;
        
        fillOptions({id:"recmdtStatusSelection", dictName:"staffRole.status", firstLabel:"不限"}) ;// 改为字典取值
        fillOptions({id:"statusSelection", dictName:"staffRole.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"noticeSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("noticeTB", {height:"400px"}) ;
        
        //E$("notice.notice_timestamp").datetimepicker({
          //  timeFormat: "HH:mm:ss"
        //});
        for(var i=0; i<5; i++){
        	fillOptions({id:"recmdt_operator"+i, dictName:"RecmdtOpr", firstLabel:"请选择...", textProperty:"recommendation_opr_symbol",titleProperty:"recommendation_opr_symbol"}) ;// 改为字典取值
            fillOptions({id:"recmdt_type"+i, dictName:"RecmdtKeyType", firstLabel:"请选择...", textProperty:"recmdt_key_type",titleProperty:"recmdt_key_type"}) ;// 改为字典取值
        };
		
        
        E$("eForm").validator();
        E$("sForm").validator();
        E("sForm").setFirstFocus();
        
        this.first();
    },
    addInfos:function() {
        this.addRows ("recmdtInfosTB", [{index:this.size}], {forceClear:false});

        fillOptions({id:"recmdt_operator"+ this.size, dictName:"RecmdtOpr", firstLabel:"请选择...", textProperty:"recommendation_opr_symbol",titleProperty:"recommendation_opr_symbol"}) ;// 改为字典取值
        fillOptions({id:"recmdt_type"+ this.size, dictName:"RecmdtKeyType", firstLabel:"请选择...", textProperty:"recmdt_key_type",titleProperty:"recmdt_key_type"}) ;// 改为字典取值
        fillOptions({id:"recmdt_type"+this.size+"_key", dictName:'', firstLabel:"请选择...", textProperty:"recmdt_key_in_inventory",titleProperty:"recmdt_key_in_inventory"}) ;// 改为字典取值
        this.size ++;
        this.refreshTableList();
        
    },
    get:function(id) {
    	if (id == -1) {
            viewJs.entity = this.eFormInitData;
            if(viewJs.onGet != undefined && viewJs.onGet) {
            	var getEvent = {isHandled:false};
            	viewJs.onGet(this.eFormInitData, getEvent);
            	if (getEvent.isHandled) {
            		return ;
            	}
            }
   		 formDeserialize("eForm", this.eFormInitData, {}) ;// reset form;
   		 return ;
   		}
    	var idProperty = (this.idName==null?"id":this.idName) ;
    	var params = {} ;
    	params[idProperty] = id ;
        ajax(
            this.get_url, 
            params,
            function(data, textStatus){
                viewJs.entity = data;
                formDeserialize("eForm", data, {}) ;
        
                $(data.recmdtInventorys).each(function (i, elem) {
                    fillOptions({id:"recmdt_operator"+i, dictName:"RecmdtOpr", firstLabel:"请选择...", textProperty:"recommendation_opr_symbol",titleProperty:"recommendation_opr_symbol", value:elem.recmdt_operator}) ;// 改为字典取值
                    fillOptions({id:"recmdt_type"+i, dictName:"RecmdtKeyType", firstLabel:"请选择...", textProperty:"recmdt_key_type",titleProperty:"recmdt_key_type", value:elem.recmdt_type}) ;// 改为字典取值
                    fillOptions({id:"recmdt_type"+i+"_key", dictName:elem.recmdt_type, firstLabel:"请选择...", textProperty:"recmdt_key_in_inventory",titleProperty:"recmdt_key_in_inventory", value:elem.recmdt_key}) ;// 改为字典取值
                    document.getElementById("recmdt_value"+i).value = elem.recmdt_value;
            	});
                
            }
        );
    }
}) ;

function tabSteps(stepNo) {
	//校验必填字段
	var recmdt_name = document.getElementById("recmdt_name");
	if(recmdt_name.value == null || recmdt_name.value == ''){
		recmdt_name.focus();
		return;
	}
	
	var t = 2;
    for (var i = 1; i <= t; i++) {
        document.getElementById("step" + i + "Show").style.display = "none";
    }
    document.getElementById(stepNo).style.display = "block";
}

var changeKeyVal = function(obj){
	
	var recmdt_type_id=$(obj).attr('id');
	var recmdt_type_val=$(obj).val();
	fillOptions({id:recmdt_type_id+"_key", dictName:recmdt_type_val, firstLabel:"请选择...", textProperty:"recmdt_key_in_inventory",titleProperty:"recmdt_key_in_inventory"}) ;// 改为字典取值
};

var cleanRecmdt = function(val){
	fillOptions({id:"recmdt_operator"+val, dictName:"RecmdtOpr", firstLabel:"请选择...", textProperty:"recommendation_opr_symbol",titleProperty:"recommendation_opr_symbol"}) ;// 改为字典取值
    fillOptions({id:"recmdt_type"+val, dictName:"RecmdtKeyType", firstLabel:"请选择...", textProperty:"recmdt_key_type",titleProperty:"recmdt_key_type"}) ;// 改为字典取值
    fillOptions({id:"recmdt_type"+val+"_key", dictName:'', firstLabel:"请选择...", textProperty:"recmdt_key_in_inventory",titleProperty:"recmdt_key_in_inventory"}) ;// 改为字典取值
    document.getElementById("recmdt_value"+val).value = "";
};

</script>


<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <div id="listDiv">
  
    <DIV class=main_title>
      <B>决策信息管理</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0 0 5px 0;">
        <input name="recmdtSO.pageIndex" id="recmdtSO.pageIndex" value="1" type="hidden" />
        <input name="recmdtSO.pageSize" id="recmdtSO.pageSize" value="10" type="hidden" />
        <table width="100%" >
          <tr>
           <td style="width:80px;" >决策名称：</td>
           <td style="width:100px;">
             <input class=mg_r name="recmdtSO.recmdt_name" value="" type="text" />
           </td>
           <td style="width:80px;" >当前状态：</td>
           <td style="width:100px;">
             <select name="recmdtSO.recmdt_status" id="recmdtStatusSelection"></select>
           </td>
           <td style="width:100px;">
             <INPUT class="ipt_btn mg_r" value=搜索 type=button name=""  onclick="viewJs.first();">
           </td>
           <td style="width:600px;">
           </td>
          </tr>
        </table>
      </form>
    </DIV>
    
    
    <DIV class=main_list>
      <TABLE border=0 width="100%" id="recmdtTB" title="决策列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
			<TH>决策名称</TH>
			<TH>状态</TH>
			<TH>决策内容</TH>
			<TH>备注</TH>
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
                    <td>{$T.recmdt_name}</td>
                    <td>{dVal("staffRole.status", "name_", {PK_ID:$T.recmdt_status})}</td>
                    <td>{$T.recmdt_detail}</td>
                    <td>{$T.recmdt_remark}</td>
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
      <B>添加决策信息</B> 
      
      <%-- 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
      --%>
    </DIV>
    
    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
    	
    	<div id="step1Show">
		    <div class="step_01">
				<span>第1步</span><span>第2步</span>
		    </div>
	    
			<input type="hidden" name="recmdt.id" id="recmdt.id"/>
			<input type="hidden" name="recmdt.version_id" id="recmdt.version_id"/>
	  
			<table width="100%" border="0">
			  <tr valign="top">
			    <td valign="top" width="15%">
			    </td>
			    <td width="45%">
			      <table width="100%" border="0">
			        <tr>
			          <th>决策名称：</th>
			          <td><input type="text" name="recmdt.recmdt_name" id="recmdt_name" maxlength="50"  required="required"/>
			          </td>
			        </tr>
			        <tr>
			          <th>决策备注：</th>
			          <td>
			            <textarea name="recmdt.recmdt_remark"  style="width: 100%;height: 80px;"></textarea>
			          </td>
			        </tr>
			        <tr>
			          <th>状态：</th>
			          <td>
			            <select name="recmdt.recmdt_status" id="statusSelection"></select>
			          </td>
			        </tr>
			
			      </table> 
			    </td>
			    <td valign="top" width="25%">
			    </td>
			  </tr>
			  <tr height="20"><td colspan="2"></td></tr>
			</table>
			<a href="#" class="btn_blue" onclick="tabSteps('step2Show');">下一步</a>
		</div>
		<div id="step2Show" style="display:none;">
			<div class="step_02">
				<span>第1步</span><span>第2步</span>
			</div>
			<div class="main_decision">
				<table width="100%" border="0">
				  <tr>
				    <th scope="col">决策条件判定与结果</th>
				  </tr>
				  <tr>
				    <td>
						<div>
							<span>
								满足
								<select class="mg_r" name="recmdt.recmdt_condition_operator" id="recmdt.recmdt_condition_operator">
								  <option selected="selected">全部</option>
								  <option>任意</option>
								</select>
								以下条件：
								<!-- <a class="de_add" href="javascript:viewJs.addInfos();">添加条件</a> -->
							</span>
							
							<div class="fr">
								<a href="javascript:showCL('test_condition')" class="btn_blue">测试以下条件</a>
							</div>
						</div>
						
						<!-- <table width="100%" border="0" id="recmdtInfosTB">
						<tbody id="listBody">
			      		</tbody>
			      		
			      		<tbody style="display:none;visibility: false;" disabled="true">
			      			<tr><td>
			      			<textarea id="templateBody" jTemplate="yes">
			      				<ul id="{$T.index}">
									<li>
										<div>
										<select class="mg_r" name="recmdtInventorys[{$T.index}].recmdt_type" id="recmdt_type{$T.index}" value="{$T.recmdt_type}" onchange="changeKeyVal(this)">
										</select>
										<select class="mg_r" name="recmdtInventorys[{$T.index}].recmdt_key" id="recmdt_type{$T.index}_key" value="{$T.recmdt_key}" onchange="selKey(this)">
										</select>
										<select class="mg_r" name="recmdtInventorys[{$T.index}].recmdt_operator" id="recmdt_operator{$T.index}" value="{$T.recmdt_operator}">
										</select>
										<input class="long_ipt" name="recmdtInventorys[{$T.index}].recmdt_value" id="recmdt_value{$T.index}" type="text" value="{$T.recmdt_value}">
										</div>
										<a class="de_del" href="#">×</a>
									</li>
								</ul>
			      			</textarea>
			      			</td></tr>
			      		</tbody>
						</table> -->
						
						<ul>
							<li>
								<div>
								<select class="mg_r" name="recmdtInventorys[0].recmdt_type" id="recmdt_type0" onchange="changeKeyVal(this)">
								</select>
								<select class="mg_r" name="recmdtInventorys[0].recmdt_key" id="recmdt_type0_key" ">
								</select>
								<select class="mg_r" name="recmdtInventorys[0].recmdt_operator" id="recmdt_operator0" >
								</select>
								<input class="long_ipt" name="recmdtInventorys[0].recmdt_value" id="recmdt_value0" type="text" >
								</div>
								<a class="de_del" href="#" onclick="cleanRecmdt('0')">×</a>
							</li>
						</ul>
						<ul>
							<li>
								<div>
								<select class="mg_r" name="recmdtInventorys[1].recmdt_type" id="recmdt_type1" onchange="changeKeyVal(this)">
								</select>
								<select class="mg_r" name="recmdtInventorys[1].recmdt_key" id="recmdt_type1_key" >
								</select>
								<select class="mg_r" name="recmdtInventorys[1].recmdt_operator" id="recmdt_operator1" >
								</select>
								<input class="long_ipt" name="recmdtInventorys[1].recmdt_value" id="recmdt_value1" type="text" >
								</div>
								<a class="de_del" href="#" onclick="cleanRecmdt('1')">×</a>
							</li>
						</ul>
						<ul>
							<li>
								<div>
								<select class="mg_r" name="recmdtInventorys[2].recmdt_type" id="recmdt_type2" onchange="changeKeyVal(this)">
								</select>
								<select class="mg_r" name="recmdtInventorys[2].recmdt_key" id="recmdt_type2_key" >
								</select>
								<select class="mg_r" name="recmdtInventorys[2].recmdt_operator" id="recmdt_operator2" >
								</select>
								<input class="long_ipt" name="recmdtInventorys[2].recmdt_value" id="recmdt_value2" type="text" >
								</div>
								<a class="de_del" href="#" onclick="cleanRecmdt('2')">×</a>
							</li>
						</ul>
						<ul>
							<li>
								<div>
								<select class="mg_r" name="recmdtInventorys[3].recmdt_type" id="recmdt_type3" onchange="changeKeyVal(this)">
								</select>
								<select class="mg_r" name="recmdtInventorys[3].recmdt_key" id="recmdt_type3_key" ">
								</select>
								<select class="mg_r" name="recmdtInventorys[3].recmdt_operator" id="recmdt_operator3" >
								</select>
								<input class="long_ipt" name="recmdtInventorys[3].recmdt_value" id="recmdt_value3" type="text" >
								</div>
								<a class="de_del" href="#" onclick="cleanRecmdt('3')">×</a>
							</li>
						</ul>
						<ul>
							<li>
								<div>
								<select class="mg_r" name="recmdtInventorys[4].recmdt_type" id="recmdt_type4" onchange="changeKeyVal(this)">
								</select>
								<select class="mg_r" name="recmdtInventorys[4].recmdt_key" id="recmdt_type4_key" ">
								</select>
								<select class="mg_r" name="recmdtInventorys[4].recmdt_operator" id="recmdt_operator4" >
								</select>
								<input class="long_ipt" name="recmdtInventorys[4].recmdt_value" id="recmdt_value4" type="text" >
								</div>
								<a class="de_del" href="#" onclick="cleanRecmdt('4')">×</a>
							</li>
						</ul>
						<div>
						决策建议：
						</div>
						<textarea name="recmdt.recmdt_detail" style="width: 100%;height: 80px;" maxlength="100"></textarea>
					</td>
				  </tr>
				</table>
				<a href="#" class="btn_blue" onclick="tabSteps('step1Show');">上一步</a><a href="javascript:viewJs.save();" class="btn_orange">完成</a>
			</div>
		</div>
    </form>
    <div id="test_condition" style="display:none;">
	  <div class="pop_box">
		<div class="pop_title"><a href="javascript:closeCL('test_condition')" class="fr">[关闭]</a>测试决策条件</div>
	
	        <div class="main_list pop_content">
	            <table width="100%" border="0">
	              <tr>
	                <th>客户号</th>
	                <th>姓名</th>
	                <th>性别</th>
	                <th>年龄</th>
	                <th>电话号码</th>
	                <th>会员号</th>
	                <th>操作</th>
	              </tr>
	              <tr>
	                <td>xcyz_001</td>
	                <td>王小明</td>
	                <td>男</td>
	                <td>28</td>
	                <td>1868888888</td>
	                <td>-</td>
	                <td><a href="#">查看</a></td>
	              </tr>
	              <tr>
	                <td>xcyz_001</td>
	                <td>王小明</td>
	                <td>男</td>
	                <td>28</td>
	                <td>1868888888</td>
	                <td>-</td>
	                <td><a href="#">查看</a></td>
	              </tr>
	              <tr>
	                <td>xcyz_001</td>
	                <td>王小明</td>
	                <td>男</td>
	                <td>28</td>
	                <td>1868888888</td>
	                <td>-</td>
	                <td><a href="#">查看</a></td>
	              </tr>
	              <tr>
	                <td>xcyz_001</td>
	                <td>王小明</td>
	                <td>男</td>
	                <td>28</td>
	                <td>1868888888</td>
	                <td>GZ88888888</td>
	                <td><a href="#">查看</a></td>
	              </tr>
	              <tr>
	                <td>xcyz_001</td>
	                <td>王小明</td>
	                <td>男</td>
	                <td>28</td>
	                <td>1868888888</td>
	                <td>-</td>
	                <td><a href="#">查看</a></td>
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