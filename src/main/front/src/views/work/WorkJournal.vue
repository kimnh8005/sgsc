<template>
  <div>
    <PageTitle/>
    <div class="page-box filtering">
      <ProcessEquipmentFiltering has-date-range-picker
                                 has-work-stat-selector
                                 process-title="검 색"
                                 datepicker-title="목표시작"
                                 :equipment-title="null"
                                 :start-date="filtering.startDate"
                                 :end-date="filtering.endDate"
                                 @filtering="onFiltering"/>
    </div>
    <div class="page-box">
      <PanelTitle title="생산공정 목록" :desc="total"/>
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
          <th>목표 시작</th>
          <th>목표 종료</th>
          <th>상태</th>
          <th>수정</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in workJournalList.list" :key="index">
          <td>{{ getNumberByRowOrder(item.RO) }}</td>
          <td>{{ item.procs_nm }}</td>
          <td>{{ item.eqmt_nm }}</td>
          <td>작업번호</td>
          <td>{{ item.work_type_nm }}</td>
          <td>{{ item.unit_procs_nm }}</td>
          <td class="description">{{ item.description }}</td>
          <td>{{ dayjs(item.goal_strt_dt).format('YYYY-MM-DD') }}</td>
          <td>{{ dayjs(item.goal_cmpt_dt).format('YYYY-MM-DD') }}</td>
          <td>{{ getWorkStatName(item.work_stat_cd) }}</td>
          <td>
            <router-link :to="'/journal/view/' + item.jnl_sid" class="btn">
              수정
            </router-link>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import PanelTitle from "@/components/ui/PanelTitle";
import PageTitle from "@/components/panels/PageTitle";
import ProcessEquipmentFiltering from "@/components/ui/ProcessEquipmentFiltering";
import dayjs from "dayjs";
import {mapState} from "vuex";
export default {
  name: "WorkJournal",
  components: {ProcessEquipmentFiltering, PageTitle, PanelTitle},

  computed: {
    ...mapState('common/code', [
      'WORK_STAT_CD'
    ]),
    ...mapState([
      'workJournalList'
    ]),
    total(){
      return (this.workJournalList.totalcount) ? '총 ' + this.workJournalList.totalcount + ' 건' : ''
    }
  },

  created() {
    this.$store.dispatch('common/code/query', {code: 'WORK_STAT_CD'})
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
        equipmentId: '',
        workStat: ''
      }
    }
  },

  methods: {
    queryList(){
      // this.$store.dispatch('gasRiskProposal', {queryString: '?strt_props_ymd=202009001&end_props_ymd=20201002&eqmt_id=E00001'})
      // 페이징 붙이고
      let qs = '?pagesize=' + this.page.size + '&pageindex=' + this.page.index;
      // 기간 붙이고
      qs += '&goal_strt_dt=' + this.filtering.startDate + '&goal_cmpt_dt=' + this.filtering.endDate;
      // 공정/설비 붙인다
      qs += '&procs_id=' + this.filtering.processId + '&eqmt_id=' + this.filtering.equipmentId
      // 상태 정보도 붙인다.
      qs += '&work_stat_cd=' + this.filtering.workStat;

      this.$store.dispatch('workJournalList', {queryString: qs});
    },
    getNumberByRowOrder(rowOrder){
      return this.workJournalList.totalcount - rowOrder + 1;
    },
    getWorkStatName(code){
      let name = '-'
      this.WORK_STAT_CD.forEach(item => {
        if (item.cd_id === code)
          name = item.cd_nm;
      })
      return name;

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
table td.description{
  max-width: 470px;
}
</style>