<template>
  <div>
    <PageTitle/>

    <div class="page-box">
      <PanelTitle title="공지사항" :desc="total"/>

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
        <tr v-for="(item, index) in noticeList.list" :key="index">
          <td>{{ getNumberByRowOrder(item.RO) }}</td>
          <td>{{ item.noti_type_nm }}</td>
          <td class="text-left">
            <router-link :to="'/notice/view/'+ item.noti_sid">
              {{ item.noti_title }}
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

      <Pagination :page-index="this.page.index"
                  :total-count="noticeList.totalcount"
                  :page-size="this.page.size"
                  @change="onChangePage"/>
    </div>
  </div>
</template>

<script>
import PageTitle from "@/components/panels/PageTitle";
import {mapGetters, mapState} from "vuex";
import dayjs from "dayjs";
import Pagination from "@/components/table/Pagination";
import PanelTitle from "@/components/ui/PanelTitle";
export default {
  name: "NoticeList",
  components: {PanelTitle, Pagination, PageTitle},

  computed: {
    ...mapState('common/notice', [
      'noticeList'
    ]),
    ...mapGetters([
      'userData'
    ]),

    total() {
      return (this.noticeList.totalcount) ? '총 '+ this.noticeList.totalcount + ' 개' : '아직 등록된 항목이 없습니다.'
    }
  },

  created() {
    this.queryList();
  },

  data() {
    return {
      dayjs,
      selectFilter: 'all',
      page: {
        index: 1,
        size: 10
      }
    }
  },

  methods: {
    queryList(){
      let qs = '?pagesize=' + this.page.size + '&pageindex=' + this.page.index;
      this.$store.dispatch('common/notice/noticeList', {queryString: qs});
    },
    getNumberByRowOrder(rowOrder){
      return this.noticeList.totalcount - rowOrder + 1;
    },
    onChangePage(index){
      this.page.index = index;
      console.log(index);
      this.queryList();
    },
    goWrite(){
      this.$router.push('/notice/write')
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/table";

</style>