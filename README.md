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



### Zookeeper服务注册与发现



新建cloud-provider-payment8004模块

修改POM

```Java
 <dependencies>
        <dependency>
            <groupId>com.example.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--SpringBoot整合Zookeeper客户端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

    </dependencies>
```

修改配置文件YML

```yaml
server:
  # 8004表示注册到zookeeper服务器的支付服务提供者端口号
  port: 8004


spring:
  application:
    # 服务别名---注册zookeeper到注册中心的名称
    name: cloud-provider-payment
  cloud:
    zookeeper:
      # 默认localhost:2181
      connect-string: localhost:2181

```

修改主启动类

```java

@EnableDiscoveryClient //该注解用于向使用zookeeper. consul作为注册中心时注册服务
@SpringBootApplication
public class PaymentMain8004 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8004.class, args);
    }

}
```

启动服务提供者之后，用zkcli查看当前节点信息

![image-20220924175357728](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220924175357728.png)



zookeeper是一个分布式协调工具，可以实现注册中心功能。

![image-20220925094227530](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220925094227530.png)

### Consul 服务注册与发现







