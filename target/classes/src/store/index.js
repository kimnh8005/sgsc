import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import API_URL from "@/modules/API_URL";
import router from "@/router";

Vue.use(Vuex)


export default new Vuex.Store({
  state: {
    accessToken: null,
    userData: null,

    realtimeRisk: {
      all: {},
      procs: [],
      eqmt: [],
      facty: []
    },
    riskHistory: {
      list: []
    },
    riskProcessHistoryMap: {
      data: [],
      cols: []
    },
    riskEquipmentHistoryMap: {
      data: [],
      cols: []
    },
    riskEquipmentCrigHistory: {
      list: []
    },
    riskEquipmentPartDbHistory: {
      list: []
    },
    riskEquipmentKsec3History: {
      list: []
    },
    equipmentByProcsId: {
      list: []
    },
    realtimeEquipmentCrig: {
      list: []
    },
    realtimeEquipmentPartDb: {
      list: []
    },
    realtimeEquipmentKsec: {
      list: []
    },
    realtimeAccidentScenarioPopup: {
      list: []
    },
    predictEquipmentRiskTop10: {
      hour24: [],
    },
    predictEquipmentHour24: {
      list: []
    },
    predictEquipmentDay7: {
      list: []
    },
    predictEquipmentWeek4: {
      list: []
    },

    gasDiffusionList: [],

    gasRiskProposalList: {
      list: []
    },

    humanErrorProposalList: {
      list: []
    },

    humanErrorDayRate: {
      FL: 0,
      SC: 0
    },

    humanErrorDay7Rate: {
      list: []
    },

    workJournalList: {
      list: []
    },

    workJournal: {}
  },

  mutations: {
    login(state, data) {
      localStorage.setItem('accessToken', data.Authorization);
      state.accessToken = data.Authorization;
      axios.defaults.headers.common['Authorization'] = data.Authorization;
    },

    logout(state) {
      state.accessToken = null;
      delete localStorage.accessToken;

      state.userData = null;
      delete localStorage.user_id;
      delete localStorage.user_nm;
      delete localStorage.auth_sid;
      delete localStorage.auth_snm;
      delete localStorage.dept_nm;
      delete localStorage.dept_cd;
      delete localStorage.contact;
    },

    userData(state, data) {
      localStorage.setItem('user_id', data.user_id);
      localStorage.setItem('user_nm', data.user_nm);
      localStorage.setItem('auth_sid', data.auth_sid);
      localStorage.setItem('auth_snm', data.auth_snm);
      localStorage.setItem('dept_nm', data.dept_nm);
      localStorage.setItem('dept_cd', data.dept_cd);
      localStorage.setItem('contact', data.contact);
      state.userData = data;
    },

    realtimeRisk(state, data) {
      state.realtimeRisk = data;
    },

    riskHistory(state, data) {
      state.riskHistory = data;
    },

    riskProcessHistoryMap(state, data) {
      state.riskProcessHistoryMap = data;
    },

    riskEquipmentHistoryMap(state, data) {
      state.riskEquipmentHistoryMap = data;
    },

    riskEquipmentCrigHistory(state, data) {
      state.riskEquipmentCrigHistory = data;
    },

    riskEquipmentPartDbHistory(state, data) {
      state.riskEquipmentPartDbHistory = data;
    },

    riskEquipmentKsec3History(state, data) {
      state.riskEquipmentKsec3History = data;
    },

    equipmentByProcsId(state, data) {
      state.equipmentByProcsId = data;
      console.log('equipmentByProcsId', data, state.equipmentByProcsId)
    },

    realtimeEquipmentCrig(state, data) {
      console.log('realtimeEquipmentCrig', data)
      state.realtimeEquipmentCrig = data;
    },

    realtimeEquipmentPartDb(state, data) {
      console.log('realtimeEquipmentPartDb', data)
      state.realtimeEquipmentPartDb = data;
    },

    realtimeEquipmentKsec(state, data) {
      console.log('realtimeEquipmentKsec', data);
      state.realtimeEquipmentKsec = data;
    },

    realtimeAccidentScenarioPopup(state, data) {
      console.log('realtimeAccidentScenarioPopup', data);
      state.realtimeAccidentScenarioPopup = data;
    },

    predictEquipmentRiskTop10(state, data) {
      console.log('predictEquipmentRiskTop10', data);
      state.predictEquipmentRiskTop10 = data;
    },

    predictEquipmentHour24(state, data) {
      console.log('predictEquipmentHour24', data);
      state.predictEquipmentHour24 = data;
    },

    predictEquipmentDay7(state, data) {
      console.log('predictEquipmentDay7', data);
      state.predictEquipmentDay7 = data;
    },

    predictEquipmentWeek4(state, data) {
      console.log('predictEquipmentWeek4', data);
      state.predictEquipmentWeek4 = data;
    },

    gasDiffusionList(state, data) {
      console.log('gasDiffusionList', data);
      state.gasDiffusionList = data;
    },

    gasRiskProposalList(state, data) {
      console.log('gasRiskProposalList', data);
      state.gasRiskProposalList = data;
    },

    humanErrorProposalList(state, data) {
      console.log('humanErrorProposalList', data);
      state.humanErrorProposalList = data;
    },

    humanErrorDayRate(state, data) {
      console.log('humanErrorDayRate', data);
      state.humanErrorDayRate = data;
    },

    humanErrorDay7Rate(state, data) {
      console.log('humanErrorDay7Rate', data);
      state.humanErrorDay7Rate = data;
    },

    workJournalList(state, data) {
      console.log('workJournalList', data);
      state.workJournalList = data;
    },

    workJournal(state, data) {
      console.log('workJournal', data);
      state.workJournal = data;
    },
  },

  getters: {
    accessToken(state) {
      if (!state.accessToken) {
        // console.log('<<<<< accessToken 이 없어 localStorage 에서 가져옴', localStorage.getItem('accessToken'))
        state.accessToken = localStorage.getItem('accessToken');
      }
      axios.defaults.headers.common['Authorization'] = state.accessToken;

      return state.accessToken;
    },
    axiosConfig(state, getters) {
      return {
        headers: {
          'Authorization': getters.accessToken
          // 'Authorization': 'dev'
        }
      }
    },
    userData(state) {
      if (!state.userData) {
        state.userData = {
          user_id: localStorage.getItem('user_id'),
          user_nm: localStorage.getItem('user_nm'),
          auth_sid: localStorage.getItem('auth_sid'),
          auth_snm: localStorage.getItem('auth_snm'),
          dept_nm: localStorage.getItem('dept_nm'),
          dept_cd: localStorage.getItem('dept_cd'),
          contact: localStorage.getItem('contact')
        }
      }
      return state.userData;
    }
  },

  actions: {
    login(context, {user_id, password, device}) {
      /*return postQuery(context, 'login', API_URL.LOGIN, {user_id, password, device})
        .then(response => {
          console.log('action>login()', response)
          return response;
        }).catch(error => {
          console.log('action>login():error', error)
        });*/

      return axios.post(API_URL.LOGIN, {user_id, password, device}, context.rootGetters.axiosConfig)
        .then((response) => {
          console.log('Vuex actions login >>', response)
          if (response.data.resultCode === 'NRL001') {
            // token 저장
            context.commit('login', response.data);
            // 그 밖의 로그인 정보 저장
            context.commit('userData', response.data);

            return response;
          }
        })
        .catch(catchError)
    },


    logout(context) {
      return postQuery(context, 'logout', API_URL.LOGOUT, {user_id: context.getters.userData.user_id})
    },

    putUserData(context, options) {
      return axios.put(API_URL.USER, options, context.rootGetters.axiosConfig)
        .then((response) => {
          if (response.data.resultCode === 'NRL001') {
            console.log('putUserData()>>>', response)
            context.commit('userData', options);
          } else {
            return response;
          }
        })
        .catch(catchError)
    },

    changePassword(context, options) {
      return axios.put(API_URL.CHANGE_PASSWORD, options, context.rootGetters.axiosConfig)
        .then(response => {
          return response;
        })
    },

    /**
     * 가스 위험도 분석 > 실시간 위험도
     * @param context
     * @returns {Promise<void>}
     */
    realtimeRisk(context) {
      return getQuery(context, 'realtimeRisk', API_URL.REALTIME_RISK);
    },

    /**
     * 실시간 위험도 > 설비 상세
     * @param context
     * @param equipmentId
     * @returns {Promise<void>}
     */
    realtimeEquipmentCrig(context, {equipmentId}) {
      return getQuery(context, 'realtimeEquipmentCrig', API_URL.REALTIME_EQUIPMENT_CRIG + '/' + equipmentId);
    },

    realtimeEquipmentPartDb(context, {equipmentId}) {
      return getQuery(context, 'realtimeEquipmentPartDb', API_URL.REALTIME_EQUIPMENT_PART_DB + '/' + equipmentId);
    },

    realtimeEquipmentKsec(context, {equipmentId}) {
      return getQuery(context, 'realtimeEquipmentKsec', API_URL.REALTIME_EQUIPMENT_KSEC + '/' + equipmentId);
    },

    realtimeAccidentScenarioPopup(context, {anayseSystemCode, eqmtID, anayseYMDH}) {
      return getQuery(context, 'realtimeAccidentScenarioPopup', API_URL.REALTIME_ACCIDENT_SCENARIO_POPUP + '/' + anayseSystemCode + '/' + eqmtID + '/' + anayseYMDH);
    },

    riskHistory(context, {queryString}) {
      return getQuery(context, 'riskHistory', API_URL.RISK_HISTORY + queryString);
    },

    riskProcessHistoryMap(context, {queryString}) {
      return getQuery(context, 'riskProcessHistoryMap', API_URL.RISK_PROCESS_HISTORY_MAP + queryString);
    },

    riskEquipmentHistoryMap(context, {queryString}) {
      return getQuery(context, 'riskEquipmentHistoryMap', API_URL.RISK_EQUIPMENT_HISTORY_MAP + queryString);
    },

    riskEquipmentCrigHistory(context, {queryString}) {
      return getQuery(context, 'riskEquipmentCrigHistory', API_URL.RISK_EQUIPMENT_CRIG_HISTORY + queryString);
    },

    riskEquipmentPartDbHistory(context, {queryString}) {
      return getQuery(context, 'riskEquipmentPartDbHistory', API_URL.RISK_EQUIPMENT_PART_DB_HISTORY + queryString);
    },

    riskEquipmentKsec3History(context, {queryString}) {
      return getQuery(context, 'riskEquipmentKsec3History', API_URL.RISK_EQUIPMENT_KSEC3_HISTORY + queryString);
    },

    equipmentByProcsId(context, {queryString}) {
      return getQuery(context, 'equipmentByProcsId', API_URL.EQUIPMENT + queryString);
    },

    predictEquipmentRiskTop10(context) {
      return getQuery(context, 'predictEquipmentRiskTop10', API_URL.PREDICT_EQUIPMENT_RISK_TOP10);
    },

    predictEquipmentHour24(context, options) {
      return getQuery(context, 'predictEquipmentHour24', API_URL.PREDICT_EQUIPMENT_HOUR24 + getQueryString(options));
    },

    predictEquipmentDay7(context, options) {
      return getQuery(context, 'predictEquipmentDay7', API_URL.PREDICT_EQUIPMENT_DAY7 + getQueryString(options));
    },

    predictEquipmentWeek4(context, options) {
      return getQuery(context, 'predictEquipmentWeek4', API_URL.PREDICT_EQUIPMENT_WEEK7 + getQueryString(options));
    },

    gasDiffusionList(context, options) {
      return axios.post(API_URL.GAS_DIFFUSION_LIST, options, context.getters.axiosConfig)
        .then(response => {
          if (response.data.code === 'Success'){
            console.log('gasDiffusionList() success: response', response)
            context.commit('gasDiffusionList', response.data.responseData.resultList)
          }
          return response
        }).catch(catchError)
    },

    gasRiskProposalList(context, {queryString}) {
      return getQuery(context, 'gasRiskProposalList', API_URL.GAS_RISK_PROPOSAL_LIST + queryString);
    },

    humanErrorProposalList(context, {queryString}) {
      return getQuery(context, 'humanErrorProposalList', API_URL.HUMAN_ERROR_PROPOSAL_LIST + queryString);
    },

    humanErrorDayRate(context) {
      return getQuery(context, 'humanErrorDayRate', API_URL.HUMAN_ERROR_DAY_RATE);
    },

    humanErrorDay7Rate(context) {
      return getQuery(context, 'humanErrorDay7Rate', API_URL.HUMAN_ERROR_DAY_7_RATE);
    },

    workJournalList(context, {queryString}) {
      return getQuery(context, 'workJournalList', API_URL.WORK_JOURNAL_LIST + queryString);
    },

    workJournal(context, {id}) {
      return getQuery(context, 'workJournal', API_URL.WORK_JOURNAL + '/' + id);
    },

    putWorkJournal(context, options) {
      return axios.put(API_URL.WORK_JOURNAL, options, context.rootGetters.axiosConfig)
        .then((response) => {
          console.log(response)
          return response;
        })
        .catch(catchError)
    },

    deleteWorkJournal(context, options) {
      let config = {
        headers: {
          'Authorization': context.rootGetters.accessToken
        },
        data: options
      }
      return axios.delete(API_URL.WORK_JOURNAL, config)
        .then((response) => {
          console.log('deleteWorkJournal success : ', response)
          return response;
        })
        .catch(catchError)
    },
  },

  modules: {

    // 공통코드 조회 모듈
    common: {
      namespaced: true,

      state: {
        riskNoticeHistoryList: {
          list: []
        }
      },

      mutations: {
        riskNoticeHistoryList(state, data) {
          state.riskNoticeHistoryList = data;
        }
      },

      actions: {
        riskNoticeHistoryList(context, {queryString}) {
          return axios.get(API_URL.RISK_NOTICE_HISTORY_LIST + queryString, context.rootGetters.axiosConfig)
            .then((response) => {
              console.log('riskNoticeHistoryList:', response.data)
              context.commit('riskNoticeHistoryList', response.data)
              return response;
            })
            .catch(catchError)
        }
      },


      modules: {

        code: {
          namespaced: true,

          state: {
            // 분석시스템코드
            ANYS_SYS_CD: null,
            // 부서코드
            DEPT_CD: null,
            // 설비종류
            EQMT_TY_CD: null,
            // 메뉴얼 종류
            MUAL_TYPE_CD: null,
            // 작업 유형
            WORK_TYPE_CD: null,
            // 단위 공정
            UNIT_PROCS_CD: null,
            //
            WORK_STAT_CD: null,
            // 공지 분류
            NOTI_TYPE_CD: null,
          },

          mutations: {
            ANYS_SYS_CD(state, data) {
              state.ANYS_SYS_CD = data;
            },
            DEPT_CD(state, data) {
              state.DEPT_CD = data;
            },
            EQMT_TY_CD(state, data) {
              state.EQMT_TY_CD = data;
            },
            MUAL_TYPE_CD(state, data) {
              state.MUAL_TYPE_CD = data;
            },
            WORK_TYPE_CD(state, data) {
              state.WORK_TYPE_CD = data;
            },
            UNIT_PROCS_CD(state, data) {
              state.UNIT_PROCS_CD = data;
            },
            WORK_STAT_CD(state, data) {
              state.WORK_STAT_CD = data;
            },
            NOTI_TYPE_CD(state, data) {
              state.NOTI_TYPE_CD = data;
            },
            process(state, data) {
              state.process = data;
            }
          },

          actions: {
            query(context, {code}) {
              return axios.get(API_URL.COMMON_CODE + '?cd_grp_id=' + code, context.rootGetters.axiosConfig)
                .then(response => {
                  console.log('common/code', code, response.data.list);
                  context.commit(code, response.data.list);
                  return response;
                })
                .catch(catchError)
            }
          }
        },

        auth: {
          namespaced: true,

          state: {
            authList: []
          },

          mutations: {
            authList(state, data) {
              state.authList = data;
            }
          },

          actions: {
            authList(context) {
              return axios.get(API_URL.COMMON_AUTH, context.rootGetters.axiosConfig)
                .then((response) => {
                  console.log('common/authList', response.data.list);
                  context.commit('authList', response.data.list);
                })
                .catch(catchError)
            }
          }
        },

        message: {
          namespaced: true,

          state: {},

          mutations: {},

          actions: {
            sendMessage(context, options) {
              return axios.post(API_URL.SEND_MESSAGE, options, context.rootGetters.axiosConfig)
                .then((response) => {
                  console.log('sendMessage.success:', response)
                  return response;
                })
                .catch(catchError)
            }
          }
        },

        process: {
          namespaced: true,

          state: {
            // 생산공정
            process: [],
          },

          mutations: {
            process(state, data) {
              state.process = data;
            },
          },

          actions: {
            query(context, {queryString}) {
              let url = (queryString) ? API_URL.PROCESS + queryString : API_URL.PROCESS
              return axios.get(url, context.rootGetters.axiosConfig)
                .then(response => {
                  console.log('common/process', queryString, response);
                  context.commit('process', response.data.list);
                })
                .catch(catchError)
            }
          }
        },

        equipment: {
          namespaced: true,

          state: {
            equipment: [],
          },

          mutations: {
            equipment(state, data) {
              state.equipment = data;
            }
          },

          actions: {
            query(context, {queryString}) {
              let url = (queryString) ? API_URL.EQUIPMENT + queryString : API_URL.EQUIPMENT
              return axios.get(url, context.rootGetters.axiosConfig)
                .then(response => {
                  console.log('common/equipment', queryString, response);
                  context.commit('equipment', response.data.list);
                })
                .catch(catchError)
            },
            clear({commit}, {stateName}) {
              commit(stateName, [])
            }
          }
        },

        manual: {
          namespaced: true,

          state: {
            incidentManualList: {
              list: []
            },
            incidentManual: {
              prev: {},
              current: {},
              next: {}
            }
          },

          mutations: {
            incidentManualList(state, data) {
              state.incidentManualList = data;
            },
            incidentManual(state, data) {
              state.incidentManual = data;
            }
          },

          actions: {
            incidentManualList(context, {queryString}) {
              return axios.get(API_URL.PAGE_INCIDENT_MANUAL + queryString, context.rootGetters.axiosConfig)
                .then(response => {
                  console.log('common/incidentManualList:', queryString, response);
                  context.commit('incidentManualList', response.data);
                })
                .catch(catchError)
            },

            incidentManual(context, options) {
              let id = (options.mual_sid) ? '/' + options.mual_sid : '';
              return axios.get(API_URL.INCIDENT_MANUAL + id, context.rootGetters.axiosConfig)
                .then(response => {
                  console.log('common/incidentManual:', API_URL.INCIDENT_MANUAL + id, response);
                  context.commit('incidentManual', response.data);
                })
                .catch(catchError)
            },

            postIncidentManual(context, options) {
              return axios.post(API_URL.INCIDENT_MANUAL, options, context.rootGetters.axiosConfig)
                .then((response) => {
                  console.log(response)
                  return response;
                })
                .catch(catchError)
            },

            putIncidentManual(context, options) {
              return axios.put(API_URL.INCIDENT_MANUAL, options, context.rootGetters.axiosConfig)
                .then((response) => {
                  console.log(response)
                  return response;
                })
                .catch(catchError)
            },

            deleteIncidentManual(context, options) {
              let config = {
                headers: {
                  'Authorization': context.rootGetters.accessToken
                },
                data: options
              }
              return axios.delete(API_URL.INCIDENT_MANUAL, config)
                .then((response) => {
                  console.log(response)
                  return response;
                })
                .catch(catchError)
            },
          }
        },

        emergencyContact: {
          namespaced: true,

          state: {
            emergencyContactList: {
              list: []
            },
            emergencyContact: {}
          },

          mutations: {
            emergencyContactList(state, data) {
              state.emergencyContactList = data;
            },
            emergencyContact(state, data) {
              state.emergencyContact = data;
            }
          },

          actions: {
            emergencyContactList(context, {queryString}) {
              return axios.get(API_URL.PAGE_EMERGENCY_CONTACT + queryString, context.rootGetters.axiosConfig)
                .then(response => {
                  console.log('common/emergencyContactList:', queryString, response);
                  context.commit('emergencyContactList', response.data);
                })
                .catch(catchError)
            },

            postEmergencyContact(context, options) {
              return axios.post(API_URL.EMERGENCY_CONTACT, options, context.rootGetters.axiosConfig)
                .then((response) => {
                  console.log('postEmergencyContact', response)
                  return response;
                })
                .catch(catchError)
            },

            putEmergencyContact(context, options) {
              return axios.put(API_URL.EMERGENCY_CONTACT, options, context.rootGetters.axiosConfig)
                .then((response) => {
                  console.log('postEmergencyContact', response)
                  return response;
                })
                .catch(catchError)
            },

            deleteEmergencyContact(context, options) {
              let config = {
                headers: {
                  'Authorization': context.rootGetters.accessToken
                },
                data: options
              }
              return axios.delete(API_URL.EMERGENCY_CONTACT, config)
                .then((response) => {
                  console.log('deleteEmergencyContact success : ', response)
                  return response;
                })
                .catch(catchError)
            }
          }
        },

        notice: {
          namespaced: true,

          state: {
            noticeList: {
              list: []
            },
            noticeView: {
              prev: {},
              current: {},
              next: {}
            }
          },

          mutations: {
            noticeList(state, data) {
              state.noticeList = data;
            },

            noticeView(state, data) {
              state.noticeView = data;
            }
          },

          actions: {
            noticeList(context, {queryString}) {
              console.log(context, queryString, '<<<<<<')
              return axios.get(API_URL.NOTICE_LIST + queryString, context.rootGetters.axiosConfig)
                .then(response => {
                  console.log('common/notice/noticeList:', queryString, response);
                  context.commit('noticeList', response.data);
                })
                .catch(catchError)
            },

            noticeView(context, options) {
              let id = (options.noti_sid) ? '/' + options.noti_sid : '';
              return axios.get(API_URL.NOTICE + id, context.rootGetters.axiosConfig)
                .then(response => {
                  console.log('common/notice/noticeView:', API_URL.NOTICE + id, response);
                  context.commit('noticeView', response.data);
                })
                .catch(catchError)
            },

            postNoticeView(context, options) {
              return axios.post(API_URL.NOTICE, options, context.rootGetters.axiosConfig)
                .then((response) => {
                  console.log(response)
                  return response;
                })
                .catch(catchError)
            },

            putNoticeView(context, options) {
              return axios.put(API_URL.NOTICE, options, context.rootGetters.axiosConfig)
                .then((response) => {
                  console.log(response)
                  return response;
                })
                .catch(catchError)
            },

            deleteNoticeView(context, options) {
              let config = {
                headers: {
                  'Authorization': context.rootGetters.accessToken
                },
                data: options
              }
              return axios.delete(API_URL.NOTICE, config)
                .then((response) => {
                  console.log(response)
                  return response;
                })
                .catch(catchError)
            },
          }
        },

        /*user: {
          namespaced: true,

          actions: {
            putUser(context, options){
              return axios.put(API_URL.USER, options, context.rootGetters.axiosConfig)
                .then((response) => {
                  if (response.data.resultCode === 'NRL001'){
                    context.commit('putUserData', options, { root: true });
                  }
                })
            }
          }
        }*/
      }
    }
  }
})


