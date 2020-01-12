package tk.jimkong.project.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private BufferedWriter out = null;

    public void createLog() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
            File fileName = new File("./log/" + df.format(new Date()).toString() + ".log");
            if (!fileName.getParentFile().exists()) {
                fileName.getParentFile().mkdirs();
            }
            fileName.createNewFile();
            out = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeLog(String str) {
        try {
            if (out == null) return;
            out.write(str + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeLog() {
        try {
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
