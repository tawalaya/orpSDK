/*
 * mhpalista Copyright (C) 2014 Sebastian Werner
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

package de.dailab.orp;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.HashBasedTable;
import de.dailab.orp.core.ORPMessage;
import de.dailab.orp.core.Observable;
import de.dailab.orp.core.Recommendation;
import de.dailab.orp.data.HashRegistry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Generic Multi-Domain Recommender that uses a provides SingleDomain prototype object
 * to generate recommendations for each encountered domain
 */
public class MDGeneric implements Recommendation.MultiDomain,Observable{

    HashBasedTable<Long,String,Integer> statistics = HashBasedTable.create();

    HashMap<Long,Recommendation.SingeDomain> recomender;


    Recommendation.SingeDomain prototype;

    public MDGeneric(Recommendation.SingeDomain prototype){
        this.prototype = prototype;
        recomender = new HashMap<>();
    }

    private SingeDomain get(long domain){
        if(!recomender.containsKey(domain)){
            try {
                recomender.put(domain,prototype.getClass().newInstance());
                recomender.get(domain).setRegistry(new HashRegistry(domain));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return recomender.get(domain);
    }


    @Override
    public void update(long domain, long userID, long itemID, ORPMessage values) throws Exception {
        get(domain).update(userID,itemID,values);
    }

    @Override
    public void item(long id, long domain, String title, String text, long time, ORPMessage values) throws Exception {
        get(domain).item(id, title, text, time, values);
    }

    @Override
    public void impression(long user, long domain, long[] items, ORPMessage values) throws Exception {
        get(domain).impression(user, items, values);
    }

    @Override
    public ArrayList<Long> recommend(long domain, long userId, int number, ORPMessage values) throws Exception {
        return get(domain).recommend(userId,number,values);
    }

    @Override
    public ArrayList<Long> recommend(long domain, long userId, long itemID, int number, ORPMessage values) throws Exception {
        return get(domain).recommend(userId,itemID,number,values);
    }

    @Override
    public void persist(File dir) throws IOException {
        for(long domain:recomender.keySet()){
            File f = new File(dir.getAbsolutePath()+File.separator+Long.toString(domain)+".csv");
            CSVWriter writer = new CSVWriter(new FileWriter(f));
            SingeDomain sd = recomender.get(domain);
            sd.persist(writer);
            writer.flush();
            writer.close();
        }
    }

    @Override
    public void reload(File dir)  throws IOException {

    }

    long latestReset = System.currentTimeMillis();

    @Override
    public void reset() {
        latestReset = System.currentTimeMillis();
        for(SingeDomain recom : recomender.values()){
            recom.reset();
        }
        statistics.clear();
    }

    @Override
    public HashBasedTable getStatisticPerDomain() {
        return statistics;
    }

    @Override
    public float getAverageResponseTime() {
        return 0;
    }


    @Override
    public long latestReset() {
        return latestReset;
    }

    @Override
    public String toString() {
        return "MDGeneric{prototype=" + prototype + '}';
    }

    @Override
    public void free() {}
}
