package com.hroniko.jload.utils.converters;

import com.hroniko.jload.entities.ActiveHost;

public class ResultLineConverter {

    // java    24126 netcrk  736u  IPv4 1692381      0t0  TCP host-192-168-100-15.openstacklocal:16955->wsmvr-154.netcracker.com:apri-lm (ESTABLISHED)
    public static ActiveHost convert(String line){
        String newline = line.replaceAll("\\s+", " "); // удаляем все пробельные символы, кроме одного
        String [] strMassive = newline.split(" "); // разбираем на отдельные слова в массив
        String rowHost = strMassive[8]; // Вытаскиваем host-192-168-100-15.openstacklocal:16955->wsmvr-154.netcracker.com:apri-lm
        String host = rowHost.split("->")[1].split(":")[0]; // Вытаскиваем wsmvr-154.netcracker.com
        ActiveHost activeHost = new ActiveHost(host);
        return activeHost;
    }
}
