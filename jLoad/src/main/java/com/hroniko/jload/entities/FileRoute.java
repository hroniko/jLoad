package com.hroniko.jload.entities;

import java.util.ArrayList;
import java.util.List;

/* Класс для сопоставления локального файла и его серверных копий */
public class FileRoute {
    private FileInfo localFile;
    private List<FileInfo> serverFiles;

    public FileRoute() {
    }

    public FileRoute(FileInfo localFile) {
        this.localFile = localFile;
    }

    /* ---------------------------------- */

    public FileInfo getLocalFile() {
        return localFile;
    }

    public void setLocalFile(FileInfo localFile) {
        this.localFile = localFile;
    }

    public List<FileInfo> getServerFiles() {
        if(serverFiles == null) serverFiles = new ArrayList<>();
        return serverFiles;
    }

    public void setServerFiles(List<FileInfo> serverFiles) {
        this.serverFiles = serverFiles;
    }
}
