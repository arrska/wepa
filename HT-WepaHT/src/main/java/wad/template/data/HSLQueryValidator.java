package wad.template.data;

import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class HSLQueryValidator implements QueryValidator {
    private Pattern pattern;
    
    //TODO: ne ääkköset
    private static final String queryPattern = "^[a-z0-9 åäö]{3,40}$";
    //private static final String queryPattern = "^[a-z0-9 \\p{L}]{3,40}$";

    @PostConstruct
    public void init() {
        this.pattern = Pattern.compile(queryPattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    }
    
    @Override
    public boolean validate(String query) {
        return (pattern.matcher(query).matches());
    }
}
