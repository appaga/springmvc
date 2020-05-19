package apgutil;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class UtilFile {
    public static File downloadFileFromUrl(String fileUrl, File file) throws Exception {
        if (Empty.isEmpty(fileUrl)) {
            throw new IllegalArgumentException("fileUrl is empty!");
        }
        if (file == null) {
            throw new IllegalArgumentException("file is null!");
        }
        if(file.isDirectory()) {
            throw new IllegalArgumentException("file is a direcoty!");
        }
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if(parentFile!=null) {
                parentFile.mkdirs();
            }
            file.createNewFile();
        }
        URL url = null;
        ReadableByteChannel readableByteChannel = null;
        FileOutputStream fos = null;
        try {
            url = new URL(fileUrl);
            readableByteChannel = Channels.newChannel(url.openStream());
            fos = new FileOutputStream(file);
            FileChannel fileChannel = fos.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            throw e;
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
        return file;
    }
}