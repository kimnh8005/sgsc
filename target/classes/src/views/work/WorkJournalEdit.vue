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
          <th>작업유형</th>
          <td>
            <select v-model="journal.work_type_cd">
              <option value="">선택</option>
              <option v-for="(item, index) in WORK_TYPE_CD"
                      :key="index"
                      :value="item.cd_id"
                      :label="item.cd_nm" />
            </select>
          </td>
          <th>단위공정</th>
          <td>
            <!-- workJournal.unit_procs_cd  -->
            <select v-model="journal.unit_procs_cd">
              <option value="">선택</option>
              <option v-for="(item, index) in UNIT_PROCS_CD"
                      :key="index"
                      :value="item.cd_id"
                      :label="item.cd_nm" />
            </select>
          </td>
        </tr>
        <tr>
          <th>설비</th>
          <td colspan="3">
            <select v-model="journal.procs_id"
                    @change="onProcessChange">
              <option value="">생산공정</option>
              <option v-for="item in process"
                      :value="item.procs_id"
                      :label="item.procs_nm"
                      :key="item.procs_id" />
            </select>
            <select v-model="journal.eqmt_id">
              <option value="">설비</option>
              <option v-for="item in equipment"
                    :value="item.eqmt_id"
                    :label="item.eqmt_nm"
                    :key="item.eqmt_id" />
            </select>
          </td>
        </tr>
        <tr>
          <th>작업설명</th>
          <td colspan="3" class="description-td">
            <input type="text" v-model="journal.description">
            <LetterLimitCounter :limit="50" :current-text="journal.description"/>
          </td>
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
          <td colspan="3">
            <select v-model="journal.work_stat_cd">
              <option value="">선택</option>
              <option v-for="(item, index) in WORK_STAT_CD"
                      :key="index"
                      :value="item.cd_id"
                      :label="item.cd_nm" />
            </select>
          </td>
        </tr>
        <tr>
          <th>목표 시작</th>
          <td>
            <div class="datepicker-container">
              <div class="icon">
                <img src="~@/assets/img/icon-calender.png" alt="calender icon">
              </div>
              <DateRangePicker
                  ref="goalStartPicker"
                  :opens="datePickerConfig.opens"
                  :locale-data="datePickerConfig.localeData"
                  :showDropdowns="datePickerConfig.showDropdowns"
                  :autoApply="datePickerConfig.autoApply"
                  :single-date-picker="true"
                  :ranges="false"

                  v-model="goalStart"
                  @update="onUpdateGoalStart"
              >
                <template v-slot:input="goalStartPicker" class="range-input">
                  {{ goalStartPicker.startDate | date }}
                </template>
              </DateRangePicker>
            </div>
            <!--{{ journal.goal_strt_dt ? dayjs(journal.goal_strt_dt).format('YYYY-MM-DD') : '' }}-->
          </td>
          <th>목표 완료</th>
          <td>
            <div class="datepicker-container">
              <div class="icon">
                <img src="~@/assets/img/icon-calender.png" alt="calender icon">
              </div>
              <DateRangePicker
                  ref="goalCompletePicker"
                  :opens="datePickerConfig.opens"
                  :locale-data="datePickerConfig.localeData"
                  :showDropdowns="datePickerConfig.showDropdowns"
                  :autoApply="datePickerConfig.autoApply"
                  :single-date-picker="true"
                  :ranges="false"

                  v-model="goalComplete"
                  @update="onUpdateGoalComplete"
              >
                <template v-slot:input="goalCompletePicker" class="range-input">
                  {{ goalCompletePicker.startDate | date }}
                </template>
              </DateRangePicker>
            </div>
            <!--{{ journal.goal_cmpt_dt ? dayjs(journal.goal_cmpt_dt).format('YYYY-MM-DD') : '' }}-->
          </td>
        </tr>
        <tr>
          <th>실제 시작</th>
          <td>
            <div class="datepicker-container">
              <div class="icon">
                <img src="~@/assets/img/icon-calender.png" alt="calender icon">
              </div>
              <DateRangePicker
                  ref="realStartPicker"
                  :opens="datePickerConfig.opens"
                  :locale-data="datePickerConfig.localeData"
                  :showDropdowns="datePickerConfig.showDropdowns"
                  :autoApply="datePickerConfig.autoApply"
                  :single-date-picker="true"
                  :ranges="false"

                  v-model="realStart"
                  @update="onUpdateRealStart"
              >
                <template v-slot:input="realStartPicker" class="range-input">
                  {{ realStartPicker.startDate | date }}
                </template>
              </DateRangePicker>
            </div>
            <!--{{ journal.real_strt_dt ? dayjs(journal.real_strt_dt).format('YYYY-MM-DD') : '' }}-->
          </td>
          <th>실제 완료</th>
          <td>
            <div class="datepicker-container">
              <div class="icon">
                <img src="~@/assets/img/icon-calender.png" alt="calender icon">
              </div>
              <DateRangePicker
                  ref="realCompletePicker"
                  :opens="datePickerConfig.opens"
                  :locale-data="datePickerConfig.localeData"
                  :showDropdowns="datePickerConfig.showDropdowns"
                  :autoApply="datePickerConfig.autoApply"
                  :single-date-picker="true"
                  :ranges="false"

                  v-model="realComplete"
                  @update="onUpdateRealComplete"
              >
                <template v-slot:input="realCompletePicker" class="range-input">
                  {{ realCompletePicker.startDate | date }}
                </template>
              </DateRangePicker>
            </div>
            <!--{{ journal.real_cmpt_dt ? dayjs(journal.real_cmpt_dt).format('YYYY-MM-DD') : '' }}-->
          </td>
        </tr>
        <tr>
          <th>작업자</th>
          <td colspan="3">
            <select v-model="journal.wrk_dept_cd">
              <option :value="null">선택</option>
              <option v-for="(item, index) in DEPT_CD"
                      :key="index"
                      :value="item.cd_id"
                      :label="item.cd_nm" />
            </select>

            <select v-model="journal.wrk_user_id">
              <option :value="null">선택</option>
              <option :value="journal.wrk_user_id">{{ journal.wrk_user_nm }}</option>
            </select>
          </td>
        </tr>
        </tbody>
      </table>

      <table class="view-table">
        <tbody>
        <tr class="multi-line">
          <th>작업지시</th>
          <td class="full-size">
            <textarea v-model="journal.work_cmd_cnt"></textarea>
            <LetterLimitCounter :current-text="journal.work_cmd_cnt" :limit="200" class="limit-counter"/>
          </td>
        </tr>
        <tr class="multi-line">
          <th>특이사항</th>
          <td class="full-size">
            <textarea v-model="journal.spcl_prbm"></textarea>
            <LetterLimitCounter :current-text="journal.spcl_prbm" :limit="200" class="limit-counter"/>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="button-group center-type article-buttons">
        <button @click="goView">취 소</button>
        <button @click="doSave">저 장</button>
      </div>
    </div>
  </div>
