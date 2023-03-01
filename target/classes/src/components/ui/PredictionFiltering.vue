<template>
  <div class="filter-container">
    <h4 class="title">설비</h4>
    <select v-model="selectProcs" @change="onProcessChange">
      <option value="all">전체 시설</option>
      <option v-for="item in process"
              :value="item.procs_id"
              :key="item.procs_id">
        {{ item.procs_nm }}
      </option>
    </select>
    <select v-model="selectEqmt">
      <option value="all">전체 설비</option>
      <option v-for="item in equipment"
              :value="item.eqmt_id"
              :key="item.eqmt_id">
        {{ item.eqmt_nm }}
      </option>
    </select>
    <button @click="onClick">검색</button>
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "PredictionFiltering",

  computed: {
    ...mapState('common/process',[
      'process'
    ]),
    ...mapState('common/equipment',[
      'equipment'
    ]),

    activeEquipmentId() {
      return this.activeEquipId;
    }
  },

  data() {
    return {
      selectedProcessName: '',
      selectedEquipmentName: '',
      selectProcs: 'all',
      selectEqmt: 'all'
    }
  },

  created() {
    // 생상공정 목록 가져오자
    this.$store.dispatch('common/process/query', {queryString: ''});
  },

  methods: {
    onClick() {
      for (const item in this.process) {
        // console.log(this.process[item]);
        if (this.process[item].procs_id === this.selectProcs) {
          this.selectedProcessName = this.process[item].procs_nm; 
        }
      }

      for (const item in this.equipment) {
        // console.log(this.equipment[item]);
        if (this.equipment[item].eqmt_id === this.selectEqmt) {
          this.selectedEquipmentName = this.equipment[item].eqmt_nm; 
        }
      }

      this.$emit('filtering', {
        process: this.selectProcs,
        equipment: this.selectEqmt,
        selected_process_nm: this.selectedProcessName,
        selected_equipment_nm: this.selectedEquipmentName,
      })
    },
    // 생산공정이 바뀌면 그에 속한 설비 목록을 새로 받아오자
    onProcessChange(){
      this.selectEqmt = 'all';
      if (this.selectProcs === 'all'){
        this.$store.dispatch('common/equipment/clear', {stateName: 'equipment'});
      } else {
        this.$store.dispatch('common/equipment/query', {queryString: '?procs_id=' + this.selectProcs});
      }
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";

.filter-container{
  height: 80px;
  box-sizing: border-box;
  padding: 20px;
  border: 1px solid $gray-line-color;
  border-radius: $half-border-radius;
  display: flex;
  align-items: center;
  margin-bottom: 40px;

  > h4{
    padding-right: 30px;
    height: 100%;
    line-height: 40px;

    &:not(:first-child){
      padding-left: 30px;
      margin-left: 30px;
      border-left: 1px solid $gray-line-color;
    }
  }

  > select {
    height: 40px;
    width: 250px;
    margin-right: 8px;
  }

  button{
    height: 40px;
    width: 130px;
    margin-left: auto;
  }
}
</style>