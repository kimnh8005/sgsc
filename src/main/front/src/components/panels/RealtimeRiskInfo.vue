<template>
  <div class="realtime-title">
    <div class="title-header page-box">
      <h4>
        <img src="~@/assets/img/icon-realtime.png" alt="실시간 전체 시설 위험도 아이콘">
        <span class="title-text">전체 계통 ㅣ 실시간 시설 위험도</span>
      </h4>
      <div class="risk-level">
        <span>통합 위험 단계</span>
        <span class="level-sign"
              :class="riskStep">
          {{ riskStepSign }}
        </span>
      </div>
      <div class="risk-percent">
        <span>통합 위험률</span>
        <span class="percent-sign" :class="riskStep">
          <span>{{ all.risk_num }}</span>
          <span>{{ all.risk_unit }}</span>
        </span>
      </div>
    </div>
    <div class="refresh-button" @click="refreshData">
      <div class="button-label">
        <img src="~@/assets/img/icon-refresh.png"
             :class="{ loading: isLoading }"
             alt="새로고침 아이콘">
        데이터 새로고침
      </div>
      <div class="update-time">
        <span>{{ updatedTime }}</span>
        기준
      </div>
    </div>
  </div>
</template>

<script>
import formatter from "@/modules/formatter";

export default {
  name: "RealtimeRiskInfo",
  props: {
    all: Object
  },

  data() {
    return {
      formatter,
      isLoading: false,
      isLoaded: false,
      // autoReloadTimerId: null,
      timerId: null
    }
  },
  created() {
    // this.autoReloadTimerId = setInterval(this.refreshData, 10000);
  },


  computed: {
    riskStepSign() {
      return (this.all.risk_step_nm) ? this.all.risk_step_nm.substr(0, 2) + ' ' + this.riskLevel + '단계' : '';
    },
    riskStep() {
      return (this.all.risk_step_cd) ? this.all.risk_step_cd.substr(0, 2) : '';
    },
    riskLevel() {
      return (this.all.risk_step_cd) ? this.all.risk_step_cd.substr(-1, 1) : '';
    },
    updatedTime() {
      return formatter.date(this.all.anys_ymdhi);
    }
  },

  methods: {
    refreshData(){
      if (this.timerId === null){
        this.timerId = setInterval(this.checkLoaded, 2000)
        this.isLoading = true;
        this.isLoaded = false;
        // this.autoReloadTimerId = null;
        this.$store.dispatch('realtimeRisk')
            .then(() => {
              this.isLoaded = true;
            })
      }
    },
    checkLoaded(){
      if (this.isLoaded){
        clearInterval(this.timerId);
        this.timerId = null;
        this.isLoading = false;
        // this.autoReloadTimerId = setInterval(this.refreshData, 10000);
        console.log('<<<<< this.timerId', this.timerId)
      }
    },
    cancelAutoUpdate () {
        // clearInterval(this.autoReloadTimerId);
    }
  },
  beforeUnmount () {
    // this.cancelAutoUpdate();
  },

}
</script>

<style scoped lang="scss">
@import "src/assets/scss/_variables";

.realtime-title{
  display: flex;
  height: 120px;
  margin-bottom: 30px;

  > .title-header{
    margin: 0 30px 0 0;
    padding: 30px;
    flex-grow: 1;
    flex-shrink: 0;
    color: #454545;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    font-size: 16px;

    > h4{
      font-size: 18px;
      height: 45px;
      display: flex;
      align-items: center;

      > .title-text{
        margin-left: 20px;
        white-space: nowrap;
      }
    }

    > .risk-level{
      height: 60px;
      border-right: 1px solid $gray-line-color;
      display: flex;
      margin-left: auto;

      > span{
        margin: auto;
        white-space: nowrap;
      }

      > span.level-sign{
        background-color: $turquoise-color;
        color: #fff;
        font-size: 18px;
        font-weight: 700;
        padding: 14px 35px;
        border-radius: 30px;
        height: 45px;
        display: inline-block;
        box-sizing: border-box;
        margin-left: 35px;
        margin-right: 60px;
        white-space: nowrap;

        &.NR{
          background-color: $normal-level-color;
        }
        &.CR{
          background-color: $caution-level-color;
        }
        &.WR{
          background-color: $warning-level-color;
        }
        &.DG{
          background-color: $danger-level-color;
        }
      }
    }

    > .risk-percent{
      display: flex;
      flex-shrink: 0;
      width: 280px;
      //margin-left: auto;

      > span{
        margin: auto;
      }

      > span.percent-sign{
        font-size: 33px;
        font-weight: 700;
        color: $turquoise-text-color;
        margin-left: 0;

        &.NR{
          color: $normal-level-color;
        }
        &.CR{
          color: $caution-level-color;
        }
        &.WR{
          color: $warning-level-color;
        }
        &.DG{
          color: $danger-level-color;
        }
      }
    }
  }

  > .refresh-button{
    border: 1px solid #d1d1d1;
    border-radius: 10px;
    background: #2d969b;
    background: linear-gradient(128deg, #2d969b 0%,#267e83 100%); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
    width: 300px;
    text-align: center;
    color: #fff;
    display: flex;
    flex-direction: column;
    cursor: pointer;

    .button-label{
      font-size: 16px;
      font-weight: 700;
      margin: auto auto 5px auto;
      > img{
        vertical-align: -2px;
        margin-right: 4px;

        &.loading{
          animation: loading-spinner 1s infinite;
        }
      }
    }
    .update-time{
      font-size: 15px;
      margin: 5px auto auto auto;
    }
  }
}
@keyframes loading-spinner {
  to{
    transform: rotate(350deg);
  }
}
</style>