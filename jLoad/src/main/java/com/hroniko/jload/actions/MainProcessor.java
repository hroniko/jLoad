package com.hroniko.jload.actions;


import com.hroniko.jload.entities.ActiveHost;
import com.hroniko.jload.utils.converters.ResultLineConverter;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.hroniko.jload.utils.constants.CommandConstants.*;
import static com.hroniko.jload.utils.constants.CommandConstants.GET_WHO_DEBUG_PORT;
import static com.hroniko.jload.utils.constants.ConnectionConstants.*;
import static com.hroniko.jload.utils.constants.ConnectionConstants.HOSTNAME_LIST;


public class MainProcessor {

    private static final Logger LOGGER = Logger.getLogger(MainProcessor.class);

    private static ActiveHost currentHost = null;


    private Boolean flagReadFiles = false;

    private Boolean flagWork = false; // Флаг, указывающий, что текущий шаг не выполнен

//    public void runForAllHostname(){
//        if (flagWork) return; // Если предыдущее задание не выполнено, новое не запускаем
//        flagWork = true;
//
//        String[] hostnames = HOSTNAME_LIST.split(",");
//        List<String> hostnameList = Arrays.asList(hostnames);
//        for (String hostname : hostnameList) {
//            if (hostname != null){
//                run(hostname.trim());
//            }
//
//        }
//        flagWork = false;
//        flagReadFiles = true; // чтобы повторно не читать
////
////        Arrays.stream(HOSTNAME_LIST.split(","))
////                .filter(x -> (x != null))
////                .map(String::trim)
////                .map(host -> run(host))
////                .collect(Collectors.toList());
//    }

    public String run(String hostname, List<File> files) {

        // 0 Добавляем новый сейвер в репозиторий, если его там нет


        // 1 Определяем разницу во времени между удаленным сервером TBAPI и локальным компьютером
        //String createTxt = ShellExecutor.shell(hostname, GET_WHO_DEBUG_PORT_16955);

        String result = ShellExecutor.shell(hostname, GET_FIND_FILE_WITH_NAME + files.get(0).getName());
//        if (result.contains("ESTABLISHED")){
//            ActiveHost activeHost = ResultLineConverter.convert(result);
//            if (currentHost == null){
//                currentHost = activeHost;
//                LOGGER.info(currentHost.toStringInit());
//            } else {
//                if (currentHost.getName().equals(activeHost.getName())){
//                    currentHost.setDateEnd(activeHost.getDateEnd());
//                } else {
//                    // сначала сбросить в файл current, а потом повесить на его место новый
//                    LOGGER.info(currentHost.toString());
//                    currentHost = activeHost;
//                }
//            }
//            result = currentHost.toString();
//        } else {
//            // сначала сбросить в файл current,
//            if (currentHost != null){
//                LOGGER.info(currentHost.toString());
//                currentHost = null;
//            }
//
//        }


        return  result;
    }


}
