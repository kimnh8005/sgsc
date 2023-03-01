(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-124c1348"],{"10a3":function(t,e,a){"use strict";var s=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"screen-bg"},[s("div",{staticClass:"viewer-container"},[s("div",{staticClass:"viewer-header"},[s("h2",[s("span",[t._v(t._s(t.title))]),null!==t.subtitle?s("span",{staticClass:"caret-right"},[s("img",{attrs:{src:a("92fe"),alt:"caret right"}})]):t._e(),s("span",[t._v(t._s(t.subtitle))])]),s("div",{staticClass:"close-button",on:{click:t.onClickClose}})]),s("div",{staticClass:"info"},[s("div",{staticClass:"update-time"},[t._v(" "+t._s(t.baseDateTime)+" ")]),s("div",{staticClass:"warning"},[t._v(" 설비 위치 이동 발생 ")])]),s("div",{staticClass:"viewer-body"},[s("div",{staticClass:"photo"},[s("iframe",{attrs:{src:t.mapUrl}})]),s("div",[s("table",{staticClass:"view-table"},[s("tbody",[t._m(0),s("tr",[s("td",[t._v("경도 / 위도")]),s("td",[t._v(t._s(t.infoData.baseX+" / "+t.infoData.baseY))])])])]),s("table",{staticClass:"view-table"},[s("tbody",[t._m(1),t._m(2),s("tr",[s("td",[t._v("주변온도(ambientTemper)")]),s("td",[t._v(t._s(t.infoData.t1h))])]),s("tr",[s("td",[t._v("풍향(windDirect)")]),s("td",[t._v(t._s(t.infoData.wsd))])]),s("tr",[s("td",[t._v("풍속(windSpeed)")]),s("td",[t._v(t._s(t.infoData.vec))])]),t._m(3),t._m(4),t._m(5),s("tr",[s("td",[t._v("대기확산모델(model)")]),s("td",[t._v(t._s(t.infoData.model))])])])]),s("table",{staticClass:"view-table"},[s("tbody",[s("tr",[s("th",{attrs:{rowspan:"12"}},[t._v("세부 사항")]),s("td",[t._v("시설물 ID")]),s("td",[t._v(t._s(t.infoData.gasFacId))])]),s("tr",[s("td",[t._v("가스확산분석 실행시간")]),s("td",[t._v(t._s(t.infoData.execTime))])]),s("tr",[s("td",[t._v("가스확산 분석 진행시간 (PUFF) [분]")]),s("td",[t._v(t._s(t.infoData.simulTime))])]),s("tr",[s("td",[t._v("가스확산 분석 진행시간 분할 [초]")]),s("td",[t._v(t._s(t.infoData.simulTimePer))])]),s("tr",[s("td",[t._v("대기 안정도")]),s("td",[t._v(t._s(t.infoData.stability))])]),s("tr",[s("td",[t._v("누출 가스 몰질량")]),s("td",[t._v(t._s(t.infoData.moleWeight))])]),s("tr",[s("td",[t._v("누출지점 높이")]),s("td",[t._v(t._s(t.infoData.leakHeight))])]),s("tr",[s("td",[t._v("총누출량(PUFF) [kg] / 초당 누출량(PLUME) [kg/s]")]),s("td",[t._v(t._s(t.infoData.totalReleaseAmount))])]),s("tr",[s("td",[t._v("최대 검측 PPM")]),s("td",[t._v(t._s(t.infoData.maxPpm))])]),s("tr",[s("td",[t._v("최소 검측 PPM")]),s("td",[t._v(t._s(t.infoData.minPpm))])]),s("tr",[s("td",[t._v("분할 PPM")]),s("td",[t._v(t._s(t.infoData.ppmPer))])]),s("tr",[s("td",[t._v("날씨 기준위치")]),s("td",[t._v(t._s(t.infoData.kmaName))])])])])])])])])},i=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("tr",[a("th",{attrs:{rowspan:"2"}},[t._v("발생지점")]),a("td",[t._v("발생지점 주소")]),a("td")])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("tr",[a("th",{attrs:{rowspan:"9"}},[t._v("시뮬레이션 조건")]),a("td",[t._v("가스종류(gasType)")]),a("td")])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("tr",[a("td",[t._v("주변압력(ambientPress)")]),a("td")])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("tr",[a("td",[t._v("대기기압(atmosPress)")]),a("td")])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("tr",[a("td",[t._v("대기기온(atmosTemper)")]),a("td")])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("tr",[a("td",[t._v("대기습도(Humidity)")]),a("td")])}],r=a("bc3a"),n=a.n(r),o={name:"AerialPhotoViewer",props:{procs_nm:{type:String},eqmt_nm:{type:String},eqmt_id:{type:String,default:""},hasDetail:{type:Boolean},displayData:Object},computed:{mapUrl:function(){var t,e="http://118.129.135.145:2122/analysis/guest/gasFacInfo.do?gasFacId=";return t=this.eqmt_id?e+this.eqmt_id:this.displayData.gasFacId?"http://118.129.135.145:2122/analysis/guest/gasFacInfo.do?gasFacId="+this.displayData.gasFacId:this.displayData.diffsMapUrl,t},baseDateTime:function(){var t=null===this.infoData.baseDate||null===this.infoData.baseTime||""===this.infoData.baseDate&&""===this.infoData.baseTime;return t?"기준 날짜와 시간 데이터가 없습니다.":this.infoData.baseDate+" "+this.infoData.baseTime},title:function(){return this.procs_nm?this.procs_nm:this.displayData?this.displayData.gasFacId?this.displayData.gasFacId:"설비명 없음":null},subtitle:function(){return this.eqmt_nm?this.eqmt_nm:this.eqmt_id?this.eqmt_id:null}},watch:{displayData:{immediate:!0,deep:!0,handler:function(){console.log("watch displayData: ",this.displayData),this.loadInfo(this.displayData.diffsInfoUrl)}}},created:function(){this.loadInfo()},data:function(){return{infoData:{baseDate:null,baseTime:null,baseX:null,baseY:null,execTime:null,fromExecTime:null,gasFacId:null,gasSimulSn:null,heatCapacityRatio:null,kmaName:null,leakHeight:null,maxPpm:null,minPpm:null,model:null,moleWeight:null,ppmPer:null,resultCode:null,simulTime:null,simulTimePer:null,sisulSn:null,stability:null,t1h:null,toExecTime:null,totalReleaseAmount:null,vec:null,wsd:null}}},methods:{queryDetail:function(){},onClickClose:function(){this.$emit("close")},loadInfo:function(t){var e=this;n.a.get(t).then((function(t){return"Success"===t.data.code&&(e.infoData=t.data.responseData,console.log("info data: ",e.infoData)),t})).catch((function(t){console.log("catch error: ",t)}))}}},l=o,c=(a("9a86"),a("2877")),u=Object(c["a"])(l,s,i,!1,null,"479a885a",null);e["a"]=u.exports},"25d9":function(t,e,a){},"2db9":function(t,e,a){},3037:function(t,e,a){"use strict";var s=a("8c0e"),i=a.n(s);i.a},3296:function(t,e,a){"use strict";a.r(e);var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("PageTitle",{attrs:{"pre-title":t.processTitle,title:t.equipmentTitle,"has-date-range-picker":""},on:{changedDate:t.onChangedDate}}),a("div",{staticClass:"page-box"},[a("PanelTitle",{attrs:{title:"센서 데이터 분석 위험도"}}),a("ChartBox",{attrs:{"raw-data":t.riskEquipmentCrigHistory.list,"chart-type":"MAIN"}}),a("table",{staticClass:"default-table"},[a("thead",[a("tr",[a("th",{staticClass:"w100"},[t._v("날짜")]),a("th",{staticClass:"w150",attrs:{colspan:"2"}},[t._v("위험도")]),t._l(t.timeListData,(function(e,s){return a("th",{key:s},[t._v(" "+t._s(e)+"시 ")])}))],2)]),a("tbody",[t._l(t.dayDataList,(function(e,s){return[a("tr",{key:s+"_0"},[a("td",{attrs:{rowspan:"6"}},[t._v(t._s(t.dayjs(t.dateList[s]).format("MM/DD")))]),a("td",{attrs:{colspan:"2"}},[t._v("화재폭발")]),t._l(e,(function(e,s){return a("td",{key:s},[e&&e.CRIG101?a("RiskLabel",{attrs:{"text-data":e.CRIG101,"is-delete-step-suffix":!0}}):a("span",[t._v("-")])],1)}))],2),a("tr",{key:s+"_1"},[a("td",{attrs:{colspan:"2"}},[t._v("질식/중독")]),t._l(e,(function(e,s){return a("td",{key:s},[e&&e.CRIG102?a("RiskLabel",{attrs:{"text-data":e.CRIG102,"is-delete-step-suffix":!0}}):a("span",[t._v("-")])],1)}))],2),a("tr",{key:s+"_2"},[a("td",{attrs:{rowspan:"4"}},[t._v("이상원인")]),a("td",[t._v("온도")]),t._l(e,(function(e,s){return a("td",{key:s},[e&&e.CRIG103?a("RiskLabel",{attrs:{"text-data":e.CRIG103,"is-delete-step-suffix":!0}}):a("span",[t._v("-")])],1)}))],2),a("tr",{key:s+"_3"},[a("td",[t._v("압력")]),t._l(e,(function(e,s){return a("td",{key:s},[e&&e.CRIG104?a("RiskLabel",{attrs:{"text-data":e.CRIG104,"is-delete-step-suffix":!0}}):a("span",[t._v("-")])],1)}))],2),a("tr",{key:s+"_4"},[a("td",[t._v("유량")]),t._l(e,(function(e,s){return a("td",{key:s},[e&&e.CRIG105?a("RiskLabel",{attrs:{"text-data":e.CRIG105,"is-delete-step-suffix":!0}}):a("span",[t._v("-")])],1)}))],2),a("tr",{key:s+"_5"},[a("td",[t._v("누출농도")]),t._l(e,(function(e,s){return a("td",{key:s},[e&&e.CRIG106?a("RiskLabel",{attrs:{"text-data":e.CRIG106,"is-delete-step-suffix":!0}}):a("span",[t._v("-")])],1)}))],2)]}))],2)]),a("PanelTitle",{attrs:{title:"예지보전 위험도"}}),a("table",{staticClass:"default-table double-row-header"},[a("thead",[a("tr",[a("th",{attrs:{rowspan:"2"}},[t._v("날짜")]),a("th",{attrs:{rowspan:"2"}},[t._v("위험 단계")]),a("th",{attrs:{rowspan:"2"}},[t._v("고장확률")]),a("th",{attrs:{rowspan:"2"}},[t._v(t._s(t.partTitles.PART102))]),a("th",{attrs:{rowspan:"2"}},[t._v(t._s(t.partTitles.PART103))]),a("th",{attrs:{colspan:"3"}},[t._v("이상원인")])]),a("tr",[a("th",[t._v(t._s(t.partTitles.PART104))]),a("th",[t._v(t._s(t.partTitles.PART105))]),a("th",[t._v(t._s(t.partTitles.PART106))])])]),a("tbody",t._l(t.partDataList,(function(e,s){return a("tr",{key:s},[a("td",[t._v(t._s(t.dayjs(e.PART101.ymd).format("M/DD")))]),a("td",[e.PART101?a("RiskLabel",{attrs:{"text-data":e.PART101.risk_value,"show-risk-label":!1}}):t._e()],1),a("td",[t._v(t._s(e.PART101.risk_value.split(":")[0]+"%"))]),a("td",[t._v(t._s(e.PART102.risk_value))]),a("td",[t._v(t._s(t.dayjs(e.PART103.risk_value).format("YYYY-MM-DD")))]),a("td",[t._v(t._s(e.PART104.risk_value))]),a("td",[t._v(t._s(e.PART105.risk_value))]),e.PART106?a("td",[t._v(t._s(e.PART106.risk_value))]):t._e()])})),0)]),a("PanelTitle",{attrs:{title:"영상 데이터 분석 위험도"}}),a("table",{staticClass:"default-table double-row-header"},[a("thead",[a("tr",[a("th",{attrs:{rowspan:"2"}},[t._v("날짜")]),a("th",{staticClass:"w300",attrs:{rowspan:"2"}},[t._v(t._s(t.ksecTitles.KSEC101))]),a("th",{attrs:{rowspan:"2"}},[t._v(t._s(t.ksecTitles.KSEC102))]),a("th",{attrs:{rowspan:"2"}},[t._v(t._s(t.ksecTitles.KSEC103))]),a("th",{attrs:{colspan:"3"}},[t._v("이상원인")])]),a("tr",[a("th",[t._v(t._s(t.ksecTitles.KSEC105))]),a("th",[t._v(t._s(t.ksecTitles.KSEC106))]),a("th",[t._v(t._s(t.ksecTitles.KSEC107))])])]),a("tbody",t._l(t.ksecDataList,(function(e,s){return a("tr",{key:s},[a("td",[t._v(t._s(t.dayjs(e.KSEC101.ymd).format("M/DD")))]),a("td",{staticClass:"flex-in-td"},[e.KSEC101?a("RiskLabel",{attrs:{"text-data":e.KSEC101.risk_value}}):t._e(),e.KSEC103.risk_value?a("button",{staticClass:"default-button",on:{click:function(a){return t.popupAerialPhoto(e)}}},[t._v(" 항공사진 ")]):t._e()],1),a("td",[e.KSEC102.risk_value?a("RiskLabel",{attrs:{"text-data":e.KSEC102.risk_value}}):t._e()],1),a("td",[e.KSEC103.risk_value?a("RiskLabel",{attrs:{"text-data":e.KSEC103.risk_value}}):t._e()],1),a("td",[e.KSEC105.risk_value?a("RiskLabel",{attrs:{"text-data":e.KSEC105.risk_value,"is-delete-step-suffix":""}}):t._e()],1),a("td",[e.KSEC106.risk_value?a("RiskLabel",{attrs:{"text-data":e.KSEC106.risk_value,"is-delete-step-suffix":""}}):t._e()],1),a("td",[e.KSEC107.risk_value?a("RiskLabel",{attrs:{"text-data":e.KSEC107.risk_value,"is-delete-step-suffix":""}}):t._e()],1)])})),0)])],1),t.isShowViewer?a("AerialPhotoViewer",{on:{close:t.onClosePopup}}):t._e()],1)},i=[],r=(a("4160"),a("c975"),a("159b"),a("5530")),n=a("25b9"),o=a("5a0c"),l=a.n(o),c=a("4ba1"),u=a("c6f9"),d=a("a321"),h=a("2f62"),f=a("7ec5"),_=a("10a3"),p={name:"RiskPredictionEquipmentDetail",components:{AerialPhotoViewer:_["a"],RiskLabel:f["a"],ChartBox:d["a"],PanelTitle:u["a"],PageTitle:n["a"]},props:{eqmt_id:String},computed:Object(r["a"])(Object(r["a"])({},Object(h["c"])(["riskEquipmentCrigHistory","riskEquipmentPartDbHistory","riskEquipmentKsec3History"])),{},{dayDataList:function(){var t=this,e=[],a=null,s=[];return this.riskEquipmentCrigHistory.list.forEach((function(i){a!==i.ymd&&(a=i.ymd,s=[],e.push(s),t.dateList.push(i.ymd));var r=t.timeListData.indexOf(i.qtime);s[r]=i})),e},ksecTitles:function(){var t={};return this.riskEquipmentKsec3History.list.forEach((function(e){t[e.risk_type_id]=e.risk_type_nm})),t},ksecDataList:function(){var t=[],e=null,a={};return this.riskEquipmentKsec3History.list.forEach((function(s){e!==s.ymd&&(e=s.ymd,a={},t.push(a)),a[s.risk_type_id]=s})),t},partTitles:function(){var t={};return this.riskEquipmentPartDbHistory.list.forEach((function(e){t[e.risk_type_id]=e.risk_type_nm})),t},partDataList:function(){var t=[],e=null,a={};return this.riskEquipmentPartDbHistory.list.forEach((function(s){e!==s.ymd&&(e=s.ymd,a={},t.push(a)),a[s.risk_type_id]=s})),t},processTitle:function(){return this.$route.query.procs_nm?this.$route.query.procs_nm:"상세 정보"},equipmentTitle:function(){return this.$route.query.eqmt_nm?this.$route.query.eqmt_nm:"상세 정보"}}),created:function(){this.queryRiskEquipmentCrigHistory()},data:function(){return{dayjs:l.a,formatter:c["a"],selectedDate:{startDate:l()().format("YYYYMMDD"),endDate:l()().format("YYYYMMDD")},timeListData:["00","03","06","09","12","15","18","21"],dateList:[],partListData:["PART101","PART102","PART103","PART104","PART105","PART106"],ksecListData:["KSEC101","KSEC102","KSEC103","KSEC104","KSEC105","KSEC106","KSEC107"],isShowViewer:!1}},methods:{queryRiskEquipmentCrigHistory:function(){this.$store.dispatch("riskEquipmentCrigHistory",{queryString:"?strt_anys_ymd="+this.selectedDate.startDate+"&end_anys_ymd="+this.selectedDate.endDate+"&eqmt_id="+this.eqmt_id}),this.$store.dispatch("riskEquipmentPartDbHistory",{queryString:"?anys_ymd="+this.selectedDate.startDate+"&end_anys_ymd="+this.selectedDate.endDate+"&eqmt_id="+this.eqmt_id}),this.$store.dispatch("riskEquipmentKsec3History",{queryString:"?anys_ymd="+this.selectedDate.startDate+"&end_anys_ymd="+this.selectedDate.endDate+"&eqmt_id="+this.eqmt_id})},getTableHeader:function(t){return"date"===t?"날짜":t+"시"},onChangedDate:function(t){console.log(t,"onChangedDate<<<<"),this.dateList.length=0,this.selectedDate.startDate=l()(t.startDate).format("YYYYMMDD"),this.selectedDate.endDate=l()(t.endDate).format("YYYYMMDD"),this.queryRiskEquipmentCrigHistory()},crigYmdList:function(){var t=[];return this.riskEquipmentCrigHistory.list.forEach((function(e){var a=t[t.length-1];a&&a.ymd===e.ymd?a.count++:t.push({ymd:e.ymd,count:1})})),t},popupAerialPhoto:function(t){console.log(t),this.isShowViewer=!this.isShowViewer},onClosePopup:function(){this.isShowViewer=!1}}},m=p,v=(a("3037"),a("2877")),D=Object(v["a"])(m,s,i,!1,null,"1fd35632",null);e["default"]=D.exports},"4ba1":function(t,e,a){"use strict";a("ac1f"),a("1276");var s={date:function(t,e){if(!t)return"";var a=t.substr(0,4),i=t.substr(4,2),r=t.substr(6,2),n=t.substr(8,2),o=t.substr(10,2),l=t.substr(12,2),c=""===n||""===o?"":" "+n+":"+o;if(c=""===l?c:" "+c+":"+l,"MM/dd~MM/dd"===e){var u=t.split("~");return s.date(u[0],"MM/dd")+"~"+s.date(u[1],"MM/dd")}return"MM/dd"===e?i+"/"+r:a+"-"+i+"-"+r+c}};e["a"]=s},6940:function(t,e,a){},"6e03":function(t,e,a){"use strict";var s=a("6940"),i=a.n(s);i.a},"7ec5":function(t,e,a){"use strict";var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return t.validated?a("div",{class:{"double-row":!t.isDoubleRow}},[a("RiskStepLabel",{class:{"full-size":t.isDoubleRow},attrs:{"delete-step-suffix":t.isDeleteStepSuffix,name:t.riskName,code:t.riskCode}}),t.showRiskLabel?a("div",{staticClass:"risk-label"},[t._v(t._s(t.riskNum+t.riskUnit))]):t._e()],1):a("div",[t._v("-")])},i=[],r=(a("c975"),a("b0c0"),a("a9e3"),a("ac1f"),a("1276"),a("6e4b")),n={name:"RiskLabel",components:{RiskStepLabel:r["a"]},props:{isDoubleRow:Boolean,isDeleteStepSuffix:Boolean,showRiskLabel:{type:Boolean,default:!0},name:String,code:String,num:Number,unit:String,textData:String},computed:{validated:function(){return this.textData||this.code},isTextData:function(){return this.textData&&this.textData.indexOf(":")>-1},riskName:function(){var t="";if(this.isTextData)t=this.textData.indexOf(":")>-1?this.textData.split(":")[1]:this.textData;else if(this.name)t=this.name;else if(this.code){var e=this.code.substr(0,2),a=this.code.substr(-1,1);switch(e){case"NR":t="정상";break;case"CR":t="주의";break;case"WR":t="경고";break;case"DG":t="위험";break;default:t="-"}t=t+" "+a+" 단계"}return this.isDeleteStepSuffix&&(t=t.substr(0,2)),t},riskCode:function(){var t;if(this.isTextData){var e=this.textData.split(":")[1].substr(0,2);switch(e){case"정상":t="NR";break;case"주의":t="CR";break;case"경고":t="WR";break;case"위험":t="DG";break;default:t=""}}else t=this.code;return t},riskNum:function(){var t=0;if(this.isTextData){var e=this.textData.split(":");t=Number(e[0])}else t=this.num;return t},riskUnit:function(){return this.unit?this.unit:"%"}},methods:{}},o=n,l=(a("dc5e"),a("2877")),c=Object(l["a"])(o,s,i,!1,null,"5401d788",null);e["a"]=c.exports},"8c0e":function(t,e,a){},"99af":function(t,e,a){"use strict";var s=a("23e7"),i=a("d039"),r=a("e8b5"),n=a("861d"),o=a("7b0b"),l=a("50c4"),c=a("8418"),u=a("65f0"),d=a("1dde"),h=a("b622"),f=a("2d00"),_=h("isConcatSpreadable"),p=9007199254740991,m="Maximum allowed index exceeded",v=f>=51||!i((function(){var t=[];return t[_]=!1,t.concat()[0]!==t})),D=d("concat"),b=function(t){if(!n(t))return!1;var e=t[_];return void 0!==e?!!e:r(t)},k=!v||!D;s({target:"Array",proto:!0,forced:k},{concat:function(t){var e,a,s,i,r,n=o(this),d=u(n,0),h=0;for(e=-1,s=arguments.length;e<s;e++)if(r=-1===e?n:arguments[e],b(r)){if(i=l(r.length),h+i>p)throw TypeError(m);for(a=0;a<i;a++,h++)a in r&&c(d,h,r[a])}else{if(h>=p)throw TypeError(m);c(d,h++,r)}return d.length=h,d}})},"9a86":function(t,e,a){"use strict";var s=a("2db9"),i=a.n(s);i.a},a321:function(t,e,a){"use strict";var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"chart-container",class:{"main-type":"MAIN"===t.chartType}},[a("highcharts",{ref:"highcharts",attrs:{options:t.chartOptions}})],1)},i=[],r=(a("99af"),a("4160"),a("a9e3"),a("ac1f"),a("1276"),a("159b"),a("4452")),n=a("4ba1"),o={name:"ChartBox",components:{highcharts:r["Chart"]},props:{chartType:String,rawData:null},watch:{seriesData:{handler:function(){var t=this;while(this.chart.series.length>0)this.chart.series[0].remove(!0);this.seriesData.forEach((function(e){t.chart.addSeries(e)})),this.chart.xAxis[0].setCategories(this.categoryData)},deep:!0}},computed:{seriesData:function(){var t=null;switch(this.chartType){case"MAIN":t=this.getMainList();break;case"PREDICTION24HOUR":t=this.getPrediction24HourList();break;case"PREDICTION7DAY":t=this.getPrediction7DayList();break;case"PREDICTION4WEEK":t=this.getPrediction4WeekList();break;case"PROCESS":t=this.getProcessList("생산공정 상세");break;case"EQUIPMENT":t=this.getProcessList("설비 상세");break;default:return[]}return t},categoryData:function(){var t=null;switch(this.chartType){case"MAIN":t=this.getHourCategories();break;case"PREDICTION24HOUR":t=this.getHourCategories();break;case"PREDICTION7DAY":t=this.getDayCategories();break;case"PREDICTION4WEEK":t=this.getWeekCategories();break;case"PROCESS":t=this.getProcessCategories();break;case"EQUIPMENT":t=this.getProcessCategories();break;default:return[]}return t}},mounted:function(){this.chart=this.$refs.highcharts.chart},data:function(){return{chart:null,chartOptions:{chart:{type:"area",height:this.getChartHeight(),animation:{duration:1e3}},title:!1,legend:this.getLegendConfig(),xAxis:{categories:[]},yAxis:{title:!1,labels:{formatter:function(){var t={0:"",25:"정상",50:"주의",75:"경고",100:"위험"},e={0:"transparent",25:"#2d969b",50:"#f06c00",75:"#ff0000",100:"#a40000"};return'<span style="color: '.concat(e[this.value],'">').concat(t[this.value],"</span>")},y:28,x:-40,style:{color:"#666666",fontSize:15,fontWeight:"bold"}},max:100,tickAmount:5,tickWidth:1,tickLength:100},plotOptions:{series:{marker:{fillColor:"#FFFFFF",lineWidth:2,lineColor:null}},area:{pointPlacement:"on"}},credits:{enabled:!1}}}},methods:{getProcessCategories:function(){var t=this,e=[];return this.rawData.data.forEach((function(a){t.rawData.cols.forEach((function(t){if("date"!==t){var s=a[t];s&&e.push(t+"시")}}))})),e},getWeekCategories:function(){var t=[];return this.rawData.forEach((function(e){t.push(n["a"].date(e.risk_dt,"MM/dd~MM/dd"))})),t},getDayCategories:function(){var t=[];return this.rawData.forEach((function(e){t.push(n["a"].date(e.ymd,"MM/dd"))})),t},getHourCategories:function(){var t=[];return this.rawData.forEach((function(e){t.push(e.qtime+"시")})),t},getChartHeight:function(){return"MAIN"===this.chartType?250:230},getLegendConfig:function(){return"MAIN"===this.chartType&&{layout:"horizontal",align:"left",verticalAlign:"top",x:50}},getProcessList:function(t){var e=[{name:t,data:[],color:"#f58fae"}],a=this.rawData.cols;return this.rawData.data.forEach((function(t){a.forEach((function(a){if("date"!==a){var s=t[a];s=s?Number(s.split(":")[0]):null,e[0].data.push(s)}}))})),e},getPrediction4WeekList:function(){var t=[{name:"4주 예측",data:[],color:"#f58fae"}];return this.rawData.forEach((function(e){t[0].data.push(Number(e.risk_rate.split(":")[0]))})),t},getPrediction7DayList:function(){var t=[{name:"7일 예측",data:[],color:"#f58fae"}];return this.rawData.forEach((function(e){t[0].data.push(Number(e.risk_rate.split(":")[0]))})),t},getPrediction24HourList:function(){var t=[{name:"24시간 예측",data:[],color:"#f58fae"}];return this.rawData.forEach((function(e){t[0].data.push(Number(e.risk_rate.split(":")[0]))})),t},getMainList:function(){var t=[{name:"화재폭발 위험도",data:[],color:"#f58fae"},{name:"질식/중독 위험도",data:[],color:"#63e3ef"}];return this.rawData.forEach((function(e){t[0].data.push(Number(e.CRIG101.split(":")[0])),t[1].data.push(Number(e.CRIG102.split(":")[0]))})),t}}},l=o,c=(a("6e03"),a("2877")),u=Object(c["a"])(l,s,i,!1,null,"bc47774c",null);e["a"]=u.exports},dc5e:function(t,e,a){"use strict";var s=a("25d9"),i=a.n(s);i.a}}]);
//# sourceMappingURL=chunk-124c1348.js.map