package bg.forcar.api;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * Enables setting the application context
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public class SpringApplicationContext {

    private static ApplicationContext CONTEXT;

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        CONTEXT = context;
    }

    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }
}
