<template>
  <div class="chart-container">
    <h3>7일간 작업 오류율</h3>
    <highcharts :options="chartOptions" ref="highcharts" class="chart-shape"></highcharts>
  </div>
</template>

<script>
import {Chart} from 'highcharts-vue';
import {mapState} from "vuex";
import dayjs from "dayjs";

export default {
  name: "WeekLineChart",

  components: {
    highcharts: Chart
  },

  watch: {
    humanErrorDay7Rate: {
      handler(){
        // 현재 series 제거
        // while(this.chart.series.length > 0)
        //   this.chart.series[0].remove(true);

        let cat = [];
        let d = {
          name: '7일간 작업 오류율',
          data: [],
          categories: [],
          color: '#96cacd'//2d969b
        };
        this.humanErrorDay7Rate.list.forEach((item) => {
          d.data.push(item.risk_rate);
          cat.push(dayjs(item.props_ymd).format('MM월DD일'))
        });

        this.chart.addSeries(d);
        this.chart.xAxis[0].setCategories(cat);

        // console.log('------', this.seriesList, this.categoryList)
      },
      deep: true
    }
  },

  computed: {
    ...mapState([
      'humanErrorDay7Rate'
    ]),
  },

  created() {
    this.$store.dispatch('humanErrorDay7Rate');
  },

  mounted() {
    this.chart = this.$refs.highcharts.chart;
  },

  data() {
    return {
      chart : null,

      chartOptions: {
        chart: {
          type: 'area',
          height: 240,
          animation: {
            duration: 1000
          }
        },
        title: false,
        legend: false,
        // legend: this.getLegendConfig(),
        xAxis: {
          labels: {
            style: {
              fontSize: 15
            },
            // y: 25,
          },
          categories: []
        },
        yAxis: {
          title: false,
          labels: false,
          max: 100,
          // tickAmount: 5,// 5단계
          // tickWidth: 1,
          tickLength: 100,
          visible: false
        },
        plotOptions: {
          series: {
            marker: {
              fillColor: '#FFFFFF',
              lineWidth: 2,
              lineColor: null // inherit from series
            }
          },
          area: {
            pointPlacement: 'on'
          }
        },
        tooltip: {
          formatter: function() {
            return this.x + ' : <b>' + this.point.y  + ' %</b>';
          },
          valueSuffix: '%'
        },
        credits: {
          enabled: false
        }
      }
    }
  },
}
</script>

<style scoped lang="scss">
@import "../../assets/scss/variables";

.chart-container{
  background-color: #3a3e43;
  height: 340px;
  border-radius: $border-radius;
  box-sizing: border-box;
  padding: 30px;

  h3{
    font-size: 18px;
    font-weight: normal;
    height: 40px;
  }
  .chart-shape{
    overflow: hidden;
    border-radius: $border-radius;
  }
}
</style>