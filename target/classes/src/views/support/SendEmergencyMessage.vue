<template>
  <div>
    <PageTitle/>
    <div class="page-box">
      <PanelTitle title="발송대상"/>

      <div class="filter-container">
        <h4 class="title">소 속</h4>
        <select v-model="payload.dept_cd">
          <option value="">전체</option>
          <option v-for="item in DEPT_CD"
                  :value="item.cd_id"
                  :label="item.cd_nm"
                  :key="item.cd_id"/>
        </select>
        <h4 class="title">권 한</h4>
        <label v-for="item in authList"
               :key="item.auth_sid">
          <input type="checkbox"
                 v-model="payload.auth_sid"
                 :value="item.auth_sid + ''">
          {{ item.auth_nm }}
        </label>
      </div>

      <PanelTitle title="발송내용"/>

      <div class="filter-container">
        <h4 class="title">메세지</h4>
        <input type="text" placeholder="비상대피 메시지 내용" v-model="payload.send_msg">
        <span class="msg-count" :class="{warning: disableSend}">{{ msgCount }}</span>
        <span class="info-msg"></span>
      </div>

      <div class="button-group center-type">
        <button @click="initialize" class="btn-white">초기화</button>
        <button @click="sendMessage" :disabled="disableSend">전 송</button>
      </div>

    </div>
  </div>
</template>

<script>
import PanelTitle from "@/components/ui/PanelTitle";
import PageTitle from "@/components/panels/PageTitle";
import {mapState} from "vuex";
export default {
  name: "SendEmergencyMessage",
  components: {PageTitle, PanelTitle},

  computed: {
    ...mapState('common/code',[
      'DEPT_CD'
    ]),
    ...mapState('common/auth',[
      'authList'
    ]),

    msgCount(){
      return this.payload.send_msg.length + ' / 30 글자수';
    },
    disableSend(){
      return this.payload.send_msg.length > 30 || this.payload.send_msg.trim() === '';
    }
  },

  created() {
    this.$store.dispatch('common/code/query', {code: 'DEPT_CD'});
    this.$store.dispatch('common/auth/authList');
  },

  data() {
    return {
      payload: {
        send_type_cd: 'SD0002',
        dept_cd: '',
        auth_sid: ['50'],
        send_msg: '[긴급상황] 즉시 비상대피 바랍니다.',
      }
    }
  },

  methods: {
    initialize(){
      let bool = confirm('작성내용을 초기화하시겠습니까?');
      if (bool) {
        this.payload = {
          send_type_cd: 'SD0002',
          dept_cd: '',
          auth_sid: ['50'],
          send_msg: '[긴급상황] 즉시 비상대피 바랍니다.',
        }
      }
    },
    sendMessage(){
      console.log(this.payload)
      let bool = confirm('비상 대피 메시지를 전송하시겠습니까?');
      if (bool){
        this.$store.dispatch('common/message/sendMessage', this.payload)
        .then((response) => {
          if (response.data.resultCode === 'NRL001'){
            alert('비상 대피 메시지를 전송하였습니다.')
          }
        })
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
    padding-left: 10px;
    height: 100%;
    width: 50px;
    line-height: 40px;
    flex-shrink: 0;

    &:not(:first-child){
      padding-left: 30px;
      margin-left: 30px;
      border-left: 1px solid $gray-line-color;
    }
  }

  > select {
    height: 40px;
    width: 250px;
  }

  > input[type=text]{
    width: 60%;
    height: 40px;
  }

  label{
    margin-right: 40px;
  }

  button{
    height: 40px;
    width: 130px;
    margin-left: auto;
  }
}

.button-group{
  margin-bottom: 30px;
}
.msg-count{
  margin-left: 30px;
  &.warning{
    color: $warning-level-color;
  }
}
</style>