function getQuery(context, stateName, url) {
  let promise = axios.get(url, context.getters.axiosConfig)
    .then((response) => {
      console.log(url, response, '\n axiosConfig', context.getters.axiosConfig)
      context.commit(stateName, response.data);
      return response;
    })
    .catch(catchError);

  return promise.then(response => response)
}


function postQuery(context, stateName, url, data) {
  let promise = axios.post(url, data, context.getters.axiosConfig)
    .then((response) => {
      console.log('postQuery then() -------------- \n', url, response)
      context.commit(stateName, response.data);
      return response;
    })
    .catch(catchError)

  return promise.then(response => response)
}


function getQueryString(options) {
  if (!options) {
    return '';
  }

  let list = [];
  if (options.facility) {
    list.push('facty_id=' + options.facility)
  }
  if (options.process) {
    list.push('procs_id=' + options.process)
  }
  if (options.equipment) {
    list.push('eqmt_id=' + options.equipment)
  }

  let str = '';
  list.forEach((item, index) => {
    if (index === 0) {
      str += '?'
    } else {
      str += '&'
    }
    str += item;
  })

  return str
}

function catchError(error) {
  let errorCode = error.response.data.resultCode;
  console.log('Vuex error catch: ', errorCode, error, error.response);

  switch (errorCode) {
    case 'ERR501':
      router.push('/login');
      break;
    case 'ERR901':
      alert('ERR901')
      break;
    default:
      alert('서버 조회중 에러가 발생했습니다. \n에러코드는 다음과 같습니다.\n' + errorCode)
  }
  return error;
}