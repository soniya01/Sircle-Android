package com.snaptech.fitness360amritsar.WebService.Common;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by soniya on 4/2/15.
 */
public class UrlGenerator implements Map {

    HashMap<String ,String > map;
    String subUrl;

    public UrlGenerator(HashMap<String, String> map,String subUrl) {
        this.map = map;
        this.subUrl = subUrl;
    }

    @Override
    public boolean containsKey(Object key) {
        if (this.map.containsKey(key)){
            return true;
        }
        return false;
    }

    @NonNull
    @Override
    public Collection values() {
        return this.map.values();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public void putAll(Map map) {
        this.map.putAll(map);
    }

    @Override
    public Object put(Object key, Object value) {
        return null;
    }

    @NonNull
    @Override
    public Set keySet() {
        return this.map.keySet();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return false;
    }

    @NonNull
    @Override
    public Set<Entry> entrySet() {
        return null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public Object get(Object key) {
        return this.map.get(key);
    }

    @Override
    public  String toString(){
        for (String key : this.map.keySet()) {
            if (this.subUrl.contains(key)){
                int length = key.toString().length() + 2;
                int index = this.subUrl.indexOf(key) - 1;
                this.subUrl = this.subUrl.replace(this.subUrl.substring( index,length+index),this.map.get(key));
            }
        }
        return this.subUrl;
    }
}
