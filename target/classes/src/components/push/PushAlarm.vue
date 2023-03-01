<template>
  <div class="push-list">
    <PushAlarmView v-for="item in viewList"
                   :key="item.id"
                   :msg="item.msg"
                   @close="onViewClose(item.id)"/>
  </div>
</template>

<script>
import PushAlarmView from "@/components/push/PushAlarmView";
import dayjs from "dayjs";
export default {
  name: "PushAlarm",
  components: {PushAlarmView},

  created() {
    this.socket = new WebSocket('ws://222.233.76.214:8080/sgsc/websocket');
    this.socket.onopen = this.onOpen;
    this.socket.onmessage = this.onMessage;
    this.socket.onclose = this.onClose;
    // this.testPush();
  },

  watch: {
    viewList:{
      handler(oldValue, newValue){
        console.log('New push alarm list:', newValue);
      },
      deep: true
    }
  },

  data() {
    return {
      socket: null,
      viewList: [],
      viewSeconds: 0, // 초 단위 (밀리세컨드 아님) 0으로 설정하면 알림이 사라지지 않음
      count: 0
    }
  },

  methods: {
    testPush(){
      let that = this;
      setTimeout(function (){
        that.count++
        that.addPush('긴급 알림 서버에 접속 되었습니다.' + that.count);
        if (that.count < 6)
          that.testPush()
      }, 2000);
    },
    onOpen(e){
      console.log('socket:', e)
      this.addPush('긴급 알림 서버에 접속 되었습니다.', 2);
    },
    onMessage(e){
      console.log('onMessage:', e)
      this.addPush(e.data);
    },
    onClose(e){
      console.log('onClose:', e)
      this.addPush('긴급 알림 서버와의 접속이 끊겼습니다.');
    },
    onViewClose(id){
      this.removePush(id);
    },
    addPush(msg, sec){
      let t = (sec) ? sec : this.viewSeconds;
      let obj = {
        time: dayjs(),
        msg: msg,
        id: 'push_' + Math.floor(Math.random() * 100000)
      }
      this.viewList.push(obj);
      if (this.viewSeconds || t){
        let that = this;
        obj.id = setTimeout(function (){
          clearInterval(obj.id);
          that.removePush(obj.id);
        }, t * 1000);
      }
    },
    removePush(id){
      let index = this.viewList.map((item) => {
        return item.id
      }).indexOf(id);
      this.viewList.splice(index, 1)
    }
  }
}
</script>

<style scoped lang="scss">
@import "../../assets/scss/variables";
.push-list{
  width: 600px;
  position: fixed;
  right: 30px;
  top: $layout-header-height;
  z-index: 9999;
}
</style>