# Myapp

## 自己的模块

### UI
* Activity : 继承 `BaseFragmentActivity`
* Fragment : 继承 `BaseFragment`
* Toast : `BaseFragmentActivity` 和 `BaseFragment` 继承接口 `IShowToast`，可以调用其 `showMsg()` 方法
* findViewById : `com.jakewharton:butterknife:8.2.1` - `ButterKnife`
* Image : `com.squareup.picasso:picasso:2.5.2` - `Picasso`
* List : `RecyclerView`、`CardView`
* SharePreference : `com.jlb.campus.stu.utils.SP`
* WebView : `WebViewActivity`，传入url和界面样式参数
* 屏幕适配 : 使用 `AbViewUtil` ，具体在 `BaseFragment` / `BaseFragmentActivity` 中已经封装
* ActionBar：使用 `BaseFragmentActivity.ActionBarBuilder` 创建并显示，详见代码注释
* 底部Popup：使用 `AppUtils.showBottomPopup()`
* Dialog: 继承 `BaseDialog`
* Handler：继承 `BaseHandler`

### 线程池
**不得使用** `new Thread()`，使用 `com.lwf.base.MyTask`
    
    MyTask.runInBackground(runnable, true);
    
### Json
使用类库：`com.google.code.gson:gson:2.7`

### Http

* 使用 `com.zhy:okhttputils:2.6.2` 类库，依赖

    'com.squareup.okio:okio:1.9.0'
    'com.squareup.okhttp3:okhttp:3.4.1'
* 使用 `HttpHelper` 封装请求
* 使用 `HttpJsonCallback` 接收回调, 回调对象需要传入参数 e.g.

        Map<String, String> params = new HashMap<>();
            params.put("phone", name);
            params.put("passwd", pwd);
            HttpHelper.post(context, ApiUrls.USER_LOGIN, params,
                new HttpJsonCallback<UserInfo>(new TypeToken<HttpResult<UserInfo>>() {}) {
                    @Override
                    public void onFailed(String msg, int id) {
                        if (callback != null)
                            callback.loginFailed(msg);
                    }

                    @Override
                    public void onSuccess(UserInfo userInfo, int id) {
                        if (userInfo != null) {
                            MyApplication.getInstance().setUser(userInfo);
                            notifyUserChanged();
                        }
                        if (callback != null)
                            callback.loginSuccessful(userInfo);
                    }
            });