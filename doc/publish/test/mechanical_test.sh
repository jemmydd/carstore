#!/usr/bin/env bash
# Created by Thor on 2017-06-22

# **************** START constant define **************** #

# today
TODAY=$(date +%Y%m%d)

# 运行环境
RUNNING_ENV=test

# 主路径
MAIN_PATH=/root/zSky

# 项目名称
PROJECT_NAME=mechanical

# auto script file name
SCRIPT_NAME=${PROJECT_NAME}_${RUNNING_ENV}.sh

# jar application name
APP_NAME=${PROJECT_NAME}.jar

# git branch
GIT_BRANCH=dev

# project source code dir
PROJECT_DIR=${MAIN_PATH}/project/${PROJECT_NAME}

# after package project, jar dir
PACKAGE_JAR_DIR=${PROJECT_DIR}/target

# after package project, the jar full path
PACKAGE_JAR_FULL_PATH=${PACKAGE_JAR_DIR}/${APP_NAME}

# run jar dir
RUN_JAR_DIR=${MAIN_PATH}/run

# run jar full path
RUN_JAR_FULL_PATH=${RUN_JAR_DIR}/${APP_NAME}

# running log file
RUNNING_LOG_FILE=${APP_NAME}_${TODAY}log.txt

# **************** END constant define **************** #

menu() {
    echo "******************************** welcome to use this script ********************************"
    echo ""
    echo "                  menu: ./${SCRIPT_NAME} [menu|restart|start|stop]"
    echo "                  default: restart"
    echo ""
    echo "******************************** welcome to use this script. *******************************"
    exit 0
}

restart() {
    echo "********************** restart **********************"

	package_project

	kill_thread

	start

	echo "restart ${APP_NAME} success!"
}

start() {
    echo "************************ start **********************"

    echo "!!!!!! RUNNING_ENV: ${RUNNING_ENV} !!!!!!"

    # 获取应用的进程号
	app_pid=`ps -ef|grep ${APP_NAME}|grep -v grep|grep -v kill|awk '{print $2}'`

	if [[ ${app_pid} ]]; then
	    echo "${APP_NAME} is running, need not to start!"
	else
	    cd ${RUN_JAR_DIR}

	    nohup java -jar ${APP_NAME} --spring.profiles.active=${RUNNING_ENV} >${RUNNING_LOG_FILE} 2>&1 &
	    tail -f ${RUNNING_LOG_FILE}

	    echo "start ${APP_NAME} success!"
	fi
}

stop() {
    echo "************************ stop ***********************"

    kill_thread
}

package_project() {
    echo "****************** package_project ******************"

    cd	${PROJECT_DIR}
	git checkout ${GIT_BRANCH} && git pull

	mvn clean package -Dmaven.test.skip=true

	rm -rf ${RUN_JAR_FULL_PATH}

	cp ${PACKAGE_JAR_FULL_PATH} ${RUN_JAR_DIR}

	echo "package ${APP_NAME} success!"
}

kill_thread() {
    echo "******************** kill_thread ********************"

    # 获取应用的进程号
	app_pid=`ps -ef|grep ${APP_NAME}|grep -v grep|grep -v kill|awk '{print $2}'`

	echo "?????? thread pid: ${app_pid}"

	if [[ ${app_pid} ]]; then
	    # 强杀进程
		kill -9 ${app_pid}

		echo "kill app thread success!"
	else
	    echo "the app is not running, need not to kill app thread!"
	fi
}

case "$1" in
    "menu")
        menu
        ;;
    "restart")
        restart
        ;;
    "start")
        start
        ;;
    "stop")
        stop
        ;;
    "")
        restart
        ;;
    *)
        menu
        ;;
esac


