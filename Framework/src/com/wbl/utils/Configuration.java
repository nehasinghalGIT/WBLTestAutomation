package com.wbl.utils;

import org.apache.log4j.Logger;

import java.io.FileReader;
import java.util.Properties;

/**
 * Created by svelupula on 8/8/2015.
 */
public class Configuration {

    public Browsers Browser;
    public String Device;
    public String BaseUrl;
    public String BaseURI;
    public String TestResultPath;
    public String TestDataPath;
    public int WaitTimeout;
    private Logger _logger;


    public Configuration() {
        try {
            _logger = Logger.getLogger(Configuration.class);
            Properties props = new Properties();
            props.load(new FileReader(String.format("%s/config.properties", System.getProperty("user.dir"))));
            //Browser = Browsers.valueOf(props.getProperty("browser"));
            String browser = props.getProperty("browser");
            Browser = Browsers.Firefox;
            if(browser.toLowerCase().equals("firefox"))
            {
                Browser = Browsers.Firefox;
            }
            else if(browser.toLowerCase().equals("chrome"))
            {
                Browser = Browsers.Chrome;
            }
            else if(browser.toLowerCase().equals("htmlunit"))
            {
                Browser = Browsers.HtmlUnit;
            }
            else if(browser.toLowerCase().equals("ie"))
            {
                Browser = Browsers.InternetExplorer;
            }

            BaseUrl = props.getProperty("url");
            BaseURI = props.getProperty("uri");
            Device = props.getProperty("device");
            TestResultPath = props.getProperty("test-result-path");
            TestDataPath = props.getProperty("test-data-path");
            WaitTimeout = Integer.parseInt(props.getProperty("wait-timeout"));
        } catch (Exception ex) {
            _logger.error(ex);
        }

    }

}
