/*
 * orpSDK Copyright (C) 2014 Sebastian Werner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.dailab.orp.core;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Java Wrapper Object for ORP Messages
 *
 * @author Sebastian Werner
 */
public class ORPMessage {

    private static final SimpleDateFormat parseDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static long toDate(String date,long defaultValue){
        try {
            date = date.substring(0,18);
            Date d = parseDateTime.parse(date);
            return d.getTime();
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    public static String toDateString(long date){
        Date d;
        if(date > 0){
            d = new Date(date);
        } else {
           d = new Date();
        }
        String s;
        try{
            s = parseDateTime.format(d);
        } catch (Exception e){
            System.out.println(date+":"+d);
            s = "2013-06-01 00:00:00";
        }
        return s;
    }

    long user;
    long item;
    long domain;

    int limit;

    long timeToAction;
    long created_at;

    long browser;
    long isp;
    long os;
    long device;


    String type;
    String text;
    String title;

    String url;

    long[] items;

    private ORPMessage() {

    }

    /**
     * greedy parser function that turns a json string into a ORPMessage object,
     * tries to convert all found information into values.
     *
     * @param _msg
     * @return new ORPMessage
     */
    public static ORPMessage getInstanceFormJson(String _msg){
        ORPMessage m = new ORPMessage();

        JsonObject msg = JsonObject.readFrom(_msg);
        JsonObject simple;

        try{
            simple = msg.get("context").asObject().get("simple").asObject();
            try{
                m.setBrowser(simple.get("4").asLong());
            } catch (Exception e){}
            try{
                m.setIsp(simple.get("5").asLong());
            } catch (Exception e){}
            try{
                m.setOs(simple.get("6").asLong());
            } catch (Exception e){}
            try{
                m.setDevice(simple.get("47").asLong());
            } catch (Exception e){}
            try{
                m.setItem(simple.get("25").asLong());
            } catch (Exception e){}
            try{
                m.setTimeToAction(simple.get("20").asLong());
            } catch (Exception e){}
            try{
                m.setUser(simple.get("57").asLong());
            } catch (Exception e){}
            try{
                m.setDomain(simple.get("27").asLong());
            } catch (Exception e){}
        } catch (Exception e){
        }

        try{
            m.setLimit(msg.get("limit").asInt());
        } catch (Exception e){}

        try{
            m.setDomain(msg.get("domainid").asLong());
        } catch (Exception e){
            try {
                m.setDomain(Long.parseLong(msg.get("domainid").asString()));
            } catch (Exception ee){

            }
        }

        try{
            m.setItem(msg.get("id").asLong());
        } catch (Exception e){
            try {
                m.setItem(Long.parseLong(msg.get("id").asString()));
            } catch (Exception ee){

            }
        }

        try{
            m.setType(msg.get("type").asString());
        } catch (Exception e){}

        try{
            m.setText(msg.get("text").asString());
        } catch (Exception e){}

        try{
            m.setTitle(msg.get("title").asString());
        } catch (Exception e){}

        try{
            m.setCreated_at(toDate(msg.get("created_at").asString(), 0));
        } catch (Exception e){

        }

        try{
            m.setUrl(msg.get("url").asString());
        } catch (Exception e){

        }

        try{
            JsonArray _items = msg.get("recs").asObject().get("ints").asObject().get("3").asArray();
            long[] items = new long[_items.size()];
            for(int i = 0;i<items.length;i++){
                items[i] = _items.get(i).asLong();
            }
            m.setItems(items);
        } catch (Exception e){

        }
        return m;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getItem() {
        return item;
    }

    public void setItem(long item) {
        this.item = item;
    }

    public long getDomain() {
        return domain;
    }

    public void setDomain(long domain) {
        this.domain = domain;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTimeToAction() {
        return timeToAction;
    }

    public void setTimeToAction(long timeToAction) {
        this.timeToAction = timeToAction;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getBrowser() {
        return browser;
    }

    public void setBrowser(long browser) {
        this.browser = browser;
    }

    public long getIsp() {
        return isp;
    }

    public void setIsp(long isp) {
        this.isp = isp;
    }

    public long getOs() {
        return os;
    }

    public void setOs(long os) {
        this.os = os;
    }

    public long getDevice() {
        return device;
    }

    public void setDevice(long device) {
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long[] getItems() {
        return items;
    }

    public void setItems(long[] items) {
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * function returns propperties of the message with the same key as in the json format
     * <a href="http://orp.plista.com/documentation/download">ORP Documentation</a>
     * @deprecated
     * @param key
     * @return
     */
    public String get(String key){
        switch (key){
            case "4":
                return (getBrowser() == 0)?"":""+getBrowser();
            case "5":
                return (getIsp() == 0)?"":""+getIsp();
            case "6":
                return (getOs() == 0)?"":""+getOs();
            case "47":
                return (getDevice() == 0)?"":""+getDevice();
            case "27":
            case "domainid":
                return (getDomain() == 0)?"":""+getDomain();
            case "57":
                return (getUser() == 0)?"":""+getUser();
            case "25":
            case "id":
                return (getItem() == 0)?"":""+getItem();
            case "20":
                return (getTimeToAction() == 0)?"":""+getTimeToAction();
            case "limit":
                return (getLimit() == 0)?"":""+getLimit();
            case "type":
                return getType();
            case "title":
                return getTitle();
            case "text":
                return getText();
            case "3":
                return (getItems() == null)?"[]":Arrays.toString(getItems());
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "ORPMessage{" +
                "user=" + user +
                ", item=" + item +
                ", domain=" + domain +
                ", limit=" + limit +
                ", timeToAction=" + timeToAction +
                ", created_at=" + created_at +
                ", browser=" + browser +
                ", isp=" + isp +
                ", os=" + os +
                ", device=" + device +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                ", items=" + Arrays.toString(items) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ORPMessage that = (ORPMessage) o;

        if (browser != that.browser) return false;
        if (created_at != that.created_at) return false;
        if (device != that.device) return false;
        if (domain != that.domain) return false;
        if (isp != that.isp) return false;
        if (item != that.item) return false;
        if (limit != that.limit) return false;
        if (os != that.os) return false;
        if (timeToAction != that.timeToAction) return false;
        if (user != that.user) return false;
        if (!Arrays.equals(items, that.items)) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (user ^ (user >>> 32));
        result = 31 * result + (int) (item ^ (item >>> 32));
        result = 31 * result + (int) (domain ^ (domain >>> 32));
        result = 31 * result + limit;
        result = 31 * result + (int) (timeToAction ^ (timeToAction >>> 32));
        result = 31 * result + (int) (created_at ^ (created_at >>> 32));
        result = 31 * result + (int) (browser ^ (browser >>> 32));
        result = 31 * result + (int) (isp ^ (isp >>> 32));
        result = 31 * result + (int) (os ^ (os >>> 32));
        result = 31 * result + (int) (device ^ (device >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (items != null ? Arrays.hashCode(items) : 0);
        return result;
    }
}
