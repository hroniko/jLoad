package com.hroniko.jload;

import com.hroniko.jload.actions.MainProcessor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class JLoad {
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);

        System.out.println("App for upload jar files to toms");

        System.out.println("Insert host ip:");
        String host = in.nextLine();

        System.out.println("Insert path to new jar file or press enter for load from tmp folder:");
        String path = in.nextLine();
        List<File> lst = new ArrayList<>();
        if (path == null || path.length() == 0){
//            path = "."; 127.0.0.1
//            File dir1 = new File(path); //path указывает на директорию
            path = "./target/tmp"; // path = "/media/hroniko/DATA/WORK/SCRIPT_LOAD/jLoad/target/tmp";
            File dir = new File(path); //path указывает на директорию
            File[] arrFiles = dir.listFiles();
            if (arrFiles == null) {
                lst = new ArrayList<>();
                System.out.println("Files for upload not found! Please repeat you insert");
            }
            else {
                lst = Arrays.asList(arrFiles);
                System.out.println("Found " + lst.size() + " files for upload:");
                for(int i = 0; i < lst.size(); i++){
                    System.out.println("[" + (i+1) + "] " + lst.get(i).getName());
                }
            }
        } else {
            File file = new File(path);
            lst.add(file);
            System.out.println("Found 1 files for upload:");
            System.out.println("[1] " + file.getName());
        }


        MainProcessor mp = new MainProcessor();
        String result = mp.run(host, lst);

        System.out.println("Found 1 files on server:");
        System.out.println(result);


//        while (true){
////            mp.run("10.101.46.26");
//            mp.run("localhost");
////            String result = mp.run("localhost");
//            // if (result.contains("ActiveHost")) System.out.println(result);
//            TimeUnit.SECONDS.sleep(2);
//        }

    }
}
