//分页查询
const getSongList = (params) => {
  return $axios({
    url: '/song/page',
    method: 'get',
    params
  })
}

// 删除接口
const deleteSong = (ids) => {
  return $axios({
    url: '/song',
    method: 'delete',
    params: { ids }
  })
}

// 新增歌曲
const addSong = (params) => {
  return $axios({
    url: '/song',
    method: 'post',
    data: { ...params }
  })
}

// 修改歌曲
const editSong = (params) => {
  return $axios({
    url: '/song',
    method: 'put',
    data: { ...params }
  })
}
// 修改页面数据回显接口
const querySongById = (id) => {
  return $axios({
    url: `/song/${id}`,
    method: 'get'
  })
}
