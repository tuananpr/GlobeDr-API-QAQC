package com.apis.globedr.business.org;

import com.apis.globedr.apis.OrgApi;
import com.apis.globedr.business.AbsBus;
import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.request.org.*;
import com.apis.globedr.model.response.branch.BranchRS;
import com.apis.globedr.model.response.org.LoadDepartmentRS;
import com.apis.globedr.model.response.org.OrgRS;
import com.apis.globedr.model.response.org.OrgsManageRS;
import com.apis.globedr.model.response.org.StaffRS;
import com.apis.globedr.model.response.other.Specialty;
import com.apis.globedr.model.step.branch.BranchMS;
import com.apis.globedr.model.step.branch.UpdateBranchMS;
import com.apis.globedr.model.step.order.OrderAssignTakenSampleMS;
import com.apis.globedr.model.step.org.*;
import com.apis.globedr.model.step.review.ReviewMS;
import com.rest.core.response.Response;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrgBus extends AbsBus {

    private OrgApi orgApi = OrgApi.getInstant();

    public OrgRS createOrg(CreateOrgMS body) {
        return orgApi.createOrg(body);
    }

    public Response updateOrg(OrgMS newData) {
        return orgApi.updateOrgInfo(newData);
    }

    public Response featureAttributes(String orgSig) {
        return orgApi.featureAttributes(new OrgSig(orgSig));
    }


    public OrgRS createBranch(BranchMS body) {
        return orgApi.createBranch(body);
    }


    public Response updateBranch(BranchMS old, UpdateBranchMS newData) {
        BranchRS oldData = loadBranch(old).stream().filter(o -> o.getOrgName().equalsIgnoreCase(old.getName())).findFirst().orElse(null);
        newData.setOrgSig(oldData.getOrgSig());
        newData.setParentOrgSig(oldData.getParentOrgSig());
        return orgApi.updateBranch(newData);
    }

    public List<BranchRS> loadBranch(String orgSig) {
        BranchMS body = new BranchMS();
        body.setOrgSig(orgSig);
        return orgApi.loadBranch(body);
    }


    public List<BranchRS> loadBranch(BranchMS body) {
        return orgApi.loadBranch(body);
    }


    public Response removeBranch(BranchMS body) {
        List<BranchRS> branchs = loadBranch(body);
        BranchRS branch = branchs.stream()
                .filter(o -> o.getOrgName().trim().equalsIgnoreCase(body.getName().trim()))
                .findFirst().get();
        RemoveBranchRQ request = new RemoveBranchRQ();
        request.setOrgSig(branch.getParentOrgSig());
        request.setBranchSig(branch.getOrgSig());
        return orgApi.removeBranch(request);
    }


    public Response uiType(OrgMS body) {
        return orgApi.uiType(body);
    }



    public Response setCurrency(OrgMS body) {
        return orgApi.setCurrency(body);
    }

    public Response setCurrency(CreateOrgMS body) {
        return orgApi.setCurrency(body);
    }

    public Response addSpecialties(OrgMS body) {
        return addSpecialties(body.getOrgSig(), body.getSpecialtyCodes());
    }

    public Response addSpecialties(String orgSig, List<String> specialtyCodes) {
        AddSpecialtiesRQ body = new AddSpecialtiesRQ();
        body.setOrgSig(orgSig);
        body.setSpecialtyCodes(specialtyCodes);
        return orgApi.addSpecialties(body);
    }

    public Response removeSpecialties(OrgMS body) {
        return orgApi.removeSpecialties(body);
    }


    public List<StaffRS> loadUsersToAddStaffs(StaffMS info) {
        return orgApi.loadUsersToAddStaffs(info);
    }

    public Response addStaffs(StaffMS body) {
        List<String> userSigs = loadUsersToAddStaffs(body).stream().map(StaffRS::getUserSignature).collect(Collectors.toList());
        Integer deptId = loadDepartment(body).stream().map(LoadDepartmentRS::getDeptId).findFirst().orElse(null);
        AddStaffsRQ request = mapping(body, AddStaffsRQ.class);
        request.setUserSigs(userSigs);
        request.setToDeptId(deptId);
        return orgApi.addStaffs(request);
    }


    public Response setOrgManager(String userSig, String orgSig, boolean isManager) {
        SetOrgManagerRQ request = new SetOrgManagerRQ();
        request.setOrgSig(orgSig);
        request.setUserSigs(Arrays.asList(userSig));
        request.setIsManager(isManager);
        return orgApi.setOrgManager(request);
    }

    public Response setOrgManager(StaffMS body, boolean isManager) {
        body.setIsAdminLoad(true);
        List<String> userSigs = loadStaffs(body).stream().map(StaffRS::getUserSignature).collect(Collectors.toList());
        SetOrgManagerRQ request = mapping(body, SetOrgManagerRQ.class);
        request.setUserSigs(userSigs);
        request.setIsManager(isManager);
        return orgApi.setOrgManager(request);
    }

    public Response setOrgAdmin(StaffMS body, boolean isAdmin) {
        body.setIsAdminLoad(true);
        List<String> userSigs = loadStaffs(body).stream().map(StaffRS::getUserSignature).collect(Collectors.toList());
        SetOrgAdminRQ request = mapping(body, SetOrgAdminRQ.class);
        request.setUserSigs(userSigs);
        request.setIsAdmin(isAdmin);
        return orgApi.setOrgAdmin(request);
    }

    public Response userTrialAdd(StaffMS body) {
        body.setIsAdminLoad(true);
        body.setIsTrial(false);
        List<String> userSigs = loadStaffs(body).stream().map(StaffRS::getUserSignature).collect(Collectors.toList());

        UserTrialAddRQ request = new UserTrialAddRQ();
        request.setUserSigs(userSigs);
        request.setOrgSig(body.getOrgSig());
        return orgApi.userTrialAdd(request);
    }


    public Response userTrialRemove(StaffMS body) {
        body.setIsAdminLoad(true);
        body.setIsTrial(true);
        List<String> userSigs = loadStaffs(body).stream().map(StaffRS::getUserSignature).collect(Collectors.toList());
        body.setUserSigs(userSigs);
        return orgApi.userTrialRemove(body);
    }

    public Response setTelemedicine(StaffMS body, boolean isTelemedicine) {
        body.setIsAdminLoad(true);
        List<String> userSigs = loadStaffs(body).stream().map(StaffRS::getUserSignature).collect(Collectors.toList());
        SetTelemedicineRQ request = mapping(body, SetTelemedicineRQ.class);
        request.setUserSigs(userSigs);
        request.setTelemedicine(isTelemedicine);
        return orgApi.setTelemedicine(request);
    }

    public void setProvider(StaffMS body) {
        body.setIsAdminLoad(true);
        List<String> userSigs = loadStaffs(body).stream().map(StaffRS::getUserSignature).collect(Collectors.toList());
        userSigs.forEach(userSig -> {
            SetProviderRQ request = mapping(body, SetProviderRQ.class);
            request.setUserSig(userSig);
            orgApi.setProvider(request);
        });
    }
    public Response accessStaff(StaffMS body) {
        body.setIsAdminLoad(true);
        List<String> userSigs = loadStaffs(body).stream().map(StaffRS::getUserSignature).collect(Collectors.toList());
        body.setUserSig(userSigs.get(0));
        return orgApi.accessStaff(body);
    }

    public void setFeaturesForStaff(StaffMS body) {
        body.setIsAdminLoad(true);
        loadStaffs(body).forEach(staffRS -> {
            body.setUserSig(staffRS.getUserSignature());
            orgApi.staffFeatureAttributes(body);
        });
    }


    public void removeStaffs(StaffMS body) {
        body.setIsAdminLoad(true);
        List<String> userSigs = loadStaffs(body).stream().map(s -> s.getUserSignature()).collect(Collectors.toList());
        body.setUserSigs(userSigs);
        orgApi.removeStaffs(body);
    }

    public void hideStaffs(StaffMS body) {
        body.setIsAdminLoad(true);
        List<String> userSigs = loadStaffs(body).stream().map(s -> s.getUserSignature()).collect(Collectors.toList());
        body.setUserSigs(userSigs);
        orgApi.hideStaffs(body);
    }

    public void moveStaffs(StaffMS body) {
        body.setIsAdminLoad(true);
        List<String> userSigs = loadStaffs(body).stream().map(s -> s.getUserSignature()).collect(Collectors.toList());
        Integer todeptId = loadDepartmentByOrg(body.getOrgSig()).stream()
                .filter(d -> d.getName().equalsIgnoreCase(body.getToDeptName())).findFirst().get().getDeptId();
        body.setUserSigs(userSigs);
        body.setToDeptId(todeptId);
        orgApi.moveStaffs(body);
    }

    public Response pwdDoctor(StaffMS body) {
        body.setIsAdminLoad(true);
        String userSig = loadStaffs(body).stream().map(s -> s.getUserSignature()).findFirst().orElse(null);
        body.setUserSig(userSig);
        return orgApi.pwdDoctor(body);
    }

    public List<StaffRS> loadStaffs(StaffMS body) {
        LoadDepartmentRS rs = loadDepartment(body).stream().filter(d -> d.getName().equalsIgnoreCase(body.getDeptName()))
                .findFirst().orElse(null);

        Integer deptIt = (rs != null) ? rs.getDeptId() : null;
        body.setDeptId(deptIt);
        return orgApi.loadStaffs(body);
    }

    public List<LoadDepartmentRS> loadDepartment(DepartmentMS body) {
        return orgApi.loadDepartment(body);
    }

    public List<LoadDepartmentRS> loadDepartment(Object body) {
        return orgApi.loadDepartment(body);
    }

    public Response addDepartment(DepartmentMS body) {
        return orgApi.addDepartment(body);
    }

    public Response removeDepartment(DepartmentMS body) {
        Integer deptId = loadDepartment(body).stream()
                .filter( d -> d.getName().equalsIgnoreCase(body.getName()))
                .map(d -> d.getDeptId()).findFirst().orElse(null);
        body.setDeptId(deptId);
        return orgApi.removeDepartment(body);
    }


    public List<LoadDepartmentRS> loadDepartmentByOrg(String orgSig) {
        LoadDepartmentsRQ body = new LoadDepartmentsRQ();
        body.setOrgSig(orgSig);
        return orgApi.loadDepartment(body);
    }

    public Response loadRating(ReviewMS body) {
        return orgApi.loadRating(body);
    }

    public Response orgFeatureAttributeForStaff(Object body) {
        return orgApi.orgFeatureAttributeForStaff(body);
    }



    public Response newDoctor(StaffMS body) {
        LoadDepartmentRS departmentRS = loadDepartment(body).stream()
                .filter(d -> d.getName().equalsIgnoreCase(body.getDeptName())).findFirst().orElse(null);
        body.setDeptId(departmentRS.getDeptId());
        return orgApi.newDoctor(body);
    }


    public List<Specialty> loadSpecialties(String orgSig, boolean loadAll) {
        GetSpecialtiesRQ body = new GetSpecialtiesRQ();
        body.setOrgSig(orgSig);
        body.setLoadAll(loadAll);
        return orgApi.getSpecialties(body);
    }

    public List<String> loadSpecialtiesCode(String orgSig, boolean loadAll) {
        return loadSpecialties(orgSig, loadAll).stream().map(s -> s.getCode()).collect(Collectors.toList());
    }

    public List<String> loadSpecialtiesCodeByName(String orgSig, boolean loadAll, List<String> names) {
        return loadSpecialties(orgSig, loadAll).stream()
                .filter(s -> names.contains(s.getName()))
                .map(s -> s.getCode())
                .collect(Collectors.toList());
    }

    public List<OrgsManageRS> getOrgsManage() {
        return orgApi.getOrgsManage();
    }

    public OrgsManageRS selectOrgManage(String orgName) {
        return getOrgsManage().stream().filter(o -> o.getName().equalsIgnoreCase(orgName)).findFirst().orElse(null);
    }

    public OrgRS getOrgInfo(String orgSig) {
        return orgApi.getOrgInfo(new OrgSig(orgSig));
    }


    public Response getOrgAttributes(String orgSig) {
        return orgApi.getOrgAttributes(new OrgSig(orgSig));
    }

    public Response updateOrgType(OrgMS body) {
        return orgApi.updateOrgType(body);
    }

    public Response defaultCover(OrgMS body) {
        return orgApi.defaultCover(body);
    }
    public Response updateIntro(OrgMS body) {
        return orgApi.updateIntro(body);
    }

    public Response deliveryTypes(OrgSig body) {
        return orgApi.deliveryTypes(body);
    }
    public Response refreshApiKey(OrgSig body) {
        return orgApi.refreshApiKey(body);
    }

    public Response cover(OrgMS body) {
        return orgApi.cover(body);
    }
    public Response consultConfig(ConsultConfigRQ body) {
        return orgApi.consultConfig(body);
    }

    public Response consultConfigValue(ConsultConfigValueRQ body) {
        return orgApi.consultConfigValue(body);
    }

    public Response checkPickUpChat(StaffMS body) {
        List<String> userSigs = loadStaffs(body).stream().map(StaffRS::getUserSignature).collect(Collectors.toList());
        body.setUserSig(userSigs.get(0));
        return orgApi.checkPickUpChat(body);
    }

    public Response paymentTypes(OrgMS body) {
        return orgApi.paymentTypes(body);
    }



}
