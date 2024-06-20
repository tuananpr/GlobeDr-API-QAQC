package config;

import com.apis.globedr.constant.Text;
import com.apis.globedr.helper.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Link tham khao
 * http://grasshopper.tech/117/
 * <p>
 * There are four types of TableTransformer – TableEntryTransformer, TableRowTransformer, TableCellTransformer, TableTransformer.
 * <p>
 * Transformer Type		Parameter passed to transform()		Usage scenarios
 * TableEntryTransformer		Map<String, String>			    Transform DataTable containing header
 * TableRowTransformer		    List<String>				    Transform DataTable without header
 * TableCellTransformer		String					        Transform a single cell into object
 * TableTransformer		    DataTable				        Transform a whole table
 **/
public class ConfigCucumber {


    private ObjectMapper objectMapper = new ObjectMapper();

    @DefaultParameterTransformer
    public Object defaultParameterTransformer(Object fromValue, Type toValueType) {
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }

    @DefaultDataTableCellTransformer
    public Object defaultDataTableCellTransformer(Object fromValue, Type toValueType) {
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }


    @DefaultDataTableEntryTransformer
    public Object defaultDataTableEntryTransformer(Object fromValue, Type toValueType) {
        LinkedHashMap<String, Object> linkedHashMap = ((LinkedHashMap<String, Object>) fromValue);
        for (Map.Entry<String, Object> entry : linkedHashMap.entrySet()) {
            if (entry.getValue() != null) {
                /*
                 * replace {{key}} by value return from Data.get("key")
                 */
                if (entry.getValue().toString().matches("\\{\\{.*\\}\\}")) {
                    Pattern p = Pattern.compile("\\{\\{(.*?)\\}\\}");
                    Matcher m = p.matcher(entry.getValue().toString());
                    while (m.find()) {
                        entry.setValue(Data.get(m.group(1)));
                    }
                }

                /*
                 * get exactly time date by text
                 */

                if (entry.getValue().toString().matches("((next|prev|nexts|prevs) (\\d+) (day|days|min|mins))|today|yesterday|tomorrow"))
                    entry.setValue(convertStrDateToDate(linkedHashMap.get(entry.getKey())));
            }
        }


        return objectMapper.convertValue(linkedHashMap, objectMapper.constructType(toValueType));
    }


    @DataTableType(replaceWithEmptyString = "[blank]")
    public String stringType(String cell) {
        return cell;
    }


    private String removeAllSpace(String input) {
        return input.replaceAll("\\s", "");
    }

    private Date today() {
        Calendar c = Calendar.getInstance();
        // get offSet timezone
        TimeZone tz = c.getTimeZone();
        int offsetMiliseconds = tz.getOffset(new Date().getTime());
        // convert time to 0+UTC
        c.add(Calendar.MILLISECOND, -offsetMiliseconds);
        return c.getTime();
    }

    private Date tomorrow() {
        return getTime(today(), 1, Calendar.DATE);
    }

    private Date yesterday() {
        return getTime(today(), -1, Calendar.DATE);
    }

    private Date getTime(Date date, int timeTotal, int calendarType) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarType, timeTotal);
        return cal.getTime();
    }


    private String format(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


    private Object convertStrDateToDate(Object oTime) {
        Date result = null;
        if (oTime != null) {
            String time = removeAllSpace(oTime.toString());

            Pattern p = Pattern.compile("((next|nexts|prev|prevs)(\\d+)(day|days|min|mins))|(today|yesterday|tomorrow)");
            Matcher m = p.matcher(time.trim());

            Integer timeTotal = null;
            String timeMatch = null;
            String timeType = null;
            int calendarType;

            while (m.find()) {
                timeMatch = m.group(5) != null ? m.group(5) : m.group(2);
                timeTotal = m.group(3) != null ? Integer.parseInt(m.group(3)) : null;
                timeType = m.group(4);
            }


            switch (timeMatch.toLowerCase()) {
                case "today":
                    result = today();
                    break;
                case "yesterday":
                    result = yesterday();
                    break;
                case "tomorrow":
                    result = tomorrow();
                    break;
                default:
                    calendarType = timeType.matches("day|days|today|yesterday|tomorrow") ? Calendar.DATE : Calendar.MINUTE;
                    timeTotal = timeMatch.matches("next|nexts") ? timeTotal : -timeTotal;
                    result = getTime(today(), timeTotal, calendarType);
            }
        }

        return format(result, Text.FTIME_FULL);
    }
}
