<template>
  <div>
    <PageTitle/>

    <div class="page-box">
      <Tab :items="tabItems"
            display-prop-name="tab_nm"
            :emit-prop-list="['tab_id','tab_nm']"
            @change="recordTabChanged"/>

      <div v-if="selectedTab.tab_id === 'notification_record'">
        <PanelProcessEquipmentFiltering has-date-range-picker
                                    process-title="설 비"
                                    :equipment-title="null"
                                    :start-date="filtering.startDate"
                                    :end-date="filtering.endDate"
                                    @filtering="onFiltering"/>
        <PanelTitle title="알림 이력" :desc="total"/>
        <table class="default-table double-row-header">
          <thead>
          <tr>
            <th rowspan="2">No</th>
            <th colspan="3">위험 시설</th>
            <th rowspan="2">제어 대상 밸브</th>
            <th rowspan="2">밸브 제어 요청</th>
            <th rowspan="2">이상원인</th>
            <th rowspan="2">발생 일시</th>
          </tr>
          <tr>
            <th>시설</th>
            <th>통합 위험단계</th>
            <th class="no-radius border-right">통합 위험률</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, index) in gasRiskProposalList.list" :key="index">
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskLabel="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskStep="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskLabel="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskStep="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskLabel="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskStep="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskLabel="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskStep="false"/></th>
          </tr>
          </tbody>
        </table>
        <Pagination :page-index="this.page.index"
                    :total-count="gasRiskProposalList.totalcount"
                    :page-size="this.page.size"
                    @change="onChangePage"/>
      </div>
      <div v-else-if="selectedTab.tab_id === 'control_record'">
        <PanelProcessEquipmentFiltering has-date-range-picker
                                    process-title="설 비"
                                    :equipment-title="null"
                                    :start-date="filtering.startDate"
                                    :end-date="filtering.endDate"
                                    @filtering="onFiltering"/>
        <PanelTitle title="제어 이력" :desc="total"/>
        <table class="default-table double-row-header">
          <thead>
          <tr>
            <th rowspan="2">No</th>
            <th colspan="3">위험 시설</th>
            <th rowspan="2">제어 대상 밸브</th>
            <th rowspan="2">밸브 제어 요청자</th>
            <th rowspan="2">발생 일시</th>
          </tr>
          <tr>
            <th>시설</th>
            <th>통합 위험단계</th>
            <th class="no-radius border-right">통합 위험률</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, index) in gasRiskProposalList.list" :key="index">
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskLabel="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskStep="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskLabel="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskStep="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskLabel="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskStep="false"/></th>
              <th><RiskLabel :text-data="item.RISK_RATE" :showRiskStep="false"/></th>
          </tr>
          </tbody>
        </table>
        <Pagination :page-index="this.page.index"
                    :total-count="gasRiskProposalList.totalcount"
                    :page-size="this.page.size"
                    @change="onChangePage"/>
      </div>
     </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import PanelTitle from "@/components/ui/PanelTitle";
import Tab from "@/components/ui/Tab";
import PageTitle from "@/components/panels/PageTitle";
import PanelProcessEquipmentFiltering from "@/components/ui/PanelProcessEquipmentFiltering";
import RiskLabel from "@/components/ui/RiskLabel";
import dayjs from "dayjs";
import Pagination from "@/components/table/Pagination";
export default {
  name: "GasRiskProposition",
  components: {Pagination, RiskLabel, PanelProcessEquipmentFiltering, Tab, PageTitle, PanelTitle},

  computed: {
    ...mapState([
      'gasRiskProposalList'
    ]),
    total(){
      return (this.gasRiskProposalList.totalcount) ? '총 ' + this.gasRiskProposalList.totalcount + ' 건' : ''
    }
  },

  created() {
    if (this.selectedTab.tab_id === 'notification_record')
    {
      // this.queryList();
    }
    else if (this.selectedTab.tab_id === 'control_record')
    {
      // this.queryList();
    }
  },

  data(){
    return {
      dayjs,
      page: {
        index: 1,
        size: 20,
      },
      filtering: {
        startDate: dayjs().startOf('month').format('YYYYMMDD'),
        endDate: dayjs().endOf('month').format('YYYYMMDD'),
        processId: '',
        equipmentId: ''
      },
      tabItems: [{ tab_id: 'notification_record', tab_nm: '알림 이력' }, { tab_id: 'control_record', tab_nm: '제어 이력' }],
      selectedTab: { tab_id: 'notification_record', tab_nm: '알림 이력' }
    }
  },

  methods: {
    queryList(){
      // this.$store.dispatch('gasRiskProposal', {queryString: '?strt_props_ymd=202009001&end_props_ymd=20201002&eqmt_id=E00001'})
      // 페이징 붙이고
      let qs = '?pagesize=' + this.page.size + '&pageindex=' + this.page.index;
      // 기간 붙이고
      qs += '&strt_props_ymd=' + this.filtering.startDate + '&end_props_ymd=' + this.filtering.endDate;
      // 공정/설비 붙인다
      qs += '&procs_id=' + this.filtering.processId + '&eqmt_id=' + this.filtering.equipmentId
      this.$store.dispatch('gasRiskProposalList', {queryString: qs});
    },
    getNumberByRowOrder(rowOrder){
      return this.gasRiskProposalList.totalcount - rowOrder + 1;
    },
    onFiltering(options){
      console.log(options)
      this.filtering = options;
      this.page.index = 1;// 필터링 하면 페이지 1로 설정
      this.queryList();
    },

    onChangePage(index){
      this.page.index = index;
      this.queryList();
    },

    recordTabChanged(selectedTab){
      // console.log(selectdTab);
      this.selectedTab = selectedTab;
      if (this.selectedTab.tab_id === 'notification_record')
      {
        // this.queryList();
      }
      else if (this.selectedTab.tab_id === 'control_record')
      {
        // this.queryList();
      }
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/table";
@import "src/assets/scss/variables";

a.equipment-link{
  border-bottom: 2px solid #b1b1b1;
  padding: 0 5px 3px;

  &:hover{
    border-bottom-color: $turquoise-color;
  }
  > span{
    margin-left: 5px;
  }
}
</style>