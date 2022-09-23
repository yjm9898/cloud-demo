## 一步一步搭建SpringCloud Alibaba步骤

### 新建父工程

- 建模块
- 改POM
- 改YML
- 主启动
- 改业务类

![image-20220923111742460](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220923111742460.png)



### 添加eureka模块

eureka集群环境，二者相互注册，相互守望。

```yaml
eureka:
  instance:
    hostname: eureka7001.com
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://eureka7002.com:7002/eureka/
```

### 添加服务提供者模块  cloud-provider-payment

![image-20220923150730930](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220923150730930.png)



```yaml
spring:
  application:
    name: cloud-consumer-order80

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka,
  instance:
    prefer-ip-address: true
```

### 添加服务消费者模块 cloud-consumer-order

```yaml
spring:
  application:
    name: cloud-consumer-order80
    
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka,
  instance:
    prefer-ip-address: true
```

服务调用端，使用RestTemplate 组件。

添加@LoadBalanced
