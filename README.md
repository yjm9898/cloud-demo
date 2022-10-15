## 一步一步搭建SpringCloud Alibaba步骤

### 新建父工程
- 建模块
- 改POM
- 改YML
- 主启动
- 改业务类
  ![image-20220923111742460](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220923111742460.png)

### 添加eureka模块

eureka集群环境，二者相互注册，相互守望。 ^cda1bf
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
```xml
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
```xml
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
zookeeper是一个分布式协调工具，可以实现注册中心功能。 ^5ec4c0
![image-20220925094227530](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220925094227530.png)

### Consul 服务注册与发现
安装启动
```shell
.\consul agent -dev
```
http://localhost:8500/ui/dc1/services 查看当前consul 管理后台 ^794ff2
![image-20220925101547375](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220925101547375.png)

#### 服务提供者搭建 provider-payment8006
![image-20220926105109669](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220926105109669.png)

```yaml
server:
  # 8006表示注册到consul服务器的支付服务提供者端口号
  port: 8006


spring:
  application:
    # 服务别名---注册zookeeper到注册中心的名称
    name: cloud-provider-payment
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        hostname: 127.0.0.1
        service-name: ${spring.application.name}


```

```xml
<dependencies>
        <dependency>
            <groupId>com.example.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--SpringBoot整合Zookeeper客户端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
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
#### 服务消费者搭建 consumerconsul-order80
![image-20220926105140262](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220926105140262.png)
![image-20220926105148671](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220926105148671.png)
配置文件
```yaml
server:
  port: 80
spring:
  application:
    name: cloud-consumer-order
  cloud:
    consul:
      # consul注册中心地址
      host: localhost
      port: 8500
      discovery:
        hostname: 127.0.0.1
        service-name: ${spring.application.name}
```
POM文件
```xml
<dependencies>

        <dependency>
            <groupId>com.example.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--SpringBoot整合consul客户端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
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
运行效果
![image-20220926110256428](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220926110256428.png)

![image-20220926110339584](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220926110339584.png)

#### 三个注册中心对比
![image-20220926111307585](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220926111307585.png)

![image-20220926111329180](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220926111329180.png)

### Robbin负载均衡调用
^51c7ef
![image-20220926143154431](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220926143154431.png)

进程内LB: 将LB逻辑集成到消费方，消费方从注册中心获知哪些地址可用，然后自己再从这些地址选择一个合适的服务器。Ribbon属于进程内LB, 它只是一个类库。

![image-20220926163040419](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220926163040419.png)

Ribbon核心组件IRule实现类

![image-20221008170519103](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221008170519103.png)

负载均衡算法

![image-20220927161110705](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220927161110705.png)

![image-20220927161405248](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20220927161405248.png)





### OpenFeign服务接口调用

* OpenFeign是什么

  Feign是一个声明式的**Web服务客户端**，让编写Web服务客户端变得非常容易，只需创建一个接口并在接口上添加注解即可

* 能干嘛

  ![image-20221006103822951](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221006103822951.png)

* Feign和OpenFeign两者区别

  ![image-20221006103948983](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221006103948983.png)

Feign使用步骤： ^98463a

接口+注解

Feign永远是在消费端使用的

建module, 改POM, 改YML，改主启动类(@EnableFeignClients)

新建一个PaymentFeignService接口，**接口上添加注解**(@FeignClient(value="CLOUD-PAYMENT-SERVICE")),

![image-20221006105140457](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221006105140457.png)

![image-20221006105314037](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221006105314037.png)



Feign超时编写

在服务提供方故意编写一个耗时服务，服务调用者会出现Read timed out 报错.

OpenFeign默认等待一秒钟，超时报错.

![image-20221008163359619](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221008163359619.png)

修改Feign 超时配置，服务调用端修改超时时间，就能正常调用耗时服务接口

![image-20221008181312202](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221008181312202.png)

