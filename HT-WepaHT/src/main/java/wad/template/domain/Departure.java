package wad.template.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Departure {
    @JsonProperty("code")
    private String linecode;
    
    @JsonProperty("time")
    private String time;
    
    @JsonProperty("date")
    private String date;

    public Departure() {
    }
    
    public String getLinecode() {
        return linecode;
    }

    public void setLinecode(String linecode) {
        this.linecode = linecode;
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

    public Date getDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        
        try {
            return sdf.parse(this.date + this.time);
        } catch (ParseException ex) {
            Logger.getLogger(Departure.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    
}
