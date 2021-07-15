import request from '@/utils/request'

export function add(id) {
  return request({
    url: '/record/add',
    method: 'put',
    params: {
      imageId: id
    }
  })
}

export function stop(id) {
  return request({
    url: '/record/stop',
    method: 'post',
    params: {
      taskId: id
    }
  })
}

export function list(data) {
  return request({
    url: '/record/list',
    method: 'get'
  })
}

export function start(id) {
  return request({
    url: '/record/start',
    method: 'post',
    params: {
      taskId: id
    }
  })
}

export function hasTask(id) {
  return request({
    url: '/record/hasTask',
    method: 'get',
    params: {
      taskId: id
    }
  })
}

export function deleteTask(id) {
  return request({
    url: '/record/delete',
    method: 'post',
    params: {
      taskId: id
    }
  })
}
