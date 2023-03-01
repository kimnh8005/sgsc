(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2b4c65fa"],{"07d9":function(t,e,a){"use strict";var s=a("f217"),i=a.n(s);i.a},"1e8c":function(t,e,a){"use strict";var s=a("62bd"),i=a.n(s);i.a},"324a":function(t,e,a){"use strict";var s=a("80da"),i=a.n(s);i.a},"33d8":function(t,e,a){"use strict";a.r(e);var s=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",[s("PageTitle",{attrs:{title:t.processTitle,"has-date-range-picker":""},on:{changedDate:t.onChangedDate}}),s("div",{staticClass:"page-box"},[s("PanelTitle",{attrs:{title:"통합 위험도"}}),s("ChartBox",{attrs:{"raw-data":t.riskProcessHistoryMap,"chart-type":"PROCESS"}}),s("table",{staticClass:"default-table"},[s("thead",[s("tr",[s("th",[t._v("날짜")]),s("th",[t._v("위험/확률")]),t._l(t.riskProcessHistoryMap.cols.slice(1),(function(e,a){return s("th",{key:a},[t._v(" "+t._s(t.getTableHeader(e))+" ")])}))],2)]),s("tbody",[t._l(t.riskProcessHistoryMap.data,(function(e,a){return[s("tr",{key:"row_1_"+a},[s("th",{attrs:{rowspan:"2"}},[t._v(t._s(t.formatter.date(e["date"],"MM/dd")))]),s("th",[t._v("위험단계")]),t._l(t.sortRow(0,e),(function(e,a){return s("th",{key:"col_1_"+a},[e?s("RiskLabel",{attrs:{"text-data":e,showRiskLabel:!1}}):s("span",[t._v("-")])],1)}))],2),s("tr",{key:"row_2_"+a},[s("th",[t._v("통합 위험률")]),t._l(t.sortRow(0,e),(function(e,a){return s("th",{key:"col_2_"+a},[e?s("RiskLabel",{attrs:{"text-data":e,showRiskStep:!1}}):s("span",[t._v("-")])],1)}))],2)]}))],2)])],1),s("div",{staticClass:"page-box"},[s("PanelTitle",{attrs:{title:"설비 별 위험도"}}),s("PanelTab",{attrs:{items:t.equipmentByProcsId.list,"display-prop-name":"eqmt_nm","emit-prop-list":["eqmt_id","eqmt_nm"]},on:{change:t.equipmentTabChanged}}),s("PanelSubTitle",{attrs:{title:t.equipmentTitle},scopedSlots:t._u([{key:"button",fn:function(){return[s("router-link",{attrs:{to:{path:"/analysis/equipment/"+t.selectedEquipmentId,query:{procs_nm:t.processTitle,eqmt_nm:t.equipmentTitle}}}},[t._v(" 자세히 보기 → ")])]},proxy:!0}])}),s("ChartBox",{attrs:{"raw-data":t.riskEquipmentHistoryMap,"chart-type":"EQUIPMENT"}}),s("table",{staticClass:"default-table"},[s("thead",[s("tr",[s("th",[t._v("날짜")]),s("th",[t._v("위험/확률")]),t._l(t.riskEquipmentHistoryMap.cols.slice(1),(function(e,a){return s("th",{key:a},[t._v(" "+t._s(t.getTableHeader(e))+" ")])}))],2)]),s("tbody",[t._l(t.riskEquipmentHistoryMap.data,(function(e,i){return[s("tr",{key:"row_1_"+i},[s("th",{attrs:{rowspan:"3"}},[t._v(t._s(t.formatter.date(e["date"],"MM/dd")))]),s("th",[t._v("위험단계")]),t._l(t.sortRow(1,e),(function(e,a){return s("th",{key:"col_1_"+a},[e?s("RiskLabel",{attrs:{"text-data":e,showRiskLabel:!1}}):s("span",{staticClass:"font-weight-black"},[t._v("-")])],1)}))],2),s("tr",{key:"row_2_"+i},[s("th",[t._v("통합 위험률")]),t._l(t.sortRow(1,e),(function(e,a){return s("th",{key:"col_2_"+a},[e?s("RiskLabel",{attrs:{"text-data":e,showRiskStep:!1}}):s("span",[t._v("-")])],1)}))],2),s("tr",{key:"row_3_"+i},[s("th",[t._v("사고 시나리오")]),t._l(t.sortRow(1,e),(function(e,i){return s("td",{key:"col_3_"+i},[e?s("button",{staticClass:"outline-dark"},[t._v(" 상세보기 "),s("img",{attrs:{src:a("92fe"),alt:"상세보기"}})]):s("span",[t._v("-")])])}))],2)]}))],2)])],1)],1)},i=[],r=(a("cb29"),a("c975"),a("5530")),n=a("c6f9"),o=a("2f62"),c=a("25b9"),u=a("a321"),l=a("7ec5"),h=a("4ba1"),d=a("7789"),f=a("3b51"),p=a("5a0c"),m=a.n(p),b={name:"RiskPredictionProcessDetail",components:{PanelSubTitle:f["a"],PanelTab:d["a"],RiskLabel:l["a"],ChartBox:u["a"],PageTitle:c["a"],PanelTitle:n["a"]},props:{procs_id:String,procs_nm:String},computed:Object(r["a"])(Object(r["a"])({},Object(o["c"])(["riskProcessHistoryMap","equipmentByProcsId","riskEquipmentHistoryMap"])),{},{processTitle:function(){return this.$route.query.procs_nm?this.$route.query.procs_nm:"공정 상세 정보"},equipmentTitle:function(){return this.selectedEquipmentName?this.selectedEquipmentName+" 위험도":"-"}}),created:function(){this.queryRiskProcessHistoryMap()},data:function(){return{formatter:h["a"],selectedEquipmentId:"",selectedEquipmentName:"",selectedDate:{startDate:m()().format("YYYYMMDD"),endDate:m()().format("YYYYMMDD")}}},methods:{queryRiskProcessHistoryMap:function(){var t=this;this.$store.dispatch("riskProcessHistoryMap",{queryString:"?strt_anys_ymd="+this.selectedDate.startDate+"&end_anys_ymd="+this.selectedDate.endDate+"&procs_id="+this.procs_id}),this.$store.dispatch("equipmentByProcsId",{queryString:"?procs_id="+this.procs_id}).then((function(){var e={eqmt_id:t.equipmentByProcsId.list[0].eqmt_id,eqmt_nm:t.equipmentByProcsId.list[0].eqmt_nm};t.equipmentTabChanged(e)}))},getTableHeader:function(t){return"date"===t?"날짜":t+"시"},sortRow:function(t,e){var a=[];a=0===t?this.riskProcessHistoryMap.cols:this.riskEquipmentHistoryMap.cols;var s=a.indexOf("date"),i=new Array(a.length-1).fill(null);for(var r in e)if("date"!=r){var n=a.indexOf(r);n<s?i[n]=e[r]:i[n-1]=e[r]}return i},equipmentTabChanged:function(t){console.log(t.eqmt_nm),this.selectedEquipmentId=t.eqmt_id,this.selectedEquipmentName=t.eqmt_nm,this.$store.dispatch("riskEquipmentHistoryMap",{queryString:"?strt_anys_ymd="+this.selectedDate.startDate+"&end_anys_ymd="+this.selectedDate.endDate+"&eqmt_id="+t.eqmt_id})},onChangedDate:function(t){console.log(t,"onChangedDate<<<<"),this.selectedDate.startDate=m()(t.startDate).format("YYYYMMDD"),this.selectedDate.endDate=m()(t.endDate).format("YYYYMMDD"),this.queryRiskProcessHistoryMap()}}},_=b,g=(a("a047"),a("2877")),k=Object(g["a"])(_,s,i,!1,null,"2ca97ca0",null);e["default"]=k.exports},"3b51":function(t,e,a){"use strict";var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"panel-title"},[a("h5",[t._v(t._s(t.title))]),t.desc?a("div",{staticClass:"title-desc"},[t._v(t._s(t.desc))]):t._e(),a("div",{staticClass:"filler"}),t._t("button")],2)},i=[],r={name:"PanelSubTitle",props:{title:String,desc:String}},n=r,o=(a("07d9"),a("2877")),c=Object(o["a"])(n,s,i,!1,null,"e6519142",null);e["a"]=c.exports},"4ba1":function(t,e,a){"use strict";a("ac1f"),a("1276");var s={date:function(t,e){if(!t)return"";var a=t.substr(0,4),i=t.substr(4,2),r=t.substr(6,2),n=t.substr(8,2),o=t.substr(10,2),c=t.substr(12,2),u=""===n||""===o?"":" "+n+":"+o;if(u=""===c?u:" "+u+":"+c,"MM/dd~MM/dd"===e){var l=t.split("~");return s.date(l[0],"MM/dd")+"~"+s.date(l[1],"MM/dd")}return"MM/dd"===e?i+"/"+r:"MM/dd hh:mm"===e?i+"/"+r+" "+n+":"+o:a+"-"+i+"-"+r+u}};e["a"]=s},"62bd":function(t,e,a){},7789:function(t,e,a){"use strict";var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("ul",{staticClass:"tab"},[t._l(t.items,(function(e,s){return a("li",{key:"filled_"+s,class:{active:t.index===s},on:{click:function(a){return t.onClick(s,e)}}},[t._v(" "+t._s(e[t.displayPropName])+" ")])})),t._l(t.getBlankNum(t.items),(function(t,e){return a("li",{key:"empty_"+e,staticClass:"blank-item"})}))],2)},i=[],r=(a("4160"),a("a9e3"),a("159b"),{name:"PanelTab",props:{items:Array,displayPropName:String,numsInRow:{type:Number,default:5},emitPropList:{type:Array,required:!0}},computed:{cssVars:function(){return{"--numsInRow":this.numsInRow}}},data:function(){return{index:0}},methods:{onClick:function(t,e){this.index=t;var a={};this.emitPropList&&this.emitPropList.length?this.emitPropList.forEach((function(t){a[t]=e[t]})):a[this.displayPropName]=e[this.displayPropName],this.$emit("change",a)},getBlankNum:function(t){var e=0;return t&&t.length&&(e=(this.numsInRow-t.length%this.numsInRow)%this.numsInRow),e}}}),n=r,o=(a("1e8c"),a("2877")),c=Object(o["a"])(n,s,i,!1,null,"1940b404",null);e["a"]=c.exports},"7ec5":function(t,e,a){"use strict";var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return t.validated?a("div",{class:{"double-row":!t.isDoubleRow}},[t.showRiskStep?a("RiskStepLabel",{class:{"full-size":t.isDoubleRow},attrs:{"delete-step-suffix":t.isDeleteStepSuffix,name:t.riskName,code:t.riskCode}}):t._e(),t.showRiskLabel?a("div",{staticClass:"risk-label"},[t._v(t._s(t.riskNum+t.riskUnit))]):t._e()],1):a("div",[t._v("-")])},i=[],r=(a("c975"),a("b0c0"),a("a9e3"),a("ac1f"),a("1276"),a("6e4b")),n={name:"RiskLabel",components:{RiskStepLabel:r["a"]},props:{isDoubleRow:Boolean,isDeleteStepSuffix:Boolean,showRiskStep:{type:Boolean,default:!0},showRiskLabel:{type:Boolean,default:!0},name:String,code:String,num:Number,unit:String,textData:String},computed:{validated:function(){return this.textData||this.code},isTextData:function(){return this.textData&&this.textData.indexOf(":")>-1},riskName:function(){var t="";if(this.isTextData)t=this.textData.indexOf(":")>-1?this.textData.split(":")[1]:this.textData;else if(this.name)t=this.name;else if(this.code){var e=this.code.substr(0,2),a=this.code.substr(-1,1);switch(e){case"NR":t="정상";break;case"CR":t="주의";break;case"WR":t="경고";break;case"DG":t="위험";break;default:t="-"}t=t+" "+a+" 단계"}return this.isDeleteStepSuffix&&(t=t.substr(0,2)),t},riskCode:function(){var t;if(this.isTextData){var e=this.textData.split(":")[1].substr(0,2);switch(e){case"정상":t="NR";break;case"주의":t="CR";break;case"경고":t="WR";break;case"위험":t="DG";break;default:t=""}}else t=this.code;return t},riskNum:function(){var t=0;if(this.isTextData){var e=this.textData.split(":");t=Number(e[0])}else t=this.num;return t},riskUnit:function(){return this.unit?this.unit:"%"}},methods:{}},o=n,c=(a("324a"),a("2877")),u=Object(c["a"])(o,s,i,!1,null,"6212cb5a",null);e["a"]=u.exports},"80da":function(t,e,a){},"81d5":function(t,e,a){"use strict";var s=a("7b0b"),i=a("23cb"),r=a("50c4");t.exports=function(t){var e=s(this),a=r(e.length),n=arguments.length,o=i(n>1?arguments[1]:void 0,a),c=n>2?arguments[2]:void 0,u=void 0===c?a:i(c,a);while(u>o)e[o++]=t;return e}},"99af":function(t,e,a){"use strict";var s=a("23e7"),i=a("d039"),r=a("e8b5"),n=a("861d"),o=a("7b0b"),c=a("50c4"),u=a("8418"),l=a("65f0"),h=a("1dde"),d=a("b622"),f=a("2d00"),p=d("isConcatSpreadable"),m=9007199254740991,b="Maximum allowed index exceeded",_=f>=51||!i((function(){var t=[];return t[p]=!1,t.concat()[0]!==t})),g=h("concat"),k=function(t){if(!n(t))return!1;var e=t[p];return void 0!==e?!!e:r(t)},v=!_||!g;s({target:"Array",proto:!0,forced:v},{concat:function(t){var e,a,s,i,r,n=o(this),h=l(n,0),d=0;for(e=-1,s=arguments.length;e<s;e++)if(r=-1===e?n:arguments[e],k(r)){if(i=c(r.length),d+i>m)throw TypeError(b);for(a=0;a<i;a++,d++)a in r&&u(h,d,r[a])}else{if(d>=m)throw TypeError(b);u(h,d++,r)}return h.length=d,h}})},a047:function(t,e,a){"use strict";var s=a("ea47"),i=a.n(s);i.a},a11a:function(t,e,a){"use strict";var s=a("a8fd"),i=a.n(s);i.a},a321:function(t,e,a){"use strict";var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"chart-container",class:{"main-type":"MAIN"===t.chartType}},[a("highcharts",{ref:"highcharts",attrs:{options:t.chartOptions}})],1)},i=[],r=(a("99af"),a("4160"),a("a9e3"),a("ac1f"),a("1276"),a("159b"),a("4452")),n=a("4ba1"),o={name:"ChartBox",components:{highcharts:r["Chart"]},props:{chartType:String,rawData:null},watch:{seriesData:{handler:function(){var t=this;while(this.chart.series.length>0)this.chart.series[0].remove(!0);this.seriesData.forEach((function(e){t.chart.addSeries(e)})),this.chart.xAxis[0].setCategories(this.categoryData)},deep:!0}},computed:{seriesData:function(){var t=null;switch(this.chartType){case"MAIN":t=this.getMainList();break;case"PREDICTION24HOUR":t=this.getPrediction24HourList();break;case"PREDICTION7DAY":t=this.getPrediction7DayList();break;case"PREDICTION4WEEK":t=this.getPrediction4WeekList();break;case"PROCESS":t=this.getProcessList();break;case"EQUIPMENT":t=this.getProcessList();break;default:return[]}return t},categoryData:function(){var t=null;switch(this.chartType){case"MAIN":t=this.getHourCategories();break;case"PREDICTION24HOUR":t=this.getHourCategories();break;case"PREDICTION7DAY":t=this.getDayCategories();break;case"PREDICTION4WEEK":t=this.getWeekCategories();break;case"PROCESS":t=this.getProcessCategories();break;case"EQUIPMENT":t=this.getProcessCategories();break;default:return[]}return t}},mounted:function(){this.chart=this.$refs.highcharts.chart},data:function(){return{chart:null,chartOptions:{chart:{type:"area",height:this.getChartHeight(),animation:{duration:1e3}},title:!1,legend:this.getLegendConfig(),xAxis:{categories:[]},yAxis:{title:!1,labels:{formatter:function(){var t={0:"",25:"정상",50:"주의",75:"경고",100:"위험"},e={0:"transparent",25:"#2d969b",50:"#f06c00",75:"#ff0000",100:"#a40000"};return'<span style="color: '.concat(e[this.value],'">').concat(t[this.value],"</span>")},y:28,x:-40,style:{color:"#666666",fontSize:15,fontWeight:"bold"}},max:100,tickAmount:5,tickWidth:1,tickLength:100},plotOptions:{series:{marker:{fillColor:"#FFFFFF",lineWidth:2,lineColor:null}},area:{pointPlacement:"on"}},credits:{enabled:!1}}}},methods:{getProcessCategories:function(){var t=this,e=[];return this.rawData.data.forEach((function(a){t.rawData.cols.forEach((function(t){if("date"!==t){var s=a[t];s?e.push(t+"시"):e.push("")}}))})),e},getWeekCategories:function(){var t=[];return this.rawData.forEach((function(e){t.push(n["a"].date(e.risk_dt,"MM/dd~MM/dd"))})),t},getDayCategories:function(){var t=[];return this.rawData.forEach((function(e){t.push(n["a"].date(e.ymd,"MM/dd"))})),t},getHourCategories:function(){var t=[];return this.rawData.forEach((function(e){t.push(e.qtime+"시")})),t},getChartHeight:function(){return"MAIN"===this.chartType?250:230},getLegendConfig:function(){return"MAIN"===this.chartType&&{layout:"horizontal",align:"left",verticalAlign:"top",x:50}},getProcessList:function(){var t=[{name:"통합 위험률(%)",data:[],color:"#f58fae"}],e=this.rawData.cols;return this.rawData.data.forEach((function(a){e.forEach((function(e){if("date"!==e){var s=a[e];s=s?Number(s.split(":")[0]):null,t[0].data.push(s)}}))})),t},getPrediction4WeekList:function(){var t=[{name:"4주 예측",data:[],color:"#f58fae"}];return this.rawData.forEach((function(e){t[0].data.push(Number(e.risk_rate.split(":")[0]))})),t},getPrediction7DayList:function(){var t=[{name:"7일 예측",data:[],color:"#f58fae"}];return this.rawData.forEach((function(e){t[0].data.push(Number(e.risk_rate.split(":")[0]))})),t},getPrediction24HourList:function(){var t=[{name:"통합 위험률(%)",data:[],color:"#f58fae"}];return this.rawData.forEach((function(e){t[0].data.push(Number(e.risk_rate.split(":")[0]))})),t},getMainList:function(){var t=[{name:"발생확률(%)",data:[],color:"#f58fae"}];return this.rawData.forEach((function(e){t[0].data.push(Number(e.CRIG101.split(":")[0]))})),t}}},c=o,u=(a("a11a"),a("2877")),l=Object(u["a"])(c,s,i,!1,null,"818736b2",null);e["a"]=l.exports},a8fd:function(t,e,a){},cb29:function(t,e,a){var s=a("23e7"),i=a("81d5"),r=a("44d2");s({target:"Array",proto:!0},{fill:i}),r("fill")},ea47:function(t,e,a){},f217:function(t,e,a){}}]);
//# sourceMappingURL=chunk-2b4c65fa.js.map