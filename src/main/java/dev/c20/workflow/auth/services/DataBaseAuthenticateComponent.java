package dev.c20.workflow.auth.services;

import dev.c20.workflow.commons.tools.StringUtils;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Perm;
import dev.c20.workflow.storage.repositories.PermRepository;
import dev.c20.workflow.storage.repositories.StorageRepository;
import dev.c20.workflow.storage.repositories.ValueRepository;
import lombok.extern.slf4j.Slf4j;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

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

    @Autowired
    PermRepository permRepository;

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

        Storage storage = storageRepository.getFile(usersDir+this.getAuhenticatedUser().getUser());
        if( storage == null ) {
            return this;
        }

        List<dev.c20.workflow.storage.entities.adds.Value> properties = valueRepository.getAllProperties(storage);

        if( getAuhenticatedUser().getPassword() != null ) {
            dev.c20.workflow.storage.entities.adds.Value password = null;
            for(dev.c20.workflow.storage.entities.adds.Value property : properties ) {
                if( property.getName().equals("password")) {
                    password = property;
                    break;
                }
            }
            if( password == null ) {
                return this;
            }

            if( !password.getValue().equals(StringUtils.encrypt(getAuhenticatedUser().getPassword(),secret))) {
                return this;
            }

        } else if( getAuhenticatedUser().getOtp() != null ) {
            dev.c20.workflow.storage.entities.adds.Value random = null;
            for(dev.c20.workflow.storage.entities.adds.Value property : properties ) {
                if( property.getName().equals("random")) {
                    random = property;
                    break;
                }
            }
            if( random == null ) {
                return this;
            }


            log.info(getAuhenticatedUser().getOtp());
            Totp totp = new Totp(random.getValue());
            if (!isValidLong(this.getAuthenticatedUser().getOtp())) {
                log.info("BadCredentialsException( Invalid verification type code )");
                return this;

            }
            if (!totp.verify(this.getAuthenticatedUser().getOtp())) {
                log.info("BadCredentialsException( Invalid verification code )");
                return this;

            }
        } else {
            return this;
        }

        List<Perm> perms = permRepository.getAll(storage);
        String roles = "";
        for( Perm perm : perms ) {
            roles += perm.getUser() + ",";
        }
        if( !roles.isEmpty() ) {
            roles = roles.substring(0, roles.length() - 2);
        }
        this.getAuthenticatedUser().setRoles(roles);

        for(dev.c20.workflow.storage.entities.adds.Value property : properties ) {
            if( property.getName().equals("email") ) {
                this.getAuthenticatedUser().setEmail(property.getValue());
            }
        }
        this.getAuthenticatedUser().setName(storage.getDescription());
        this.getAuthenticatedUser().setPassword("*********");
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
