package dev.c20.workflow.auth.services;

import dev.c20.workflow.commons.tools.StringUtils;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Perm;
import dev.c20.workflow.storage.entities.adds.ProtectedValue;
import dev.c20.workflow.storage.repositories.PermRepository;
import dev.c20.workflow.storage.repositories.ProtectedValueRepository;
import dev.c20.workflow.storage.repositories.StorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class DataBaseAuthenticateComponent extends AuthenticateBase {

    public static String QR_PREFIX =
            "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";

    public static String APP_NAME = "MAIN_AMB_DEV";

    public static String USER_MAIL = "amorales@c20.dev";
    public static final String EMAIL_CHECK = "^(.+)@(.+)$";

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
    ProtectedValueRepository protectedValueRepository;

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
        Pattern pattern = Pattern.compile(EMAIL_CHECK);
        Matcher matcher = pattern.matcher(this.getAuthenticatedUser().getUser());

        Storage storage = null;
        if( this.getAuthenticatedUser().getUser() != null && !matcher.matches()) {
            storage = storageRepository.getFile(usersDir + this.getAuhenticatedUser().getUser());
        } else {
            List<dev.c20.workflow.storage.entities.adds.Value> emails = protectedValueRepository.getFindByNameValue( "email", this.getAuthenticatedUser().getUser());
            if( emails.size() != 1 ) {
                hasError = -100; // no existe el correo
                return this;
            }
            storage = emails.get(0).getParent();
            this.getAuthenticatedUser().setUser(storage.getName());
            this.getAuthenticatedUser().setEmail(emails.get(0).getValue());
        }

        if( storage == null ) {
            hasError = -200; // no existe el usuario
            return this;
        }

        List<ProtectedValue> properties = protectedValueRepository.getAllProperties(storage);

        if( getAuhenticatedUser().getPassword() != null ) {
            ProtectedValue password = null;
            for(ProtectedValue property : properties ) {
                if( property.getName().equals("password")) {
                    password = property;
                    break;
                }
            }
            if( password == null ) {
                hasError = -300; // no tiene password
                return this;
            }

            if( !password.getValue().equals(StringUtils.encrypt(getAuhenticatedUser().getPassword(),secret))) {
                hasError = -400; // el password no corresponde
                return this;
            }

        } else if( getAuhenticatedUser().getOtp() != null ) {
            ProtectedValue random = null;
            for(ProtectedValue property : properties ) {
                if( property.getName().equals("random")) {
                    random = property;
                    break;
                }
            }
            if( random == null ) {
                hasError = -500; // no tiene configurado el OTP
                return this;
            }


            log.info(getAuhenticatedUser().getOtp());
            Totp totp = new Totp(random.getValue());
            if (!isValidLong(this.getAuthenticatedUser().getOtp())) {
                log.info("BadCredentialsException( Invalid verification type code )");
                hasError = -600; // OTP Random no es correcto
                return this;

            }
            if (!totp.verify(this.getAuthenticatedUser().getOtp())) {
                log.info("BadCredentialsException( Invalid verification code )");
                hasError = -700; // OTP incorrecto
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

        for(ProtectedValue property : properties ) {
            if( property.getName().equals("email") ) {
                this.getAuthenticatedUser().setEmail(property.getValue());
            }
        }
        this.getAuthenticatedUser().setName(storage.getDescription());
        this.getAuthenticatedUser().setPassword("*********");
        this.getAuthenticatedUser().setOtp(null);
        this.setAuthenticated(true);

        return this;
    }

}
