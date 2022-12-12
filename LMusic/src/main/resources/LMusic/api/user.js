//分页查询
const getUserList = (params) => {
  return $axios({
    url: '/user/page',
    method: 'get',
    params
  })
}

// 删除接口
const deleteUser = (ids) => {
  return $axios({
    url: '/user',
    method: 'delete',
    params: { ids }
  })
}

// 起用停用---批量起用停用接口
const enableOrDisableUser = (params) => {
  return $axios({
    url: `/user/status/${params.status}`,
    method: 'post',
    params: { ids: params.id }
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

// 修改页面数据回显接口
const queryUserById = (id) => {
  return $axios({
    url: `/user/${id}`,
    method: 'get'
  })
}
