package com.wbl.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * Created by svelupula on 8/8/2015.
 */
public class Configuration {

    public Browsers Browser;
    public String Device;
    public String BaseUrl;
    public String TestResultPath;
    public String TestDataPath;
    public int WaitTimeout;
    private Logger _logger;


    public Configuration() {
        try {
            _logger = Logger.getLogger(Configuration.class);
            Properties props = new Properties();
            props.load(new FileReader(new File("config.properties")));
            Browser = Browsers.valueOf(props.getProperty("browser"));
            BaseUrl = props.getProperty("url");
            Device = props.getProperty("device");
            TestResultPath = props.getProperty("test-result-path");
            TestDataPath = props.getProperty("test-data-path");
            WaitTimeout = Integer.parseInt(props.getProperty("wait-timeout"));
        } catch (Exception ex) {
            _logger.error(ex);
        }

    }

}
