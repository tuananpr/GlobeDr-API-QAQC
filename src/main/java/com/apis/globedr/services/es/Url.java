package com.apis.globedr.services.es;

import com.apis.globedr.services.config.ElasticCfg;

public class Url {

    private static String getElasticUri() {
        String domain = new ElasticCfg().get("es.uri");
        String apiVersion = "";
        return domain + apiVersion;
    }


    public static class Elastic {

        private static final String FULL_DOMAIN_ELASTIC = getElasticUri();
        private static final String USER_ELASTIC_SEARCH = FULL_DOMAIN_ELASTIC + "/user/";
        private static final String ORG_ELASTIC_SEARCH = FULL_DOMAIN_ELASTIC + "/org/";
        private static final String All = FULL_DOMAIN_ELASTIC + "/_all/";
        public static class Org {
            public static final String DELETE = ORG_ELASTIC_SEARCH + "_delete_by_query";
            public static final String REFRESH = ORG_ELASTIC_SEARCH + "_refresh";
            public static final String SEARCH = ORG_ELASTIC_SEARCH + "_search";
        }

        public static class User {
            public static final String SEARCH = USER_ELASTIC_SEARCH + "_search";
            public static final String DELETE = USER_ELASTIC_SEARCH + "_delete_by_query";
            public static final String USER_ID = USER_ELASTIC_SEARCH + "/user/";
            public static final String REFRESH = USER_ELASTIC_SEARCH + "_refresh";

        }

        public static class All {
            public static final String SETTINGS = ORG_ELASTIC_SEARCH + "_settings";
        }
    }
}
