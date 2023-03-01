<template>
  <div class="login-container">
    <!--<div class="login-visual">
      <div class="title-box">
        <h1>
          Smart Gas<br>
          Safety Control
        </h1>
        <h2>
          스마트 가스 안전제어
          <span>관리자</span>
        </h2>
      </div>
    </div>-->
    <div class="login-form">
      <img src="../assets/img/sgsc-login-logo.png" alt="SGSC logo" class="logo-img">
      <input type="text"
             placeholder="아이디 입력"
             v-model="user_id"
             @keyup.enter="onSubmit">
      <input type="password"
             placeholder="비밀번호 입력"
             v-model="password"
             @keyup.enter="onSubmit">
      <div class="msg-text" v-html="errorMsg"></div>
      <button class="login-button"
              :disabled="user_id === '' || password === ''"
              @click="onSubmit">
        로그인
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "LogIn",
  data(){
    return {
      user_id: '',
      password: '',
      device: 'W',

      errorDesc: {
        'ERR500': '아이디와 비밀번호가 일치하지 않습니다.<br>다시 입력해 주세요.',
        'ERR501': '로그인 하지 않은 사용자 입니다.',
        'ERR502': '아이디와 비밀번호가 일치하지 않습니다.<br>다시 입력해 주세요.',
        'ERR503': '아이디와 비밀번호가 일치하지 않습니다.<br>다시 입력해 주세요.',
        'ERR504': '이미 로그인 한 사용자 입니다.',
        'ERR505': '권한이 없는 계정 입니다.',
      },

      errorMsg : ''
    }
  },

  mounted() {
    this.$store.dispatch('logout')
  },

  methods: {
    onSubmit(){
      this.$store
          .dispatch('login', {user_id: this.user_id, password: this.password, device: 'W'})
          .then(response => {
            switch (response.data.resultCode){
              case 'NRL001':
                this.$router.push('/');
                break;
              case 'ERR500':
              case 'ERR501':
              case 'ERR502':
              case 'ERR503':
              case 'ERR504':
                this.errorMsg = this.errorDesc[response.data.resultCode];
                break;
              default:
                this.errorMsg = '';
            }
          })
    }
  }
}
</script>

<style scoped lang="scss">
.login-container{
  display: flex;
  height: 100vh;
  background: url("~@/assets/img/visual-bg.jpg") center center;
  background-size: cover;

  /*.login-visual{
    flex-grow: 1;
    //width: 100%;
    background: url("~@/assets/img/visual-bg.jpg") center center;
    background-size: cover;


    .title-box{
      margin: 220px 8% 0 15%;
      h1{
        font-size: 100px;
        color: #fff;
      }
      h2{
        margin-top: 30px;
        font-size: 25px;
        color: #9cd9d9;
        > span{
          color: #61d8dd;
        }
      }
    }
  }*/
  .login-form{
    flex-shrink: 1;
    width: 300px;
    text-align: center;
    display: flex;
    flex-direction: column;
    margin: auto;

    .logo-img{
      margin-top: auto;
      margin-bottom: 20px;
    }
    input[type=text],
    input[type=password]{
      width: 100%;
      height: 47px;
      box-sizing: border-box;
      padding: 0 10px;
      border: 1px solid #d1d1d1;
      margin-bottom: 7px;
      background-color: rgba(255,255,255,0.8);
      transition: background-color 1.5s;
      &:focus{
        background-color: #fff;
        transition: background-color 0.3s;
      }
    }
    .msg-text{
      color: #ff0707;
      line-height: 20px;
      min-height: 15px;
      text-align: left;
      padding: 0 10px;
      margin-bottom: 15px;
    }
    .login-button{
      width: 100%;
      height: 45px;
      border-radius: 0;
      font-size: 17px;
      font-weight: 700;
      letter-spacing: 5px;
      margin-bottom: auto;

      &[disabled]{
        opacity: 0.5;
      }
    }
  }
}
</style>