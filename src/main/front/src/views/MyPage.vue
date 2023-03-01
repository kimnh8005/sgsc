<template>
  <div>
    <PageTitle title="마이 페이지"/>

    <div class="page-box">
      <PanelTitle title="회원 정보 수정"/>

      <table class="view-table wide-th">
        <tbody>
        <tr>
          <th>소속</th>
          <td>
            <select v-model="profile.dept_cd">
              <option v-for="item in DEPT_CD"
                      :value="item.cd_id"
                      :label="item.cd_nm"
                      :key="item.cd_id"/>
            </select>
          </td>
        </tr>
        <tr>
          <th>아이디</th>
          <td>{{ userData.user_id }}</td>
        </tr>
        <tr>
          <th>이름</th>
          <td>
            <input type="text" v-model="profile.user_nm">
          </td>
        </tr>
        <tr>
          <th>권한그룹</th>
          <td>{{ userData.auth_snm }}</td>
        </tr>
        <tr>
          <th>
            휴대폰 번호
            <span class="required">*</span>
          </th>
          <td>
            <input type="text" v-model="profile.contact">
          </td>
        </tr>
        <tr>
          <th>비밀번호 변경</th>
          <td>
            <button class="btn-dark" @click="() => { dialog.isShow = true }">비밀번호 변경하기</button>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="button-group center-type">
        <button @click="doSave" :disabled="disableSave">저 장</button>
      </div>
    </div>

    <Dialog title="비밀번호 변경" v-if="dialog.isShow" @close="hideDialog" class="no-hr-line">
      <template v-slot:body>
        <table class="view-table wide-th in-dialog">
          <tbody>
          <tr>
            <th>
              현재 비밀번호
              <span class="required">*</span>
            </th>
            <td>
              <input type="password" v-model="dialog.payload.password">
            </td>
          </tr>
          <tr>
            <th>
              새 비밀번호
              <span class="required">*</span>
            </th>
            <td>
              <input type="password" v-model="dialog.payload.changepassword">
              <span v-text="comparePasswordMsg" class="info-msg"></span>
            </td>
          </tr>
          <tr>
            <th>
              새 비밀번호 확인
              <span class="required">*</span>
            </th>
            <td>
              <input type="password" v-model="dialog.confirmChangePassword">
              <span v-text="compareNewPasswordMsg" class="info-msg"></span>
            </td>
          </tr>
          </tbody>
        </table>
      </template>

      <template v-slot:footer>
        <button @click="hideDialog">취 소</button>
        <button
            @click="savePassword"
            :disabled="dialog.payload.password.length === 0 || isInvalidPassword || isInvalidNewPassword">
          저 장
        </button>
      </template>
    </Dialog>
  </div>
</template>

<script>
import PageTitle from "@/components/panels/PageTitle";
import PanelTitle from "@/components/ui/PanelTitle";
import {mapGetters, mapState} from "vuex";
import Dialog from "@/components/ui/Dialog";
export default {
  name: "MyPage",
  components: {Dialog, PanelTitle, PageTitle},

  computed: {
    ...mapState('common/code',[
      'DEPT_CD'
    ]),
    ...mapGetters([
      'userData'
    ]),

    disableSave(){
      return this.changedPassword === false &&
          this.profile.dept_cd === this.userData.dept_cd &&
          (this.profile.user_nm.trim().length === 0 || this.profile.user_nm.trim() === this.userData.user_nm.trim()) &&
          this.profile.contact.trim() === this.userData.contact.trim()
    },

    isInvalidPassword(){
      return (this.dialog.payload.password.trim().length &&
          this.dialog.payload.password.trim().length &&
          this.dialog.payload.password.trim() === this.dialog.payload.changepassword.trim())
    },

    isInvalidNewPassword(){
      return (this.dialog.payload.changepassword.trim().length &&
          this.dialog.confirmChangePassword.trim().length &&
          this.dialog.payload.changepassword.trim() !== this.dialog.confirmChangePassword.trim())
    },

    comparePasswordMsg(){
      return (this.isInvalidPassword) ? '비밀번호가 변경되지 않았습니다.' : '';
    },

    compareNewPasswordMsg(){
      return (this.isInvalidNewPassword) ? '새 비밀번호와 일치하지 않습니다.' : '';
    }
  },

  created() {
    this.$store.dispatch('common/code/query', {code: 'DEPT_CD'});
    this.profile = JSON.parse(JSON.stringify(this.userData));
  },

  data() {
    return {
      changedPassword: false,
      profile: {
        dept_nm: '',
        dept_cd: '',
        user_id: '',
        user_nm: '',
        auth_snm: '',
        contact: ''
      },
      dialog: {
        isShow: false,
        isEdit: false,

        password: {
          current: '',
          changing: '',
          changingConfirm: ''
        },

        payload: {
          password: '',
          changepassword: '',
          user_id: ''
        },

        // isValidPassword: false,
        // isValidNewPassword: false,
        confirmChangePassword: '',

        infoMsg: ''
      }
    }
  },

  methods: {
    doSave() {
      console.log(this.profile)
      this.$store.dispatch('putUserData', JSON.parse(JSON.stringify(this.profile)))
      .then(response => {
        if (response.data.resultCode === 'NRL001'){
          alert('회원정보 수정 되었습니다.')
        } else {
          alert('에러 코드 : ' + response.data.resultCode + ' / 저장 되지 않았습니다.')
        }
      })
    },
    showDialog(info){
      this.dialog.isShow = true;
      this.dialog.activeItem = info;
    },

    hideDialog(){
      this.dialog.isShow = false;
    },

    savePassword(){
      console.log('<<<',this.dialog.payload, this.profile)
      this.dialog.payload.user_id = this.profile.user_id;
      this.$store.dispatch('changePassword', this.dialog.payload)
      .then(response => {
        if (response.data.resultCode === 'NRL001'){
          console.log('changePassword()>>>성공', response)
          alert('비밀번호가 변경 되었습니다.')
        } else {
          console.log('changePassword()>>>실패', response)
          alert('비밀번호 변경에 실패하였습니다.')
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/table";
table.view-table{

  th{
    width: 300px;
  }
  span.required{
    color: $warning-level-color;
    margin-left: 5px;
    padding-top: 5px;
    line-height: 10px;
    font-size: 20px;
  }
}
input[type=password] ~ span{
  margin-left: 20px;
}
.info-msg{
  color: $warning-level-color;
}
</style>