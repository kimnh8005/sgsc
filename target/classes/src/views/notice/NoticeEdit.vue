<template>
  <div>
    <PageTitle title="공지사항"/>

    <div class="page-box">
      <PanelTitle title="공지사항 등록/수정"/>

      <div class="article-view">
        <div class="article-header edit-mode">
          <div class="item-box">
            <label>
              분류
              <span class="required">*</span>
            </label>
            <select v-model="article.category">
              <option value="all">선택</option>
              <option v-for="item in NOTI_TYPE_CD"
                      :value="item.cd_id"
                      :key="item.cd_id">
                {{ item.cd_nm }}
              </option>
            </select>
          </div>
          <div class="item-box">
            <label>
              제목
              <span class="required">*</span>
            </label>
            <input type="text" v-model="article.title">
          </div>
        </div>

        <div class="article-body edit-mode">
          <vue-editor v-model="article.bodyText" :editor-toolbar="customToolbar" />
        </div>

        <div class="attach-file">
          <div class="attach-inner">
            첨부파일 :
            <input type="text" readonly v-model="article.attach.fileName">
            <input type="file" style="display: none" id="attach">
            <button class="btn-dark"
                    @click="onClickSearchAttach">
              파일 선택
            </button>
            <span class="error-msg">{{ (isExcess10Mb) ? '업로드 파일은 10Mb 크기 제한이 있습니다.': ''}}</span>
          </div>
        </div>
      </div>

      <div class="button-group center-type article-buttons">
        <button @click="goView">취 소</button>
        <button
            @click="doSave"
            :disabled="disableSave"
        >저 장</button>
      </div>

    </div>
    <LoadingSpinner v-if="isShowSpinner"/>
  </div>
</template>

<script>
import PanelTitle from "@/components/ui/PanelTitle";
import PageTitle from "@/components/panels/PageTitle";
import {mapState} from "vuex";
import dayjs from "dayjs";
// import { VueEditor } from "vue2-editor";
import { VueEditor } from "vue2-editor/dist/vue2-editor.core.js";
import LoadingSpinner from "@/components/ui/LoadingSpinner";

export default {
  name: "NoticeEdit",
  components: {LoadingSpinner, VueEditor, PageTitle, PanelTitle},

  props: {
    mode: {
      type: String,
      required: true
    },
    noti_sid: {
      type: String
    }
  },

  computed: {
    ...mapState('common/notice',[
      'noticeView'
    ]),
    ...mapState('common/code',[
      'NOTI_TYPE_CD'
    ]),

    disableSave() {
      return (this.article.category === 'all') ||
          (this.article.title.trim() === '') ||
          (this.article.bodyText.trim() === '') ||
          (this.isExcess10Mb)
    },

    isExcess10Mb() {
      return (this.article.attach.file && this.article.attach.file.size > 1e+7);
    }
  },

  created() {
    this.queryServer();
    this.$store.dispatch('common/code/query', {code: 'NOTI_TYPE_CD'})
  },

  data() {
    return {
      dayjs,

      article: {
        category: 'all',
        title: '',
        bodyText: '',
        attach: {
          fileName : null,
          file: null
        }
      },
      isShowSpinner: false,

      customToolbar: [
        [{ 'header': [false, 1, 2, 3, 4, 5, 6, ] }],
        ['bold', 'italic', 'underline', 'strike'],
        [{'align': ''}, {'align': 'center'}, {'align': 'right'}, {'align': 'justify'}],
        ['blockquote', 'code-block'],
        [{ 'list': 'ordered'}, { 'list': 'bullet' }],
        [{ 'indent': '-1'}, { 'indent': '+1' }],
        [{ 'color': [] }, { 'background': [] }],
        ['link', 'video'],
        ['clean'],
      ]
    }
  },

  methods: {
    queryServer(){
      if(this.mode === 'edit'){
        this.$store.dispatch('common/notice/noticeView', {noti_sid: this.noti_sid})
            .then(() => {
              this.article.title = this.noticeView.current.noti_title;
              this.article.bodyText = this.noticeView.current.noti_cnt;
              this.article.category = this.noticeView.current.noti_type_cd;
              this.article.attach.fileName = this.noticeView.current.file_nm;
              // this.article.attach.physicalFileName = this.noticeView.current.file_phy_nm;
            })
      }
    },
    goView(){
      let url = (this.noti_sid) ? '/notice/view/' + this.noti_sid : '/notice'
      this.$router.push(url)
    },
    onClickSearchAttach(){
      let input = document.getElementById('attach');
      input.onchange = e => {
        this.article.attach.file = e.target.files[0];
        this.article.attach.fileName = this.article.attach.file.name;
      };
      input.click();
    },
    doSave(){
      this.isShowSpinner = true;
      let formData = new FormData();
      formData.append('Content-Type', 'multipart/form-data');
      formData.append('noti_type_cd', this.article.category);
      formData.append('noti_title', this.article.title);
      formData.append('noti_cnt', this.article.bodyText);
      if (this.article.attach.file){
        formData.append('file', this.article.attach.file);
      }

      let stateName;
      if (this.mode === 'edit'){
        stateName = 'common/notice/putNoticeView';
        formData.append('noti_sid', this.noti_sid)
      } else if (this.mode === 'write'){
        stateName = 'common/notice/postNoticeView';
      }
      this.$store.dispatch(stateName, formData)
          .then((response) => {
            if(response.data.resultCode === 'NRL001'){
              this.goView();
            }
            this.isShowSpinner = false;
          })
          .catch(() => {
            this.isShowSpinner = false;
          })
    }
  }
}
</script>

<style scoped lang="scss">
//@import "~vue2-editor/dist/vue2-editor.css";
//@import '~quill/dist/quill.core.css';
//@import '~quill/dist/quill.bubble.css';
@import '~quill/dist/quill.snow.css';
//@import "src/assets/scss/editor-custom";
@import "src/assets/scss/table";

.article-view{

  .item-box{
    display: flex;
    align-items: center;
    padding: 12px 0;

    > h4{
      margin-right: 30px;
    }

    label{
      display: inline-flex;
      flex-shrink: 0;
      width: 80px;

      span.required{
        color: $warning-level-color;
        margin-left: 5px;
        padding-top: 5px;
        line-height: 10px;
        font-size: 20px;
      }
    }
    select {
      min-width: 150px;
      padding-left: 10px;
    }
    input[type=text]{
      width: 100%;
    }
  }

  .article-header{
    border-top: 2px solid $gray-line-color;
    border-bottom: 1px solid $gray-line-color;
    padding: 15px 30px;

    &.edit-mode{
      padding-right: 0;
    }

    h2{
      font-size: 20px;
      font-weight: 400;
      height: 40px;
    }
  }

  .article-body{
    min-height: 100px;
    padding: 30px;

    &.edit-mode{
      padding: 25px 0;
    }
  }

  .attach-file{
    background-color: #f6f6f6;
    margin-bottom: 28px;
    padding: 2px 0;

    > .attach-inner{
      border: 1px solid $gray-line-color;
      padding: 0 30px;
      display: flex;
      align-items: center;
      border-left: 0;
      border-right: 0;
      min-height: 96px;
      box-sizing: border-box;

      input[type=text]{
        width: 300px;
        margin-left: 20px;
      }
      button{
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;
      }
    }
  }
}
.article-buttons{
  margin-bottom: 40px;
}
</style>