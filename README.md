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

#### 添加@LoadBalanced

实现应用调用的负载均衡



#### actuator微服务信息完善

1. **主机名称**修改

```yml
eureka:
  instance:
    instance-id: payment8001
```

这样在eureka注册中心就可以显示 主机名，ip，端口号

2. 访问信息有IP

```yml
eureka:
  instance:
    prefer-ip-address: true  #访问路径可以显示IP
```

如下图所示:

![image-20220923160831297](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220923160831297.png)


#### 服务发现Discovery
1. 注册在eureka里面的微服务，可以通过服务发现来获得该服务的信息.
```java
@Resource
private DiscoveryClient discoveryClient;

@GetMapping("/payment/discovery")
public Object discovery() {
        List<String> services = discoveryClient.getServices();

        services.forEach(x -> {
            log.info("******element****" + x);
        });

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + instance.getUri());
        }
        return this.discoveryClient;
    }
```
2. 服务发现Discovery注册

@EnableDiscoveryClient

![image-20220924133919706](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220924133919706.png)



禁用自我保护

```yaml
server:
  enable-self-preservation: false    #禁用自我保护
```



### eureka 停止更新，配置Zookeeper







