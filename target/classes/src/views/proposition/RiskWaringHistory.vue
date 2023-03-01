<template>
  <div>
    <PageTitle/>
    <div class="page-box filtering">
      <ProcessEquipmentFiltering has-date-range-picker
                                 process-title="검 색"
                                 :equipment-title="null"
                                 :start-date="filtering.startDate"
                                 :end-date="filtering.endDate"
                                 @filtering="onFiltering"/>
    </div>
    <div class="page-box">
      <PanelTitle title="위험도 알림 이력"/>
      <table class="default-table">
        <thead>
        <tr>
          <th>No.</th>
          <th>생산공정</th>
          <th>위험설비</th>
          <th>위험단계</th>
          <th>고장확률</th>
          <th>발송 메시지(PUSH)</th>
          <th>발송 시간</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in riskNoticeHistoryList.list" :key="index">
          <td>{{ getNumberByRowOrder(item.RO) }}</td>
          <td>{{ item.procs_nm }}</td>
          <td>
            <router-link :to="'/analysis/equipment/' + item.eqmt_id + '?procs_nm=' + item.procs_nm + '&eqmt_nm=' + item.eqmt_nm"
                         class="equipment-link">
              {{ item.eqmt_nm }}
              <span>&gt;</span>
            </router-link>
          </td>
          <td>
            <RiskStepLabel :name="item.risk_step_nm" :code="item.risk_step_cd"/>
          </td>
          <td>{{ item.cbm_fixd_rate + '%' }}</td>
          <td class="text-left">{{ item.send_msg }}</td>
          <td>{{ dayjs(item.send_ymdhis).format('YYYY-MM-DD HH:mm:ss') }}</td>
        </tr>
        </tbody>
      </table>
      <Pagination :page-index="this.page.index"
                  :total-count="riskNoticeHistoryList.totalcount"
                  :page-size="this.page.size"
                  @change="onChangePage"/>
    </div>
  </div>
</template>

<script>
import PageTitle from "@/components/panels/PageTitle";
import dayjs from "dayjs";
import ProcessEquipmentFiltering from "@/components/ui/ProcessEquipmentFiltering";
import {mapState} from "vuex";
import Pagination from "@/components/table/Pagination";
import PanelTitle from "@/components/ui/PanelTitle";
import RiskStepLabel from "@/components/ui/RiskStepLabel";
export default {
  name: "WorkerErrorProposition",
  components: {RiskStepLabel, PanelTitle, Pagination, ProcessEquipmentFiltering, PageTitle},

  computed: {
    ...mapState('common', [
      'riskNoticeHistoryList'
    ]),
    total(){
      return (this.riskNoticeHistoryList.totalcount) ? '총 ' + this.riskNoticeHistoryList.totalcount + ' 건' : ''
    }
  },

  created() {
    this.queryList();
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
      }
    }
  },

  methods: {
    queryList(){
      // this.$store.dispatch('gasRiskProposal', {queryString: '?strt_props_ymd=202009001&end_props_ymd=20201002&eqmt_id=E00001'})
      // 페이징 붙이고
      let qs = '?send_type_cd=SD0001&pagesize=' + this.page.size + '&pageindex=' + this.page.index;
      // 기간 붙이고
      qs += '&strt_send_ymd=' + this.filtering.startDate + '&end_send_ymd=' + this.filtering.endDate;
      // 공정/설비 붙인다
      qs += '&procs_id=' + this.filtering.processId + '&eqmt_id=' + this.filtering.equipmentId
      this.$store.dispatch('common/riskNoticeHistoryList', {queryString: qs});
    },
    getNumberByRowOrder(rowOrder){
      return this.riskNoticeHistoryList.totalcount - rowOrder + 1;
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
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/table";
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