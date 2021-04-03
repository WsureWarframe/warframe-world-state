# warframe-world-state
## 欢迎回来，这里是Wsure的第三代Warframe插件，由于酷Q倒闭，现在已经转战Mirai
### 兼容Mirai-Console (Windows/MacOS/Linux)
    最新:
    Mirai-Console 2.4.0+ -> 0.0.4
    历史:
    Mirai-Console 2.0.0 ->  0.0.3 
    Mirai-Console 0.5.2 ->  0.0.2-alpha
                            0.0.1-alpha
### 兼容MiraiAndroid (Android)
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
# MiraiAndroid安装方法 (Android)
0. 从 [MiraiAndroid](https://github.com/mzdluo123/MiraiAndroid/releases) 下载 `MiraiAndroid` 最新版 (兼容性查看上方)
1. 在安卓手机上安装MiraiAndroid
2. 选择1:`jar插件` (如无法正常安装请尝试`选择2`)
    - 从 [warframe-world-stat/release](https://github.com/WsureDev/warframe-world-state/releases) 下载 最新版 插件(兼容性查看上方)
    - 点击左上角 
    - 插件管理
    - 如果以前安装过本插件，无论apk还是jar，都先删除
    - 选择右上角添加本插件 
    - 点击导入（保持默认）
    - 输入warframe.jar
    - 等待编译完成
3. 选择2:`apk插件`
    - 从[warframe-world-state-apk/release](https://github.com/WsureDev/warframe-world-state-apk/releases) 下载 最新版 插件(兼容性查看上方)
    - 点击左上角
    - 插件管理
    - 如果以前安装过本插件，无论apk还是jar，都先删除    
    - 安装下载的apk插件
4. 在界面右上角添加快速登录的qq号和密码
5. 点击左上角 选择快速重启
# Mirai-Console安装方法 (Windows/MacOS/Linux)
0. 准备工作: 下载[MCL一键版](https://github.com/iTXTech/mcl-installer/releases) 和  [MiraiAndroid](https://github.com/mzdluo123/MiraiAndroid/releases) ,如果缺少`DLL`,请下载`vc++2015` 
1. 新建一个空文件夹把mcl安装器放进去运行 ，windows直接双击就能跑，linux和macOS自己给可执行文件赋权。跑起来之后一路回车，直到最后退出。
2. mcl一键程序最后一步是给你下载mcl脚本包，如果这一步因为网络原因失败了，请重复执行。或者自己手动下载 [mcl脚本包](https://github.com/iTXTech/mirai-console-loader/releases) ，解压到这个位置，然后自己改mcl脚本里的java目录
3. 运行mcl脚本，运行完成后退出，确保一定要退出关掉
4. 修改`config\Console\AutoLogin`文件中的qq 123456和密码 pwd，保存
5. 再运行mcl脚本，直到最后跑完，中间报错无视，运行完成后退出，确保一定要退出关掉。看到目录下生成了`bots`文件夹，里面有你的qq号文件夹，进去之后有个deviceInfo文件
6. 现在开始你有3个选择让这份device文件绑定此qq：
   1. 在手机上的MiraiAndroid登录QQ后导出device.json分享到pc，替换此deviceInfo.json
   2. 将pc的deviceInfo.json传给手机上的MiraiAndroid导入后登录，完成device.json和qq的绑定（由于MiraiAndroid的导入device.json在调用系统文件选择器时候可能会报错，建议手动移动文件到/sdcard/Android/data/io.github.mzdluo123.mirai.android/files/device.json）
   3. 使用[`mirai-login-solver-selenium`](https://github.com/project-mirai/mirai-login-solver-selenium) 插件,使用方法自己看 (不过我并不推荐，你可能需要安装浏览器，并且设置chrome为默认浏览器)
7. 绑定了device.json和qq之后，你就可以把插件放进plugin目录，运行mcl开始使用了
# 默认指令列表  
    warframe.market: 
        wm 关键词
        
    riven.market: 
        rm 关键词
        
    灰机wiki: 
        wiki 关键词

    游戏词库中英文翻译: 
        tran 关键词

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
    
    游戏状态通知任务: master和群管理有task的编辑权限
      查看任务列表: task list
      启用通知任务: task add <任务名称>             #在控制台使用会对所有群开启此任务，警报、仲裁、入侵等比较频繁更新的通知请慎用
      停用通知任务: task del <任务名称>             #在控制台使用会对所有群停用此任务

    master设置: 第一个master请在控制台设置，此菜单权限仅限master
      查看master列表: master list
      添加master: master add <qq或者at>
      删除master: master del <qq或者at>
# 开发记录：MiraiAndroid兼容踩坑
0. 插件特点: 这是一个全平台Mirai兼容的插件，集成了h2作为db，如果你也在寻找兼容MiraiAndroid的数据库集成方案，我向你推荐h2+Exposed
1. 失败经验：mybatisPlus、mybatis、ktorm、sqlite jdbc在MiraiAndroid上无法运行(扫包路径问题、MiraiAndroid内二次打包文件过滤问题、安卓so文件加载问题、Android.jar与rt.jar区别)。
2. 成功经验：数据库方案选型：h2 jdbc+Exposed 或 原生jdbc
3. exposed：写操作使用entity（DAO），读操作请用table（DSL），使用Entity读会导致出现对象已关闭的错误
4. 数据库已经从本项目`master`分支移除，需要查看用例请看`Exposed-archived`分支
