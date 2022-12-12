//分页查询
const getEmployeeList = (params) => {
  return $axios({
    url: '/employee/page',
    method: 'get',
    params
  })
}

// 删除接口
const deleteEmployee = (ids) => {
  return $axios({
    url: '/employee',
    method: 'delete',
    params: { ids }
  })
}

// 起用停用---批量起用停用接口
const enableOrDisableEmployee = (params) => {
  return $axios({
    url: `/employee/status/${params.status}`,
    method: 'post',
    params: { ids: params.id }
  })
}

// 新增---添加员工
const addEmployee = (params) => {
  return $axios({
    url: '/employee',
    method: 'post',
    data: { ...params }
  })
}

// 修改---添加员工
const editEmployee = (params) => {
  return $axios({
    url: '/employee',
    method: 'put',
    data: { ...params }
  })
}

// 修改页面数据回显接口
const queryEmployeeById = (id) => {
  return $axios({
    url: `/employee/${id}`,
    method: 'get'
  })
}