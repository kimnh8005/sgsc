<template>
  <div class="screen-bg">
    <div class="viewer-container">
      <div class="viewer-header">
        <h2>
          <span>{{ title }}</span>
          <span class="caret-right" v-if="subtitle !== null"><img src="~@/assets/img/caret-right.png" alt="caret right"></span>
          <span>{{ subtitle }}</span>
        </h2>
        <div class="close-button" @click="onClickClose"></div>
      </div>
      <div class="info">
        <div class="update-time">
          {{ baseDateTime }}
        </div>
        <div class="warning">
          설비 위치 이동 발생
        </div>
      </div>
      <div class="viewer-body">
        <div class="photo">
          <iframe :src="mapUrl"></iframe>
<!--          <img src="/sgsc/sample/sample-aerial-photo.jpg">-->
        </div>
        <div>
          <table class="view-table">
            <tbody>
            <tr>
              <th rowspan="2">발생지점</th>
              <td>발생지점 주소</td>
              <td></td>
            </tr>
            <tr>
              <td>경도 / 위도</td>
              <td>{{ infoData.baseX + ' / ' + infoData.baseY }}</td>
            </tr>
            </tbody>
          </table>
          <table class="view-table">
            <tbody>
            <tr>
              <th rowspan="9">시뮬레이션 조건</th>
              <td>가스종류(gasType)</td>
              <td></td>
            </tr>
            <tr>
              <td>주변압력(ambientPress)</td>
              <td></td>
            </tr>
            <tr>
              <td>주변온도(ambientTemper)</td>
              <td>{{ infoData.t1h }}</td>
            </tr>
            <tr>
              <td>풍향(windDirect)</td>
              <td>{{ infoData.wsd }}</td>
            </tr>
            <tr>
              <td>풍속(windSpeed)</td>
              <td>{{ infoData.vec }}</td>
            </tr>
            <tr>
              <td>대기기압(atmosPress)</td>
              <td></td>
            </tr>
            <tr>
              <td>대기기온(atmosTemper)</td>
              <td></td>
            </tr>
            <tr>
              <td>대기습도(Humidity)</td>
              <td></td>
            </tr>
            <tr>
              <td>대기확산모델(model)</td>
              <td>{{ infoData.model }}</td>
            </tr>
            </tbody>
          </table>
          <table class="view-table">
            <tbody>
            <tr>
              <th rowspan="12">세부 사항</th>
              <td>시설물 ID</td>
              <td>{{ infoData.gasFacId }}</td>
            </tr>
            <tr>
              <td>가스확산분석 실행시간</td>
              <td>{{ infoData.execTime }}</td>
            </tr>
            <tr>
              <td>가스확산 분석 진행시간 (PUFF) [분]</td>
              <td>{{ infoData.simulTime }}</td>
            </tr>
            <tr>
              <td>가스확산 분석 진행시간 분할 [초]</td>
              <td>{{ infoData.simulTimePer }}</td>
            </tr>
            <tr>
              <td>대기 안정도</td>
              <td>{{ infoData.stability }}</td>
            </tr>
            <tr>
              <td>누출 가스 몰질량</td>
              <td>{{ infoData.moleWeight }}</td>
            </tr>
            <tr>
              <td>누출지점 높이</td>
              <td>{{ infoData.leakHeight }}</td>
            </tr>
            <tr>
              <td>총누출량(PUFF) [kg] / 초당 누출량(PLUME) [kg/s]</td>
              <td>{{ infoData.totalReleaseAmount }}</td>
            </tr>
            <tr>
              <td>최대 검측 PPM</td>
              <td>{{ infoData.maxPpm }}</td>
            </tr>
            <tr>
              <td>최소 검측 PPM</td>
              <td>{{ infoData.minPpm }}</td>
            </tr>
            <tr>
              <td>분할 PPM</td>
              <td>{{ infoData.ppmPer }}</td>
            </tr>
            <tr>
              <td>날씨 기준위치</td>
              <td>{{ infoData.kmaName }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "AerialPhotoViewer",

  props:{
    procs_nm: {
      type: String
    },
    eqmt_nm: {
      type: String
    },
    eqmt_id: {
      type: String,
      default: ''
    },
    hasDetail: {
      type: Boolean
    },
    displayData: Object
  },

  computed: {
    mapUrl(){
      const gasFacInfoUrl = 'http://118.129.135.145:2122/analysis/guest/gasFacInfo.do?gasFacId=';
      let url;
      if (this.eqmt_id){
        url = gasFacInfoUrl + this.eqmt_id;
      } else {
        url = (this.displayData.gasFacId) ? 'http://118.129.135.145:2122/analysis/guest/gasFacInfo.do?gasFacId=' + this.displayData.gasFacId : this.displayData.diffsMapUrl;
      }
      return url;
    },

    baseDateTime(){
      let bool = ((this.infoData.baseDate === null || this.infoData.baseTime === null) || (this.infoData.baseDate === '' && this.infoData.baseTime === ''));
      return (bool) ? '기준 날짜와 시간 데이터가 없습니다.' : this.infoData.baseDate + ' ' + this.infoData.baseTime;
    },

    title(){
      if (this.procs_nm){
        return this.procs_nm;
      } else if (this.displayData){
        return (this.displayData.gasFacId) ? this.displayData.gasFacId : '설비명 없음';
      } else {
        return null
      }
    },

    subtitle(){
      if (this.eqmt_nm){
        return this.eqmt_nm
      } else if (this.eqmt_id){
        return this.eqmt_id
      } else {
        return null
      }
    }
  },

  watch: {
    displayData: {
      immediate: true,
      deep: true,
      handler(){
        console.log('watch displayData: ', this.displayData)
        this.loadInfo(this.displayData.diffsInfoUrl);
      }
    }
  },

  created() {
    this.loadInfo();
  },

  data(){
    return {
      infoData: {
        baseDate: null,
        baseTime: null,
        baseX: null,
        baseY: null,
        execTime: null,
        fromExecTime: null,
        gasFacId: null,
        gasSimulSn: null,
        heatCapacityRatio: null,
        kmaName: null,
        leakHeight: null,
        maxPpm: null,
        minPpm: null,
        model: null,
        moleWeight: null,
        ppmPer: null,
        resultCode: null,
        simulTime: null,
        simulTimePer: null,
        sisulSn: null,
        stability: null,
        t1h: null,
        toExecTime: null,
        totalReleaseAmount: null,
        vec: null,
        wsd: null
      }
    }
  },

  methods: {
    queryDetail(){

    },
    onClickClose() {
      this.$emit('close');
    },

    loadInfo(url){
      axios.get(url)
      .then(response => {
        if (response.data.code === 'Success'){
          this.infoData = response.data.responseData;
          console.log('info data: ', this.infoData)
        }
        return response;
      }).catch((e) => {
        console.log('catch error: ', e)
      })
    },
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";
@import "src/assets/scss/table";

.screen-bg{
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;

  .viewer-container{
    background-color: #fff;
    min-width: 1000px;
    min-height: 500px;
    box-shadow: 0 4px 18px rgba(0,0,0,0.4);
    padding: 30px;

    .viewer-header{
      height: 40px;
      border-bottom: 1px solid $gray-line-color;
      padding-bottom: 25px;
      display: flex;
      justify-content: space-between;
      align-items: center;

      h2{
        font-size: 20px;
        color: $turquoise-color;

        .caret-right{
          padding: 0 10px;
        }
      }

      .close-button{
        width: 40px;
        height: 40px;
        position: relative;
        cursor: pointer;
        //background-color: red;

        &:before, &:after{
          content: '';
          display: block;
          width: 50px;
          height: 2px;
          background-color: #919191;
          position: absolute;
          left: -5px;
          top: 20px;
        }
        &:before{
          transform: rotate(45deg);
        }
        &:after{
          transform: rotate(-45deg);
        }
      }
    }

    .info{
      height: 65px;
      display: flex;
      align-items: center;
      justify-content: space-between;

      .warning{
        height: 35px;
        line-height: 35px;
        border: 1px solid #ff0000;
        border-radius: 35px;
        color: $warning-level-font-color;
        padding: 0 20px 0 38px;
        background: url("~@/assets/img/icon-exclamtion.png") no-repeat 12px center;
      }
    }

    .viewer-body{
      max-height: 70vh;
      overflow-y: auto;
      .photo{
        height: 500px;
        width: 100%;
        background-color: #eee;
        margin-bottom: 30px;

        iframe{
          width: 100%;
          height: 100%;
          border: 0;
        }
      }

      .view-table{
        table-layout: initial;
        width: 100%;

        tbody{
          tr{
            th{
              width: 200px;

              + td{
                width: 300px;
              }
            }
          }
        }
      }
    }

  }
}
</style>