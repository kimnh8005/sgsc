(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-148e1936"],{2085:function(e,t,a){},"2b90":function(e,t,a){"use strict";var s=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("ul",{staticClass:"pagination"},[a("li",{class:{disabled:1===e.pageIndex||0===e.totalPages},on:{click:e.prev}},[e._v(" < 이전 ")]),e._l(e.pageList,(function(t,s){return a("li",{key:s,class:{active:e.pageIndex===t},on:{click:function(a){return e.onChange(t)}}},[e._v(" "+e._s(t)+" ")])})),a("li",{class:{disabled:e.pageIndex===e.totalPages||0===e.totalPages},on:{click:e.next}},[e._v("다음 >")])],2)},n=[],i=(a("a9e3"),{name:"Pagination",props:{totalCount:{type:Number,default:0},maxDisplayPageCount:{type:Number,default:5},pageIndex:{type:Number,default:1},pageSize:{type:Number,default:100}},computed:{totalPages:function(){return Math.ceil(this.totalCount/this.pageSize)},pageCount:function(){return this.totalPages>this.maxDisplayPageCount?this.maxDisplayPageCount:this.totalPages},pageList:function(){var e=this.pageIndex>3?this.pageIndex-2:1,t=this.totalPages-this.pageIndex;e=this.totalPages>this.maxDisplayPageCount&&t<3?e-(this.maxDisplayPageCount-t-3):e;for(var a=[],s=0;s<this.pageCount;++s)a.push(e+s);return a}},methods:{onChange:function(e){this.pageIndex!==e&&this.$emit("change",e)},prev:function(){1!==this.pageIndex&&this.onChange(this.pageIndex-1)},next:function(){this.pageIndex!==this.totalPages&&this.onChange(this.pageIndex+1)}}}),o=i,r=(a("9750"),a("2877")),l=Object(r["a"])(o,s,n,!1,null,"119af7fa",null);t["a"]=l.exports},3145:function(e,t,a){"use strict";var s=a("c2a6"),n=a.n(s);n.a},"324a":function(e,t,a){"use strict";var s=a("80da"),n=a.n(s);n.a},"3a65":function(e,t,a){},"7e3e":function(e,t,a){"use strict";a.r(t);var s=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("PageTitle"),a("div",{staticClass:"page-box"},[a("Tab",{attrs:{items:e.tabItems,"display-prop-name":"tab_nm","emit-prop-list":["tab_id","tab_nm"]},on:{change:e.recordTabChanged}}),a("PanelProcessEquipmentFiltering",{attrs:{"has-date-range-picker":"","process-title":"설 비","equipment-title":null,"start-date":e.filtering.startDate,"end-date":e.filtering.endDate},on:{filtering:e.onFiltering}}),a("PanelTitle",{attrs:{title:"알림 이력",desc:e.total}}),a("table",{staticClass:"default-table double-row-header"},[e._m(0),a("tbody",e._l(e.gasRiskProposalList.list,(function(t,s){return a("tr",{key:s},[a("td",[e._v(e._s(e.getNumberByRowOrder(t.RO)))]),a("td",[e._v(e._s(t.procs_nm))]),a("td",[a("router-link",{staticClass:"equipment-link",attrs:{to:"/analysis/equipment/"+t.eqmt_id+"?procs_nm="+t.procs_nm+"&eqmt_nm="+t.eqmt_nm}},[e._v(" "+e._s(t.eqmt_nm)+" "),a("span",[e._v(">")])])],1),a("td",[a("RiskLabel",{attrs:{"is-double-row":"",code:t.RISK_STEP_CD,num:t.RISK_NUM,unit:t.RISK_UNIT}})],1),a("td",[a("RiskStepLabel",{attrs:{"use-step-by-name":"","delete-step-suffix":"",name:t.CRIG103}})],1),a("td",[a("RiskStepLabel",{attrs:{"use-step-by-name":"","delete-step-suffix":"",name:t.CRIG104}})],1),a("td",[a("RiskStepLabel",{attrs:{"use-step-by-name":"","delete-step-suffix":"",name:t.CRIG105}})],1),a("td",[a("RiskStepLabel",{attrs:{"use-step-by-name":"","delete-step-suffix":"",name:t.CRIG106}})],1),a("td",[a("RiskStepLabel",{attrs:{"use-step-by-name":"","delete-step-suffix":"",name:t.PART104}})],1),a("td",[a("RiskStepLabel",{attrs:{"use-step-by-name":"","delete-step-suffix":"",name:t.PART105}})],1),a("td",[a("RiskStepLabel",{attrs:{"use-step-by-name":"","delete-step-suffix":"",name:t.PART106}})],1),a("td",[a("RiskStepLabel",{attrs:{"use-step-by-name":"","delete-step-suffix":"",name:t.KSEC105}})],1),a("td",[a("RiskStepLabel",{attrs:{"use-step-by-name":"","delete-step-suffix":"",name:t.KSEC106}})],1),a("td",[a("RiskStepLabel",{attrs:{"use-step-by-name":"","delete-step-suffix":"",name:t.KSEC107}})],1),a("td",[e._v(" "+e._s(e.dayjs(t.props_ymdhis).format("YYYY-MM-DD hh:mm:ss"))+" ")])])})),0)]),a("Pagination",{attrs:{"page-index":this.page.index,"total-count":e.gasRiskProposalList.totalcount,"page-size":this.page.size},on:{change:e.onChangePage}})],1)],1)},n=[function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("thead",[a("tr",[a("th",{attrs:{rowspan:"2"}},[e._v("No")]),a("th",{attrs:{colspan:"3"}},[e._v("위험 시설")]),a("th",{attrs:{rowspan:"2"}},[e._v("차단 대상 밸브")]),a("th",{attrs:{rowspan:"2"}},[e._v("밸브 차단 요청")]),a("th",{attrs:{rowspan:"2"}},[e._v("이상원인")]),a("th",{attrs:{rowspan:"2"}},[e._v("발생 일시")])]),a("tr",[a("th",[e._v("시설")]),a("th",[e._v("통합 위험단계")]),a("th",{staticClass:"no-radius border-right"},[e._v("통합 위험률")])])])}],i=a("5530"),o=a("2f62"),r=a("c6f9"),l=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("ul",{staticClass:"tab"},[e._l(e.items,(function(t,s){return a("li",{key:"filled_"+s,class:{active:e.index===s},on:{click:function(a){return e.onClick(s,t)}}},[e._v(" "+e._s(t[e.displayPropName])+" ")])})),e._l(e.getBlankNum(e.items),(function(e,t){return a("li",{key:"empty_"+t,staticClass:"blank-item"})}))],2)},c=[],u=(a("4160"),a("a9e3"),a("159b"),{name:"Tab",props:{items:Array,displayPropName:String,numsInRow:{type:Number,default:5},emitPropList:{type:Array,required:!0}},computed:{cssVars:function(){return{"--numsInRow":this.numsInRow}}},data:function(){return{index:0}},methods:{onClick:function(e,t){this.index=e;var a={};this.emitPropList&&this.emitPropList.length?this.emitPropList.forEach((function(e){a[e]=t[e]})):a[this.displayPropName]=t[this.displayPropName],this.$emit("change",a)},getBlankNum:function(e){var t=0;return e&&e.length&&(t=(this.numsInRow-e.length%this.numsInRow)%this.numsInRow),t}}}),d=u,p=(a("3145"),a("2877")),m=Object(p["a"])(d,l,c,!1,null,"e473d0e0",null),h=m.exports,f=a("25b9"),g=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"filter-container"},[a("h4",{staticClass:"title"},[e._v(e._s(e.processTitle))]),a("select",{directives:[{name:"model",rawName:"v-model",value:e.selectProcs,expression:"selectProcs"}],on:{change:[function(t){var a=Array.prototype.filter.call(t.target.options,(function(e){return e.selected})).map((function(e){var t="_value"in e?e._value:e.value;return t}));e.selectProcs=t.target.multiple?a:a[0]},e.onProcessChange]}},[a("option",{attrs:{value:""}},[e._v("전체 시설")]),e._l(e.process,(function(t){return a("option",{key:t.procs_id,domProps:{value:t.procs_id}},[e._v(" "+e._s(t.procs_nm)+" ")])}))],2),a("h4",{staticClass:"title",class:{"blank-title":null===e.equipmentTitle}},[e._v(e._s(e.equipmentTitle))]),a("select",{directives:[{name:"model",rawName:"v-model",value:e.selectEqmt,expression:"selectEqmt"}],on:{change:function(t){var a=Array.prototype.filter.call(t.target.options,(function(e){return e.selected})).map((function(e){var t="_value"in e?e._value:e.value;return t}));e.selectEqmt=t.target.multiple?a:a[0]}}},[a("option",{attrs:{value:""}},[e._v("전체 설비")]),e._l(e.equipment,(function(t){return a("option",{key:t.eqmt_id,domProps:{value:t.eqmt_id}},[e._v(" "+e._s(t.eqmt_nm)+" ")])}))],2),a("div",{staticClass:"vertical-line"}),e.hasDateRangePicker?[a("h4",{staticClass:"title"},[e._v(e._s(e.datepickerTitle))]),a("div",{staticClass:"datepicker-container"},[e._m(0),a("DateRangePicker",{ref:"picker",attrs:{opens:e.dateRange.opens,"locale-data":e.dateRange.localeData,showDropdowns:e.dateRange.showDropdowns,autoApply:e.dateRange.autoApply,ranges:e.dateRange.showRange},on:{update:e.onUpdateDateRange},scopedSlots:e._u([{key:"input",fn:function(t){return[e._v(" "+e._s(e._f("date")(t.startDate))+" ~ "+e._s(e._f("date")(t.endDate))+" ")]}}],null,!1,985506992),model:{value:e.selectedDateRange,callback:function(t){e.selectedDateRange=t},expression:"selectedDateRange"}})],1)]:e._e(),e.hasLeakageCheck?[a("h4",{staticClass:"title"},[e._v("누출위험")]),e._l(e.checkboxList,(function(t){return a("label",{key:t.value},[a("input",{directives:[{name:"model",rawName:"v-model",value:t.bool,expression:"item.bool"}],attrs:{type:"checkbox"},domProps:{checked:Array.isArray(t.bool)?e._i(t.bool,null)>-1:t.bool},on:{change:[function(a){var s=t.bool,n=a.target,i=!!n.checked;if(Array.isArray(s)){var o=null,r=e._i(s,o);n.checked?r<0&&e.$set(t,"bool",s.concat([o])):r>-1&&e.$set(t,"bool",s.slice(0,r).concat(s.slice(r+1)))}else e.$set(t,"bool",i)},e.onCheckboxChange]}}),e._v(" "+e._s(t.name)+" ")])}))]:e._e(),e.hasWorkStatSelector?[a("h4",{staticClass:"title"},[e._v("상태")]),a("select",{directives:[{name:"model",rawName:"v-model",value:e.selectedWorkStatCode,expression:"selectedWorkStatCode"}],on:{change:function(t){var a=Array.prototype.filter.call(t.target.options,(function(e){return e.selected})).map((function(e){var t="_value"in e?e._value:e.value;return t}));e.selectedWorkStatCode=t.target.multiple?a:a[0]}}},[a("option",{attrs:{value:""}},[e._v("전체")]),e._l(e.WORK_STAT_CD,(function(e){return a("option",{key:e.cd_id,attrs:{label:e.cd_nm},domProps:{value:e.cd_id}})}))],2)]:e._e(),a("button",{attrs:{disabled:e.requireSelectEquipment&&""===e.selectEqmt},on:{click:e.onClick}},[e._v("검 색")])],2)},b=[function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"icon"},[s("img",{attrs:{src:a("7cb0"),alt:"calender icon"}})])}],_=a("bbf5"),v=a.n(_),k=(a("4b7f"),a("5a0c")),D=a.n(k),y={name:"PanelProcessEquipmentFiltering",components:{DateRangePicker:v.a},props:{requireSelectEquipment:Boolean,hasDateRangePicker:Boolean,hasLeakageCheck:Boolean,hasWorkStatSelector:Boolean,startDate:{type:String},endDate:{type:String},processTitle:{type:String,default:"생산공정"},equipmentTitle:{type:String,default:"설 비"},datepickerTitle:{type:String,default:"발생일시"}},computed:Object(i["a"])(Object(i["a"])(Object(i["a"])(Object(i["a"])({},Object(o["c"])("common/process",["process"])),Object(o["c"])("common/equipment",["equipment"])),Object(o["c"])("common/code",["WORK_STAT_CD"])),{},{activeEquipmentId:function(){return this.activeEquipId}}),data:function(){var e=this.startDate?D()(this.startDate).format("YYYY-MM-DD"):D()().format("YYYY-MM-DD"),t=this.endDate?D()(this.endDate).format("YYYY-MM-DD"):D()().format("YYYY-MM-DD");return{selectProcs:"",selectEqmt:"",dayjs:D.a,selectedDateRange:{startDate:e,endDate:t},dateRange:{today:new Date,opens:"left",showDropdowns:!1,showRange:!1,localeData:{direction:"ltr",format:"yyyy-mm-dd",separator:" ~ ",applyLabel:"적용",cancelLabel:"취소",weekLabel:"W",customRangeLabel:"Custom Range",daysOfWeek:["일","월","화","수","목","금","토"],monthNames:["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],firstDay:0}},checkboxList:[{name:"ON",value:"on",bool:!0},{name:"OFF",value:"off",bool:!0}],selectedWorkStatCode:""}},created:function(){this.$store.dispatch("common/process/query",{queryString:""}),this.hasWorkStatSelector&&this.$store.dispatch("common/code/query",{code:"WORK_STAT_CD"})},filters:{date:function(e){return e?D()(e).format("YYYY-MM-DD"):""}},methods:{onClick:function(){var e={processId:this.selectProcs,equipmentId:this.selectEqmt};this.hasDateRangePicker&&(e["startDate"]=D()(this.selectedDateRange.startDate).format("YYYYMMDD"),e["endDate"]=D()(this.selectedDateRange.endDate).format("YYYYMMDD")),this.hasWorkStatSelector&&(e["workStat"]=this.selectedWorkStatCode),this.$emit("filtering",e)},onProcessChange:function(){this.selectEqmt="",""===this.selectProcs?this.$store.dispatch("common/equipment/clear",{stateName:"equipment"}):this.$store.dispatch("common/equipment/query",{queryString:"?procs_id="+this.selectProcs})},onUpdateDateRange:function(e){console.log("this.onUpdateDateRange",e),this.$emit("changedDate",e)},onCheckboxChange:function(){var e="";this.list.forEach((function(t){t.bool&&(e.length>0&&(e+=","),e+=t.value)}))}}},R=y,x=(a("9808"),Object(p["a"])(R,g,b,!1,null,"0b24c8fe",null)),C=x.exports,P=a("7ec5"),S=a("6e4b"),q=a("2b90"),w={name:"GasRiskProposition",components:{Pagination:q["a"],RiskStepLabel:S["a"],RiskLabel:P["a"],PanelProcessEquipmentFiltering:C,Tab:h,PageTitle:f["a"],PanelTitle:r["a"]},computed:Object(i["a"])(Object(i["a"])({},Object(o["c"])(["gasRiskProposalList"])),{},{total:function(){return this.gasRiskProposalList.totalcount?"총 "+this.gasRiskProposalList.totalcount+" 건":""}}),created:function(){this.queryList()},data:function(){return{dayjs:D.a,page:{index:1,size:20},filtering:{startDate:D()().startOf("month").format("YYYYMMDD"),endDate:D()().endOf("month").format("YYYYMMDD"),processId:"",equipmentId:""},tabItems:[{tab_id:"alarm_record",tab_nm:"알림 이력"},{tab_id:"block_record",tab_nm:"차단 이력"}]}},methods:{queryList:function(){var e="?pagesize="+this.page.size+"&pageindex="+this.page.index;e+="&strt_props_ymd="+this.filtering.startDate+"&end_props_ymd="+this.filtering.endDate,e+="&procs_id="+this.filtering.processId+"&eqmt_id="+this.filtering.equipmentId,this.$store.dispatch("gasRiskProposalList",{queryString:e})},getNumberByRowOrder:function(e){return this.gasRiskProposalList.totalcount-e+1},onFiltering:function(e){console.log(e),this.filtering=e,this.page.index=1,this.queryList()},onChangePage:function(e){this.page.index=e,this.queryList()},recordTabChanged:function(e){console.log(e)}}},L=w,Y=(a("dab3"),Object(p["a"])(L,s,n,!1,null,"3bc817bc",null));t["default"]=Y.exports},"7ec5":function(e,t,a){"use strict";var s=function(){var e=this,t=e.$createElement,a=e._self._c||t;return e.validated?a("div",{class:{"double-row":!e.isDoubleRow}},[e.showRiskStep?a("RiskStepLabel",{class:{"full-size":e.isDoubleRow},attrs:{"delete-step-suffix":e.isDeleteStepSuffix,name:e.riskName,code:e.riskCode}}):e._e(),e.showRiskLabel?a("div",{staticClass:"risk-label"},[e._v(e._s(e.riskNum+e.riskUnit))]):e._e()],1):a("div",[e._v("-")])},n=[],i=(a("c975"),a("b0c0"),a("a9e3"),a("ac1f"),a("1276"),a("6e4b")),o={name:"RiskLabel",components:{RiskStepLabel:i["a"]},props:{isDoubleRow:Boolean,isDeleteStepSuffix:Boolean,showRiskStep:{type:Boolean,default:!0},showRiskLabel:{type:Boolean,default:!0},name:String,code:String,num:Number,unit:String,textData:String},computed:{validated:function(){return this.textData||this.code},isTextData:function(){return this.textData&&this.textData.indexOf(":")>-1},riskName:function(){var e="";if(this.isTextData)e=this.textData.indexOf(":")>-1?this.textData.split(":")[1]:this.textData;else if(this.name)e=this.name;else if(this.code){var t=this.code.substr(0,2),a=this.code.substr(-1,1);switch(t){case"NR":e="정상";break;case"CR":e="주의";break;case"WR":e="경고";break;case"DG":e="위험";break;default:e="-"}e=e+" "+a+" 단계"}return this.isDeleteStepSuffix&&(e=e.substr(0,2)),e},riskCode:function(){var e;if(this.isTextData){var t=this.textData.split(":")[1].substr(0,2);switch(t){case"정상":e="NR";break;case"주의":e="CR";break;case"경고":e="WR";break;case"위험":e="DG";break;default:e=""}}else e=this.code;return e},riskNum:function(){var e=0;if(this.isTextData){var t=this.textData.split(":");e=Number(t[0])}else e=this.num;return e},riskUnit:function(){return this.unit?this.unit:"%"}},methods:{}},r=o,l=(a("324a"),a("2877")),c=Object(l["a"])(r,s,n,!1,null,"6212cb5a",null);t["a"]=c.exports},"80da":function(e,t,a){},9750:function(e,t,a){"use strict";var s=a("d72f"),n=a.n(s);n.a},9808:function(e,t,a){"use strict";var s=a("2085"),n=a.n(s);n.a},c2a6:function(e,t,a){},d72f:function(e,t,a){},dab3:function(e,t,a){"use strict";var s=a("3a65"),n=a.n(s);n.a}}]);
//# sourceMappingURL=chunk-148e1936.js.map