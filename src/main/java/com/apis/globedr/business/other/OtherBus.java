package com.apis.globedr.business.other;

import com.apis.globedr.apis.OtherApi;
import com.apis.globedr.enums.Language;
import com.apis.globedr.model.general.Address;
import com.apis.globedr.model.general.City;
import com.apis.globedr.model.general.District;
import com.apis.globedr.model.general.Ward;
import com.apis.globedr.model.request.other.GetCitiesRQ;
import com.apis.globedr.model.request.other.GetDistrictsRQ;
import com.apis.globedr.model.request.other.GetWardsRQ;
import com.apis.globedr.model.request.other.LoadSpecialtiesRQ;
import com.apis.globedr.model.response.other.CountryRS;
import com.apis.globedr.model.response.other.Specialty;
import com.apis.globedr.model.step.account.AccountMS;
import com.apis.globedr.model.step.org.CreateOrgMS;
import com.apis.globedr.model.step.org.OrgMS;
import com.rest.core.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OtherBus {
    public static OtherBus INSTANCE = new OtherBus();

    OtherApi otherApi = OtherApi.getInstant();

    public List<String> loadSubSpecialtiesCode(List<String> names) {
        return loadSubSpecialties(names).stream().map(s -> s.getCode()).collect(Collectors.toList());
    }

    public List<Specialty> loadSubSpecialties(List<String> names) {
        List<Specialty> specialties = new ArrayList<>();
        names.stream().forEach(name -> {
            specialties.addAll(loadSubSpecialties(name, Language.English.value()));
            specialties.addAll(loadSubSpecialties(name, Language.Vietnam.value()));
        });
        return specialties;
    }


    public List<Specialty> loadSubSpecialties(String name, int language) {
        LoadSpecialtiesRQ body = new LoadSpecialtiesRQ();
        body.setName(name);
        body.setIncludeSub(false);
        body.setOnlySub(true);
        body.setLanguage(language);
        return loadSpecialties(body);
    }


    public List<Specialty> loadSpecialties(LoadSpecialtiesRQ body) {
        return otherApi.loadSpecialties(body);
    }

    public List<String> loadSpecialties(String name) {
        LoadSpecialtiesRQ body = LoadSpecialtiesRQ.builder().name(name).build();
        return loadSpecialties(body).stream().map(s -> s.getCode()).collect(Collectors.toList());
    }

    public Response getRegions(String country) {
        return otherApi.getRegions(country);
    }

    public Response loadMetaData() {
        return otherApi.loadMetaData();
    }

    public Response getLandingPage(int language) {
        return otherApi.getLandingPage(language);
    }

    public Response checkPhone(String phone) {
        return otherApi.checkPhone(phone);
    }

    public Response detectLocation() {
        return otherApi.detectLocation();
    }

    public Response getHosts(Map<String, Object> body) {
        return otherApi.getHosts(body);
    }

    public Response getDownloadApp() {
        return otherApi.getDownloadApp();
    }

    public List<CountryRS> getCountries() {
        return otherApi.getCountries();
    }

    public CountryRS getCountry(String country) {
        return getCountries().stream()
                .filter(c -> c.hasCountry(country))
                .findFirst().orElse(null);
    }

    public List<City> getCity(GetCitiesRQ body) {
        return otherApi.getCities(body);
    }

    public City getCity(Address info) {
        return getCity(info.getCountry(), info.getCity());
    }

    public City getCity(String country, String city) {
        GetCitiesRQ body = new GetCitiesRQ();
        body.setCountry(getCountry(country).getCountry());
        return getCity(body).stream()
                .filter(c -> c.has(city)).findFirst().orElse(null);
    }


    public List<District> getDistrict(GetDistrictsRQ body) {
        return otherApi.getDistricts(body);
    }

    public District getDistrict(Address info) {
        return getDistrict(info.getCountry(), info.getCity(), info.getDistrict());
    }


    public District getDistrict(String country, String city, String district) {
        GetDistrictsRQ body = new GetDistrictsRQ();
        body.setCity(getCity(country, city).getCode());
        return getDistrict(body).stream()
                .filter(c -> c.has(district)).findFirst().orElse(null);
    }

    public District getDistrict(CreateOrgMS info) {
        return getDistrict(info.getCountry().getCountry(), info.getCity().getCode(), info.getDistrictName());
    }


    public List<Ward> getWards(GetWardsRQ body) {
        return otherApi.getWards(body);
    }

    public List<Ward> getWards(Address info) {
        GetWardsRQ body = new GetWardsRQ();
        body.setDistrict(getDistrict(info).getCode());
        return getWards(body);
    }

    public Ward getWard(String country, String city, String district, String ward) {
        GetWardsRQ body = new GetWardsRQ();
        body.setDistrict(getDistrict(country, city, district).getCode());
        return getWards(body).stream()
                .filter(c -> c.has(ward)).findFirst().orElse(null);
    }

    public Ward getWard(CreateOrgMS info) {
        return getWard(info.getCountry().getCountry(), info.getCity().getCode(), info.getDistrictName(), info.getWardName());
    }


}
