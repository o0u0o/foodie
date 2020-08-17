# foodie 单体应用

## 应用简介
该项目为美食电商项目


### Restful Web Service

1、通信方式

2、信息传递

3、无状态

4、独立性

#### Rest 设计规范
- GET

    用户代表获取资源信息（查询操作）
    
    如:-> `/order/{id}`  ->  `/getOrder?id=1001`

- POST

    保存信息、更新资源
    
    如:->` /order` -> `/saveOrder`
    
- PUT

    更新资源
    
    如:-> `/order/{id}` -> `modifyOrder`
    
- DELETE

    删除操作
    
    如:-> `/order/{id}` -> `/deleteOrder?id=1001`
    
备注: 左边的URL风格为强Restful 规范 右边为 弱Restful 规范
    
注意:Restful 风格接口尽量少用动词