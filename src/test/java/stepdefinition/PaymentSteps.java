package stepdefinition;

import com.apis.globedr.business.org.OrgBus;
import com.apis.globedr.business.orgManager.OrgManagerBus;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;
import com.apis.globedr.enums.PaymentType;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.response.org.LoadOrgsRS;
import com.apis.globedr.model.step.org.OrgMS;
import com.apis.globedr.model.step.payment.PaymentConfigMS;
import io.cucumber.java.en.And;

public class PaymentSteps extends Data {
    OrgManagerBus orgManagerBus = new OrgManagerBus();
    OrgBus orgBus = new OrgBus();

    @And("I get payment info")
    public void iGetPaymentInfo(PaymentConfigMS info) {
        LoadOrgsRS rs = orgManagerBus.loadOrgsByName(info.getOrgName()).get(0);
        info.setOrgSig(rs.getOrgSig());
        orgManagerBus.orgPMs(info);
    }

    @And("I get payment type")
    public void iGetPaymentType(OrgMS info) {
        LoadOrgsRS rs = orgManagerBus.loadOrgsByName(info.getName()).get(0);
        info.setOrgSig(rs.getOrgSig());
        orgBus.paymentTypes(info);
    }

    @And("I add payoo payment for org")
    public void iAddPayooPaymentForOrg(PaymentConfigMS info) {
        LoadOrgsRS rs = orgManagerBus.loadOrgsByName(info.getOrgName()).get(0);
        info.setOrgSig(rs.getOrgSig())
                .setCheckoutUrl("https://newsandbox.payoo.com.vn/v2/paynow/order/create")
                .setPaymentType(PaymentType.Payoo)
                .setAvailable(true)
                .setMerchantShareKey("NTBmY2IzMTAyNTJhY2NiYjllNDBlNjE4MjRlOTdhOWM=")
                .setMerchantSecretKey("NTBmY2IzMTAyNTJhY2NiYjllNDBlNjE4MjRlOTdhOWM=")
                .setChecksumKey("NTBmY2IzMTAyNTJhY2NiYjllNDBlNjE4MjRlOTdhOWM=")
                .setBusinessUsername("SB_GLOBEDR")
                .setShopId(1479)
                .setShopTitle("GLOBEDR")
                .setShopDomain(Text.WEB_DOMAIN)
                .setShopBackUrl("payoosdk1479://postbacksdkurl")
                .setUrlApi("https://biz-sb.payoo.vn/BusinessRestAPI.svc")
                .setNotifyUrl(API.version() + "PM/PND")
                .setApiSignature("RahqzghjnDrVegmXn50lJLGQnBqMhtmjp4573iDMZpcv9PIxRzndMGaFM5dXkpCS")
                .setApiUsername("SB_GLOBEDR_BizAPI")
                .setApiPassword("cOyCWUrM0T43vYDY")
                .setShippingDays(0)
                .setValidityTime(1)
                .setItServiceFee(0)
                .setPwd("123456");
        orgManagerBus.newOrgPM(info);
    }


    @And("I update payoo payment for org")
    public void iUpdatePayooPaymentForOrg(PaymentConfigMS info) {
        LoadOrgsRS rs = orgManagerBus.loadOrgsByName(info.getOrgName()).get(0);
        info.setOrgSig(rs.getOrgSig())
                .setCheckoutUrl("https://newsandbox.payoo.com.vn/v2/paynow/order/create")
                .setPaymentType(PaymentType.Payoo)
                .setAvailable(true)
                .setMerchantShareKey("NTBmY2IzMTAyNTJhY2NiYjllNDBlNjE4MjRlOTdhOWM=")
                .setMerchantSecretKey("NTBmY2IzMTAyNTJhY2NiYjllNDBlNjE4MjRlOTdhOWM=")
                .setChecksumKey("NTBmY2IzMTAyNTJhY2NiYjllNDBlNjE4MjRlOTdhOWM=")
                .setBusinessUsername("SB_GLOBEDR")
                .setShopId(1479)
                .setShopTitle("GLOBEDR")
                .setShopDomain(Text.WEB_DOMAIN)
                .setShopBackUrl("payoosdk1479://postbacksdkurl")
                .setUrlApi("https://biz-sb.payoo.vn/BusinessRestAPI.svc")
                .setNotifyUrl(API.version() + "PM/PND")
                .setApiSignature("RahqzghjnDrVegmXn50lJLGQnBqMhtmjp4573iDMZpcv9PIxRzndMGaFM5dXkpCS")
                .setApiUsername("SB_GLOBEDR_BizAPI")
                .setApiPassword("cOyCWUrM0T43vYDY")
                .setShippingDays(0)
                .setValidityTime(1)
                .setItServiceFee(0)
                .setPwd("123456");
        orgManagerBus.orgPMUpdate(info);
    }
}