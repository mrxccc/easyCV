const Mock = require('mockjs')

const img = Mock.mock({
  id: '@id',
  name: 'word(8)',
  path: '@url("http")',
  uploadTime: '@datetime',
  taskNum: '@natural(0,1)'
})

const imgRecord = Mock.mock({
  id: '@id',
  'imageId|1-10': 1,
  'status|1-2': 1,
  playUrl: '@url("http")',
  createTime: '@datetime',
  updateTime: '@datetime',
})

const listData = Mock.mock({
  'items|10': [{
    imgRecordTask: imgRecord,
    img: img
  }]
})

const startData = Mock.mock({
  code: '@natural(19999,20001)',
  message: "test"
})

module.exports = [
  {
    url: '/record/list',
    type: 'get',
    response: config => {
      return {
        code: 20000,
        data: listData.items
      }
    }
  },
  {
    url: '/record/start',
    type: 'post',
    response: config => {
      return startData
    }
  },
]
