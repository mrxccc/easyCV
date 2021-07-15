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

export function view(imgName) {
  return request({
    url: '/images/view',
    method: 'get',
    params: {
      imageName: imgName
    }
  })
}
