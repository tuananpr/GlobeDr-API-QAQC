package stepdefinition;


import com.apis.globedr.helper.Data;
import com.apis.globedr.services.redis.RedisUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class CacheSteps extends Data {

    @Then("I load cache of above user")
    public void iLoadCacheOfAboveUser() {
        RedisUtil.getInstant().getUser(response.extract("data.userId"));
    }

    @Then("I load all cache")
    public void iLoadAllCache() {
        RedisUtil.getInstant().getAll();
    }

    @Given("Load all cache and it should be not empty")
    public void cacheShouldBeNotEmpty() {
        Assert.assertTrue(RedisUtil.getInstant().getAll().size() > 0 );
    }
}
