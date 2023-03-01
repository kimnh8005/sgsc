<template>
  <div class="filter-container">
    <h4 class="title">위험도</h4>
    <label v-for="item in list" :key="item.value">
      <input type="checkbox" v-model="item.bool" @change="onChange">
      {{ item.name }}
    </label>
  </div>
</template>

<script>
export default {
  name: "RiskFiltering",
  data(){
    return {
      list : [
        {
          name: '정상',
          value: 'NORL',
          bool: true
        },
        {
          name: '주의',
          value: 'CARE',
          bool: true
        },
        {
          name: '경고',
          value: 'WARG',
          bool: true
        },
        {
          name: '위험',
          value: 'DGER',
          bool: true
        }
      ]
    }
  },

  methods: {
    onChange(){
      console.log(this.list)
      let v = '';
      this.list.forEach((item) => {
        if (item.bool){
          if (v.length > 0){
            v += ',';
          }
          v += item.value;
        }
      })
      this.$emit('change', v);
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
  }

  > label{
    font-size: 15px;
    margin-right: 40px;
  }
}
</style>