package dev.c20.workflow.auth.services;

import dev.c20.workflow.storage.repositories.StorageRepository;
import dev.c20.workflow.storage.repositories.ValueRepository;
import lombok.extern.slf4j.Slf4j;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@Component
public class DataBaseAuthenticateComponent extends AuthenticateBase {

    public static String QR_PREFIX =
            "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";

    public static String APP_NAME = "MAIN_AMB_DEV";

    public static String USER_MAIL = "amorales@c20.dev";

    static public void main(String[] args) throws UnsupportedEncodingException {
        //System.out.println(Base32.random());
        String rnd = "MSWBO2PJ57X6NQEV";
        String qrURL =QR_PREFIX + URLEncoder.encode(String.format(
                        "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                        APP_NAME, USER_MAIL, rnd, APP_NAME),
                "UTF-8");

        System.out.println(qrURL);
    }

    @Autowired
    StorageRepository storageRepository;

    @Autowired
    ValueRepository valueRepository;

    @Value("${users-dir}")
    String usersDir;

    @Value("${sys.secret}")
    String secret;

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    @Override
    public AuthenticateBase authenticate() {
        this.setAuthenticated(false);
        /*
        Storage storage = storageRepository.getFile(usersDir+this.getUser());
        if( storage == null ) {
            return this;
        }
*/
        log.info(getAuhenticatedUser().getOtp());
        Totp totp = new Totp("MSWBO2PJ57X6NQEV");
        if (!isValidLong(this.getAuthenticatedUser().getOtp()) ) {
            log.info( "BadCredentialsException( Invalid verfication type code )");
            return this;

        }
        if (!totp.verify(this.getAuthenticatedUser().getOtp())) {
            log.info( "BadCredentialsException( Invalid verfication code )");
            return this;

        }
        this.setAuthenticated(true);
/*
        dev.c20.workflow.storage.entities.adds.Value value = valueRepository.getByName(storage,"password");

        if( value == null ) {
            return this;
        }

        if(StringUtils.encrypt(this.getPass(),secret).equals(value.getValue())) {
            this.setAuthenticated(true);
            return this;
        }
        */

        return this;
    }

}
