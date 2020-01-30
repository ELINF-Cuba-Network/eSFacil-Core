package cu.vlired.esFacilCore.components;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class I18n {

    final
    MessageSource messageSource;

    public I18n(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String t(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    public String t(String key, Object[] objects) {
        return messageSource.getMessage(key, objects, LocaleContextHolder.getLocale());
    }
}
