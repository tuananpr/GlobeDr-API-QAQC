package observers;

import com.rest.core.observers.IObserverApi;
import com.rest.core.request.AbsRequest;
import com.rest.core.response.NullResponse;
import com.rest.core.response.Response;
import org.testng.Assert;

public class AssertDefaultResponse implements IObserverApi {

    static private String getLog() {
        return null;
    }

    private void checkReponseIsEncypt(Response response){
        if (response.isHasPath("d") && response.isHasPath("s")) {
            String isEncryption = response.getHeader("Encryption");
            Assert.assertTrue(isEncryption != null && isEncryption.equals("true"),
                    String.format("Encrypted response must be header 'Encryption' is true. but header 'Encryption' return %s.", isEncryption));
        }
    }

    @Override
    public void update(String content) {

    }

    @Override
    public void update(AbsRequest absRequest, Response response) {
        long timeout = 60000; // milliseconds

        Assert.assertTrue(response.isSuccessStatusCode(), String.format("Response is fail by status code[%d].\n", response.statusCode()) + getLog());
        Assert.assertFalse((response instanceof NullResponse), "Body Response isn't null\n" + getLog());
        Assert.assertTrue(response.getTime() <= timeout, String.format("Response time must be lower %d milliseconds.\n", timeout));

        checkReponseIsEncypt(response);

    }





}
