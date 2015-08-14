package com.wbl.pages;

import com.wbl.base.BaseWebTest;

import java.util.Set;

/**
 * Created by svelupula on 8/14/2015.
 */
public abstract class PortalPage extends BaseWebTest {


    public String getTitle() {
        return driver.getTitle();
    }

    public boolean isLogoPresent() {
        return driver.findElements("header.logo").size() > 0;
    }

    public boolean areSocialIconsPresent() {
        return driver.findElements("header.social").size() > 0;
    }

    public int getSocialIconsCount() {
        return driver.findElements("header.social").size();
    }

    public Set<String> getSocialLinks() {
        return driver.getLinks("header.social.links");
    }


}
