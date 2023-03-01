<template>
  <div>
    <PageTitle/>

    <RealtimeRiskInfo :all="realtimeRisk.all"/>

    <div class="page-box">
      <PanelTitle title="위험도 높은 시설"
                  desc="실시간 위험도 높은 시설"/>

      <table class="default-table">
        <thead>
        <tr>
          <th>시설</th>
          <th>통합 위험 단계</th>
          <th>통합 위험률</th>
          <th class="w200"></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in realtimeRisk.procs"
            :key="item.procs_id">
          <td>{{ item.procs_nm }}</td>
          <td>
            <RiskStepLabel
                :name="item.risk_step_nm"
                :code="item.risk_step_cd"/>
          </td>
          <th>{{ item.risk_num + item.risk_unit }}</th>
          <td>
            <router-link
                :to="{ path:'/analysis/process/' + item.procs_id, query: {procs_nm: item.procs_nm}}"
            >
              <button class="outline-dark">
                상세보기
                <img src="~@/assets/img/caret-right.png" alt="상세보기">
              </button>
            </router-link>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="page-box">
      <PanelTitle title="위험도 높은 설비"
                  desc="실시간 위험도 높은 설비"/>

      <table class="default-table double-row-header">
        <thead>
        <tr>
          <th rowspan="2">시설</th>
          <th rowspan="2">이상 설비</th>
          <th rowspan="2">위험 단계</th>
          <th rowspan="2">통합 위혐률</th>
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
        <tr v-for="item in realtimeRisk.eqmt"
            :key="item.eqmt_id">
          <td>{{ item.procs_nm }}</td>
          <td>{{ item.eqmt_nm }}</td>
          <td>
            <RiskStepLabel :useStepByName="true" :name="item.risk_step_nm" />
          </td>
          <th>{{ item.risk_num + item.risk_unit }}</th>
          <th>{{ item.norl }}건</th>
          <th>{{ item.care }}건</th>
          <th>{{ item.warg }}건</th>
          <th>{{ item.eror }}건</th>
          <td>
            <button class="outline-dark" @click="showPopup('KIES', item.procs_nm, item.eqmt_nm, item.eqmt_id, realtimeRisk.all.anys_ymdhi)">
              상세보기
              <img src="~@/assets/img/caret-right.png" alt="상세보기">
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="page-box">
      <PanelTitle title="설비 분석 데이터"
                  desc="실시간 위험도 높은 설비의 24시간 분석 데이터"/>

      <PanelTab :items="realtimeRisk.eqmt"
                display-prop-name="eqmt_nm"
                :emit-prop-list="['eqmt_id','eqmt_nm']"
                @change="facilityTabChanged"/>

      <PanelSubTitle title="센서 데이터 분석 위험도" />

      <ChartBox :raw-data="realtimeEquipmentCrig.list" chart-type="MAIN"/>

      <table class="default-table double-row-header">
        <thead>
        <tr>
          <th rowspan="2" colspan="2" >날짜/시간</th>
          <th v-for="item in crigYmdList"
              :key="item.ymd"
              :colspan="item.count">
            {{ formatter.date(item.ymd) }}
          </th>
        </tr>
        <tr>
          <th v-for="(item, idx) in realtimeEquipmentCrig.list"
              :key="idx">
            {{ item.qtime }}시
          </th>
        </tr>
        </thead>
        <tbody v-if="realtimeEquipmentCrig.list.length">
        <tr>
          <th colspan="2">위험단계</th>
          <td v-for="(item, idx) in realtimeEquipmentCrig.list"
              :key="idx">
            <RiskLabel :text-data="item.risk_rate" :showRiskLabel="false" />
          </td>
        </tr>
        <tr>
          <th colspan="2">발생확률</th>
          <th v-for="(item, idx) in realtimeEquipmentCrig.list"
              :key="idx">
            <RiskLabel :text-data="item.risk_rate" :showRiskStep="false" />
          </th>
        </tr>
        <tr>
          <th rowspan="4">사고<br>시나리오<br>위험 단계</th>
          <th>정상</th>
          <th v-for="(item, idx) in realtimeEquipmentCrig.list"
              :key="idx">
            {{ item.norl }}건
          </th>
        </tr>
        <tr>
          <th>주의</th>
          <th v-for="(item, idx) in realtimeEquipmentCrig.list"
              :key="idx">
            {{ item.care }}건
          </th>
        </tr>
        <tr>
          <th>경고</th>
          <th v-for="(item, idx) in realtimeEquipmentCrig.list"
              :key="idx">
            {{ item.warg }}건
          </th>
        </tr>
        <tr>
          <th>위험</th>
          <th v-for="(item, idx) in realtimeEquipmentCrig.list"
              :key="idx">
            {{ item.eror }}건
          </th>
        </tr>
        <tr>
          <th colspan="2">사고 시나리오</th>
          <td v-for="(item, idx) in realtimeEquipmentCrig.list" :key="idx">
            <button v-if="item" class="outline-dark" @click="showPopup('CRIG', item.procs_nm, selectedEquipmentName, selectedEquipmentId, item.ymd + item.qtime + '00')">
              상세보기
              <img src="~@/assets/img/caret-right.png" alt="상세보기">
            </button>
            <span v-else>-</span>
          </td>
        </tr>
        </tbody>
      </table>

      <PanelSubTitle title="예지보전 위험도" />

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
        <tr v-for="(item, index) in realtimeEquipmentPartDb.list" :key="index">
          <td>{{ formatter.date(item.anys_ymdh, 'MM/dd hh:mm') }}</td>
          <td><RiskLabel :text-data="item.risk_value" :showRiskLabel="false"/></td>
          <th><RiskLabel :text-data="item.risk_value" :showRiskStep="false"/></th>
          <th>{{ item.NORL }}건</th>
          <th>{{ item.CARE }}건</th>
          <th>{{ item.WARG }}건</th>
          <th>{{ item.EROR }}건</th>
          <td>
            <button class="outline-dark" @click="showPopup('PARTDB', null, item.eqmt_nm, selectedEquipmentId, item.anys_ymdh + '00')">
              상세보기
              <img src="~@/assets/img/caret-right.png" alt="상세보기">
            </button>
          </td>
        </tr>
        </tbody>
      </table>

      <PanelSubTitle title="영상 데이터 분석 위험도" />

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
        <tr v-for="(item, index) in realtimeEquipmentKsec.list" :key="index">
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
            <button class="outline-dark" @click="showPopup('KSEC', null, item.eqmt_nm, selectedEquipmentId, item.anys_ymdh + '00')">
              상세보기
              <img src="~@/assets/img/caret-right.png" alt="상세보기">
            </button>
          </td>
          <!-- <td class="flex-in-td">
            <RiskLabel :text-data="realtimeEquipmentKsec.list[0].risk_value"/>
            <a v-if="realtimeEquipmentKsec.list[3]"
               :href="realtimeEquipmentKsec.list[3].risk_value"
               target="_blank">
              <button class="default-button">항공사진 &gt;</button>
            </a>
          </td>
          <td>
            <RiskLabel :text-data="realtimeEquipmentKsec.list[1].risk_value"/>
          </td>
          <td>
            <RiskLabel :text-data="realtimeEquipmentKsec.list[2].risk_value"/>
          </td>
          <td>{{ realtimeEquipmentKsec.list[4].risk_value }}</td>
          <td>{{ realtimeEquipmentKsec.list[5].risk_value }}</td>
          <td>{{ realtimeEquipmentKsec.list[6].risk_value }}</td> -->
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
import RealtimeRiskInfo from "@/components/panels/RealtimeRiskInfo";
import PanelTitle from "@/components/ui/PanelTitle";
import RiskStepLabel from "@/components/ui/RiskStepLabel";
import RiskLabel from "@/components/ui/RiskLabel";
import PanelTab from "@/components/ui/PanelTab";
import { mapState } from 'vuex';
import PanelSubTitle from "@/components/ui/PanelSubTitle";
import ChartBox from "@/components/chart/ChartBox";
import formatter from "@/modules/formatter";
import PageTitle from "@/components/panels/PageTitle";
import AccidentScenarioPopup from "@/components/ui/AccidentScenarioPopup";

