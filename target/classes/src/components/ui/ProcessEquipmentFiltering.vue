<template>
  <div class="filter-container">
    <h4 class="title">{{ processTitle }}</h4>
    <select v-model="selectProcs" @change="onProcessChange">
      <option value="">전체 생산공정</option>
      <option v-for="item in process"
              :value="item.procs_id"
              :key="item.procs_id">
        {{ item.procs_nm }}
      </option>
    </select>
    <h4 class="title" :class="{ 'blank-title': equipmentTitle === null }">{{ equipmentTitle }}</h4>
    <select v-model="selectEqmt">
      <option value="">전체 설비</option>
      <option v-for="item in equipment"
              :value="item.eqmt_id"
              :key="item.eqmt_id">
        {{ item.eqmt_nm }}
      </option>
    </select>

    <template v-if="hasDateRangePicker">
      <h4 class="title">{{ datepickerTitle }}</h4>
      <div class="datepicker-container">
        <div class="icon">
          <img src="~@/assets/img/icon-calender.png" alt="calender icon">
        </div>
        <DateRangePicker
            ref="picker"
            :opens="dateRange.opens"
            :locale-data="dateRange.localeData"
            :showDropdowns="dateRange.showDropdowns"
            :autoApply="dateRange.autoApply"
            :ranges="dateRange.showRange"

            v-model="selectedDateRange"
            @update="onUpdateDateRange"
        >
          <template v-slot:input="picker" class="range-input">
            {{ picker.startDate | date }} - {{ picker.endDate | date }}
          </template>
        </DateRangePicker>
      </div>
    </template>

    <template v-if="hasLeakageCheck">
      <h4 class="title">누출위험</h4>
      <label v-for="item in checkboxList" :key="item.value">
        <input type="checkbox" v-model="item.bool" @change="onCheckboxChange">
        {{ item.name }}
      </label>
    </template>

    <template v-if="hasWorkStatSelector">
      <h4 class="title">상태</h4>
      <select v-model="selectedWorkStatCode">
        <option value="">전체</option>
        <option v-for="item in WORK_STAT_CD"
                :value="item.cd_id"
                :label="item.cd_nm"
                :key="item.cd_id">
        </option>
      </select>
    </template>

    <button @click="onClick" :disabled="requireSelectEquipment && selectEqmt === ''">검색</button>
  </div>
</template>

<script>
import {mapState} from "vuex";
import DateRangePicker from 'vue2-daterange-picker'
import '@/assets/scss/vue2-daterange-picker-sgsc-custom.css'
import dayjs from 'dayjs'

export default {
  name: "ProcessEquipmentFiltering",
  components: {DateRangePicker},

  props: {
    requireSelectEquipment: Boolean,//설비를 반드시 선택해야만 검색 가능 옵션
    hasDateRangePicker: Boolean,
    hasLeakageCheck: Boolean,
    hasWorkStatSelector: Boolean,
    /**
     * 날짜가 들어올 때와 나올 때 모두 YYYYMMDD 형식 (API 가 그렇게 요구하고 있기 때문)
     * DateRangePicker 에 표시할 때 는 YYYY-MM-DD 형식 (사람이 읽어야 하므로)
     */
    startDate: {
      type: String // YYYYMMDD
    },
    endDate: {
      type: String // YYYYMMDD
    },
    processTitle: {
      type: String,
      default: '생산공정'
    },
    equipmentTitle: {
      type: String,
      default: '설 비'
    },
    datepickerTitle: {
      type: String,
      default: '날 짜'
    }
  },

  computed: {
    ...mapState('common/process',[
      'process'
    ]),
    ...mapState('common/equipment',[
      'equipment'
    ]),
    ...mapState('common/code',[
      'WORK_STAT_CD'
    ]),

    activeEquipmentId() {
      return this.activeEquipId;
    }
  },

  data() {
    let startDate = (this.startDate) ? dayjs(this.startDate).format('YYYY-MM-DD') : dayjs().format('YYYY-MM-DD');
    let endDate = (this.endDate) ? dayjs(this.endDate).format('YYYY-MM-DD') : dayjs().format('YYYY-MM-DD');
    // let startDate = (this.startDate) ? this.startDate : new Date();
    // let endDate = (this.endDate) ? this.endDate : new Date();

    return {
      selectProcs: '',
      selectEqmt: '',

      dayjs,
      selectedDateRange: {startDate, endDate},
      dateRange: {
        today: new Date(),
        opens: 'left',
        showDropdowns: false,
        showRange: false,
        localeData: {
          direction: 'ltr',
          format: 'yyyy-mm-dd',
          separator: ' ~ ',
          applyLabel: '적용',
          cancelLabel: '취소',
          weekLabel: 'W',
          customRangeLabel: 'Custom Range',
          daysOfWeek: ['일', '월', '화', '수', '목', '금', '토'],
          monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
          firstDay: 0
        }
      },
      checkboxList : [
        {
          name: 'ON',
          value: 'on',
          bool: true
        },
        {
          name: 'OFF',
          value: 'off',
          bool: true
        }
      ],
      selectedWorkStatCode: '',
    }
  },

  created() {
    // 생상공정 목록 가져오자
    this.$store.dispatch('common/process/query', {queryString: ''});
    if (this.hasWorkStatSelector)
      this.$store.dispatch('common/code/query', {code: 'WORK_STAT_CD'})
  },

  filters: {
    date(val) {
      return (val) ? dayjs(val).format('YYYY-MM-DD') : ''
    }
  },

  methods: {
    onClick() {
      let args = {
        processId: this.selectProcs,
        equipmentId: this.selectEqmt
      }
      if (this.hasDateRangePicker){
        args['startDate'] = dayjs(this.selectedDateRange.startDate).format('YYYYMMDD');
        args['endDate'] = dayjs(this.selectedDateRange.endDate).format('YYYYMMDD');
      }
      if (this.hasWorkStatSelector){
        args['workStat'] = this.selectedWorkStatCode;
      }
      this.$emit('filtering', args)
    },
    // 생산공정이 바뀌면 그에 속한 설비 목록을 새로 받아오자
    onProcessChange(){
      this.selectEqmt = '';
      if (this.selectProcs === ''){
        this.$store.dispatch('common/equipment/clear', {stateName: 'equipment'});
      } else {
        this.$store.dispatch('common/equipment/query', {queryString: '?procs_id=' + this.selectProcs});
      }
    },

    onUpdateDateRange(range){
      console.log('this.onUpdateDateRange', range)
      this.$emit('changedDate', range)
    },

    onCheckboxChange(){
      let v = '';
      this.list.forEach((item) => {
        if (item.bool){
          if (v.length > 0){
            v += ',';
          }
          v += item.value;
        }
      })
      // this.$emit('change', v);
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";

.filter-container{
  display: flex;
  height: 40px;
  box-sizing: border-box;
  align-items: center;

  > h4{
    color: $default-font-color;
    font-size: 18px;
    margin-right: 40px;
    white-space: nowrap;

    &:not(:first-child){
      padding-left: 30px;
      margin-left: 30px;
      border-left: 1px solid $gray-line-color;

      &.blank-title{
        margin: 0;
      }
    }
  }

  > label{
    font-size: 15px;
    margin-right: 40px;
  }

  > select {
    height: 40px;
    min-width: 120px;
  }

  button{
    height: 40px;
    width: 130px;
    margin-left: auto;
  }
  .datepicker-container{
    width: 250px;
    height: 38px;
    background-color: #ffffff;
    border: 1px solid $gray-line-color;
    border-radius: $half-border-radius;
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