<template>
  <header>
    <div class="logo">
      <router-link to="/">
        <img src="../assets/img/sgsc-logo.png" alt="SGSC Logo">
      </router-link>
    </div>
    <div class="info" v-if="authInfo.hasAuth">
      <div class="send-msg">
        <router-link to="/support/message">
          <img src="../assets/img/send-msg.png" alt="비상대피 메시지 전송">
          <span>비상대피 메시지 전송</span>
        </router-link>
      </div>
      <div class="info-division"></div>
      <div class="login-info">
        <span>
          <router-link to="/mypage">{{ userData.user_nm }}</router-link> 님 환영합니다.
        </span>
        <button @click="logout">로그아웃</button>
      </div>
    </div>

    <PushAlarm/>

  </header>
</template>

<script>
import router from "@/router";
import {mapGetters} from "vuex";
import PushAlarm from "@/components/push/PushAlarm";

export default {
  name: "layout-header",
  components: {PushAlarm},
  props: {
    authInfo: Object
  },

  computed: {
    ...mapGetters([
      'userData'
    ]),
  },

  methods: {
    logout(){
      this.$store.dispatch('logout').then(() => {
        router.push('/login');
      })
    }
  }
}
</script>

<style scoped lang="scss">
header{
  box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2);
  display: flex;
  justify-content: space-between;

  .logo{
    margin: 23px 30px;
  }
  .info{
    display: flex;
    margin: 30px;
    font-size: 16px;
    align-items: center;

    .send-msg{
      height: 33px;

      > a{
        display: flex;
        align-items: center;

        span{
          display: inline-block;
          line-height: 1em;
          padding: 0 10px;
        }
      }
    }

    .info-division{
      width: 2px;
      height: 16px;
      margin: 0 8px;
      background-color: #bebebe;
    }

    .login-info{
      height: 33px;
      display: flex;
      align-items: center;
      //line-height: 33px;

      > span{
        display: inline-block;
        line-height: 1em;
        padding: 0 10px;

        a{
          font-weight: bold;
        }
      }
    }
  }
}
</style>