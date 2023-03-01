<template>
  <span v-if="name"
        class="risk-step-label"
        :class="riskCode">
    {{ riskStepSign }}
  </span>
  <span v-else>-</span>
</template>

<script>
export default {
  name: "RiskStepLabel",
  props: {
    enforceStepSuffix: {
      type: Boolean,
      default: false
    },
    useStepByName: {
      type: Boolean,
      default: false
    },
    deleteStepSuffix: {
      type: Boolean,
      default: false
    },
    name: String,
    code: String
  },

  computed:{
    riskStepSign() {
      let v = '';
      if (this.enforceStepSuffix){
        v = this.name + ' ' + this.riskLevel + ' 단계';
      }
      if (this.deleteStepSuffix && this.name) {
        v = this.name.substr(0,2);
      } else {
        v = this.name;
      }
      return v;
      // return (this.enforceStepSuffix) ? this.name.substr(0,2) + ' ' + this.riskLevel + ' 단계' : this.name;
    },
    riskCode() {
      let c;
      if (this.useStepByName && this.name){
        let temp = this.name.substr(0, 2);
        switch (temp){
          case '정상':
            c = 'NR';
            break;
          case '주의':
            c = 'CR';
            break;
          case '경고':
            c = 'WR';
            break;
          case '위험':
            c = 'DG';
            break;
          default:
            c = '';
        }
      } else {
        c = (this.code) ? this.code.substr(0, 2) : '';
      }
      return c
    },
    riskLevel() {
      let c;
      if (this.useStepByName && this.name){
        c = this.name.substr(2, 1);
      } else {
        c = (this.code) ? this.code.substr(-1, 1) : '';
      }
      return c
    }
  },
  data(){
    return{
      stepSuffix: false,
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";
//위험단계 표시 라벨 (둥근 테두리와 색상)
.risk-step-label{
  border-width: 2px;
  border-style: solid;
  display: inline-block;
  padding: 4px 10px;
  border-color: #aaa;
  border-radius: 30px;
  //box-sizing: border-box;
  max-width: 60px;
  white-space: nowrap;

  &.NR{
    border-color: $normal-level-color;
    color: $normal-level-color;
  }
  &.CR{
    border-color: $caution-level-color;
    color: $caution-level-color;
  }
  &.WR{
    border-color: $warning-level-color;
    color: $warning-level-color;
  }
  &.DG{
    border-color: $danger-level-color;
    color: $danger-level-color;
  }
}

</style>