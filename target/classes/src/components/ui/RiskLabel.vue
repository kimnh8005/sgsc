<template>
  <div v-if="validated" :class="{'double-row' : !isDoubleRow}">
    <RiskStepLabel v-if="showRiskStep"
        :class="{'full-size': isDoubleRow}"
        :delete-step-suffix="isDeleteStepSuffix"
        :name="riskName"
        :code="riskCode"/>
    <div v-if="showRiskLabel" class="risk-label">{{ riskNum + riskUnit }}</div>
  </div>
  <div v-else>-</div>
</template>

<script>
import RiskStepLabel from "@/components/ui/RiskStepLabel";
export default {
  name: "RiskLabel",
  components: {RiskStepLabel},
  props: {
    isDoubleRow: Boolean,
    isDeleteStepSuffix: Boolean,
    showRiskStep: {
      type: Boolean,
      default: true
    },
    showRiskLabel: {
      type: Boolean,
      default: true
    },
    name: String,
    code: String,
    num: Number,
    unit: String,

    textData: String
  },

  computed: {
    validated() {
      return this.textData || this.code;
    },
    isTextData(){
      return (this.textData && (this.textData.indexOf(':') > -1));
    },
    riskName(){
      let n = '';
      if (this.isTextData){
        // let temp = this.textData.split(':')[1];
        // n = temp.substr(0, 2);
        if (this.textData.indexOf(':') > -1){
          n = this.textData.split(':')[1];
        } else {
          n = this.textData;
        }
      } else {
        if (this.name){
          n = this.name;
        } else if (this.code){
          let temp = this.code.substr(0, 2);
          let step = this.code.substr(-1, 1)
          switch (temp){
            case 'NR':
              n = '정상';
              break;
            case 'CR':
              n = '주의';
              break;
            case 'WR':
              n = '경고';
              break;
            case 'DG':
              n = '위험';
              break;
            default:
              n = '-';
          }
          n = n + ' ' + step + ' 단계';
        }
      }

      if (this.isDeleteStepSuffix){
        n = n.substr(0, 2);
      }
      return n;
    },

    riskCode(){
      let c;
      if (this.isTextData){
        let temp = this.textData.split(':')[1].substr(0, 2);
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
        c = this.code;
      }
      return c;
    },

    riskNum() {
      let n = 0;
      if (this.isTextData){
        let temp = this.textData.split(':');
        n = Number(temp[0])
      } else {
        n = this.num;
      }
      return n;
    },

    riskUnit() {
      return (this.unit) ? this.unit : '%';
    }
  },

  methods: {

  }
}
</script>

<style scoped lang="scss">
.double-row{
  display: flex;
  align-items: center;
  justify-content: center;

  .risk-label{
    margin-top: 0;
    margin-left: 5px;
  }
}
.risk-label{
  margin-top: 10px;
}
</style>