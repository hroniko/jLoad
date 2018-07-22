package com.hroniko.jload.entities;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileInfo {
    private String fullName;
    private String name;
    private String extension;
    private String fullPath;
    private String dir;

    private Long size;
    private Date dateModified;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateFormat dateFormatForBackup = new SimpleDateFormat("yyyy-MM-dd_HH-mm");

    public FileInfo() {
    }

    public FileInfo(String fullName, String name, String extension, String fullPath, String dir, Long size, Date dateModified) {
        this.fullName = fullName;
        this.name = name;
        this.extension = extension;
        this.fullPath = fullPath;
        this.dir = dir;
        this.size = size;
        this.dateModified = dateModified;
    }

    public FileInfo(String fullPath) {
        this.fullPath = fullPath;


    }

    public FileInfo(File file) {
        String name = "";
        String ext = "";
        String fullName = file.getName();
        String [] partName = fullName.split("\\.");
        if (partName.length > 1){
            ext = partName[partName.length-1];
            name = fullName.substring(0, fullName.length() - ext.length() - 1);
        }

        this.fullName = fullName;
        this.name = name;
        this.extension = ext;
        this.fullPath = file.getAbsolutePath().replace("/./", "/");
        this.dir = this.fullPath.substring(0, fullPath.length() - fullName.length() - 1);
        this.size = file.length();
        this.dateModified = new Date(file.lastModified());
    }


    /* ---------------------------------------------------- */
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    /* Info */

    public String info(){
        return "(" + new DecimalFormat("#0.00").format((getSize() + 0.0)/1024) + " kB, mf "
                + dateFormat.format(getDateModified()) + ")";
    }

    public String dateTimeForBackup(){
        return dateFormatForBackup.format(getDateModified());
    }
}
