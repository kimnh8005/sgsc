<template>
  <div>
    <PageTitle
        :title="processTitle"
        has-date-range-picker
        @changedDate="onChangedDate"/>

    <div class="page-box">
      <PanelTitle title="통합 위험도" />
      <ChartBox :raw-data="riskProcessHistoryMap" chart-type="PROCESS"/>
      <table class="default-table">
        <thead>
        <tr>
          <th>날짜</th>
          <th>위험/확률</th>
          <th v-for="(item, index) in riskProcessHistoryMap.cols.slice(1)"
              :key="index">
            {{ getTableHeader(item) }}
          </th>
        </tr>
        </thead>
        <tbody>
          <template v-for="(row, index) in riskProcessHistoryMap.data">
            <tr :key="'row_1_' + index">
              <th rowspan="2">{{ formatter.date(row['date'], 'MM/dd') }}</th>
              <th>위험단계</th>
              <th v-for="(item, idx) in sortRow(0, row)" :key="'col_1_' + idx">
                <RiskLabel v-if="item" :text-data="item" :showRiskLabel="false" />
                <span v-else>-</span>
              </th>
            </tr>
            <tr :key="'row_2_' + index">
              <th>통합 위험률</th>
              <th v-for="(item, idx) in sortRow(0, row)" :key="'col_2_' + idx">
                <RiskLabel v-if="item" :text-data="item" :showRiskStep="false" />
                <span v-else>-</span>
              </th>
            </tr>
          </template>
        </tbody>
      </table>
    </div>

    <div class="page-box">
      <PanelTitle title="설비 별 위험도" />
      <PanelTab :items="equipmentByProcsId.list"
                display-prop-name="eqmt_nm"
                :emit-prop-list="['eqmt_id','eqmt_nm']"
                @change="equipmentTabChanged"/>
      <PanelSubTitle :title="equipmentTitle">
        <template v-slot:button>
          <router-link :to="{ path: '/analysis/equipment/' + selectedEquipmentId, query: { procs_nm: processTitle, eqmt_nm: equipmentTitle }}">
            자세히 보기 →
          </router-link>
        </template>
      </PanelSubTitle>

      <ChartBox :raw-data="riskEquipmentHistoryMap" chart-type="EQUIPMENT"/>
      <table class="default-table">
        <thead>
        <tr>
          <th>날짜</th>
          <th>위험/확률</th>
          <th v-for="(item, index) in riskEquipmentHistoryMap.cols.slice(1)"
              :key="index">
            {{ getTableHeader(item) }}
          </th>
        </tr>
        </thead>
        <tbody>
          <template v-for="(row, index) in riskEquipmentHistoryMap.data">
            <tr :key="'row_1_' + index">
              <th rowspan="3">{{ formatter.date(row['date'], 'MM/dd') }}</th>
              <th>위험단계</th>
              <th v-for="(item, idx) in sortRow(1, row)" :key="'col_1_' + idx">
                <RiskLabel v-if="item" :text-data="item" :showRiskLabel="false" />
                <span v-else class="font-weight-black">-</span>
              </th>
            </tr>
            <tr :key="'row_2_' + index">
              <th>통합 위험률</th>
              <th v-for="(item, idx) in sortRow(1, row)" :key="'col_2_' + idx">
                <RiskLabel v-if="item" :text-data="item" :showRiskStep="false" />
                <span v-else>-</span>
              </th>
            </tr>
            <tr :key="'row_3_' + index">
              <th>사고 시나리오</th>
              <td v-for="(item, idx) in sortRow(1, row)" :key="'col_3_' + idx">
                <button v-if="item" class="outline-dark" @click="showPopup('KIES', selectedProcessName, selectedEquipmentName, selectedEquipmentId, row['date'] + riskEquipmentHistoryMap.cols.slice(1)[idx] + '00')">
                  상세보기
                  <img src="~@/assets/img/caret-right.png" alt="상세보기">
                </button>
                <span v-else>-</span>
              </td>
            </tr>
          </template>
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
import PanelTitle from "@/components/ui/PanelTitle";
import {mapState} from "vuex";
import PageTitle from "@/components/panels/PageTitle";
import ChartBox from "@/components/chart/ChartBox";
import RiskLabel from "@/components/ui/RiskLabel";
import formatter from "@/modules/formatter";
import PanelTab from "@/components/ui/PanelTab";
import PanelSubTitle from "@/components/ui/PanelSubTitle";
import dayjs from "dayjs";
import AccidentScenarioPopup from "@/components/ui/AccidentScenarioPopup";

