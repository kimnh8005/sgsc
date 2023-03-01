<template>
  <div>
    <PageTitle title="작업 일지"/>

    <div class="page-box">
      <table class="view-table">
        <tbody>
        <tr class="table-guide">
          <th></th>
          <td></td>
          <th></th>
          <td></td>
        </tr>
        <tr>
          <th>작업번호</th>
          <td colspan="3">{{ jnl_sid }}</td>
        </tr>
        <tr>
          <th>작업유형</th>
          <td>{{ workJournal.work_type_nm }}</td>
          <th>단위공정</th>
          <td>{{ workJournal.unit_procs_nm }}</td>
        </tr>
        <tr>
          <th>설비</th>
          <td colspan="3">
            {{ workJournal.procs_nm }}
            &gt;
            {{ workJournal.eqmt_nm }}
          </td>
        </tr>
        <tr>
          <th>작업설명</th>
          <td colspan="3">{{ workJournal.description }}</td>
        </tr>
        </tbody>
      </table>

      <table class="view-table">
        <tbody>
        <tr class="table-guide">
          <th></th>
          <td></td>
          <th></th>
          <td></td>
        </tr>
        <tr>
          <th>작업상태</th>
          <td colspan="3">{{ workJournal.work_stat_nm }}</td>
        </tr>
        <tr>
          <th>목표 시작</th>
          <td>{{ workJournal.goal_strt_dt ? dayjs(workJournal.goal_strt_dt).format('YYYY-MM-DD') : '' }}</td>
          <th style="width: 120px;">목표 완료</th>
          <td>{{ workJournal.goal_cmpt_dt ? dayjs(workJournal.goal_cmpt_dt).format('YYYY-MM-DD') : '' }}</td>
        </tr>
        <tr>
          <th>실제 시작</th>
          <td>{{ workJournal.real_strt_dt ? dayjs(workJournal.real_strt_dt).format('YYYY-MM-DD') : '' }}</td>
          <th>실제 완료</th>
          <td>{{ workJournal.real_cmpt_dt ? dayjs(workJournal.real_cmpt_dt).format('YYYY-MM-DD') : '' }}</td>
        </tr>
        <tr>
          <th>작업자</th>
          <td colspan="3">
            {{ workJournal.wrk_dept_nm }}
            <span v-if="workJournal.wrk_dept_cd && workJournal.wrk_user_nm ">{{ '&gt;'}}</span>
            {{ workJournal.wrk_user_nm }}
          </td>
        </tr>
        </tbody>
      </table>

      <table class="view-table">
        <tbody>
        <tr class="multi-line">
          <th>작업지시</th>
          <td>{{ workJournal.work_cmd_cnt }}</td>
        </tr>
        <tr class="multi-line">
          <th>특이사항</th>
          <td v-if="isWorkerEditMode">
            <textarea v-model="specialProblemInput"></textarea>
            <LetterLimitCounter :current-text="specialProblemInput" :limit="200" class="limit-counter"/>
          </td>
          <td v-else>{{ workJournal.spcl_prbm }}</td>
        </tr>
        </tbody>
      </table>


      <div v-if="isWorkerEditMode" class="button-group center-type article-buttons">
        <button @click="doCancel">취 소</button>
        <button @click="doSave"
                :disabled="specialProblemInput.trim() === workJournal.spcl_prbm.trim()">
          저 장
        </button>
      </div>
      <div v-else class="button-group center-type article-buttons">
        <button @click="goList">목 록</button>
        <button @click="doEdit"
                v-if="isEditable">
          수 정
        </button>
        <button @click="doDelete"
                v-if="isEditable"
                class="btn-dark">
          삭 제
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import PageTitle from "@/components/panels/PageTitle";
import {mapGetters, mapState} from "vuex";
import dayjs from "dayjs";
import LetterLimitCounter from "@/components/ui/LetterLimitCounter";
export default {
  name: "WorkJournalView",
  components: {LetterLimitCounter, PageTitle},

  props: {
    jnl_sid: String
  },

  computed: {
    ...mapGetters([
      'userData'
    ]),
    ...mapState([
      'workJournal'
    ]),
    ...mapState('common/code',[
      'WORK_STAT_CD',
      'DEPT_CD'
    ]),

    isEditable(){
      return (this.userData.user_id === this.workJournal.regist_id || this.userData.auth_sid > 99)
    }
  },

  created() {
    this.$store.dispatch('common/code/query', {code: 'WORK_STAT_CD'})
    this.$store.dispatch('common/code/query', {code: 'DEPT_CD'})
    .then((response) => {
      if (response.data.resultCode === 'NRL001'){
        this.queryServer();
      }
    });
  },

  data(){
    return {
      dayjs,
      workStatName: '',
      isWorkerEditMode: false,
      specialProblemInput: ''
    }
  },

  methods: {
    queryServer(){
      this.$store.dispatch('workJournal', {id: this.jnl_sid})
    },
    goList(){
      this.$router.push('/journal');
    },
    doEdit(){
      if (this.userData && this.userData.auth_sid > 99){
        this.$router.push('/journal/edit/' + this.jnl_sid);
      } else {
        this.specialProblemInput = this.workJournal.spcl_prbm;
        this.isWorkerEditMode = true;
      }
    },
    doDelete(){
      let bool = confirm('작업일지를 삭제 하시겠습니까?')
      if (bool){
        this.$store.dispatch('deleteWorkJournal', {jnl_sid: this.jnl_sid})
        .then(response => {
          if (response.data.resultCode === 'NRL001'){
            alert('작업일지를 삭제 하였습니다.');
            this.goList();
          }
        })
      }
    },

    doCancel(){
      this.isWorkerEditMode = false
    },
    doSave(){
      let payload = JSON.parse(JSON.stringify(this.workJournal));
      payload.spcl_prbm = this.specialProblemInput;
      this.$store.dispatch('putWorkJournal', payload)
          .then(response => {
            if(response.data.resultCode === 'NRL001'){
              this.queryServer();
              this.doCancel()
            } else {
              alert('저장 과정에서 오류가 있어 저장되지 않았습니다. \nCode: ' + response.data.resultCode)
            }
          })
    },
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/table";
.page-box{
  padding: 30px;

  tr{
    &.multi-line{
      td{
        white-space: pre-line;
        word-break: break-all;
      }
    }
    td{
      textarea{
        width: 100%;
        height: 120px;
      }
      .limit-counter{
        display: block;
        text-align: right;
      }
    }
  }

}
</style>