package com.wbl.pages;

import com.wbl.utils.PageDriver;

/**
 * Created by svelupula on 8/14/2015.
 */
public class HomePage extends PortalPage {

    public HomePage(PageDriver driver) {
        super(driver);
        driver.open("/");
    }

    public int getSliderItemsCount() {
        return driver.findElements("home.slider.items").size();
    }

}
