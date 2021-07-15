const Mock = require('mockjs')

const data = Mock.mock({
  'items|10': [{
    id: '@id',
    name: 'word(8)',
    path: '@url("http")',
    uploadTime: '@datetime',
  }]
})

module.exports = [
  {
    url: '/images/list',
    type: 'get',
    response: config => {
      return {
        code: 20000,
        data: data.items
      }
    }
  },
  {
    url: '/images/hasTask',
    type: 'get',
    response: config => {
      return {
        code: 20000,
        data: 0
      }
    }
  }
]
