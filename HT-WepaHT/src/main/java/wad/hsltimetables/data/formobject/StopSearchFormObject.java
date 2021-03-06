package wad.hsltimetables.data.formobject;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class StopSearchFormObject {
    @Length(min = 3, max=40)
    @Pattern(regexp = "(?ui)([a-z0-9 ´`'\\(\\)-:,.]|[äöå])*", message="Only alphanumeric characters and some special characters")
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
