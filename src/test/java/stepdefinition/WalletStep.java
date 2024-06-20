package stepdefinition;


import com.apis.globedr.business.userManager.UserManagerBus;
import com.apis.globedr.business.wallet.WalletBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.request.wallet.UsePointNotiRQ;
import com.apis.globedr.model.request.wallet.UsedPointHistoryDetailRQ;
import com.apis.globedr.model.step.wallet.PointHistoryMS;
import com.apis.globedr.model.step.wallet.WalletMS;
import io.cucumber.java.en.And;

public class WalletStep extends Data {

    WalletBus walletBus = new WalletBus();
    UserManagerBus userManagerBus = new UserManagerBus();

    @And("I load points")
    public void iLoadPointWallet() {
        walletBus.points();
    }

    @And("I load reward points instructions")
    public void iLoadRewardPointsInstructions(WalletMS info) {
        walletBus.loadRewardPointIntruct(info);
    }


    @And("I load points history")
    public void iLoadPointsHistory(WalletMS info) {
        walletBus.loadPointsHistory(info);
    }


    @And("As sysAdmin, I load point history of user")
    public void iLoadUsedPointsHistory(PointHistoryMS info) {
        info.setUserSig(userManagerBus.loadUsersAndGetSigs(info.getName()).get(0));
        walletBus.usedPointHistoryDetail(info);
    }


    @And("I load function fees")
    public void iLoadFunctionFees(UsePointNotiRQ info) {
        walletBus.usePointNoti(info);
    }
}
