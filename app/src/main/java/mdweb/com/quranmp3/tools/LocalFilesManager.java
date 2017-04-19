package mdweb.com.quranmp3.tools;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

// TODO: Auto-generated Javadoc

/**
 * LocalFilesManager class to store, extract data into SD card.
 */
public class LocalFilesManager {

    /**
     * The context.
     */
    static Context context;

    /**
     * Instantiates a new locally files.
     *
     * @param ctx the ctx
     */
    public LocalFilesManager(Context ctx) {
        context = ctx;
    }

    /**
     * Save locally file.
     *
     * @param fileName the file name
     * @param content  the content
     */
    public void saveLocallyFile(String fileName, String content) {

        File file = context.getDir("_data", Context.MODE_PRIVATE);
        File layoutFile = new File(file.getAbsolutePath() + "/data_files");
        boolean dir = layoutFile.isDirectory();
        if (!dir) {
            // create a diractory if not exist
            dir = layoutFile.mkdir();
        }
        if (dir) {
            File f = new File(layoutFile.getAbsolutePath() + "/" + fileName);
            Writer writer = null;

            if (f.exists() && content.length() > 0) {
                f.delete();
                try {
                    f.createNewFile();
                    writer = new BufferedWriter(new FileWriter(f));
                    writer.write(content);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (!f.exists()) {
                try {
                    f.createNewFile();
                    writer = new BufferedWriter(new FileWriter(f));
                    writer.write(content);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    /**
     * Gets the file content array.
     *
     * @param filename the filename
     * @return the file content array
     */
    public JSONArray getFileContentArray(String filename) {
        File file = context.getDir("_data", Context.MODE_PRIVATE);
        File filepath = new File(file.getAbsolutePath() + "/data_files/"
                + filename);
        BufferedReader reader = null;
        Writer writer = null;
        String path = filepath.getAbsolutePath();
        JSONArray UIJson = null;
        if (FileExist(filename)) {
            try {
                InputStream inputStream = null;
                inputStream = new FileInputStream(path);

                reader = new BufferedReader(new UnicodeReader(inputStream, "UTF-8"));
                char[] buffer = new char[2048];
                writer = new StringWriter();
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException logOrIgnore) {
                    }
            }

            try {
                UIJson = new JSONArray(writer.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return UIJson;
        }
        return null;
    }

    /**
     * Gets the file content.
     *
     * @param filename the filename
     * @return the file content
     */
    public JSONObject getFileContent(String filename) {
        File file = context.getDir("_data", Context.MODE_PRIVATE);
        File filepath = new File(file.getAbsolutePath() + "/data_files/"
                + filename);
        BufferedReader reader = null;
        Writer writer = null;
        String path = filepath.getAbsolutePath();
        JSONObject UIJson = null;
        if (FileExist(filename)) {
            try {
                InputStream inputStream = null;
                inputStream = new FileInputStream(path);

                reader = new BufferedReader(new UnicodeReader(inputStream,
                        "UTF-8"));
                char[] buffer = new char[2048];
                writer = new StringWriter();
                int number;
                while ((number = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, number);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException logOrIgnore) {
                    }
            }
            String text = writer.toString();

            try {
                UIJson = new JSONObject(text);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return UIJson;
        }
        return null;
    }

    /**
     * Gets the file content text.
     *
     * @param filename the filename
     * @return the file content text
     */
    public String getFileContentText(String filename) {
        File file = context.getDir("_data", Context.MODE_PRIVATE);
        File filepath = new File(file.getAbsolutePath() + "/data_files/"
                + filename);
        BufferedReader reader = null;
        Writer writer = null;
        String path = filepath.getAbsolutePath();
        String UIJson = null;
        if (FileExist(filename)) {
            try {
                InputStream inputStream = null;
                inputStream = new FileInputStream(path);

                reader = new BufferedReader(new UnicodeReader(inputStream,
                        "UTF-8"));
                char[] buffer = new char[2048];
                writer = new StringWriter();
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException logOrIgnore) {
                    }
            }
            UIJson = writer.toString();
            return UIJson;
        }
        return null;
    }

    /**
     * Files locally saved.
     *
     * @return true, if successful
     */
    public boolean FilesLocallySaved() {
        File file = context.getDir("_data", Context.MODE_PRIVATE);
        File filepath = new File(file.getAbsolutePath() + "/data_files/"
                + "wasafat");
        File jsonfile = new File(filepath.getAbsolutePath());
        boolean result = false;
        if (!jsonfile.exists()) {
            result = false;
        } else if (jsonfile.exists()) {
            result = true;
        }
        return result;
    }

    /**
     * Gets the file.
     *
     * @param fileName the file name
     * @return the file
     */
    public File getFile(String fileName) {
        File file = context.getDir("_data", Context.MODE_PRIVATE);
        File filepath = new File(file.getAbsolutePath() + "/data_files/"+ fileName);
        File jsonfile = new File(filepath.getAbsolutePath());
        return jsonfile;
    }

    /**
     * First file starting with.
     *
     * @param fileName the file name
     * @return the file
     */
    public File firstFileStartingWith(String fileName) {
        File dir = context.getDir("_data", Context.MODE_PRIVATE);
        dir = new File(dir.getAbsolutePath() + "/data_files/");
        File[] files = dir.listFiles();
        int i = 0;
        if (files != null)
            while (i < files.length) {
                if (files[i].getName().startsWith(fileName))
                    return files[i];
                i++;
            }
        return new File("");
    }

    /**
     * Gets the files path.
     *
     * @param fileName the file name
     * @return the files path
     */
    public String getFilesPath(String fileName) {
        File file = context.getDir("_data", Context.MODE_PRIVATE);
        File filepath = new File(file.getAbsolutePath() + "/data_files/" + fileName);
        File jsonfile = new File(filepath.getAbsolutePath());
        String result = null;
        if (jsonfile.exists()) {
            result = filepath.getAbsolutePath();
        }
        return result;
    }

    /**
     * File exist.
     *
     * @param fileName the file name
     * @return true, if successful
     */
    public boolean FileExist(String fileName) {
        String path = getFilesPath(fileName);
        if (path == null) return false;
        File jsonfile = new File(path);
        return jsonfile.exists();
    }

}
