# Dcokfile以from关键字开始，from后面接的是基础镜像Base Image
# FROM <image>:<tag> 指定基础docker镜像， :<tag>表示版本，不写表示最新
FROM java:8
# VOLUME 可以是数组VOLUME ["/data"]，或者路径字符串。VOLUME（指定挂载点）
VOLUME ["/data"]
# 定义参数JAR_FILE，JAR_FILE来自pom.xml的dockerfile-maven-plugin的buildArgs
ARG JAR_FILE
ADD ${JAR_FILE} demo.jar
# 执行项目 demo.jar。为了缩短 Tomcat 启动时间，添加一个系统属性指向 “/dev/urandom” 作为 Entropy Source
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/demo.jar"]