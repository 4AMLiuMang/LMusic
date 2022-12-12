function employeeLoginApi(data) {
  return $axios({
    'url': '/employee/login',
    'method': 'post',
    data
  })
}

function userLoginApi(data) {
  return $axios({
    'url': '/user/login',
    'method': 'post',
    data
  })
}

function logoutApi(){
  return $axios({
    'url': '/employee/logout',
    'method': 'post',
  })
}

function userRegisterApi(data) {
  return $axios({
    'url': '/user/register',
    'method': 'post',
    data
  })
}