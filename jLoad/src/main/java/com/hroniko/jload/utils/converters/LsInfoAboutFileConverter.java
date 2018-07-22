package com.hroniko.jload.utils.converters;

import com.hroniko.jload.entities.FileInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LsInfoAboutFileConverter {
    public static FileInfo convert(String result) {
        FileInfo fileInfo = null;
        try {
            // -rw-r--r-- 1 user user 423341 2018-07-18 11:30:03 /путь_до_файла.jar
            result = result.replace("\n", "");
            String [] partLine = result.split(" ");
            Long size = new Long(partLine[4]);
            //
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = partLine[5] + " " + partLine[6];
            Date dateModified = format.parse(strDate);
            //
            String fullPath = partLine[7];
            String [] partPath = fullPath.split("/");
            String fullName = partPath[partPath.length-1];
            String dir = fullPath.substring(0, fullPath.length() - fullName.length() - 1);
            //
            String name = "";
            String ext = "";
            String [] partName = fullName.split("\\.");
            if (partName.length > 1){
                ext = partName[partName.length-1];
                name = fullName.substring(0, fullName.length() - ext.length() - 1);
            }

            fileInfo = new FileInfo(fullName, name, ext, fullPath, dir, size, dateModified);
        } catch (Exception e){
            fileInfo = null;
        }
        return fileInfo;
    }
}
