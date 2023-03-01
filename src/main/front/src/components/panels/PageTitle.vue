<template>
  <div class="page-title">
    <h1>
      <span v-if="preTitle" class="pre-title">
        {{ preTitle }}
        <span class="caret-right">&gt;</span>
      </span>
      {{ displayTitle }}
    </h1>
    <div v-if="hasDateRangePicker" class="datepicker-container">
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
  </div>
</template>

<script>
import DateRangePicker from 'vue2-daterange-picker'
import '@/assets/scss/vue2-daterange-picker-sgsc-custom.css'
import dayjs from 'dayjs'

// import "@/assets/scss/vue2-daterange-picker-sgsc-custom.scss";
export default {
  name: "page-title",
  components: {DateRangePicker},
  props: {
    preTitle: String,
    title: String,
    hasDateRangePicker: Boolean
  },
  computed: {
    displayTitle(){
      return (this.title) ? this.title : this.$route.name;
    }
  },

  data(){
    let startDate = new Date();
    let endDate = new Date();
    return {
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
      }
    }
  },

  filters: {
    date(val) {
      return (val) ? dayjs(val).format('YYYY-MM-DD') : ''
    }
  },

  methods: {
    onUpdateDateRange(range){
      console.log('this.onUpdateDateRange', range)
      this.$emit('changedDate', range)
    },
    // toggleDaterangePicker(){
    //   this.$refs.picker.togglePicker(true)
    // }
  }
}
</script>

<style scoped lang="scss">
//@import "src/assets/scss/vue2-daterange-picker-sgsc-custom";
@import "src/assets/scss/variables";

.page-title{
  //width: 1590px;
  height: 100px;
  border-radius: 10px;
  background-image: url("../../assets/img/title-bg.png");
  border: 1px solid #d1d1d1;
  padding: 36px 30px;
  box-sizing: border-box;
  margin-bottom: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  h1{
    .pre-title{
      color: #d4d4d4;
      .caret-right{
        margin: 0 10px;
      }
    }
    font-size: 25px;
    color: #fff;
  }

  .datepicker-container{
    width: 270px;
    height: 38px;
    background-color: #ffffff;
    border-radius: $half-border-radius;
    display: flex;
    align-items: center;
    //justify-content: space-between;

    > .icon{
      display: flex;
      align-items: center;
      height: 18px;
      margin: auto 18px;
      padding-right: 18px;
      border-right: 2px solid $gray-line-color;
    }
    .form-control{
      margin-right: 10px;
    }
    div.calendars.row{
      display: flex;
    }
  }
}

</style>