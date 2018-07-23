package com.hroniko.jload.actions;


import com.hroniko.jload.entities.FileInfo;
import com.hroniko.jload.entities.FileRoute;
import com.hroniko.jload.utils.converters.DateConverter;
import com.hroniko.jload.utils.converters.LsInfoAboutFileConverter;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.hroniko.jload.utils.constants.CommandConstants.*;


public class MainProcessor {

    private static final Logger LOGGER = Logger.getLogger(MainProcessor.class);

    public String run(String hostname, List<File> files) {
        // 1 Определяем разницу во времени между удаленным сервером TBAPI и локальным компьютером
        //String createTxt = ShellExecutor.shell(hostname, GET_WHO_DEBUG_PORT_16955);
        FileRoute fileRoutes = new FileRoute();

        // 1. Построение маршрутов замены файлов с информацией о файлах
        List<FileRoute> fileRouteList = files.stream()
                .map(FileInfo::new)
                .map(FileRoute::new)
                .map(fileRoute -> {
                    String localFileName = fileRoute.getLocalFile().getName();
                    String localFileExt = fileRoute.getLocalFile().getExtension();
                    String correctLocalFileName = localFileName.replaceAll("(-)*([0-9]+\\.)*[0-9](-)*(SNAPSHOT)*", ""); // удаляем информацию о версии
                    String result = ShellExecutor.shell(hostname, GET_FIND_FILE_WITH_NAME + correctLocalFileName + "*." + localFileExt); // String result = ShellExecutor.shell(hostname, GET_FIND_FILE_WITH_NAME + localFileName + "*." + localFileExt);
                    if (result.length() == 0){
                        return fileRoute;
                    }
                    List<String> serverFiles = Arrays.asList(result.split("\n"));
                    for (String fullPath : serverFiles){
                        //fileRoute.getServerFiles().add(new FileInfo(fileName));
                        String infoAboutFile = ShellExecutor.shell(hostname, GET_INFO_ABOUT_FILE + fullPath);
                        FileInfo serverFileInfo = LsInfoAboutFileConverter.convert(infoAboutFile);
                        fileRoute.getServerFiles().add(serverFileInfo);
                    }
                    return fileRoute;
                }).collect(Collectors.toList());

        // 2. Вывод информации о маршрутах
        System.out.println("Files will be replace from local to server:");
        fileRouteList.forEach(fileRoute -> {
            String localFileFullName = fileRoute.getLocalFile().getFullName();
            if (fileRoute.getServerFiles() != null && fileRoute.getServerFiles().size() != 0){ // if (fileRoute.getServerFiles() != null && fileRoute.getServerFiles().size() != 0){
                // System.out.print(localFileFullName + " --> ");
                // String.join("", Collections.nCopies(n, " ")); // Генерировать строку из п штук пробелов
                for (FileInfo serverFile : fileRoute.getServerFiles()){
                    System.out.println(localFileFullName + " "
                            + fileRoute.getLocalFile().info()
                            + " --> "
                            + serverFile.info() + " "
                            + "[" + hostname + "] " + serverFile.getFullPath());
                }

            } else {
                System.out.println(localFileFullName + " not found on server [" + hostname + "]");
            }


        });

        // 3. Спрашиваем о продолжении
        System.out.println("Do you want to continue? (Y/n)");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        if (!(answer == null || answer.length() == 0 || answer.toLowerCase().equals("y") || answer.toLowerCase().equals("yes") )){
            System.out.println("Process was stopped by user");
            return "ok";
        }
        // иначе продолжаем

        // 4. Получаем имя локальной машины и логируем в файл на сервере
        String localHostname = ShellExecutor.shell(hostname, GET_LOCAL_MACHINE_NAME);
        if (localHostname != null && localHostname.length() > 0) {
            localHostname = localHostname.substring(0, localHostname.length()-2);
        } else {
            localHostname = "unknown.host";
        }
        final String locHost = localHostname;

        // 5. Переименовываем файл (делаем бекап) и копируем локальный на сервер
        System.out.println("Loading files to server:");
        fileRouteList.stream()
                .filter(fileRoute -> fileRoute.getServerFiles() != null && fileRoute.getServerFiles().size() > 0) // оставляем только те, которые нашли на сервере
                .peek(fileRoute -> {
                    for(FileInfo serverFileInfo : fileRoute.getServerFiles()){
                        // Получаем информацию о времени хоста
                        String serverSysdate = ShellExecutor.shell(hostname, GET_SERVER_SYSDATE_);

                        // бекапим
                        String backup = serverFileInfo.getFullPath() + ".backup_" + serverSysdate; // + серверное время // DateConverter.sysdate(); // но лучше брать сисдейт сервера
                        String result = ShellExecutor.shell(hostname,
                                RENAME_FILE + serverFileInfo.getFullPath() + " "
                                        + backup);
                        // Загружаем
                        Sftp.shell(hostname,
                                UPLOAD_FILE_FROM_LOCAL_TO_SERVER + fileRoute.getLocalFile().getFullPath() + " "
                                        + serverFileInfo.getFullPath());

                        // Обновляем информацию о файле на сервере
                        String infoAboutFile = ShellExecutor.shell(hostname, GET_INFO_ABOUT_FILE + serverFileInfo.getFullPath());
                        serverFileInfo = LsInfoAboutFileConverter.convert(infoAboutFile);
                        //fileRoute.getServerFiles().add(serverFileInfo);

                        // Информируем пользователя
                        System.out.println(fileRoute.getLocalFile().getFullName() + " "
                                + fileRoute.getLocalFile().info()
                                + " OK, backup "
                                + serverFileInfo.info() + " "
                                + "[" + hostname + "] " + backup);

                        // Логируем в локальный журнал
                        String logMessage = DateConverter.sysdate() + " upload: "
                                + fileRoute.getLocalFile().getFullPath() + " "
                                + fileRoute.getLocalFile().info()
                                + " --> "
                                + serverFileInfo.info() + " "
                                + "[" + hostname + "] " + serverFileInfo.getFullPath() + "; backup to "
                                + backup + "\n";
                        LOGGER.info(logMessage);

                        // Логируем в лог-файл на сервере
                        String serverLogMessage = serverSysdate.replace("\n", "").replace("_", " ") + " " + locHost + " upload: "
                                + fileRoute.getLocalFile().getFullPath() + " "
                                + fileRoute.getLocalFile().info()
                                + " --> "
                                + serverFileInfo.info() + " "
                                + "[" + hostname + "] " + serverFileInfo.getFullPath() + "; backup to "
                                + backup;
                        String reslog = ShellExecutor.shell(hostname, SET_MESSAGE_TO_SERVER_LOG_FILE.replace("*", serverLogMessage));

                    }
                }).collect(Collectors.toList());


        return  "ok";
    }


}
