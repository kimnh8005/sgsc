<template>
  <div>
    <PageTitle title="사고 대응 매뉴얼"/>

    <div class="page-box">
      <PanelTitle title="대응 매뉴얼"/>

      <div class="article-view">
        <div class="article-header">
          <div class="manual-type">{{ incidentManual.current.mual_type_nm }}</div>
          <h2>{{ incidentManual.current.mual_title }}</h2>
          <div class="date">
            <span>{{ dayjs(incidentManual.current.regist_date).format('YYYY.MM.DD HH:mm') }}</span>
            <span class="update-time">{{ updateDate }}</span>
          </div>
        </div>
        <div class="article-body" v-html="incidentManual.current.mual_cnt">
        </div>
        <div v-if="incidentManual.current.downloadurl" class="attach-file">
          <div class="attach-inner">
            첨부파일 :
            <a :href="incidentManual.current.downloadurl" download>
              {{ incidentManual.current.file_nm }}
            </a>
          </div>
        </div>
      </div>

      <div class="button-group center-type article-buttons">
        <button @click="goList">목 록</button>
        <button @click="goEdit" v-if="userData.auth_sid > 99">수 정</button>
        <button @click="doDelete" v-if="userData.auth_sid > 99" class="btn-dark">삭제</button>
      </div>

      <table class="default-table no-header">
        <colgroup>
          <col class="w80">
          <col class="w200">
          <col>
          <col class="w200">
        </colgroup>
        <tbody>
        <tr>
          <template v-if="incidentManual.next">
            <td class="next-article-icon">다음 글</td>
            <td>{{ incidentManual.next.mual_type_nm }}</td>
            <td class="text-left">
              <router-link :to="'/support/manual/view/'+ incidentManual.next.mual_sid">
                {{ incidentManual.next.mual_title }}
              </router-link>
            </td>
            <td>{{ dayjs(incidentManual.next.update_date).format('YYYY-MM-DD') }}</td>
          </template>
          <td v-else colspan="4" class="has-no-item">다음 글이 없습니다.</td>
        </tr>
        <tr>
          <template v-if="incidentManual.prev">
            <td class="prev-article-icon">이전 글</td>
            <td>{{ incidentManual.prev.mual_type_nm }}</td>
            <td class="text-left">
              <router-link :to="'/support/manual/view/'+ incidentManual.prev.mual_sid">
                {{ incidentManual.prev.mual_title }}
              </router-link>
            </td>
            <td>{{ dayjs(incidentManual.prev.update_date).format('YYYY-MM-DD') }}</td>
          </template>
          <td v-else colspan="4" class="has-no-item">이전 글이 없습니다.</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import PanelTitle from "@/components/ui/PanelTitle";
import PageTitle from "@/components/panels/PageTitle";
import {mapGetters, mapState} from "vuex";
import dayjs from "dayjs";

export default {
  name: "IncidentResponseManualView",
  components: {PageTitle, PanelTitle},

  props: {
    mual_sid: String
  },

  watch: {
    '$route'(){
      this.queryList();
    }
  },

  computed: {
    ...mapState('common/manual',[
      'incidentManual'
    ]),
    ...mapGetters([
      'userData'
    ]),

    updateDate() {
      let bool = (this.incidentManual.current.regist_date === this.incidentManual.current.update_date);
      return (bool) ? '' : '최종 수정 : ' + dayjs(this.incidentManual.current.update_date).format('YYYY.MM.DD HH:mm');
    }
  },
  created() {
    this.queryList();
  },

  data() {
    return {
      dayjs,
    }
  },

  methods: {
    queryList(){
      this.$store.dispatch('common/manual/incidentManual', {mual_sid: this.mual_sid});
    },
    goList(){
      this.$router.push('/support/manual')
    },
    goEdit(){
      this.$router.push('/support/manual/edit/'+ this.mual_sid)
    },
    doDelete(){
      let bool = confirm('정말 삭제 하시겠습니까?');
      if (bool){
        let payload = {'mual_sid': this.mual_sid}
        this.$store.dispatch('common/manual/deleteIncidentManual', payload )
            .then((response) => {
              if(response.data.resultCode === 'NRL001'){
                this.goList();
              }
            })
      }
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
.article-view{

  .article-header{
    border-top: 2px solid $gray-line-color;
    border-bottom: 1px solid $gray-line-color;
    padding: 15px 30px;
    
    .manual-type{
      color: $light-font-color;
      font-size: 16px;
      height: 30px;
    }
    h2{
      font-size: 20px;
      font-weight: 400;
      height: 40px;
    }
    .date{
      color: #9b9b9b;
      font-size: 15px;
      height: 25px;

      .update-time{
        margin-left: 20px;
      }
    }
  }
  .article-body{
    min-height: 100px;
    padding: 30px;
    white-space: pre-line;
    word-break: break-all;
    line-height: 1.6em;
  }
  .attach-file{
    background-color: #f6f6f6;
    margin-bottom: 28px;
    padding: 2px 0;

    > .attach-inner{
      border: 1px solid $gray-line-color;
      padding: 0 30px;
      display: flex;
      align-items: center;
      border-left: 0;
      border-right: 0;
      min-height: 96px;
      box-sizing: border-box;

      a{
        margin-left: 10px;
      }
    }
  }
}
.article-buttons{
  margin-bottom: 40px;
}
.next-article-icon{
  background: url("~@/assets/img/icon-up.png") no-repeat left center;
}
.prev-article-icon{
  background: url("~@/assets/img/icon-down.png") no-repeat left center;
}
</style>