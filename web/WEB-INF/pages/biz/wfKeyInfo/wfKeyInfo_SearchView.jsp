<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="wfKeyInfo_searchViewDiv" style="display:none;">
<script>
g$v<%=view_id%>.wfKeyInfoSearchView = $.extend(newView(), {
    view:document.getElementById("wfKeyInfo_searchViewDiv"), 
    pageIndex : document.getElementById("wfKeyInfoSO.pageIndex") ,
    
    list:function() {
	    ajax(
	        root + "/biz/wfKeyInfo_query4Selection.action", 
	        E$("sWfKeyInfoForm").serialize(),
	        function(data, textStatus){
	            viewJs.addRows("wfKeyInfoTB", data.list) ;
	            var $pageSize = E$("wfKeyInfoSO.pageSize") ;
	            
	            viewJs.lastIndex = Math.ceil(data.total/parseInt($pageSize.val()));
	            viewJs.setPaginationInfo(data.total, data.pageIndex, viewJs.lastIndex, "wfKeyInfoSearchView") ;
	        }
	    );
    },
    selectIds:function() {
    	var selectIds = "";
    	$("input[id='selIds']").each(function(){
            if($(this).is(':checked')){
            	var chkId = $(this).val();
            	selectIds += chkId + ",";
            }
            
        });
    	if(selectIds.indexOf(',')>0){
        	selectIds=selectIds.substring(0, selectIds.lastIndexOf(','));
        }
    	viewJs.selectWfKeyInfo(selectIds);
    	closeCL('wfKeyInfo_searchViewDiv');
    	
    },
    open:function() {
    	showCL('wfKeyInfo_searchViewDiv');
    }
});
</script>


<form method="post" id="sWfKeyInfoForm" name="sWfKeyInfoForm" onsubmit="return false;" style="margin: 0">
      <input name="wfKeyInfoSO.pageIndex" id="wfKeyInfoSO.pageIndex" value="1" type="hidden" />
      <input name="wfKeyInfoSO.pageSize" id="wfKeyInfoSO.pageSize" value="10" type="hidden" />
      
      <div class="pop_box" style="width: 800px;height: 550px;top:50px;">
        <div class="pop_title">
            <a href="javascript:closeCL('wfKeyInfo_searchViewDiv')" class="fr">[关闭]</a>选择关键信息
        </div>
        <div class="main_list pop_content">
            <div class="pop_search">
                <lable>名称</lable>
                <input type="text" name="wfKeyInfoSO.wf_key_info_name"/>
                <a href="javascript:viewJs.wfKeyInfoSearchView.list();" class="btn_blue">搜索</a>
                <a href="javascript:viewJs.wfKeyInfoSearchView.selectIds();" class="btn_orange">选择</a>
            </div>
            
            <table width="100%" border="0" id="wfKeyInfoTB">
                <thead>
	              <tr>
	                <TH width="20px"></TH>
		            <TH>信息编号</TH>
		            <TH>信息名称</TH>
		            <TH>信息类型</TH>
		            <TH>是否必填</TH>
	              </tr>
                </thead>
		        
		        <tbody id="listBody" >
		        </tbody>
              
		         <tbody style="display:none;visibility: false;" disabled="true">
		          <tr>
		            <td>
		              <textarea id="templateBody" jTemplate="yes">
		                  <tr id="{$T.id}" ondblclick="viewJs.selectWfKeyInfo({$T.id});closeCL('wfKeyInfo_searchViewDiv');">
		                    <td>
		                      <input type="checkbox" name="ids" id="selIds" value="{$T.id}" />
		                    </td>
		                    <td>{$T.id}</td>
		                    <td>{$T.wf_key_info_name}</td>
		                    <td>{#if $T.wf_key_info_type == '0'}单选{#/if}{#if $T.wf_key_info_type == '1'}多选{#/if}{#if $T.wf_key_info_type == '2'}填空{#/if}</td>
		                    <td>{#if $T.is_required == '0'}是{#/if}{#if $T.is_required == '1'}否{#/if}</td>
		                  </tr>
		              </textarea>
		            </td>
		          </tr>
		        </tbody>
            </table>
            
            <%request.setAttribute("pagination_id", "wfKeyInfoSearchView"); %>
            <%@include file="/WEB-INF/pages/frame/pagination.jsp" %>
        </div>
     
      </div>
      <div class="pop_bg_500"></div>
</form>
</div>