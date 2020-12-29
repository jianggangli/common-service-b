#!/bin/bash

remote_debug_port="49001"
app_name="$(basename $(pwd))"
app_log_home="./logs/$(basename $(pwd))"

if [ ! -d "${app_log_home}" ]; then
    mkdir -p "${app_log_home}"
fi

#remote debug
jvm_param1="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=$remote_debug_port,suspend=n"
#memory
jvm_param2=" -Xms512m -Xmx2048m "
JAVA_OPTS="$jvm_param1 $jvm_param2"
root_dir=$0/..
echo "服务根路径目录:[$root_dir]"
echo "服务名称:[$app_name]"
echo "启动日志目录:[$app_log_home]"
echo "服务启动JVM参数:[$JAVA_OPTS]"
echo "开始编译工程:[clean build -x test]"
./gradlew clean build -x test
echo "结束编译工程:[clean build]"
echo "开始运行工程jar:[java $JAVA_OPTS  -jar build/libs/$app_name-*.jar]"
java $JAVA_OPTS -jar -Dserver.port=9001 build/libs/"$app_name"-*.jar 
echo "结束运行工程jar:[clean build]"

echo "Server End"