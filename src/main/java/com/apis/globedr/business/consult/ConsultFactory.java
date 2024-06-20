package com.apis.globedr.business.consult;

public class ConsultFactory {

    public static ConsultBus init(String role){
        if(role.equalsIgnoreCase("coordinator")) return new ConsultCoor();
        if(role.equalsIgnoreCase("auditor")) return new ConsultAuditor();
        if(role.equalsIgnoreCase("doctor")) return new ConsultDoctor();

        return new ConsultUser();
    }


    public static ConsultRecipient initRecipient(String role){
        if(role.equalsIgnoreCase("auditor")) return new ConsultAuditor();
        if(role.equalsIgnoreCase("doctor")) return new ConsultDoctor();

        return null;
    }

}
