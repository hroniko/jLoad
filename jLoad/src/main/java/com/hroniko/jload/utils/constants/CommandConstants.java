package com.hroniko.jload.utils.constants;

public interface CommandConstants {
    String GET_SERVER_TIME = "date";
    String GET_SERVER_DATETIME ="date +'%Y-%m-%d %_H:%M:%S'";
    String GET_ALL_FILE_NAME = "ls /u02/netcracker/tbapi/tbapi-spring-boot";
    String GET_TEXT_FOR_FILE = "cat /u02/netcracker/tbapi/tbapi-spring-boot/";
    String GET_WORD_COUNT_IN_FILE = "wc -l /u02/netcracker/tbapi/tbapi-spring-boot/";
    String GET_TEXT_FOR_ACTIVE_FILE_WITH_POSITION = "sed -n pos1,pos2p /u02/netcracker/tbapi/tbapi-spring-boot/";
    String GET_HOSTNAME_BY_ID = "host ";

    /* Server Monitor's commands */
    String GET_CPU_USAGE_PERCENT_v1 = "top -bn1 | grep \"Cpu(s)\" | sed \"s/.*, \\([0-9,\\.]*\\)%* id.*/\\1/\" | awk '{print 100 - $1\"%\"}'"; // 16%
    String GET_CPU_USAGE_PERCENT_v2 = "top -bn1 | awk '/Cpu/ { cpu = 100 - $8 \"%\" }; END   { print cpu }'"; // 16%
    String GET_CPU_USAGE_PERCENT_v3 = "grep 'cpu ' /proc/stat | awk '{usage=($2+$4)*100/($2+$4+$5)} END {print usage}'"; // 14.874 without persent %
    String GET_CPU_USAGE_PERCENT_v4 = "grep 'cpu ' /proc/stat | awk '{usage=($2+$4)*100/($2+$4+$5)} END {print usage \"%\"}'"; // 14.874%

    String GET_MEMORY_TOTAL = "cat /proc/meminfo | grep MemTotal | awk '{print $2}'"; // return in kb
    String GET_MEMORY_FREE = "cat /proc/meminfo | grep MemFree | awk '{print $2}'"; // return in kb

    /* Who is debugger port commands */
    String GET_WHO_DEBUG_PORT_16955 ="lsof -i :16955 | grep java > /home/netcrk/1.txt";
    // String GET_WHO_DEBUG_PORT ="lsof -i :";
    // String GET_WHO_DEBUG_PORT ="/home/netcrk/lsofssh.sh";
    String GET_WHO_DEBUG_PORT ="/usr/sbin/lsof -i :16955 | grep java";

    /* upload jar commands */
    String GET_FIND_FILE_WITH_NAME = "find /u02/netcracker/toms/u39_i2_6955 -name "; // + добавить имя файла в кавычках
    String GET_INFO_ABOUT_FILE = "ls -l --time-style=\"+%Y-%m-%d %H:%M:%S\" "; // + добавить полный путь до файла
    // ls -l /путь_до_файла.jar
    // Вернет
    // -rw-r--r-- 1 user user 423341 2018-07-18 11:30:03 /путь_до_файла.jar
    // ls -l --time-style="+%Y-%m-%d" // Форматирование вывода времени
    String RENAME_FILE = "mv "; // + добавить имя старого файла + имя нового файла: mv file newfile
    String UPLOAD_FILE_FROM_LOCAL_TO_SERVER = "put "; // + добавить имя локального файла + имя серверного файла: put /media/hroniko/DATA/WORK/jLoad/README.md /u02/netcracker/toms/u39_i2_6955/logs/anbe/README.md
    String GET_LOCAL_MACHINE_NAME = "host $(echo $SSH_CONNECTION | cut -d ' ' -f 1) | cut -d ' ' -f 5"; // вернет адрес с точкой на конце, надо уудалять ее wsmvr-154.netcracker.com.
    String SET_MESSAGE_TO_SERVER_LOG_FILE = "echo \"*\" >> /u02/netcracker/toms/u39_i2_6955/logs/anbe/jload.log"; // заменить * на сообщение
    String GET_SERVER_SYSDATE = "echo $(date +%Y-%m-%d) $(date +%H:%M:%S)"; // Вернет в формате 2018-07-23 08:41:18
    String GET_SERVER_SYSDATE_ = "echo $(date +%Y-%m-%d)_$(date +%H-%M)"; // Вернет в формате 2018-07-23_08-41
}
