(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-f7bc50fc"],{"031d":function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"filter-container"},[a("h4",{staticClass:"title"},[t._v(t._s(t.processTitle))]),a("select",{directives:[{name:"model",rawName:"v-model",value:t.selectProcs,expression:"selectProcs"}],on:{change:[function(e){var a=Array.prototype.filter.call(e.target.options,(function(t){return t.selected})).map((function(t){var e="_value"in t?t._value:t.value;return e}));t.selectProcs=e.target.multiple?a:a[0]},t.onProcessChange]}},[a("option",{attrs:{value:""}},[t._v("전체 생산공정")]),t._l(t.process,(function(e){return a("option",{key:e.procs_id,domProps:{value:e.procs_id}},[t._v(" "+t._s(e.procs_nm)+" ")])}))],2),a("h4",{staticClass:"title",class:{"blank-title":null===t.equipmentTitle}},[t._v(t._s(t.equipmentTitle))]),a("select",{directives:[{name:"model",rawName:"v-model",value:t.selectEqmt,expression:"selectEqmt"}],on:{change:function(e){var a=Array.prototype.filter.call(e.target.options,(function(t){return t.selected})).map((function(t){var e="_value"in t?t._value:t.value;return e}));t.selectEqmt=e.target.multiple?a:a[0]}}},[a("option",{attrs:{value:""}},[t._v("전체 설비")]),t._l(t.equipment,(function(e){return a("option",{key:e.eqmt_id,domProps:{value:e.eqmt_id}},[t._v(" "+t._s(e.eqmt_nm)+" ")])}))],2),t.hasDateRangePicker?[a("h4",{staticClass:"title"},[t._v(t._s(t.datepickerTitle))]),a("div",{staticClass:"datepicker-container"},[t._m(0),a("DateRangePicker",{ref:"picker",attrs:{opens:t.dateRange.opens,"locale-data":t.dateRange.localeData,showDropdowns:t.dateRange.showDropdowns,autoApply:t.dateRange.autoApply,ranges:t.dateRange.showRange},on:{update:t.onUpdateDateRange},scopedSlots:t._u([{key:"input",fn:function(e){return[t._v(" "+t._s(t._f("date")(e.startDate))+" - "+t._s(t._f("date")(e.endDate))+" ")]}}],null,!1,3375447491),model:{value:t.selectedDateRange,callback:function(e){t.selectedDateRange=e},expression:"selectedDateRange"}})],1)]:t._e(),t.hasLeakageCheck?[a("h4",{staticClass:"title"},[t._v("누출위험")]),t._l(t.checkboxList,(function(e){return a("label",{key:e.value},[a("input",{directives:[{name:"model",rawName:"v-model",value:e.bool,expression:"item.bool"}],attrs:{type:"checkbox"},domProps:{checked:Array.isArray(e.bool)?t._i(e.bool,null)>-1:e.bool},on:{change:[function(a){var i=e.bool,n=a.target,s=!!n.checked;if(Array.isArray(i)){var o=null,r=t._i(i,o);n.checked?r<0&&t.$set(e,"bool",i.concat([o])):r>-1&&t.$set(e,"bool",i.slice(0,r).concat(i.slice(r+1)))}else t.$set(e,"bool",s)},t.onCheckboxChange]}}),t._v(" "+t._s(e.name)+" ")])}))]:t._e(),t.hasWorkStatSelector?[a("h4",{staticClass:"title"},[t._v("상태")]),a("select",{directives:[{name:"model",rawName:"v-model",value:t.selectedWorkStatCode,expression:"selectedWorkStatCode"}],on:{change:function(e){var a=Array.prototype.filter.call(e.target.options,(function(t){return t.selected})).map((function(t){var e="_value"in t?t._value:t.value;return e}));t.selectedWorkStatCode=e.target.multiple?a:a[0]}}},[a("option",{attrs:{value:""}},[t._v("전체")]),t._l(t.WORK_STAT_CD,(function(t){return a("option",{key:t.cd_id,attrs:{label:t.cd_nm},domProps:{value:t.cd_id}})}))],2)]:t._e(),a("button",{attrs:{disabled:t.requireSelectEquipment&&""===t.selectEqmt},on:{click:t.onClick}},[t._v("검색")])],2)},n=[function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"icon"},[i("img",{attrs:{src:a("7cb0"),alt:"calender icon"}})])}],s=(a("4160"),a("159b"),a("5530")),o=a("2f62"),r=a("bbf5"),l=a.n(r),c=(a("4b7f"),a("5a0c")),u=a.n(c),d={name:"ProcessEquipmentFiltering",components:{DateRangePicker:l.a},props:{requireSelectEquipment:Boolean,hasDateRangePicker:Boolean,hasLeakageCheck:Boolean,hasWorkStatSelector:Boolean,startDate:{type:String},endDate:{type:String},processTitle:{type:String,default:"생산공정"},equipmentTitle:{type:String,default:"설 비"},datepickerTitle:{type:String,default:"날 짜"}},computed:Object(s["a"])(Object(s["a"])(Object(s["a"])(Object(s["a"])({},Object(o["c"])("common/process",["process"])),Object(o["c"])("common/equipment",["equipment"])),Object(o["c"])("common/code",["WORK_STAT_CD"])),{},{activeEquipmentId:function(){return this.activeEquipId}}),data:function(){var t=this.startDate?u()(this.startDate).format("YYYY-MM-DD"):u()().format("YYYY-MM-DD"),e=this.endDate?u()(this.endDate).format("YYYY-MM-DD"):u()().format("YYYY-MM-DD");return{selectProcs:"",selectEqmt:"",dayjs:u.a,selectedDateRange:{startDate:t,endDate:e},dateRange:{today:new Date,opens:"left",showDropdowns:!1,showRange:!1,localeData:{direction:"ltr",format:"yyyy-mm-dd",separator:" ~ ",applyLabel:"적용",cancelLabel:"취소",weekLabel:"W",customRangeLabel:"Custom Range",daysOfWeek:["일","월","화","수","목","금","토"],monthNames:["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],firstDay:0}},checkboxList:[{name:"ON",value:"on",bool:!0},{name:"OFF",value:"off",bool:!0}],selectedWorkStatCode:""}},created:function(){this.$store.dispatch("common/process/query",{queryString:""}),this.hasWorkStatSelector&&this.$store.dispatch("common/code/query",{code:"WORK_STAT_CD"})},filters:{date:function(t){return t?u()(t).format("YYYY-MM-DD"):""}},methods:{onClick:function(){var t={processId:this.selectProcs,equipmentId:this.selectEqmt};this.hasDateRangePicker&&(t["startDate"]=u()(this.selectedDateRange.startDate).format("YYYYMMDD"),t["endDate"]=u()(this.selectedDateRange.endDate).format("YYYYMMDD")),this.hasWorkStatSelector&&(t["workStat"]=this.selectedWorkStatCode),this.$emit("filtering",t)},onProcessChange:function(){this.selectEqmt="",""===this.selectProcs?this.$store.dispatch("common/equipment/clear",{stateName:"equipment"}):this.$store.dispatch("common/equipment/query",{queryString:"?procs_id="+this.selectProcs})},onUpdateDateRange:function(t){console.log("this.onUpdateDateRange",t),this.$emit("changedDate",t)},onCheckboxChange:function(){var t="";this.list.forEach((function(e){e.bool&&(t.length>0&&(t+=","),t+=e.value)}))}}},p=d,f=(a("b9d5"),a("2877")),m=Object(f["a"])(p,i,n,!1,null,"1496b39a",null);e["a"]=m.exports},"10a3":function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"screen-bg"},[i("div",{staticClass:"viewer-container"},[i("div",{staticClass:"viewer-header"},[i("h2",[i("span",[t._v(t._s(t.title))]),null!==t.subtitle?i("span",{staticClass:"caret-right"},[i("img",{attrs:{src:a("92fe"),alt:"caret right"}})]):t._e(),i("span",[t._v(t._s(t.subtitle))])]),i("div",{staticClass:"close-button",on:{click:t.onClickClose}})]),i("div",{staticClass:"info"},[i("div",{staticClass:"update-time"},[t._v(" "+t._s(t.baseDateTime)+" ")]),i("div",{staticClass:"warning"},[t._v(" 설비 위치 이동 발생 ")])]),i("div",{staticClass:"viewer-body"},[i("div",{staticClass:"photo"},[i("iframe",{attrs:{src:t.mapUrl}})]),i("div",[i("table",{staticClass:"view-table"},[i("tbody",[t._m(0),i("tr",[i("td",[t._v("경도 / 위도")]),i("td",[t._v(t._s(t.infoData.baseX+" / "+t.infoData.baseY))])])])]),i("table",{staticClass:"view-table"},[i("tbody",[t._m(1),t._m(2),i("tr",[i("td",[t._v("주변온도(ambientTemper)")]),i("td",[t._v(t._s(t.infoData.t1h))])]),i("tr",[i("td",[t._v("풍향(windDirect)")]),i("td",[t._v(t._s(t.infoData.wsd))])]),i("tr",[i("td",[t._v("풍속(windSpeed)")]),i("td",[t._v(t._s(t.infoData.vec))])]),t._m(3),t._m(4),t._m(5),i("tr",[i("td",[t._v("대기확산모델(model)")]),i("td",[t._v(t._s(t.infoData.model))])])])]),i("table",{staticClass:"view-table"},[i("tbody",[i("tr",[i("th",{attrs:{rowspan:"12"}},[t._v("세부 사항")]),i("td",[t._v("시설물 ID")]),i("td",[t._v(t._s(t.infoData.gasFacId))])]),i("tr",[i("td",[t._v("가스확산분석 실행시간")]),i("td",[t._v(t._s(t.infoData.execTime))])]),i("tr",[i("td",[t._v("가스확산 분석 진행시간 (PUFF) [분]")]),i("td",[t._v(t._s(t.infoData.simulTime))])]),i("tr",[i("td",[t._v("가스확산 분석 진행시간 분할 [초]")]),i("td",[t._v(t._s(t.infoData.simulTimePer))])]),i("tr",[i("td",[t._v("대기 안정도")]),i("td",[t._v(t._s(t.infoData.stability))])]),i("tr",[i("td",[t._v("누출 가스 몰질량")]),i("td",[t._v(t._s(t.infoData.moleWeight))])]),i("tr",[i("td",[t._v("누출지점 높이")]),i("td",[t._v(t._s(t.infoData.leakHeight))])]),i("tr",[i("td",[t._v("총누출량(PUFF) [kg] / 초당 누출량(PLUME) [kg/s]")]),i("td",[t._v(t._s(t.infoData.totalReleaseAmount))])]),i("tr",[i("td",[t._v("최대 검측 PPM")]),i("td",[t._v(t._s(t.infoData.maxPpm))])]),i("tr",[i("td",[t._v("최소 검측 PPM")]),i("td",[t._v(t._s(t.infoData.minPpm))])]),i("tr",[i("td",[t._v("분할 PPM")]),i("td",[t._v(t._s(t.infoData.ppmPer))])]),i("tr",[i("td",[t._v("날씨 기준위치")]),i("td",[t._v(t._s(t.infoData.kmaName))])])])])])])])])},n=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("tr",[a("th",{attrs:{rowspan:"2"}},[t._v("발생지점")]),a("td",[t._v("발생지점 주소")]),a("td")])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("tr",[a("th",{attrs:{rowspan:"9"}},[t._v("시뮬레이션 조건")]),a("td",[t._v("가스종류(gasType)")]),a("td")])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("tr",[a("td",[t._v("주변압력(ambientPress)")]),a("td")])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("tr",[a("td",[t._v("대기기압(atmosPress)")]),a("td")])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("tr",[a("td",[t._v("대기기온(atmosTemper)")]),a("td")])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("tr",[a("td",[t._v("대기습도(Humidity)")]),a("td")])}],s=a("bc3a"),o=a.n(s),r={name:"AerialPhotoViewer",props:{procs_nm:{type:String},eqmt_nm:{type:String},eqmt_id:{type:String,default:""},hasDetail:{type:Boolean},displayData:Object},computed:{mapUrl:function(){var t,e="http://118.129.135.145:2122/analysis/guest/gasFacInfo.do?gasFacId=";return t=this.eqmt_id?e+this.eqmt_id:this.displayData.gasFacId?"http://118.129.135.145:2122/analysis/guest/gasFacInfo.do?gasFacId="+this.displayData.gasFacId:this.displayData.diffsMapUrl,t},baseDateTime:function(){var t=null===this.infoData.baseDate||null===this.infoData.baseTime||""===this.infoData.baseDate&&""===this.infoData.baseTime;return t?"기준 날짜와 시간 데이터가 없습니다.":this.infoData.baseDate+" "+this.infoData.baseTime},title:function(){return this.procs_nm?this.procs_nm:this.displayData?this.displayData.gasFacId?this.displayData.gasFacId:"설비명 없음":null},subtitle:function(){return this.eqmt_nm?this.eqmt_nm:this.eqmt_id?this.eqmt_id:null}},watch:{displayData:{immediate:!0,deep:!0,handler:function(){console.log("watch displayData: ",this.displayData),this.loadInfo(this.displayData.diffsInfoUrl)}}},created:function(){this.loadInfo()},data:function(){return{infoData:{baseDate:null,baseTime:null,baseX:null,baseY:null,execTime:null,fromExecTime:null,gasFacId:null,gasSimulSn:null,heatCapacityRatio:null,kmaName:null,leakHeight:null,maxPpm:null,minPpm:null,model:null,moleWeight:null,ppmPer:null,resultCode:null,simulTime:null,simulTimePer:null,sisulSn:null,stability:null,t1h:null,toExecTime:null,totalReleaseAmount:null,vec:null,wsd:null}}},methods:{queryDetail:function(){},onClickClose:function(){this.$emit("close")},loadInfo:function(t){var e=this;o.a.get(t).then((function(t){return"Success"===t.data.code&&(e.infoData=t.data.responseData,console.log("info data: ",e.infoData)),t})).catch((function(t){console.log("catch error: ",t)}))}}},l=r,c=(a("9a86"),a("2877")),u=Object(c["a"])(l,i,n,!1,null,"479a885a",null);e["a"]=u.exports},"21de":function(t,e,a){},"2b90":function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("ul",{staticClass:"pagination"},[a("li",{class:{disabled:1===t.pageIndex||0===t.totalPages},on:{click:t.prev}},[t._v(" < 이전 ")]),t._l(t.pageList,(function(e,i){return a("li",{key:i,class:{active:t.pageIndex===e},on:{click:function(a){return t.onChange(e)}}},[t._v(" "+t._s(e)+" ")])})),a("li",{class:{disabled:t.pageIndex===t.totalPages||0===t.totalPages},on:{click:t.next}},[t._v("다음 >")])],2)},n=[],s=(a("a9e3"),{name:"Pagination",props:{totalCount:{type:Number,default:0},maxDisplayPageCount:{type:Number,default:5},pageIndex:{type:Number,default:1},pageSize:{type:Number,default:100}},computed:{totalPages:function(){return Math.ceil(this.totalCount/this.pageSize)},pageCount:function(){return this.totalPages>this.maxDisplayPageCount?this.maxDisplayPageCount:this.totalPages},pageList:function(){var t=this.pageIndex>3?this.pageIndex-2:1,e=this.totalPages-this.pageIndex;t=this.totalPages>this.maxDisplayPageCount&&e<3?t-(this.maxDisplayPageCount-e-3):t;for(var a=[],i=0;i<this.pageCount;++i)a.push(t+i);return a}},methods:{onChange:function(t){this.pageIndex!==t&&this.$emit("change",t)},prev:function(){1!==this.pageIndex&&this.onChange(this.pageIndex-1)},next:function(){this.pageIndex!==this.totalPages&&this.onChange(this.pageIndex+1)}}}),o=s,r=(a("9750"),a("2877")),l=Object(r["a"])(o,i,n,!1,null,"119af7fa",null);e["a"]=l.exports},"2db9":function(t,e,a){},"43bd":function(t,e,a){"use strict";var i=a("21de"),n=a.n(i);n.a},"4c22":function(t,e,a){},5899:function(t,e){t.exports="\t\n\v\f\r                　\u2028\u2029\ufeff"},"58a8":function(t,e,a){var i=a("1d80"),n=a("5899"),s="["+n+"]",o=RegExp("^"+s+s+"*"),r=RegExp(s+s+"*$"),l=function(t){return function(e){var a=String(i(e));return 1&t&&(a=a.replace(o,"")),2&t&&(a=a.replace(r,"")),a}};t.exports={start:l(1),end:l(2),trim:l(3)}},7156:function(t,e,a){var i=a("861d"),n=a("d2bb");t.exports=function(t,e,a){var s,o;return n&&"function"==typeof(s=e.constructor)&&s!==a&&i(o=s.prototype)&&o!==a.prototype&&n(t,o),t}},9750:function(t,e,a){"use strict";var i=a("d72f"),n=a.n(i);n.a},"9a86":function(t,e,a){"use strict";var i=a("2db9"),n=a.n(i);n.a},a9e3:function(t,e,a){"use strict";var i=a("83ab"),n=a("da84"),s=a("94ca"),o=a("6eeb"),r=a("5135"),l=a("c6b6"),c=a("7156"),u=a("c04e"),d=a("d039"),p=a("7c73"),f=a("241c").f,m=a("06cf").f,h=a("9bf2").f,_=a("58a8").trim,v="Number",g=n[v],D=g.prototype,b=l(p(D))==v,y=function(t){var e,a,i,n,s,o,r,l,c=u(t,!1);if("string"==typeof c&&c.length>2)if(c=_(c),e=c.charCodeAt(0),43===e||45===e){if(a=c.charCodeAt(2),88===a||120===a)return NaN}else if(48===e){switch(c.charCodeAt(1)){case 66:case 98:i=2,n=49;break;case 79:case 111:i=8,n=55;break;default:return+c}for(s=c.slice(2),o=s.length,r=0;r<o;r++)if(l=s.charCodeAt(r),l<48||l>n)return NaN;return parseInt(s,i)}return+c};if(s(v,!g(" 0o1")||!g("0b1")||g("+0x1"))){for(var C,P=function(t){var e=arguments.length<1?0:t,a=this;return a instanceof P&&(b?d((function(){D.valueOf.call(a)})):l(a)!=v)?c(new g(y(e)),a,P):y(e)},k=i?f(g):"MAX_VALUE,MIN_VALUE,NaN,NEGATIVE_INFINITY,POSITIVE_INFINITY,EPSILON,isFinite,isInteger,isNaN,isSafeInteger,MAX_SAFE_INTEGER,MIN_SAFE_INTEGER,parseFloat,parseInt,isInteger".split(","),I=0;k.length>I;I++)r(g,C=k[I])&&!r(P,C)&&h(P,C,m(g,C));P.prototype=D,D.constructor=P,o(n,v,P)}},b9d5:function(t,e,a){"use strict";var i=a("4c22"),n=a.n(i);n.a},d72f:function(t,e,a){},ef56:function(t,e,a){"use strict";a.r(e);var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("PageTitle"),a("div",{staticClass:"page-box filtering"},[a("ProcessEquipmentFiltering",{attrs:{"has-date-range-picker":"","process-title":"검 색","equipment-title":null},on:{filtering:t.onFiltering}})],1),a("div",{staticClass:"page-box"},[a("div",{staticClass:"simulation-list"},t._l(t.pagedList,(function(e,i){return a("div",{key:i,staticClass:"simulation-item",on:{click:function(a){return t.showAerialPhoto(e)}}},[a("div",{staticClass:"item-header"},[a("div",{staticClass:"item-info"},[a("h4",[t._v(t._s(e.gasFacId?e.gasFacId:"설비명 없음"))]),a("div",{staticClass:"item-date"},[t._v(t._s(t.dayjs(e.analysisDt).format("YYYY-MM-DD HH:mm:ss")))])]),"D"===e.stability?a("div",{staticClass:"item-badge"},[t._v(" 누출 위험 ")]):t._e()]),a("div",{staticClass:"item-img"},[a("iframe",{attrs:{src:e.diffsMapUrl,frameborder:"0"}}),a("div",{staticClass:"prevent-click-screen"})])])})),0),a("Pagination",{attrs:{"page-index":this.page.index,"total-count":t.gasDiffusionList.length,"page-size":this.page.size},on:{change:t.onChangePage}})],1),t.aerialPhoto.isShow?a("AerialPhotoViewer",{attrs:{"display-data":t.aerialPhoto.data},on:{close:t.hideAerialPhoto}}):t._e()],1)},n=[],s=(a("4de4"),a("5530")),o=a("25b9"),r=a("031d"),l=a("2f62"),c=a("5a0c"),u=a.n(c),d=a("2b90"),p=a("10a3"),f={name:"IncidentSimulation",components:{AerialPhotoViewer:p["a"],Pagination:d["a"],ProcessEquipmentFiltering:r["a"],PageTitle:o["a"]},computed:Object(s["a"])(Object(s["a"])({},Object(l["c"])(["gasDiffusionList"])),{},{pagedList:function(){var t=this;return this.gasDiffusionList.filter((function(e,a){return a>=(t.page.index-1)*t.page.size&&a<t.page.index*t.page.size}))}}),created:function(){this.queryList()},data:function(){return{dayjs:u.a,page:{index:1,size:6},payload:{gasFacId:"",fromAnalyDt:"2020-11-05",toAnalyDt:"2020-11-05"},aerialPhoto:{isShow:!1,data:null}}},methods:{queryList:function(){this.$store.dispatch("gasDiffusionList",this.payload)},onFiltering:function(t){this.payload={gasFacId:t.equipmentId,fromAnalyDt:u()(t.startDate).format("YYYY-MM-DD"),toAnalyDt:u()(t.endDate).format("YYYY-MM-DD")},console.log("onFiltering()",t,this.payload),this.queryList()},onChangePage:function(t){this.page.index=t,this.queryList()},showAerialPhoto:function(t){this.aerialPhoto.data=t,this.aerialPhoto.isShow=!0},hideAerialPhoto:function(){this.aerialPhoto.isShow=!1}}},m=f,h=(a("43bd"),a("2877")),_=Object(h["a"])(m,i,n,!1,null,"2fb0b8cc",null);e["default"]=_.exports}}]);
//# sourceMappingURL=chunk-f7bc50fc.js.map