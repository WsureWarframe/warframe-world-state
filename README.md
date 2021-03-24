# warframe-world-state
## 欢迎回来，这里是Wsure的第三代Warframe插件，由于酷Q倒闭，现在已经转战Mirai
### 兼容Mirai-Console 
    最新:
    Mirai-Console 2.4.0+ -> 0.0.4
    历史:
    Mirai-Console 2.0.0 ->  0.0.3 
    Mirai-Console 0.5.2 ->  0.0.2-alpha
                            0.0.1-alpha
### 兼容MiraiAndroid
    最新: 
    MiraiAndroid 3.1.0+ ->  0.0.4
    历史:
    MiraiAndroid 3.0.0 ->   0.0.3
    MiraiAndroid 2.10.4 ->  0.0.2-alpha
                            0.0.1-alpha
### 项目依赖[【warframe-info-api】](https://github.com/WsureDev/warframe-info-api) 提供给接口服务
# warframe-world-state （Warframe世界状态查询插件）
- 群：[435021808](https://jq.qq.com/?_wv=1027&k=rGrjxfv0) 
# 特色
- 支持warframe世界状态查询、包括赏金任务  
# 基本使用方法
0. 从 [Release](https://github.com/WsureDev/warframe-world-state/releases) 下载 插件(兼容性查看上方)
1. 从 [MiraiAndroid](https://github.com/mzdluo123/MiraiAndroid/releases) 下载 `MiraiAndroid`(兼容性查看上方)
2. 在安卓手机上安装MiraiAndroid
3.  - 点击左上角 
    - 插件管理 
    - 选择右上角添加本插件 
    - 点击导入（保持默认）
    - 输入warframe.jar
    - 点击左上角 选择快速重启
4. 在界面右上角添加qq号和密码
# 默认指令列表  
    warframe.market: 
        wm 关键词
        
    riven.market: 
        rm 关键词
        
    灰机wiki: 
        wiki 关键词
        
    warframe世界状态
        新闻
        事件
        警报
        突击
        地球赏金
        金星赏金
        火卫二赏金
        裂缝
        促销商品
        入侵
        奸商
        特价
        小小黑
        地球
        地球平原
        火卫二平原
        金星平原
        电波
        仲裁
        舰队
# 开发记录：MiraiAndroid兼容踩坑
0. 插件特点: 这是一个全平台Mirai兼容的插件，集成了h2作为db，如果你也在寻找兼容MiraiAndroid的数据库集成方案，我向你推荐h2+Exposed
1. 失败经验：mybatisPlus、mybatis、ktorm、sqlite jdbc在MiraiAndroid上无法运行(扫包路径问题、MiraiAndroid内二次打包文件过滤问题、安卓so文件加载问题、Android.jar与rt.jar区别)。
2. 成功经验：数据库方案选型：h2 jdbc+Exposed 或 原生jdbc
3. exposed：写操作使用entity（DAO），读操作请用table（DSL），使用Entity读会导致出现对象已关闭的错误
4. 数据库已经从本项目`master`分支移除，需要查看用例请看`Exposed-archived`分支
