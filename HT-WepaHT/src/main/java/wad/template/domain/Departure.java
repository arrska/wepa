package wad.template.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

public class Departure {
    private String lineCode;
    
    @JsonIgnore
    private Line line;
    
    @JsonProperty("time")
    private String time;
    
    @JsonProperty("date")
    private String date;

    public Departure() {
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
    
    @JsonProperty("linecode")
    public String getLineCode() {
        return lineCode;
    }

    @JsonProperty("code")
    public void setLinecode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        if (time.length()==3) {
            time = "0" + time;
        }
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @JsonIgnore
    public Date getDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        
        try {
            return sdf.parse(this.date + this.time);
        } catch (ParseException ex) {
            Logger.getLogger(Departure.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @JsonIgnore
    public Integer getMinutesUntil() {
        return Minutes.minutesBetween(new DateTime(), new DateTime(getDatetime())).getMinutes();
    }
}
