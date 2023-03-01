<template>
  <div>
    <PageTitle/>
    <div class="page-box filtering">
      <RiskFiltering @change="onChangeFilter"/>
    </div>
    <div class="page-box">
      <PanelTitle title="시설 목록"/>

      <table class="default-table">
        <thead>
        <tr>
          <th>No.</th>
          <th>시설</th>
          <th>위험 단계</th>
          <th>통합 위험률</th>
          <th>최근 분석 시간</th>
          <th class="w200">상세</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in riskHistory.list"
            :key="index">
          <td>{{ getNumberByRowOrder(item.RO) }}</td>
          <td>{{ item.procs_nm }}</td>
          <td>
            <RiskStepLabel
                :enforce-step-suffix="true"
                :name="item.risk_step_nm"
                :code="item.risk_step_cd"/>
          </td>
          <th>{{ item.risk_num + item.risk_unit }}</th>
          <td>{{ formatter.date(item.anys_ymdhi) }}</td>
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
      <Pagination :page-index="this.pageIndex"
                  :total-count="riskHistory.totalcount"
                  :page-size="this.pageSize"
                  @change="onChangePage"/>
    </div>
  </div>
</template>

<script>
import PanelTitle from "@/components/ui/PanelTitle";
import RiskStepLabel from "@/components/ui/RiskStepLabel";
import {mapState} from "vuex";
import formatter from "@/modules/formatter";
import RiskFiltering from "@/components/ui/RiskFiltering";
import Pagination from "@/components/table/Pagination";
import PageTitle from "@/components/panels/PageTitle";

export default {
  name: "RiskHistory",
  components: {PageTitle, Pagination, RiskFiltering, RiskStepLabel, PanelTitle},

  computed: {
    ...mapState([
      'riskHistory'
    ])
  },

  data() {
    return {
      formatter,
      pageIndex: 1,
      pageSize: 20,
      filtering: 'NORL,CARE,WARG,DGER'
    }
  },

  created() {
    // 최초는 전체 선택
    this.queryList();
  },

  methods: {
    queryList(){
      let payload = {
        queryString: '?pagesize=' + this.pageSize + '&pageindex=' + this.pageIndex + '&risk_step_cd=' + this.filtering
      }
      // console.log('payload:>>', payload)
      this.$store.dispatch('riskHistory', payload);
    },

    getNumberByRowOrder(rowOrder){
      return this.riskHistory.totalcount - rowOrder + 1;
    },

    onChangeFilter(filter){
      console.log(filter)
      this.pageIndex = 1;
      this.filtering = filter;
      this.queryList();
    },

    onChangePage(index){
      this.pageIndex = index;
      console.log(index);
      this.queryList();
    },

  }

}
</script>

<style scoped lang="scss">
@import "src/assets/scss/table";
</style>