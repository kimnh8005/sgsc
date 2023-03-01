<template>
  <div class="chart-container" :class="{'main-type': chartType === 'MAIN'}">
    <highcharts :options="chartOptions" ref="highcharts"></highcharts>
  </div>
</template>

<script>
import {Chart} from 'highcharts-vue';
import formatter from "@/modules/formatter";

export default {
  name: "ChartBox",

  components: {
    highcharts: Chart
  },

  props: {
    chartType: String,
    rawData : null
  },

  watch: {
    seriesData: {
      handler(){
        // 현재 series 제거
        while(this.chart.series.length > 0)
          this.chart.series[0].remove(true);

        // 새로운 series data 업데이트
        this.seriesData.forEach((item) => {
          this.chart.addSeries(item);
        });

        // console.log('seriesData. watch::::', this.chart, this.seriesData, this.categoryData)
        // x 축 시간 업데이트
        this.chart.xAxis[0].setCategories(this.categoryData);
      },
      deep: true
    }
  },

  computed: {
    seriesData() {
      let list = null
      switch (this.chartType){
        case 'MAIN':
          list = this.getMainList();
          break;
        case 'PREDICTION24HOUR':
          list = this.getPrediction24HourList();
          break;
        case 'PREDICTION7DAY':
          list = this.getPrediction7DayList();
          break;
        case 'PREDICTION4WEEK':
          list = this.getPrediction4WeekList();
          break;
        case 'PROCESS':
          list = this.getProcessList();
          break;
        case 'EQUIPMENT':
          list = this.getProcessList();
          break;
        default:
          return [];
      }
      return list;
    },

    categoryData() {
      let list = null
      switch (this.chartType){
        case 'MAIN':
          list = this.getHourCategories();
          break;
        case 'PREDICTION24HOUR':
          list = this.getHourCategories();
          break;
        case 'PREDICTION7DAY':
          list = this.getDayCategories();
          break;
        case 'PREDICTION4WEEK':
          list = this.getWeekCategories();
          break;
        case 'PROCESS':
          list = this.getProcessCategories();
          break;
        case 'EQUIPMENT':
          list = this.getProcessCategories();
          break;
        default:
          return [];
      }
      return list;
    }
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
          height: this.getChartHeight(),
          animation: {
            duration: 1000
          }
        },
        title: false,
        legend: this.getLegendConfig(),
        xAxis: {
          categories: []
        },
        yAxis: {
          title: false,
          labels: {
            formatter: function() {
              let v = {0: '', 25:'정상', 50: '주의', 75: '경고', 100: '위험'};
              let c = {0: 'transparent', 25: '#2d969b', 50: '#f06c00', 75: '#ff0000', 100: '#a40000'};

              return `<span style="color: ${c[this.value]}">${v[this.value]}</span>`
            },
            y: 28,
            x: -40,
            style:{
              color: '#666666',
              fontSize:15,
              fontWeight: 'bold'
            }
          },
          max: 100,
          tickAmount: 5,// 5단계
          tickWidth: 1,
          tickLength: 100
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
        credits: {
          enabled: false
        }
      }
    }
  },

  methods: {
    getProcessCategories(){
      let list = [];
      this.rawData.data.forEach(row => {
        this.rawData.cols.forEach(col => {
          if (col !== 'date'){
            let d = row[col];
            if (d){
              list.push(col + '시')
            }
            else {
              list.push('')
            }
          }
        })
      })

      // console.log(list);
      return list;
    },
    getWeekCategories(){
      let list = [];

      this.rawData.forEach((item) => {
        list.push(formatter.date(item.risk_dt, 'MM/dd~MM/dd'))
      })

      return list;
    },
    getDayCategories(){
      let list = [];

      this.rawData.forEach((item) => {
        list.push(formatter.date(item.ymd, 'MM/dd'));
      })

      return list;
    },
    getHourCategories(){
      let list = [];

      this.rawData.forEach((item) => {
        list.push(item.qtime + '시');
      })

      return list;
    },
    getChartHeight(){
      return (this.chartType === 'MAIN') ? 250 : 230;
    },
    getLegendConfig(){
      return (this.chartType === 'MAIN') ? {
        layout: 'horizontal',
        align: 'left',
        verticalAlign: 'top',
        x: 50
      } : false;
    },

    getProcessList(){
      let list = [{
        name: '통합 위험률(%)',
        data: [],
        color: '#f58fae'
      }];
      let cats = this.rawData.cols;
      this.rawData.data.forEach((row) => {
        cats.forEach(catItem => {
          if (catItem !== 'date'){
            let v = row[catItem];
            if (v){
              v = Number(v.split(':')[0]);
            } else {
              v = null;
            }
            list[0].data.push(v);
          }
        })
      })
      return list;
    },
    getPrediction4WeekList(){
      let list = [{
        name: '4주 예측',
        data: [],
        color: '#f58fae'
      }];

      this.rawData.forEach((item) => {
        list[0].data.push(Number(item.risk_rate.split(':')[0]));
      });

      return list
    },
    getPrediction7DayList(){
      let list = [{
        name: '7일 예측',
        data: [],
        color: '#f58fae'
      }];

      this.rawData.forEach((item) => {
        list[0].data.push(Number(item.risk_rate.split(':')[0]));
      });

      return list
    },
    getPrediction24HourList(){
      let list = [{
        name: '통합 위험률(%)',
        data: [],
        color: '#f58fae'
      }];

      this.rawData.forEach((item) => {
        list[0].data.push(Number(item.risk_rate.split(':')[0]));
      });

      return list
    },
    getMainList(){
      let list = [{
        name: '발생확률(%)',
        data: [],
        color: '#f58fae'
      }];

      this.rawData.forEach((item) => {
        list[0].data.push(Number(item.risk_rate.split(':')[0]));
      });

      return list;
    }
  },

}
</script>

<style scoped lang="scss">
@import "../../assets/scss/variables";

.chart-container{
  border: 1px solid $gray-line-color;
  border-radius: $border-radius;
  overflow: hidden;
  height: 230px;
  margin-bottom: 30px;

  &.main-type{
    height: 250px;
  }
}

</style>