</template>

<script>
import PageTitle from "@/components/panels/PageTitle";
import {mapState, mapGetters} from "vuex";
import dayjs from "dayjs";
import DateRangePicker from 'vue2-daterange-picker'
import LetterLimitCounter from "@/components/ui/LetterLimitCounter";

export default {
  name: "WorkJournalView",
  components: {LetterLimitCounter, DateRangePicker, PageTitle},

  props: {
    jnl_sid: String,
  },

  computed: {
    ...mapGetters([
      'userData'
    ]),
    ...mapState('common/process',[
      'process'
    ]),
    ...mapState('common/equipment',[
      'equipment'
    ]),
    ...mapState('common/code',[
      'WORK_TYPE_CD',
      'UNIT_PROCS_CD',
      'WORK_STAT_CD',
      'DEPT_CD'
    ]),
    ...mapState([
      'workJournal'
    ]),

    isAdmin(){
      return (this.userData && this.userData.auth_sid > 99)
    }
  },

  watch: {
    journal(){
      this.goalStart.endDate = this.goalStart.startDate = dayjs(this.journal.goal_strt_dt).format('YYYY-MM-DD')
      this.goalComplete.endDate = this.goalComplete.startDate = dayjs(this.journal.goal_cmpt_dt).format('YYYY-MM-DD')
      this.realStart.endDate = this.realStart.startDate = dayjs(this.journal.real_strt_dt).format('YYYY-MM-DD')
      this.realComplete.endDate = this.realComplete.startDate = dayjs(this.journal.real_cmpt_dt).format('YYYY-MM-DD')
    }
  },

  created() {
    if (this.isAdmin){
      console.log('this.userData : ', this.userData.auth_sid)
      this.$store.dispatch('common/code/query', {code: 'WORK_TYPE_CD'});
      this.$store.dispatch('common/code/query', {code: 'UNIT_PROCS_CD'});
      this.$store.dispatch('common/code/query', {code: 'WORK_STAT_CD'});
      this.$store.dispatch('common/code/query', {code: 'DEPT_CD'});
      this.$store.dispatch('common/process/query', {queryString: ''});
      this.$store.dispatch('common/equipment/query', {queryString: ''});
    }
    this.$store.dispatch('workJournal', {id: this.jnl_sid})
        .then(() => {
          this.journal = JSON.parse(JSON.stringify(this.workJournal));
          console.log('workJournal:', this.journal)
        })
  },

  data(){
    return {
      dayjs,
      /**
       "goal_strt_dt": "20200920",
       "procs_id": "P00002",
       "real_cmpt_dt": "",
       "spcl_prbm": "없음",
       "work_cmd_cnt": "열심히 공정에 힘써주시기 바랍니다",
       "wrk_user_id": "S00002",
       "description": "예외 없음",
       "facty_id": "F00002",
       "unit_procs_cd": "UP0002",
       "user_id": "S00001",
       "eqmt_id": "E00002",
       "real_strt_dt": "20200923",
       "work_stat_cd": "WS0002",
       "work_type_cd": "WT0002",
       "wrk_dept_cd": "DT0002",
       "goal_cmpt_dt": "20201005"
       */
      // 화면에 표시되는 순서대로
      // 화면에 표시되지는 않지만 수정사항에는 항목이 더 있음.
      journal: {
        work_type_cd: '',
        unit_procs_cd: '',
        procs_id: '',
        eqmt_id: '',
        description: '',
        work_stat_cd: '',
        goal_strt_dt: '',
        goal_cmpt_dt: '',
        real_strt_dt: '',
        real_cmpt_dt: '',
        wrk_dept_cd: '',
        wrk_user_id: '',
        work_cmd_cnt: '',
        spcl_prbm: ''
      },

      datePickerConfig: {
        opens: 'left',
        showDropdowns: false,
        showRange: false,
        localeData: {
          direction: 'ltr',
          format: 'yyyy-mm-dd',
          separator: ' ~ ',
          applyLabel: '적 용',
          cancelLabel: '취 소',
          weekLabel: 'W',
          customRangeLabel: 'Custom Range',
          daysOfWeek: ['일', '월', '화', '수', '목', '금', '토'],
          monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
          firstDay: 0
        }
      },

      goalStart: {
        startDate: ''
      },
      goalComplete: {
        startDate: ''
      },
      realStart: {
        startDate: ''
      },
      realComplete: {
        startDate: ''
      }
    }
  },

  filters: {
    date(val) {
      return (val) ? dayjs(val).format('YYYY-MM-DD') : ''
    }
  },

  methods: {
    goView(){
      this.$router.push('/journal/view/' + this.jnl_sid);
    },
    doSave(){
      /*{
        "goal_strt_dt": "20200921",
          "procs_id": "P00002",
          "real_cmpt_dt": "",
          "spcl_prbm": "없음1234",
          "work_cmd_cnt": "좀더부탁드립니다1234",
          "wrk_user_id": "S00001",
          "description": "예외 있음1234",
          "facty_id": "F00002",
          "unit_procs_cd": "UP0002",
          "user_id": "S00002",
          "eqmt_id": "E00002",
          "real_strt_dt": "20200925",
          "work_stat_cd": "WS0003",
          "work_type_cd": "WT0003",
          "wrk_dept_cd": "DT0003",
          "goal_cmpt_dt": "20201015",
          "jnl_sid" : 8
      }*/

      console.log('save:>>', this.journal)
      this.$store.dispatch('putWorkJournal', this.journal)
      .then(response => {
        if(response.data.resultCode === 'NRL001'){
          this.goView();
        } else {
          alert('저장 과정에서 오류가 있어 저장되지 않았습니다. \nCode: ' + response.data.resultCode)
        }
      })
    },

    // 생산공정이 바뀌면 그에 속한 설비 목록을 새로 받아오자
    onProcessChange(){
      this.journal.eqmt_id = '';
      if (this.journal.procs_id === ''){
        this.$store.dispatch('common/equipment/clear', {stateName: 'equipment'});
      } else {
        this.$store.dispatch('common/equipment/query', {queryString: '?procs_id=' + this.journal.procs_id});
      }
    },

    onUpdateGoalStart(date){
      console.log('this.onUpdateGoalStart', date)
      this.journal.goal_strt_dt = dayjs(date.startDate).format('YYYYMMDD')
    },

    onUpdateGoalComplete(date){
      console.log('this.onUpdateGoalComplete', date)
      this.journal.goal_cmpt_dt = dayjs(date.startDate).format('YYYYMMDD')
    },

    onUpdateRealStart(date){
      console.log('this.onUpdateRealStart', date)
      this.journal.real_strt_dt = dayjs(date.startDate).format('YYYYMMDD')
    },

    onUpdateRealComplete(date){
      console.log('this.onUpdateRealComplete', date)
      this.journal.real_cmpt_dt = dayjs(date.startDate).format('YYYYMMDD')
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/table";
.page-box{
  padding: 30px;

  td{
    select{
      width: 200px;
      margin-right: 20px;
    }
    input[type=text], textarea{
      width: 100%;
    }
    textarea{
      height: 120px;
    }

    &.full-size{
      padding-right: 0;
    }
    &.description-td{
      input[type=text]{
        width: calc(100% - 100px);
      }
      span{
        width: 100px;
        box-sizing: border-box;
        padding-left: 20px;
      }
    }
    .limit-counter{
      display: block;
      text-align: right;
    }
  }

  .datepicker-container{
    width: 200px;
    height: 38px;
    background-color: #ffffff;
    border: 1px solid $gray-line-color;
    border-radius: $half-border-radius;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    //justify-content: space-between;

    > .icon{
      display: flex;
      align-items: center;
      height: 18px;
      margin: auto 13px;
      padding-right: 13px;
      border-right: 2px solid $gray-line-color;
    }
    .form-control{

    }
    div.calendars.row{
      display: flex;
    }
  }
}
</style>