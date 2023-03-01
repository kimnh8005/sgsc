<template>
  <div>
    <PageTitle/>

    <div class="page-graph-box">
      <BlackPieChart class="pie-type"/>
      <WeekLineChart class="line-type"/>
    </div>

    <div class="page-box filtering">
      <ProcessEquipmentFiltering has-date-range-picker
                                 process-title="검 색"
                                 :equipment-title="null"
                                 :start-date="filtering.startDate"
                                 :end-date="filtering.endDate"
                                 @filtering="onFiltering"/>
    </div>
    <div class="page-box">
      <table class="default-table">
        <thead>
        <tr>
          <th>No.</th>
          <th>생산공정</th>
          <th>설비</th>
          <th>작업번호</th>
          <th>작업유형</th>
          <th>단위공정</th>
          <th>작업 설명</th>
          <th>실제 시작</th>
          <th>실제 종료</th>
          <th>작업 오류율</th>
          <th>보기</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in humanErrorProposalList.list" :key="index">
          <td>{{ getNumberByRowOrder(item.RO) }}</td>
          <td>{{ item.procs_nm }}</td>
          <td>{{ item.eqmt_nm }}</td>
          <td>작업번호</td>
          <td>{{ item.work_type_nm }}</td>
          <td>{{ item.unit_procs_nm }}</td>
          <td>{{ item.description }}</td>
          <td>{{ dayjs(item.real_rise_dt).format('YYYY-MM-DD') }}</td>
          <td>{{ dayjs(item.real_cmpt_dt).format('YYYY-MM-DD') }}</td>
          <td>{{ item.work_err_rate + '%'}}</td>
          <td>
            <button>
              상세보기
              <img src="@/assets/img/caret-right.png">
            </button>
          </td>
        </tr>
        </tbody>
      </table>
      <Pagination :page-index="this.page.index"
                  :total-count="humanErrorProposalList.totalcount"
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
import BlackPieChart from "@/components/chart/BlackPieChart";
import WeekLineChart from "@/components/chart/WeekLineChart";
export default {
  name: "WorkerErrorProposition",
  components: {WeekLineChart, BlackPieChart, Pagination, ProcessEquipmentFiltering, PageTitle},

  computed: {
    ...mapState([
      'humanErrorProposalList'
    ]),
    total(){
      return (this.humanErrorProposalList.totalcount) ? '총 ' + this.humanErrorProposalList.totalcount + ' 건' : ''
    }
  },

  created() {
    // this.$store.dispatch('common/code/query', {code: 'WORK_TYPE_CD'})
    // this.$store.dispatch('common/code/query', {code: 'UNIT_PROCS_CD'})
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
      let qs = '?pagesize=' + this.page.size + '&pageindex=' + this.page.index;
      // 기간 붙이고
      qs += '&strt_props_ymd=' + this.filtering.startDate + '&end_props_ymd=' + this.filtering.endDate;
      // 공정/설비 붙인다
      qs += '&procs_id=' + this.filtering.processId + '&eqmt_id=' + this.filtering.equipmentId
      this.$store.dispatch('humanErrorProposalList', {queryString: qs});
    },
    getNumberByRowOrder(rowOrder){
      return this.humanErrorProposalList.totalcount - rowOrder + 1;
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
@import "src/assets/scss/variables";
.page-graph-box{
  margin-bottom: 30px;
  color: #fff;
  display: flex;

  > .pie-type{
    width: 500px;
    flex-shrink: 0;
  }

  > .line-type{
    flex-grow: 1;
    margin-left: 30px;
  }
}
</style>