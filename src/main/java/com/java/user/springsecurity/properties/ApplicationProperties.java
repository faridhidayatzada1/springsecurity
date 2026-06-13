package com.java.user.springsecurity.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("application")
public class ApplicationProperties {


    private final Client client = new Client();

    @Getter
    @Setter
    public static class Client {

        private final Akb akb = new Akb();

        @Getter
        @Setter
        public static class Akb {

            private final Credentials credentials = new Credentials();

            @Getter
            @Setter
            @Component
            public static class Credentials {

                private String iss;
                private String aud;

            }
        }
    }
}
