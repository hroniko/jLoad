package com.hroniko.jload.entities;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActiveHost {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String name;
    private Date dateStart;
    private Date dateEnd;
    private Double duration;

    public ActiveHost() {
    }

    public ActiveHost(String name, Date dateStart, Date dateEnd, Double duration) {
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.duration = duration;
    }

    public ActiveHost(String name, Date dateStart, Date dateEnd) {
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.duration = solveDuration(dateStart, dateEnd); // Продолжительность в минутах
    }

    public ActiveHost(String name) {
        this.name = name;
        this.dateStart = new Date();
        this.dateEnd = this.dateStart;
        this.duration = 0.0; // Продолжительность в минутах
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
        this.duration = solveDuration(dateStart, this.dateEnd);
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
        this.duration = solveDuration(this.dateStart, dateEnd);
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {

        return "ActiveHost ~ " +
                name +
                " ~ " + dateFormat.format(dateStart) + " - " +
                dateFormat.format(dateEnd) +
                " ~ " + new DecimalFormat("#0.00").format(duration) + " min" + //"#0.00" // #.0#"
                ';';
    }

    public String toStringInit() {

        return "ActiveHost ~ " +
                name +
                " ~ " + dateFormat.format(dateStart) + " - " +
                "init;";
    }

    private Double solveDuration(Date dateStart, Date dateEnd){
        return (dateEnd.getTime() - dateStart.getTime() + 0.0) / 60000; // Продолжительность в минутах
    }
}
