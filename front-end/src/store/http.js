import axios from 'axios'
import router from '@/router/index'

axios.defaults.timeout = 100000; // 请求超时时间  5秒钟
//axios.defaults.baseURL = '';

// http request 拦截器
axios.interceptors.request.use(
  config => {
    let token=localStorage.getItem('value');
    let name=localStorage.getItem('name');
    if (token) {  // 判断是否存在token，如果存在的话，则每个http header都加上token
      setCookie(name,token);
      // config.headers._auth  = `${name}=${token}`;
      config.headers._auth  = `${token}`;
    }
    return config;
  },
  err => {
    return Promise.reject(err);
  });

// http response 拦截器
axios.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 401 清除token信息并跳转到登录页面
          //store.commit('loginOut');
          localStorage.removeItem('value');//清除缓存
          router.replace({
            path: '/login',
            query: {redirect: router.currentRoute.fullPath}
          });
      }
    }
    // console.log(JSON.stringify(error));//console : Error: Request failed with status code 402
    return Promise.reject(error.response.data)  // 返回接口返回的错误信息
  });


function setCookie(name,value)
{
  var Days = 30;
  var exp = new Date();
  exp.setTime(exp.getTime() + Days*24*60*60*1000);
  document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
export default axios;
