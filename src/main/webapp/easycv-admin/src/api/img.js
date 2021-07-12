import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/images/list',
    method: 'get',
    data
  })
}

export function deleteImage(data) {
  return request({
    url: '/images/delete',
    method: 'post',
    data
  })
}
