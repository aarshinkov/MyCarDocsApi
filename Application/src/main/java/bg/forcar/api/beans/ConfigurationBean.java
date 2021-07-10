package bg.forcar.api.beans;

import bg.forcar.api.configuration.settings.EmailSettings;
import javax.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
public class ConfigurationBean {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private EmailSettings emailSettings;

//    private String appUrl;
    public ConfigurationBean() {
    }

    @PostConstruct
    private void init() {
        log.debug("Loading configuration params...");
        emailSettings = loadEmailSettings();
//        appUrl = loadAppUrl();
        log.debug("Configuration params loaded.");
    }

//    @Async
//    public String loadAppUrl() {
//        try {
//            String sql = "SELECT value FROM SYS_PARAMS WHERE name = 'APPURL'";
//            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
//
//            if (rs.next()) {
//                log.debug("APPURL = " + rs.getString("value"));
//                return rs.getString("value");
//            }
//        } catch (DataAccessException e) {
//            log.error("Error loading app URL from database!", e);
//        }
//        return null;
//    }
    public EmailSettings getEmailSettings() {
        return emailSettings;
    }

    @Async
    public EmailSettings loadEmailSettings() {
        EmailSettings settings = new EmailSettings();

        try {
            String sql = "SELECT name, value FROM SYS_PARAMS WHERE name LIKE 'MAIL.%'";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);

            while (rs.next()) {
                if ("MAIL.HOST".equalsIgnoreCase(rs.getString("name"))) {
                    settings.setHost(rs.getString("value"));
                    log.debug("MAIL.HOST = " + rs.getString("value"));
                    continue;
                }

                if ("MAIL.PORT".equalsIgnoreCase(rs.getString("name"))) {
                    settings.setPort(rs.getInt("value"));
                    log.debug("MAIL.PORT = " + rs.getString("value"));
                    continue;
                }

                if ("MAIL.PROTOCOL".equalsIgnoreCase(rs.getString("name"))) {
                    settings.setProtocol(rs.getString("value"));
                    log.debug("MAIL.PROTOCOL = " + rs.getString("value"));
                    continue;
                }

                if ("MAIL.SENDER".equalsIgnoreCase(rs.getString("name"))) {
                    settings.setSender(rs.getString("value"));
                    log.debug("MAIL.SENDER = " + rs.getString("value"));
                    continue;
                }

                if ("MAIL.USERNAME".equalsIgnoreCase(rs.getString("name"))) {
                    settings.setUsername(rs.getString("value"));
                    log.debug("MAIL.USERNAME = " + rs.getString("value"));
                    continue;
                }

                if ("MAIL.PASSWORD".equalsIgnoreCase(rs.getString("name"))) {
                    settings.setPassword(rs.getString("value"));
                    log.debug("MAIL.PASSWORD = " + rs.getString("value"));
                    //continue
                }

//                if ("MAIL.DELAY_CHECK".equalsIgnoreCase(rs.getString("name")))
//                {
//                    settings.setDelayCheck(rs.getInt("value"));
//                    log.debug("MAIL.DELAY_CHECK = " + rs.getString("value"));
//                    continue;
//                }
//
//                if ("MAIL.INITIAL_DELAY".equalsIgnoreCase(rs.getString("name")))
//                {
//                    settings.setInitialDelay(rs.getInt("value"));
//                    log.debug("MAIL.INITIAL_DELAY = " + rs.getString("value"));
//                    continue;
//                }
            }
        } catch (DataAccessException e) {
            log.error("Error loading e-mail configuration from database!", e);
        }
        return settings;
    }

//  public String getEnvStyled()
//  {
//    return this.env.equalsIgnoreCase("DEV") ? "Development" : "Production";
//  }
}