// import PageTitle from "@/components/panels/PageTitle";
export default {
  name: "RealTimeRisk",
  components: {PageTitle, ChartBox, PanelSubTitle, PanelTab, RiskLabel, RiskStepLabel, PanelTitle, RealtimeRiskInfo, AccidentScenarioPopup},

  data(){
    return{
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

  computed: {
    ...mapState([
        'realtimeRisk',
        'realtimeEquipmentCrig',
        'realtimeEquipmentPartDb',
        'realtimeEquipmentKsec',
        'realtimeAccidentScenarioPopup'
   ]),
    crigYmdList() {
      let list = [];
      this.realtimeEquipmentCrig.list.forEach(item => {
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
    this.queryData();
  },

  methods:{
    queryData(){
      this.$store.dispatch('realtimeRisk')
          .then(() => {
            let info = {
              eqmt_id: this.realtimeRisk.eqmt[0].eqmt_id,
              eqmt_nm: this.realtimeRisk.eqmt[0].eqmt_nm
            }
            this.facilityTabChanged(info);
          })
    },

    facilityTabChanged(info){
      console.log(info)
      this.selectedEquipmentId = info.eqmt_id;
      this.selectedEquipmentName = info.eqmt_nm;
      this.$store.dispatch('realtimeEquipmentCrig', {equipmentId: info.eqmt_id});
      this.$store.dispatch('realtimeEquipmentPartDb', {equipmentId: info.eqmt_id});
      this.$store.dispatch('realtimeEquipmentKsec', {equipmentId: info.eqmt_id});
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
</style>