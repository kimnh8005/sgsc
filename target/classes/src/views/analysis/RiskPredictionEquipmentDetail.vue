<template>
  <div>
    <PageTitle
        :pre-title="processTitle"
        :title="equipmentTitle"
        has-date-range-picker
        @changedDate="onChangedDate"/>

    <div class="page-box">
      <PanelTitle title="센서 데이터 분석 위험도"/>
      <ChartBox :raw-data="riskEquipmentCrigHistory.list" chart-type="MAIN"/>

      <table class="default-table double-row-header">
        <thead>
        <tr>
          <th rowspan="2" colspan="2">날짜/시간</th>
          <th v-for="item in crigYmdList"
              :key="item.ymd"
              :colspan="item.count">
            {{ formatter.date(item.ymd, "MM/dd") }}
          </th>
        </tr>
        <tr>
          <th v-for="(item, idx) in riskEquipmentCrigHistory.list"
              :key="idx">
            {{ item.qtime }}시
          </th>
        </tr>
        </thead>
        <tbody v-if="riskEquipmentCrigHistory.list.length">
          <tr>
            <th colspan="2">위험단계</th>
            <th v-for="(item, idx) in riskEquipmentCrigHistory.list" :key="idx">
              <RiskLabel :text-data="item.risk_rate" :showRiskLabel="false" />
            </th>
          </tr>
          <tr>
            <th colspan="2">발생확률</th>
            <th v-for="(item, idx) in riskEquipmentCrigHistory.list" :key="idx">
              <RiskLabel :text-data="item.risk_rate" :showRiskStep="false" />
            </th>
          </tr>
          <tr>
            <th rowspan="4">사고<br>시나리오<br>위험 단계</th>
            <th>정상</th>
            <th v-for="(item, idx) in riskEquipmentCrigHistory.list" :key="idx">
              {{item.norl}}건
            </th>
          </tr>
          <tr>
            <th>주의</th>
            <th v-for="(item, idx) in riskEquipmentCrigHistory.list" :key="idx">
              {{item.care}}건
            </th>
          </tr>
          <tr>
            <th>경고</th>
            <th v-for="(item, idx) in riskEquipmentCrigHistory.list" :key="idx">
              {{item.warg}}건
            </th>
          </tr>
          <tr>
            <th>위험</th>
            <th v-for="(item, idx) in riskEquipmentCrigHistory.list" :key="idx">
              {{item.eror}}건
            </th>
          </tr>
          <tr>
            <th colspan="2">사고 시나리오</th>
            <td v-for="(item, idx) in riskEquipmentCrigHistory.list" :key="idx">
                <button v-if="item" class="outline-dark" @click="showPopup('CRIG', selectedProcessName, selectedEquipmentName, eqmt_id, item.ymd + item.qtime + '00')">
                  상세보기
                  <img src="~@/assets/img/caret-right.png" alt="상세보기">
                </button>
                <span v-else>-</span>
            </td>
          </tr>
        </tbody>
      </table>

      <PanelTitle title="예지보전 위험도"/>

      <table class="default-table double-row-header">
        <thead>
          <tr>
            <th rowspan="2">날짜/시간</th>
            <th rowspan="2">위험 단계</th>
            <th rowspan="2">고장확률</th>
            <th colspan="4">사고 시나리오 위험 단계</th>
            <th rowspan="2">사고 시나리오</th>
          </tr>
          <tr>
            <th>정상</th>
            <th>주의</th>
            <th>경고</th>
            <th class="no-radius border-right">위험</th>
          </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in riskEquipmentPartDbHistory.list" :key="index">
          <td>{{ formatter.date(item.anys_ymdh, 'MM/dd hh:mm') }}</td>
          <td><RiskLabel :text-data="item.risk_value" :showRiskLabel="false"/></td>
          <th><RiskLabel :text-data="item.risk_value" :showRiskStep="false"/></th>
          <th>{{ item.norl }}건</th>
          <th>{{ item.care }}건</th>
          <th>{{ item.WARG }}건</th>
          <th>{{ item.EROR }}건</th>
          <td>
            <button class="outline-dark" @click="showPopup('PARTDB', selectedProcessName, selectedEquipmentName, eqmt_id, item.ymd + item.qtime + '00')">
              상세보기
              <img src="~@/assets/img/caret-right.png" alt="상세보기">
            </button>
          </td>
        </tr>
        </tbody>
      </table>

      <PanelTitle title="영상 데이터 분석 위험도"/>

      <table class="default-table double-row-header">
        <thead>
          <tr>
            <th rowspan="2">날짜/시간</th>
            <th rowspan="2">위험 단계</th>
            <th rowspan="2">고장확률</th>
            <th colspan="4">사고 시나리오 위험 단계</th>
            <th rowspan="2">사고 시나리오</th>
          </tr>
          <tr>
            <th>정상</th>
            <th>주의</th>
            <th>경고</th>
            <th class="no-radius border-right">위험</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in riskEquipmentKsec3History.list" :key="index">
            <td>{{ formatter.date(item.anys_ymdh, 'MM/dd hh:mm') }}</td>
            <td>
              <RiskLabel :text-data="item.risk_value" :showRiskLabel="false"/>
              <!-- <a
                v-if="realtimeEquipmentKsec.list[3]"
                :href="realtimeEquipmentKsec.list[3].risk_value"
                target="_blank"
              > -->
              <a
                target="_blank"
              >
                <button class="default-button">항공사진 &gt;</button>
              </a>
            </td>
            <th><RiskLabel :text-data="item.risk_value" :showRiskStep="false"/></th>
            <th>{{ item.norl }}건</th>
            <th>{{ item.care }}건</th>
            <th>{{ item.warg }}건</th>
            <th>{{ item.eror }}건</th>
            <td>
              <button class="outline-dark" @click="showPopup('KSEC', selectedProcessName, selectedEquipmentName, eqmt_id, item.ymd + item.qtime + '00')">
                상세보기
                <img src="~@/assets/img/caret-right.png" alt="상세보기">
              </button>
            </td>

           <!-- <td class="flex-in-td">
              <RiskLabel
                  v-if="item.KSEC101"
                  :text-data="item.KSEC101.risk_value"/>
              <button v-if="item.KSEC103.risk_value"
                      @click="popupAerialPhoto(item)"
                class="default-button">
                항공사진
              </button>
            </td>
            <td>
              <RiskLabel
                  v-if="item.KSEC102.risk_value"
                  :text-data="item.KSEC102.risk_value"/>
            </td>
            <td>
              <RiskLabel
                  v-if="item.KSEC103.risk_value"
                  :text-data="item.KSEC103.risk_value"/>
            </td>
            <td>
              <RiskLabel
                  v-if="item.KSEC105.risk_value"
                  :text-data="item.KSEC105.risk_value"
                  is-delete-step-suffix/>
            </td>
            <td>
              <RiskLabel
                  v-if="item.KSEC106.risk_value"
                  :text-data="item.KSEC106.risk_value"
                  is-delete-step-suffix/>
            </td>
            <td>
              <RiskLabel
                  v-if="item.KSEC107.risk_value"
                  :text-data="item.KSEC107.risk_value"
                  is-delete-step-suffix/>
            </td> -->
          </tr>
        </tbody>
      </table>
    </div>
    <AerialPhotoViewer v-if="isShowViewer" @close="onClosePopup" />

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
import PageTitle from "@/components/panels/PageTitle";
import dayjs from "dayjs";
import formatter from "@/modules/formatter";
import PanelTitle from "@/components/ui/PanelTitle";
import ChartBox from "@/components/chart/ChartBox";
import {mapState} from "vuex";
import RiskLabel from "@/components/ui/RiskLabel";
import AerialPhotoViewer from "@/components/ui/AerialPhotoViewer";
import AccidentScenarioPopup from "@/components/ui/AccidentScenarioPopup";

