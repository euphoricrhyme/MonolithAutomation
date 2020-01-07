package monolith.api.invoices;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        features = "src/test/resources/monolith/api/invoices/",
        glue = "src/test/java/monolith/api/invoices/StepDefinitions")
public class RunCucumberTest {
}
