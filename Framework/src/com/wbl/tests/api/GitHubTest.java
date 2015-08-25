package com.wbl.tests.api;

import com.wbl.base.BaseApiTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by svelupula on 8/25/2015.
 */
public class GitHubTest extends BaseApiTest {


    @BeforeClass
    public void beforeClass() {
        //data setup
    }

    @DataProvider(name = "users-data")
    public Object[][] getUsers() {
        return new Object[][]{{"users/whiteboxhub"}};
    }

    @Test(priority = 1, alwaysRun = true, dataProvider = "users-data")
    public void testUser(String username) {
        try {
            restUtil.getJSONEntity(username);
            assertEquals(restUtil.getStatusCode(), 200);
        } catch (Exception e) {
            assertFalse(true);
        }
    }

}