export default {
  name: "RiskPredictionEquipmentDetail",
  components: {AerialPhotoViewer, RiskLabel, ChartBox, PanelTitle, PageTitle, AccidentScenarioPopup},
  props: {
    eqmt_id: String
  },
  computed: {
    ...mapState([
      'riskEquipmentCrigHistory',
      'riskEquipmentPartDbHistory',
      'riskEquipmentKsec3History',
      'realtimeAccidentScenarioPopup'
     ]),
    crigYmdList() {
      let list = [];
      this.riskEquipmentCrigHistory.list.forEach(item => {
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
    },
    processTitle(){
      return (this.$route.query.procs_nm) ? this.$route.query.procs_nm : '상세 정보';
    },
    equipmentTitle(){
      return (this.$route.query.eqmt_nm) ? this.$route.query.eqmt_nm : '상세 정보';
    },

    selectedProcessName(){
      return (this.$route.query.procs_nm) ? this.$route.query.procs_nm : null;
    },
    selectedEquipmentName(){
      return (this.$route.query.eqmt_nm) ? this.$route.query.eqmt_nm : null;
    },

  },

  created() {
    this.queryRiskEquipmentCrigHistory();
  },

  data(){
    return {
      dayjs,
      formatter,
      selectedDate: {
        startDate: dayjs().format('YYYYMMDD'),
        endDate: dayjs().format('YYYYMMDD')
      },
      isShowViewer: false,
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
    queryRiskEquipmentCrigHistory(){
      this.$store.dispatch('riskEquipmentCrigHistory', {queryString: '?strt_anys_ymd=' + this.selectedDate.startDate + '&end_anys_ymd=' + this.selectedDate.endDate + '&eqmt_id=' + this.eqmt_id})
      this.$store.dispatch('riskEquipmentPartDbHistory', {queryString: '?anys_ymd=' + this.selectedDate.startDate + '&end_anys_ymd=' + this.selectedDate.endDate + '&eqmt_id=' + this.eqmt_id})
      this.$store.dispatch('riskEquipmentKsec3History', {queryString: '?anys_ymd=' + this.selectedDate.startDate + '&end_anys_ymd=' + this.selectedDate.endDate + '&eqmt_id=' + this.eqmt_id})
      // this.$store.dispatch('riskEquipmentCrigHistory', {queryString: '?strt_anys_ymd=20200929&end_anys_ymd=20201001&eqmt_id=E00001'})
      // this.$store.dispatch('riskEquipmentPartDbHistory', {queryString: '?anys_ymd=20200929&end_anys_ymd=20201001&eqmt_id=E00001'})
      // this.$store.dispatch('riskEquipmentKsec3History', {queryString: '?anys_ymd=20200929&end_anys_ymd=20201001&eqmt_id=E00001'})
    },
    getTableHeader(str){
      return (str === 'date') ? '날짜' : str + '시';
    },
    onChangedDate(range){
      console.log(range, 'onChangedDate<<<<')
      this.selectedDate.startDate = dayjs(range.startDate).format('YYYYMMDD');
      this.selectedDate.endDate = dayjs(range.endDate).format('YYYYMMDD');
      this.queryRiskEquipmentCrigHistory();
    },
    popupAerialPhoto(info){
      console.log(info)
      this.isShowViewer = !this.isShowViewer
    },
    onClosePopup(){
      this.isShowViewer = false;
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