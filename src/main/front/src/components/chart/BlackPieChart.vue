<template>
  <div class="chart-container">
    <div class="info">
      <h3>금일 작업 오류율</h3>
      <div class="percent">
        <span class="percent-amount">{{ percentAmount }}</span>
        <span class="percent-unit">%</span>
      </div>
    </div>
    <highcharts :options="chartOptions" ref="highcharts"></highcharts>
  </div>
</template>

<script>
import {Chart} from 'highcharts-vue';
import {mapState} from "vuex";

export default {
  name: "BlackPieChart",

  components: {
    highcharts: Chart
  },

  watch: {
    humanErrorDayRate: {
      handler(oldValue, newValue){
        console.log(newValue)
        let v = newValue.FL;
        let series = {
          name: '금일 작업 오류율',
          data: [
            {
              name: '정상',
              y: 100 - v,
              color: '#d4d4d4'
            },
            {
              name: '오류',
              y: v,
              color: '#2d969b'
            }
          ]
        }

        this.targetNum = v;
        this.increaseNum();
        this.chart.addSeries(series)
      },
      deep: true
    }
  },

  computed: {
    ...mapState([
      'humanErrorDayRate'
    ]),

    percentAmount(){
      return this.percentNum.toFixed(1);
    }
  },

  created() {
    this.$store.dispatch('humanErrorDayRate');
  },

  mounted() {
    this.chart = this.$refs.highcharts.chart;
  },

  data() {
    return {
      chart : null,

      percentNum: 0,
      targetNum: 0,

      chartOptions: {
        chart: {
          type: 'pie',
          width: 255,
          height: 300,
          margin: [0, 0, 45, 0],
          backgroundColor: 'transparent',
          animation: {
            duration: 5000
          },
        },

        legend: {
          y: 15,
          itemStyle: {
            fontSize:'15px',
            color: '#FFFFFF'
          },
        },

        title: false,
        tooltip: {
          formatter: function() {
            return this.point.name + ' : <b>' + this.point.y  + ' %</b>';
          },
          // pointFormat: '{point.name}: <b>{point.y}</b><br/>',
          valueSuffix: '%',
          shared: true
        },

        plotOptions: {
          pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
              enabled: false
            },
            showInLegend: true
          }
        },
        credits: {
          enabled: false
        }
      }
    }
  },

  methods: {
    increaseNum(){
      if (this.percentNum < this.targetNum){
        this.percentNum += (this.targetNum - this.percentNum ) * 0.05;
        // console.log(this.percentNum, this.targetNum)
        setTimeout(this.increaseNum, 20);
      } else {
        this.percentNum = this.targetNum;
      }
    }
  }
}
</script>

<style scoped lang="scss">
@import "../../assets/scss/variables";

.chart-container{
  background-color: #3a3e43;
  height: 340px;
  border-radius: $border-radius;
  box-sizing: border-box;
  padding: 20px 30px;
  display: flex;

  .info{
    margin-top: 80px;
    width: 175px;

    .h3{
      font-size: 18px;
      font-weight: normal;
      margin-bottom: 30px;
    }

    .percent{
      margin-top: 30px;

      .percent-amount{
        font-size: 45px;
        font-weight: 700;
      }
      .percent-unit{
        font-size: 20px;
      }
    }
  }
}
</style>