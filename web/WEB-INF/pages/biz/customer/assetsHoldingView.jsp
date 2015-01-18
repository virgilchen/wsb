<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
<!--
g$v<%=view_id%>.assetsHoldingView = $.extend(newView(), {
    view:document.getElementById("assetsHoldingListDiv"), 
    pageIndex : document.getElementById("assetsHoldingSO.pageIndex") ,
    
    list:function() {
    	this.getAssetsHolding();
    },
    
    getAssetsHolding:function() {
    	V("assetsHoldingSO.customer_id", V("customer.id"));
    	
        ajax(
            root + "/biz/customer_getAssetsHolding.action", 
            E$("sAssetsHoldingForm").serialize(),
            function(data, textStatus){
                var $div = E$("assetsHoldingListDiv");
                $div.html("");
                
                var assetsHoldingRowTemplate = V("assetsHoldingRowTemplate");
                var options = [];
                var existOptions = {} ;
                $(data.list).each(function(i, assetsHolding) {
                	E$("effect_date").datepicker({});
                	E$("expire_date").datepicker({});
                    $div.append(parse(assetsHoldingRowTemplate, assetsHolding));
                    if (typeof(existOptions[assetsHolding.pro_assets_id]) == "undefined") {
                    	existOptions[assetsHolding.pro_assets_id] = true ;
                        options[options.length] = {"pro_assets_id":assetsHolding.pro_assets_id, "prod_pack_name":assetsHolding.prod_pack_name};
                    }
                });
                
                if ($("option", E$("assetsHoldingSO.pro_assets_id")).length == 0) {// 填充options
                    fillOptions({
                        id:"assetsHoldingSO.pro_assets_id", 
                        data:options, 
                        valueProperty:"pro_assets_id",
                        textProperty:"prod_pack_name", 
                        firstLabel:"全部"
                    }) ;// 改为字典取值
                }
            }
        );
    }
});
//-->
</script>
<form method="post" id="sAssetsHoldingForm" name="sAssetsHoldingForm" onsubmit="return false;" style="margin: 0">
	<input name="assetsHoldingSO.pageIndex" id="assetsHoldingSO.pageIndex" value="1" type="hidden" />
    <input name="assetsHoldingSO.pageSize" id="assetsHoldingSO.pageSize" value="10" type="hidden" />
	<input name="assetsHoldingSO.customer_id" id="assetsHoldingSO.customer_id" value="" type="hidden" />

	<DIV class="main_order_detail" style="border: 0px;">
	  <select name="assetsHoldingSO.pro_assets_id" id="assetsHoldingSO.pro_assets_id" onchange="viewJs.assetsHoldingView.getAssetsHolding();"></select>
	  <TABLE border=0 width="100%" id="assetsHoldingTB" title="客户拥有商品包列表">
        <thead>
          <TR>
			<TH width="200px">基础商品</TH>
			<TH width="200px">归属业务</TH>
			<TH width="80px">原包量</TH>
			<TH width="80px">剩余量</TH>
			<TH width="100px">生效时间</TH>
			<TH width="100px">到期时间</TH>
		  <TR>
        </thead>
        
        <tbody id="assetsHoldingListDiv" >
        </tbody>
         
        <tbody style="display:none;visibility: false;" disabled="true">
          <tr>
            <td>
              <textarea id="assetsHoldingRowTemplate" jTemplate="yes">
                  <tr id="{$T.id}">
                    <td align="center">{$T.prod_name}</td>
                    <td align="center">{$T.business_name}</td>
                    <td align="center">{$T.total_amount}</td>
                    <td align="center">{$T.remain_amount}</td>
                    <td align="center">{fmt.maxlen($T.effect_date, 10)}</td>
                    <td align="center">{fmt.maxlen($T.expire_date, 10)}</td>
                  </tr>
              </textarea>
            </td>
          </tr>
        </tbody>

      </TABLE>
    </DIV>
</form>