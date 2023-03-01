<template>
  <div class="screen-bg">
    <div class="dialog-container">
      <div class="dialog-header">
        <h2>{{ title }}</h2>
        <div class="close-button" @click="onClickClose"></div>
      </div>
      <div class="subtitle">
        <slot name="subtitle"></slot>
        <div class ="filler"></div>
        <slot name="time"></slot>
      </div>
      <div class="dialog-body">
        <slot name="body"></slot>
      </div>
      <div class="dialog-footer">
        <slot name="footer"></slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AccidentScenarioPopup",

  props: {
    title: {
      type: String,
      default: '사고 시나리오'
    }
  },

  methods: {
    onClickClose() {
      this.$emit('close');
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";

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

  &.no-hr-line{
    .dialog-container{
      .dialog-body{
        padding: 0;
      }
      .dialog-footer{
        margin-top: 0;
      }
    }
  }

  .dialog-container{
    background-color: #fff;
    min-width: 1400px;
    min-height: 800px;
    box-shadow: 0 4px 18px rgba(0,0,0,0.4);
    padding: 30px;
    display: flex;
    flex-direction: column;

    .dialog-header{
      height: 40px;
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

    .subtitle {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 23px;

      h2 {
        font-size: 20px;
        color: $default-font-color;
      }

      h3 {
        font-size: 16px;
        font-weight: 500;
        letter-spacing: -0.48px;
        color: $default-font-color;
      }

      .arrow {
        margin-left: 15px;
        margin-right: 13px;
      }

      .filler {
        flex-grow: 1;
      }

    }

    .dialog-body{
      padding: 20px 0;
      margin: 0 auto;

      .in-popup{
        width: 1400px;
      }
    }

    .dialog-footer{
      height: 40px;
      padding-top: 30px;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-top: auto;

      button{
        padding: 0 45px;
        height: 50px;
        margin: 0 7px;
      }
    }
  }
}
</style>