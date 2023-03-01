<template>
  <div class="push-container">
    <div class="msg">
      {{ msg }}
    </div>
    <div class="close-button" @click="onClose"></div>
  </div>
</template>

<script>
export default {
  name: "PushAlarmView",

  props: {
    msg: String
  },

  data() {
    return {

    }
  },

  methods: {
    onClose(){
      this.$emit('close');
    }
  }
}
</script>

<style scoped lang="scss">
@import "../../assets/scss/variables";
.push-container{
  width: 600px;
  height: 100px;
  background-color: #fff;
  padding: 30px;
  box-sizing: border-box;
  border-radius: $border-radius;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  margin-top: 30px;

  .msg{
    background: url("~@/assets/img/icon-siren.png") no-repeat left center;
    padding-left: 50px;
    padding-right: 10px;
    min-height: 21px;
    line-height: 21px;
    font-size: 18px;
  }
  .close-button{
    margin-left: auto;
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
</style>