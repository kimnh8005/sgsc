(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2ff98652"],{"070b":function(t,e,s){"use strict";var i=s("5f8d"),n=s.n(i);n.a},"1e8c":function(t,e,s){"use strict";var i=s("62bd"),n=s.n(i);n.a},"33d8":function(t,e,s){"use strict";s.r(e);var i=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",[i("PageTitle",{attrs:{title:t.processTitle,"has-date-range-picker":""},on:{changedDate:t.onChangedDate}}),i("div",{staticClass:"page-box"},[i("PanelTitle",{attrs:{title:"통합 위험도"}}),i("ChartBox",{attrs:{"raw-data":t.riskProcessHistoryMap,"chart-type":"PROCESS"}}),i("table",{staticClass:"default-table"},[i("thead",[i("tr",[i("th",[t._v("날짜")]),i("th",[t._v("위험/확률")]),t._l(t.riskProcessHistoryMap.cols.slice(1),(function(e,s){return i("th",{key:s},[t._v(" "+t._s(t.getTableHeader(e))+" ")])}))],2)]),i("tbody",[t._l(t.riskProcessHistoryMap.data,(function(e,s){return[i("tr",{key:"row_1_"+s},[i("th",{attrs:{rowspan:"2"}},[t._v(t._s(t.formatter.date(e["date"],"MM/dd")))]),i("th",[t._v("위험단계")]),t._l(t.sortRow(0,e),(function(e,s){return i("th",{key:"col_1_"+s},[e?i("RiskLabel",{attrs:{"text-data":e,showRiskLabel:!1}}):i("span",[t._v("-")])],1)}))],2),i("tr",{key:"row_2_"+s},[i("th",[t._v("통합 위험률")]),t._l(t.sortRow(0,e),(function(e,s){return i("th",{key:"col_2_"+s},[e?i("RiskLabel",{attrs:{"text-data":e,showRiskStep:!1}}):i("span",[t._v("-")])],1)}))],2)]}))],2)])],1),i("div",{staticClass:"page-box"},[i("PanelTitle",{attrs:{title:"설비 별 위험도"}}),i("PanelTab",{attrs:{items:t.equipmentByProcsId.list,"display-prop-name":"eqmt_nm","emit-prop-list":["eqmt_id","eqmt_nm"]},on:{change:t.equipmentTabChanged}}),i("PanelSubTitle",{attrs:{title:t.equipmentTitle},scopedSlots:t._u([{key:"button",fn:function(){return[i("router-link",{attrs:{to:{path:"/analysis/equipment/"+t.selectedEquipmentId,query:{procs_nm:t.processTitle,eqmt_nm:t.equipmentTitle}}}},[t._v(" 자세히 보기 → ")])]},proxy:!0}])}),i("ChartBox",{attrs:{"raw-data":t.riskEquipmentHistoryMap,"chart-type":"EQUIPMENT"}}),i("table",{staticClass:"default-table"},[i("thead",[i("tr",[i("th",[t._v("날짜")]),i("th",[t._v("위험/확률")]),t._l(t.riskEquipmentHistoryMap.cols.slice(1),(function(e,s){return i("th",{key:s},[t._v(" "+t._s(t.getTableHeader(e))+" ")])}))],2)]),i("tbody",[t._l(t.riskEquipmentHistoryMap.data,(function(e,n){return[i("tr",{key:"row_1_"+n},[i("th",{attrs:{rowspan:"3"}},[t._v(t._s(t.formatter.date(e["date"],"MM/dd")))]),i("th",[t._v("위험단계")]),t._l(t.sortRow(1,e),(function(e,s){return i("th",{key:"col_1_"+s},[e?i("RiskLabel",{attrs:{"text-data":e,showRiskLabel:!1}}):i("span",{staticClass:"font-weight-black"},[t._v("-")])],1)}))],2),i("tr",{key:"row_2_"+n},[i("th",[t._v("통합 위험률")]),t._l(t.sortRow(1,e),(function(e,s){return i("th",{key:"col_2_"+s},[e?i("RiskLabel",{attrs:{"text-data":e,showRiskStep:!1}}):i("span",[t._v("-")])],1)}))],2),i("tr",{key:"row_3_"+n},[i("th",[t._v("사고 시나리오")]),t._l(t.sortRow(1,e),(function(n,a){return i("td",{key:"col_3_"+a},[n?i("button",{staticClass:"outline-dark",on:{click:function(s){t.showPopup("KIES",t.selectedProcessName,t.selectedEquipmentName,t.selectedEquipmentId,e["date"]+t.riskEquipmentHistoryMap.cols.slice(1)[a]+"00")}}},[t._v(" 상세보기 "),i("img",{attrs:{src:s("92fe"),alt:"상세보기"}})]):i("span",[t._v("-")])])}))],2)]}))],2)])],1),t.accident_scenario_popup.isShow?i("AccidentScenarioPopup",{staticClass:"no-hr-line",on:{close:t.hidePopup},scopedSlots:t._u([t.accident_scenario_popup.procs_nm&&t.accident_scenario_popup.procs_nm.length>0?{key:"subtitle",fn:function(){return[i("h2",[t._v(t._s(t.accident_scenario_popup.procs_nm)),i("img",{staticClass:"arrow",attrs:{src:s("92fe"),alt:""}}),t._v(t._s(t.accident_scenario_popup.eqmt_nm))])]},proxy:!0}:t.accident_scenario_popup.eqmt_nm&&t.accident_scenario_popup.eqmt_nm.length>0?{key:"subtitle",fn:function(){return[i("h2",[t._v(t._s(t.accident_scenario_popup.eqmt_nm))])]},proxy:!0}:{key:"subtitle",fn:function(){return[i("h2",[t._v("장비 이름 없음")])]},proxy:!0},{key:"time",fn:function(){return[i("h3",[t._v(t._s(t.popupTimeText()))])]},proxy:!0},{key:"body",fn:function(){return[i("table",{staticClass:"default-table in-popup"},[i("thead",[i("tr",[i("th",[t._v("사고 코드")]),i("th",[t._v("사고 발생 원인")]),i("th",[t._v("사고 발생 내용")]),i("th",[t._v("점검 사항")]),i("th",[t._v("위험단계")]),i("th",[t._v(t._s(t.popupRiskText()))])])]),i("tbody",t._l(t.realtimeAccidentScenarioPopup.list,(function(e,s){return i("tr",{key:s},[i("th",[t._v(t._s(e.SCEN_NM))]),i("th",[t._v(t._s(e.SCEN_CAUSE))]),i("th",[t._v(t._s(e.SCEN_RESULT))]),i("th",[t._v(t._s(e.ETC))]),i("td",[i("RiskLabel",{attrs:{"text-data":e.risk_rate,showRiskLabel:!1}})],1),i("th",[i("RiskLabel",{attrs:{"text-data":e.risk_rate,showRiskStep:!1}})],1)])})),0)])]},proxy:!0}],null,!0)}):t._e()],1)},n=[],a=(s("cb29"),s("c975"),s("fb6a"),s("5530")),r=s("c6f9"),o=s("2f62"),c=s("25b9"),p=s("a321"),u=s("7ec5"),_=s("4ba1"),d=s("7789"),l=s("3b51"),h=s("5a0c"),m=s.n(h),y=s("602f"),f={name:"RiskPredictionProcessDetail",components:{PanelSubTitle:l["a"],PanelTab:d["a"],RiskLabel:u["a"],ChartBox:p["a"],PageTitle:c["a"],PanelTitle:r["a"],AccidentScenarioPopup:y["a"]},props:{procs_id:String,procs_nm:String},computed:Object(a["a"])(Object(a["a"])({},Object(o["c"])(["riskProcessHistoryMap","equipmentByProcsId","riskEquipmentHistoryMap","realtimeAccidentScenarioPopup"])),{},{processTitle:function(){return this.$route.query.procs_nm?this.$route.query.procs_nm:"공정 상세 정보"},selectedProcessName:function(){return this.$route.query.procs_nm?this.$route.query.procs_nm:null},equipmentTitle:function(){return this.selectedEquipmentName?this.selectedEquipmentName+" 위험도":"-"}}),created:function(){this.queryRiskProcessHistoryMap()},data:function(){return{formatter:_["a"],selectedEquipmentId:"",selectedEquipmentName:"",selectedDate:{startDate:m()().format("YYYYMMDD"),endDate:m()().format("YYYYMMDD")},accident_scenario_popup:{isShow:!1,anys_sys_cd:"",procs_nm:"",eqmt_nm:"",anys_time:""}}},methods:{queryRiskProcessHistoryMap:function(){var t=this;this.$store.dispatch("riskProcessHistoryMap",{queryString:"?strt_anys_ymd="+this.selectedDate.startDate+"&end_anys_ymd="+this.selectedDate.endDate+"&procs_id="+this.procs_id}),this.$store.dispatch("equipmentByProcsId",{queryString:"?procs_id="+this.procs_id}).then((function(){var e={eqmt_id:t.equipmentByProcsId.list[0].eqmt_id,eqmt_nm:t.equipmentByProcsId.list[0].eqmt_nm};t.equipmentTabChanged(e)}))},getTableHeader:function(t){return"date"===t?"날짜":t+"시"},sortRow:function(t,e){var s=[];s=0===t?this.riskProcessHistoryMap.cols:this.riskEquipmentHistoryMap.cols;var i=s.indexOf("date"),n=new Array(s.length-1).fill(null);for(var a in e)if("date"!=a){var r=s.indexOf(a);r<i?n[r]=e[a]:n[r-1]=e[a]}return n},equipmentTabChanged:function(t){console.log(t.eqmt_nm),this.selectedEquipmentId=t.eqmt_id,this.selectedEquipmentName=t.eqmt_nm,this.$store.dispatch("riskEquipmentHistoryMap",{queryString:"?strt_anys_ymd="+this.selectedDate.startDate+"&end_anys_ymd="+this.selectedDate.endDate+"&eqmt_id="+t.eqmt_id})},onChangedDate:function(t){console.log(t,"onChangedDate<<<<"),this.selectedDate.startDate=m()(t.startDate).format("YYYYMMDD"),this.selectedDate.endDate=m()(t.endDate).format("YYYYMMDD"),this.queryRiskProcessHistoryMap()},showPopup:function(t,e,s,i,n){this.accident_scenario_popup.isShow=!0,this.accident_scenario_popup.anys_sys_cd=t,this.accident_scenario_popup.procs_nm=e,this.accident_scenario_popup.eqmt_nm=s,this.accident_scenario_popup.eqmt_id=i,this.accident_scenario_popup.anys_time=n,this.$store.dispatch("realtimeAccidentScenarioPopup",{anayseSystemCode:this.accident_scenario_popup.anys_sys_cd,eqmtID:this.accident_scenario_popup.eqmt_id,anayseYMDH:this.accident_scenario_popup.anys_time.slice(0,10)})},hidePopup:function(){this.accident_scenario_popup.isShow=!1},popupTimeText:function(){return"분석일시 : "+_["a"].date(this.accident_scenario_popup.anys_time)},popupRiskText:function(){return"KIES"===this.accident_scenario_popup.anys_sys_cd?"통합 위험률":"CRIG"===this.accident_scenario_popup.anys_sys_cd?"발생확률":"PARTDB"===this.accident_scenario_popup.anys_sys_cd?"고장확률":"KSEC"===this.accident_scenario_popup.anys_sys_cd?"위험확률":"통합 위험률"}}},k=f,b=(s("070b"),s("2877")),q=Object(b["a"])(k,i,n,!1,null,"2fe2767c",null);e["default"]=q.exports},"5f8d":function(t,e,s){},"62bd":function(t,e,s){},7789:function(t,e,s){"use strict";var i=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("ul",{staticClass:"tab"},[t._l(t.items,(function(e,i){return s("li",{key:"filled_"+i,class:{active:t.index===i},on:{click:function(s){return t.onClick(i,e)}}},[t._v(" "+t._s(e[t.displayPropName])+" ")])})),t._l(t.getBlankNum(t.items),(function(t,e){return s("li",{key:"empty_"+e,staticClass:"blank-item"})}))],2)},n=[],a=(s("4160"),s("a9e3"),s("159b"),{name:"PanelTab",props:{items:Array,displayPropName:String,numsInRow:{type:Number,default:5},emitPropList:{type:Array,required:!0}},computed:{cssVars:function(){return{"--numsInRow":this.numsInRow}}},data:function(){return{index:0}},methods:{onClick:function(t,e){this.index=t;var s={};this.emitPropList&&this.emitPropList.length?this.emitPropList.forEach((function(t){s[t]=e[t]})):s[this.displayPropName]=e[this.displayPropName],this.$emit("change",s)},getBlankNum:function(t){var e=0;return t&&t.length&&(e=(this.numsInRow-t.length%this.numsInRow)%this.numsInRow),e}}}),r=a,o=(s("1e8c"),s("2877")),c=Object(o["a"])(r,i,n,!1,null,"1940b404",null);e["a"]=c.exports},"81d5":function(t,e,s){"use strict";var i=s("7b0b"),n=s("23cb"),a=s("50c4");t.exports=function(t){var e=i(this),s=a(e.length),r=arguments.length,o=n(r>1?arguments[1]:void 0,s),c=r>2?arguments[2]:void 0,p=void 0===c?s:n(c,s);while(p>o)e[o++]=t;return e}},cb29:function(t,e,s){var i=s("23e7"),n=s("81d5"),a=s("44d2");i({target:"Array",proto:!0},{fill:n}),a("fill")}}]);
//# sourceMappingURL=chunk-2ff98652.js.map