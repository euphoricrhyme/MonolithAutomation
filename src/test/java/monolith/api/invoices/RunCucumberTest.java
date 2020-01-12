package monolith.api.invoices;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Features",
        glue = "src/test/java/monolith/api/stepDef")
public class RunCucumberTest {
}
