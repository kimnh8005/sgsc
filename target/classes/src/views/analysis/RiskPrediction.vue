<template>
  <div>
    <PageTitle/>

    <div class="page-box">
      <PanelTitle title="24시간 예측 위험도 높은 설비" />
      <div class="container">
        <div class="table-container">
          <table class="default-table">
            <thead>
            <tr>
              <th>순위</th>
              <th>설비</th>
              <th>위험단계</th>
              <th>발생확률</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in predictEquipmentRiskTop10.hour24.slice(0, 5)" :key="index">
              <th>{{index + 1}}</th>
              <td>{{item.procs_nm}}<span class="arrow">&gt;</span>{{item.eqmt_nm}}</td>
              <td>
                <RiskStepLabel :useStepByName= true :name="item.risk_step_nm" />
              </td>
              <th>{{ item.risk_num + item.risk_unit }}</th>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="table-container">
          <table class="default-table">
            <thead>
            <tr>
              <th>순위</th>
              <th>설비</th>
              <th>위험단계</th>
              <th>발생확률</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in predictEquipmentRiskTop10.hour24.slice(5, 10)" :key="index">
              <th>{{index + 6}}</th>
              <td>{{item.procs_nm}}<span class="arrow">&gt;</span>{{item.eqmt_nm}}</td>
              <td>
                <RiskStepLabel :useStepByName= true :name="item.risk_step_nm" />
              </td>
              <th>{{ item.risk_num + item.risk_unit }}</th>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <div class="page-box">
      <PanelTitle title="위험도 예측"/>
      <PredictionFiltering @filtering="onFiltering"/>

      <PanelSubTitle title="통합 위험도 예측"/>
      <ChartBox :raw-data="predictEquipmentHour24.list" chart-type="PREDICTION24HOUR"/>
      <table class="default-table double-row-header">
        <thead>
        <!-- <tr v-if="predictEquipmentHour24.list.length > 0"> -->
        <tr>
          <th rowspan="2" colspan="2">날짜/시간</th>
          <th v-for="item in ymdList"
              :key="item.ymd"
              :colspan="item.count">
            {{ formatter.date(item.ymd, 'MM/dd') }}
          </th>
        </tr>
        <tr>
          <th v-for="(item, idx) in predictEquipmentHour24.list"
              :key="idx">
            {{ item.qtime }}시
          </th>
        </tr>
        </thead>
        <tbody v-if="predictEquipmentHour24.list.length">
          <tr>
            <th colspan="2">위험단계</th>
            <td v-for="item in predictEquipmentHour24.list" :key="item.qtime">
              <RiskLabel :text-data="item.risk_rate" :showRiskLabel="false"/>
            </td>
          </tr>
          <tr>
            <th colspan="2">발생확률</th>
            <th v-for="(item, idx) in predictEquipmentHour24.list"
                :key="idx">
              <RiskLabel :text-data="item.risk_rate" :showRiskStep="false" />
            </th>
          </tr>
          <tr>
            <th rowspan="4">사고<br>시나리오<br>위험 단계</th>
            <th>정상</th>
            <th v-for="(item, idx) in predictEquipmentHour24.list"
                :key="idx">
              {{ item.norl }}건
            </th>
          </tr>
          <tr>
            <th>주의</th>
            <th v-for="(item, idx) in predictEquipmentHour24.list"
                :key="idx">
               {{ item.care }}건
            </th>
          </tr>
          <tr>
            <th>경고</th>
            <th v-for="(item, idx) in predictEquipmentHour24.list"
                :key="idx">
              {{ item.warg }}건
            </th>
          </tr>
          <tr>
            <th>위험</th>
            <th v-for="(item, idx) in predictEquipmentHour24.list"
                :key="idx">
              {{ item.eror }}건
            </th>
          </tr>
          <tr>
            <th colspan="2">사고 시나리오</th>
            <td v-for="(item, idx) in predictEquipmentHour24.list" :key="idx">
              <button v-if="item" class="outline-dark" @click="showPopup('KIES', selectedProcessName, selectedEquipmentName, selectedEquipmentId, item.ymd + item.qtime + '00')">
                상세보기
                <img src="~@/assets/img/caret-right.png" alt="상세보기">
              </button>
              <span v-else>-</span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <AccidentScenarioPopup v-if="accident_scenario_popup.isShow" @close="hidePopup" class="no-hr-line">
      <template v-if='accident_scenario_popup.procs_nm && accident_scenario_popup.procs_nm.length > 0' v-slot:subtitle>
        <h2>{{accident_scenario_popup.procs_nm}}<img class="arrow" src="~@/assets/img/caret-right.png" alt="">{{accident_scenario_popup.eqmt_nm}}</h2>
      </template>
      <template v-else-if="accident_scenario_popup.eqmt_nm && accident_scenario_popup.eqmt_nm.length > 0" v-slot:subtitle>
        <h2>{{accident_scenario_popup.eqmt_nm}}</h2>
      </template>
      <template v-else v-slot:subtitle>
        <h2>장비 이름 없음</h2>
      </template>
      <template v-slot:time>
        <h3>{{popupTimeText()}}</h3>
      </template>
      <template v-slot:body>
        <table class="default-table in-popup">
          <thead>
            <tr>
              <th>사고 코드</th>
              <th>사고 발생 원인</th>
              <th>사고 발생 내용</th>
              <th>점검 사항</th>
              <th>위험단계</th>
              <th>{{popupRiskText()}}</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in realtimeAccidentScenarioPopup.list" :key="index">
              <th>{{ item.scen_nm }}</th>
              <th>{{ item.scen_cause }}</th>
              <th>{{ item.scen_result }}</th>
              <th>{{ item.etc }}</th>
              <td><RiskLabel :text-data="item.RISK_RATE" :showRiskLabel="false"/></td>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskStep="false"/></th>
            </tr>
          </tbody>
        </table>
      </template>
    </AccidentScenarioPopup>

  </div>
