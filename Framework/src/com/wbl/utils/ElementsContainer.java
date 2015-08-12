package com.wbl.utils;

import java.util.Collection;

/**
 * Created by svelupula on 8/8/2015.
 */
public interface ElementsContainer {

    HtmlElement findElement(String locator);

    Collection<HtmlElement> findElements(String locator);

    String getDescription();
}
