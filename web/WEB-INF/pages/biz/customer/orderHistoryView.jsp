<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="orderHistoryListDiv">
    <div class="main_order_detail">
        <div class="title">业务单编号：000213｜业务提交时间：2013-12-01 13:22
            <ul class="title_right">
                <li onclick="tabShow('menu3_','con3_',1,2);" id="menu3_1">车险A餐（1份）</li>
                <li onclick="tabShow('menu3_','con3_',2,2);" id="menu3_2">洗车B套餐（1份）</li>
            </ul>
        </div>
        <div class="content" id="con3_1" style="display:block;">
            <div class="business_note"><b>业务单备注：</b>备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注</div>
            <ul>
                <li class="selected" onclick="tabShow('menu4_','con4_',1,2);" id="menu4_1">保险</li>
                <li onclick="tabShow('menu4_','con4_',2,2);" id="menu4_2">道路救援</li>
            </ul>
            <div id="con4_1" style="display:block;" >
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
            </div>
            <div id="con4_2" style="display:none;" >我是 道路救援 的内容</div>
        </div>
        <div class="content" id="con3_2" style="display:none;">我是 洗车B套餐（1份）的内容</div>
    </div>
</div>    

    <textarea id="orderRowTemplate" jTemplate="yes" style="display: none;">
        <div class="main_order_detail">
            <div class="title">业务单编号：000214｜业务提交时间：2013-10-21 18:06
                <ul class="title_right">
                    <li class="selected" onclick="tabShow('menu5_','con5_',1,2);" id="menu5_1">车险A餐（1份）</li>
                    <li onclick="tabShow('menu5_','con5_',2,2);" id="menu5_2">洗车B套餐（1份）</li>
                </ul>
            </div>
            <div class="content" id="con5_1" style="display:block;">
            
                <div class="business_note"><b>业务单备注：</b>备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注</div>
            
                <ul>
                    <li class="selected" onclick="tabShow('menu6_','con6_',1,2);" id="menu6_1">保险</li>
                    <li onclick="tabShow('menu6_','con6_',2,2);" id="menu6_2">道路救援</li>
                </ul>
                <div id="con6_1" style="display:block;" >
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
                </div>
                <div id="con6_2" style="display:none;" >我是 道路救援 的内容</div>
            </div>
            <div class="content" id="con5_2" style="display:none;">我是 洗车B套餐（1份）的内容</div>
        </div>
    </textarea>    
        
        
        
        
        
        
        
        
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