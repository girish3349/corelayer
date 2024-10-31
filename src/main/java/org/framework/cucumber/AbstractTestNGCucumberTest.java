package org.framework.cucumber;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.framework.utilities.FileUtil;
import org.framework.utilities.Screenshots;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Listeners(org.framework.listeners.TestListener.class)
public abstract class AbstractTestNGCucumberTest {

    protected static HashMap<String, List<String>> tagsInScenario = new HashMap<>();
    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    public void reportsFolderCleanUp() {
        File file = new File(Screenshots.getScreenshotsFolderPath());
        if (file.exists()) FileUtil.cleanDirectory(file); //Need to Implement
    }

    @Test(description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        List<String> getTagNames = new ArrayList<>();
        for (String getTag : pickleWrapper.getPickle().getTags()) {
            getTagNames.add(getTag);
        }
        tagsInScenario.put(pickleWrapper.getPickle().getName(), getTagNames);

        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        if (testNGCucumberRunner == null) {
            return new Object[0][0];
        }
        return testNGCucumberRunner.provideScenarios();
    }


}

