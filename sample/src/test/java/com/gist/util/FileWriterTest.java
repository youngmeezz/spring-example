package com.gist.util;

import java.io.File;
import java.io.FileWriter;

import org.junit.Test;

public class FileWriterTest {
    @Test
    public void createPrivacy() throws Exception {
        String value = "values values";
        String temp = "asdflasjdh flasdfh uyiadf lkjasdh lfkjasnf lkjasnflasfkasdf ";
        File file = new File("D:\\privacy.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            for (int i = 0; i < 10; i++) {
                fw.write(value);
                fw.flush();
            }
            for (int i = 0; i <= 1000000; i++) {
                fw.write(temp);
                fw.flush();
            }

        }
        finally {
            if (fw != null) {
                fw.close();
            }
        }
    }

}
