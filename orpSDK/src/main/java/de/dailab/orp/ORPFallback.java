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

package de.dailab.orp;

import com.google.common.collect.HashBasedTable;
import de.dailab.orp.core.ORPMessage;
import de.dailab.orp.core.Recommendation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Default Recommender (basic Buffer to return valid recommendation)
 */
public class ORPFallback implements Recommendation.MultiDomain {

    final int defaultSize;

    final LinkedHashMap<Long,Long[]>  domains = new LinkedHashMap<>();
    final LinkedHashMap<Long,Integer> pointer = new LinkedHashMap<>();

    public ORPFallback(int defaultSize){
        this.defaultSize = defaultSize;
    }

    private Long[] get(Long domain){
        domains.putIfAbsent(domain,new Long[defaultSize]);

        return domains.get(domain);
    }

    private void put(Long domain,long item){
        domains.putIfAbsent(domain,new Long[defaultSize]);
        pointer.putIfAbsent(domain,0);

        int pid = ((pointer.get(domain)+1)%defaultSize);

        pointer.replace(domain,pointer.get(domain),pid);
        domains.get(domain)[pid] = item;
    }

    @Override
    public void update(long domain, long userID, long itemID, ORPMessage values) throws Exception {
        put(domain,itemID);
    }

    @Override
    public void item(long id, long domain, String title, String text, long time, ORPMessage values) throws Exception {
        put(domain,id);
    }

    @Override
    public void impression(long user, long domain, long[] items, ORPMessage values) throws Exception {
        for(long item : items){
            put(domain,item);
        }
    }

    @Override
    public ArrayList<Long> recommend(long domain, long userId, int number, ORPMessage values) throws Exception {
        Long[] data = get(domain);
        ArrayList<Long> recom = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            int pid = (pointer.getOrDefault(domain,0)+i)%defaultSize;
            recom.add(data[pid]);
        }
        return recom;
    }

    @Override
    public ArrayList<Long> recommend(long domain, long userId, long itemID, int number, ORPMessage values) throws Exception {
        update(domain,userId,itemID,values);
        return recommend(domain,userId,number,values);
    }

    @Override
    public void persist(File dir) throws IOException {

    }

    @Override
    public void reload(File dir) throws IOException {

    }

    @Override
    public HashBasedTable<Long, String, Integer> getStatisticPerDomain() {
        return HashBasedTable.create();
    }

    @Override
    public float getAverageResponseTime() {
        return 0;
    }

    @Override
    public long latestReset() {
        return 0;
    }

    @Override
    public void reset() {

    }

    @Override
    public void free() {

    }
}