export default {
  name: "RiskPredictionProcessDetail",
  components: {PanelSubTitle, PanelTab, RiskLabel, ChartBox, PageTitle, PanelTitle, AccidentScenarioPopup},
  props: {
    procs_id: String,
    procs_nm: String
  },

  computed: {
    ...mapState([
      'riskProcessHistoryMap',
      'equipmentByProcsId',
      'riskEquipmentHistoryMap',
      'realtimeAccidentScenarioPopup'
    ]),

    processTitle(){
      return (this.$route.query.procs_nm) ? this.$route.query.procs_nm : '공정 상세 정보';
    },

    selectedProcessName(){
      return (this.$route.query.procs_nm) ? this.$route.query.procs_nm : null;
    },

    equipmentTitle(){
      return (this.selectedEquipmentName) ? this.selectedEquipmentName + ' 위험도' : '-';
    }
  },

  created() {
    this.queryRiskProcessHistoryMap();
  },

  data(){
    return {
      formatter,
      selectedEquipmentId: '',
      selectedEquipmentName: '',
      selectedDate: {
        startDate: dayjs().format('YYYYMMDD'),
        endDate: dayjs().format('YYYYMMDD')
      },
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
    queryRiskProcessHistoryMap(){
      this.$store.dispatch('riskProcessHistoryMap', {queryString: '?strt_anys_ymd=' + this.selectedDate.startDate + '&end_anys_ymd=' + this.selectedDate.endDate + '&procs_id=' + this.procs_id});
      this.$store.dispatch('equipmentByProcsId', {queryString: '?procs_id=' + this.procs_id})
          .then(() => {
            let info = {
              eqmt_id: this.equipmentByProcsId.list[0].eqmt_id,
              eqmt_nm: this.equipmentByProcsId.list[0].eqmt_nm
            }
            this.equipmentTabChanged(info);
          })
    },
    getTableHeader(str){
      return (str === 'date') ? '날짜' : str + '시';
    },
    sortRow(type, row){
      let cols = [];
      if (type === 0)
        cols = this.riskProcessHistoryMap.cols;
      else
        cols = this.riskEquipmentHistoryMap.cols;

      let date_index = cols.indexOf('date');

      let list = new Array(cols.length - 1).fill(null);

      for(let key in row){
        if (key != 'date') {
          let index = cols.indexOf(key);

          if(index < date_index) {
            list[index] = row[key];
          }
          else {
            list[index - 1] = row[key];
          }
        }
      }

      // console.log(list);

      return list;
    },
    equipmentTabChanged(info){
      console.log(info.eqmt_nm)
      this.selectedEquipmentId = info.eqmt_id;
      this.selectedEquipmentName = info.eqmt_nm;
      this.$store.dispatch('riskEquipmentHistoryMap', {queryString: '?strt_anys_ymd=' + this.selectedDate.startDate + '&end_anys_ymd=' + this.selectedDate.endDate + '&eqmt_id=' + info.eqmt_id});
    },
    onChangedDate(range){
      console.log(range, 'onChangedDate<<<<')
      this.selectedDate.startDate = dayjs(range.startDate).format('YYYYMMDD');
      this.selectedDate.endDate = dayjs(range.endDate).format('YYYYMMDD');
      this.queryRiskProcessHistoryMap();
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
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/table";
</style>