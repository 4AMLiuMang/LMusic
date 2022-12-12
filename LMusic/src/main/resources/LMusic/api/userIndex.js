// 分页查询歌曲数据
const getSongList = (params) => {
  return $axios({
    url: '/song/page',
    method: 'get',
    params
  })
}

// 查询用户数据
const queryUserById = (id) => {
  return $axios({
    url: `/user/${id}`,
    method: 'get'
  })
}

// 修改用户
const editUser = (params) => {
  return $axios({
    url: '/user',
    method: 'put',
    data: { ...params }
  })
}

//登出
const logoutApi = () => {
  return $axios({
    'url': '/user/logout',
    'method': 'post',
  })
}