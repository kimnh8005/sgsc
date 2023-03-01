<template>
  <div>
    <PageTitle/>
    <div class="page-box filter-box filtering">
      <h4>검 색</h4>
      <select v-model="selectFilter">
        <option value="all">전체 설비</option>
        <option v-for="item in MUAL_TYPE_CD"
                :value="item.cd_id"
                :key="item.cd_id">
          {{ item.cd_nm }}
        </option>
      </select>

      <button @click="queryList">검 색</button>
    </div>
    <div class="page-box">
      <PanelTitle title="대응 매뉴얼" :desc="total"/>

      <table class="default-table">
        <thead>
        <tr>
          <th class="w80">No.</th>
          <th class="w200">분류</th>
          <th>제목</th>
          <th class="w200">등록일</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in incidentManualList.list" :key="index">
          <td>{{ getNumberByRowOrder(item.RO) }}</td>
          <td>{{ item.mual_type_nm }}</td>
          <td class="text-left">
            <router-link :to="'/support/manual/view/'+ item.mual_sid">
              {{ item.mual_title }}
            </router-link>
          </td>
          <td>{{ dayjs(item.update_date).format('YYYY-MM-DD') }}</td>
        </tr>
        </tbody>
      </table>

      <div class="button-group">
        <button @click="goWrite"
                v-if="userData.auth_sid > 99">
          등 록
        </button>
      </div>

      <Pagination :page-index="this.pageIndex"
                  :total-count="incidentManualList.totalcount"
                  :page-size="this.pageSize"
                  @change="onChangePage"/>
    </div>
  </div>
</template>

<script>
import PanelTitle from "@/components/ui/PanelTitle";
import PageTitle from "@/components/panels/PageTitle";
import {mapGetters, mapState} from "vuex";
import dayjs from "dayjs";
import Pagination from "@/components/table/Pagination";

export default {
  name: "IncidentResponseManual",
  components: {Pagination, PageTitle, PanelTitle},

  computed: {
    ...mapState('common/manual',[
      'incidentManualList'
    ]),
    ...mapState('common/code',[
      'MUAL_TYPE_CD'
    ]),
    ...mapGetters([
      'userData'
    ]),

    total() {
      return (this.incidentManualList.totalcount) ? '총 '+ this.incidentManualList.totalcount + ' 개' : '아직 등록된 항목이 없습니다.'
    }
  },
  created() {
    this.queryList();
    this.$store.dispatch('common/code/query', {code: 'MUAL_TYPE_CD'})
  },

  data() {
    return {
      dayjs,
      selectFilter: 'all',
      pageIndex: 1,
      pageSize: 20,
    }
  },

  methods: {
    queryList(){
      let qs = '?pagesize=' + this.pageSize + '&pageindex=' + this.pageIndex;
      qs += (this.selectFilter === 'all') ? '' : '&mual_type_cd=' + this.selectFilter;
      this.$store.dispatch('common/manual/incidentManualList', {queryString: qs});
    },
    getNumberByRowOrder(rowOrder){
      return this.incidentManualList.totalcount - rowOrder + 1;
    },
    onChangePage(index){
      this.pageIndex = index;
      console.log(index);
      this.queryList();
    },
    goWrite(){
      this.$router.push('/support/manual/write')
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/table";
.filter-box{
  display: flex;
  align-items: center;

  > h4{
    margin-right: 30px;
  }

  select {
    height: 40px;
    min-width: 150px;
  }
}

</style>