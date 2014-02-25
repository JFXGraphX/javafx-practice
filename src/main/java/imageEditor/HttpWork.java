package imageEditor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpWork
{
    private HttpURLConnection connection;

    public int doPost(String url, String query) throws MalformedURLException, IOException
    {
        String charset = "UTF-8";
        String param = query;
        String boundary = Long.toHexString(System.currentTimeMillis());

        this.connection = ((HttpURLConnection) new URL(url).openConnection());

        this.connection.setDoOutput(true);
        this.connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; boundary=" + boundary);
        PrintWriter writer = null;

        if (this.connection.getDoOutput())
        {
            try
            {
                OutputStream output = this.connection.getOutputStream();
                writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
                writer.append(param).flush();
            } finally
            {
                if (writer != null) writer.close();
            }
        }

        InputStream is = this.connection.getInputStream();
        int i = -1;
        while ((i = is.read()) != -1)
        {
            System.out.print((char) i);
        }

        if (this.connection.getResponseCode() == 200)
        {
            return 200;
        }

        return this.connection.getResponseCode();
    }
}