![image-20221008181211449](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221008181211449.png)

```yaml
server:
  port: 80
eureka:
  client:
    register-with-eureka: false
    fetch-registry: true
    service-url:
#      单机版
      defaultZone: http://localhost:7001/eureka
feign:
  client:
    config:
      default:
#        建立连接所用时间，适用于网络状况正常情况下，两端连接所需时间
        connect-timeout: 5000
#        指建立连接后从服务端读取到可用资源所用时间
        read-timeout: 5000

```

### Hystrix 熔断限流
是什么？能干嘛？操作步骤？  (理论，实操，小总结) ^64424b
#### 是什么？
Hystrix是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统里，许多依赖不可避免的会调用失败，比如超时、异常等，Hystix能够保证一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障，提高分布式系统的弹性。

* 服务降级  [fallback]
  如下情况将出现服务降级
  服务器忙，请稍后再忙，不让客户端等待并立即返回一个友好提示.
1. **程序运行异常**
2. **超时**
3. **服务熔断**触发服务降级
4. **线程池、信号量**也会导致服务降级

* 服务熔断  [break]   类比保险丝，达到最大服务访问后，直接拒绝访问,然后调用服务降级返回友好提示.

* 服务限流 [flow limit]  秒杀高并发等操作，严禁一窝蜂拥挤，大家排队，一秒钟N个，有序进行
  ![image-20221008214454802](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221008214454802.png)

##### 降级实操:

一. 服务提供端

在服务客户端consumer添加@HystrixCommand(fallback="paymentTimeOut",  commandProperties={})

```
@HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod", commandProperties = {  
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")  
})
public CommonResult<String> paymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {  
    return new CommonResult<>(200, "我是消费者80,对方支付系统繁忙请10秒种后再试或者自己运行出错请检查自己,o(╥﹏╥)o");  
}
```

服务端做熔断设置：



二. 服务调用端

80订单微服务，也可以按照8001支付微服务进行客户端降级保护。

实操步骤:

1. 建Module cloud-consumer-feign-hystrix-order80
2. 修改POM  添加spring-cloud-starter-netflix-hystrix 依赖
3. 修改YML   添加feign: hystrix: enable: true
4. 改主启动   主启动类上添加@EnableHystrix
5. 修改业务类 在controller层面，添加@HystrixCommand( fallbackMethod="XXX", commandProperties={ @HystrixProperty } )



三. 通过一二做服务降级处理之后，会出现**代码膨胀,业务逻辑混乱**，业务类上每个方法都需要对应的fallbakcMethod进行兜底处理.

解决方案：

1. 比较低级容易理解： 在订单微服务侧，controller添加**@DefaultProperties(defaultFallback="XXXX") **

2. 上述第一种方法避免了代码膨胀，但是会出现业务逻辑混乱问题。解决办法是：给Feign客户端定义的接口**添加一个服务降级处理的实现类**， 细节： Feign接口添加 fallback=xxx.class,  服务降级实现类添加@Component注解.

![image-20221015111734645](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221015111734645.png)

![image-20221015111802145](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221015111802145.png)

检验生效： 启动eureka 7001, 在启动payment8001, 最后启动order 80, 调用 /consumer/payment/hystrix/timeout/22； 停用payment8001，调用 /consumer/payment/hystrix/ok/22
![image-20221015120238592](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221015120238592.png)
![image-20221015120257786](https://ossjiemin.oss-cn-hangzhou.aliyuncs.com/img/image-20221015120257786.png)
上面两张图表明，服务fallback 能处理服务超时和服务宕机异常。

##### 熔断实操
简称断路器， 类比于家里保险丝

熔断机制是应对雪崩效应的一种微服务链路保护机制。当删除链路的某个为服务出错不可用或者响应时间太长时，会进行服务的降级，进而熔断该节点微服务调用，快速返回错误的响应信息。

**当检测到该微服务调用响应正常后，恢复调用链路**

