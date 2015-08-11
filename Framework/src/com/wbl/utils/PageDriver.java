package com.wbl.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by svelupula on 8/8/2015.
 */
public class PageDriver {

    private final Configuration _configuration;
    private final Browsers _browser;
    private WebDriver _webDriver;
    private String _mainWindowHandler;

    public PageDriver(Configuration configuration) {
        _configuration = configuration;
        _browser = _configuration.Browser;
    }

    public WebDriver getDriver() {
        if (_webDriver == null) {
            try {
                Start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return _webDriver;
    }

    public Browsers getBrowser() {
        return _browser;
    }

    public String getMainWindowHandler() {
        return _mainWindowHandler;
    }

    public HtmlElement findElement(String locator) throws Exception {
        try {
            return new HtmlElement(this, getDriver().findElement(WBy.get(locator)));
        } catch (Exception ex) {
            throw ex;
        }
    }

    // Do not throws exceptions, only return null
    public Collection<HtmlElement> findElements(String locator) throws Exception {
        Collection<HtmlElement> elements = null;
        try {
            Collection<WebElement> webElements = getDriver().findElements(WBy.get(locator));
            if (webElements.size() > 0) {
                elements = new ArrayList<HtmlElement>();
            }
            for (WebElement element : webElements) {
                HtmlElement el = new HtmlElement(this, element);
                if (elements != null) elements.add(el);
            }
            return elements;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void quit() {
        if (_webDriver == null) {
            return;
        }
        _webDriver.quit();
        _webDriver = null;

        // TODO: Kill web driver process: chromedriver.exe, IEDriverServer.exe (test regarding should it be done on start)
    }

    public void open(String url) {
        _webDriver.navigate().to(url);
        try {
            implicitWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void implicitWait() throws Exception {
        if (_browser != Browsers.HtmlUnit) {
            _webDriver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
            return;
        }
        Thread.sleep(2000);
    }

    public String GetCurrentUrl() {
        return _webDriver.getCurrentUrl();
    }

    public void SaveScreenshot(String path) {
        try {
            FileUtils.copyFile(((TakesScreenshot) _webDriver).getScreenshotAs(OutputType.FILE),new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object ExecuteJavaScript(String javaScript, Object[] args) {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) _webDriver;

        return javaScriptExecutor.executeScript(javaScript, args);
    }

    public String GetDescription() {
        return "Browser";
    }

    private void Start() throws Exception {
        switch (_browser) {
            case InternetExplorer:
                _webDriver = StartInternetExplorer();
                break;
            case Firefox:
                _webDriver = StartFirefox();
                break;
            case Chrome:
                _webDriver = StartChrome();
                break;
            case HtmlUnit:
                _webDriver = StartHtmlUnit();
                break;
            default:
                throw new Exception(String.format("Unknown browser selected: {0}.", _configuration.Browser));
        }
        if (_browser != Browsers.HtmlUnit) {
            _webDriver.manage().window().maximize();
            _webDriver.manage().deleteAllCookies();
        }
        _mainWindowHandler = _webDriver.getWindowHandle();
    }

    private InternetExplorerDriver StartInternetExplorer() {
                return new InternetExplorerDriver();
    }

    private FirefoxDriver StartFirefox() {
        return new FirefoxDriver();
    }

    private ChromeDriver StartChrome() {
         //var defaultDataFolder = Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + @"\..\Local\Google\Chrome\User Data\Default";
        return new ChromeDriver();
    }

    private HtmlUnitDriver StartHtmlUnit() {
        return new HtmlUnitDriver();
    }

}