</template>

<script>
import {mapState} from "vuex";
import PanelTitle from "@/components/ui/PanelTitle";
import PanelSubTitle from "@/components/ui/PanelSubTitle";
import RiskStepLabel from "@/components/ui/RiskStepLabel";
import PredictionFiltering from "@/components/ui/PredictionFiltering";
import ChartBox from "@/components/chart/ChartBox";
import formatter from "@/modules/formatter";
import RiskLabel from "@/components/ui/RiskLabel";
import PageTitle from "@/components/panels/PageTitle";
import AccidentScenarioPopup from "@/components/ui/AccidentScenarioPopup";

export default {
  name: "RiskPrediction",
  // components: {PageTitle, RiskLabel, ChartBox, PredictionFiltering, RiskStepLabel, PanelSubTitle, PanelTitle},
  components: {PageTitle, RiskLabel, ChartBox, PredictionFiltering, RiskStepLabel, PanelSubTitle, PanelTitle, AccidentScenarioPopup},

  computed: {
    ...mapState([
      'predictEquipmentRiskTop10',
      'predictEquipmentHour24',
      'realtimeAccidentScenarioPopup'
    ]),

    ymdList() {
      let list = [];
      this.predictEquipmentHour24.list.forEach(item => {
        let temp = list[list.length - 1];
        if (temp && temp.ymd === item.ymd){
          temp.count++;
        } else {
          list.push({
            ymd: item.ymd,
            count: 1
          });
        }
      })
      return list
    }
  },

  created() {
    this.$store.dispatch('predictEquipmentRiskTop10');
  },

  data(){
    return {
      selectedProcessId: '',
      selectedProcessName: '',
      selectedEquipmentId: '',
      selectedEquipmentName: '',
      formatter,
      accident_scenario_popup : {
        isShow : false,
        anys_sys_cd : '',
        procs_nm : '',
        eqmt_nm : '',
        anys_time : ''
      }
    }
  },

  methods: {
    onFiltering(options){
      console.log(options)

      this.selectedProcessId = options.process;
      this.selectedProcessName = options.selected_process_nm;
      this.selectedEquipmentId = options.equipment;
      this.selectedEquipmentName = options.selected_equipment_nm;

      console.log(this.selectedEquipmentId);

      this.$store.dispatch('predictEquipmentHour24', options);
      // this.$store.dispatch('predictEquipmentDay7', options);
      // this.$store.dispatch('predictEquipmentWeek4', options);
    },

    showPopup(anys_sys_cd, procs_nm, eqmt_nm, eqmt_id, anys_time){
      this.accident_scenario_popup.isShow = true;
      this.accident_scenario_popup.anys_sys_cd = anys_sys_cd;
      this.accident_scenario_popup.procs_nm = procs_nm;
      this.accident_scenario_popup.eqmt_nm = eqmt_nm;
      this.accident_scenario_popup.eqmt_id = eqmt_id;
      this.accident_scenario_popup.anys_time = anys_time;
      this.$store.dispatch('realtimeAccidentScenarioPopup',
        {anayseSystemCode : this.accident_scenario_popup.anys_sys_cd,
        eqmtID : this.accident_scenario_popup.eqmt_id,
        anayseYMDH : this.accident_scenario_popup.anys_time.slice(0,10)})
    },

    hidePopup(){
      this.accident_scenario_popup.isShow = false;
    },

    popupTimeText(){
      return '분석일시 : ' + formatter.date(this.accident_scenario_popup.anys_time);
    },

    popupRiskText(){
      if (this.accident_scenario_popup.anys_sys_cd === 'KIES') {
        return '통합 위험률';
      }
      else if (this.accident_scenario_popup.anys_sys_cd === 'CRIG') {
        return '발생확률';
      }
      else if (this.accident_scenario_popup.anys_sys_cd === 'PARTDB') {
        return '고장확률';
      }
      else if (this.accident_scenario_popup.anys_sys_cd === 'KSEC') {
        return '위험확률';
      }

       return '통합 위험률';
    }

  }
};
</script>

<style scoped lang="scss">
@import "src/assets/scss/table";
.page-box{
  > .container{
    display: flex;
    justify-content: space-between;

    .table-container{
      width: 49%;

      span.arrow{
        padding: 0 6px;
      }
    }
  }
}

</style>