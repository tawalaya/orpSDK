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

package de.dailab.orp.data;

import de.dailab.orp.core.Registry;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * HashRegistry is a implementation of the Registry interface @see Registry
 * witch uses HashMap's as its backend.
 *
 * @author Sebastian Werner
 */
public class HashRegistry implements Registry {

    Map<Long,ConcurrentSkipListSet<Long>> impression = new ConcurrentHashMap<>();
    Map<Long,ConcurrentSkipListSet<Long>> recommendation = new ConcurrentHashMap<>();

    long id;

    @Override
    public long getID() {
        return this.id;
    }

    public HashRegistry(long id){
        this.id = id;
    }

    @Override
    public void impression(long user, long item) {
        if(!impression.containsKey(user)){
            impression.put(user,new ConcurrentSkipListSet<>());
        }
        impression.get(user).add(item);
    }

    @Override
    public void impression(long user, Collection<Long> items) {
        for(long item:items){
            impression(user,item);
        }
    }

    @Override
    public Collection<Long> getImpressions(long user) {
        if(impression.containsKey(user)){
            return impression.get(user);
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public void recommendation(long user, long item) {
        if(!recommendation.containsKey(user)){
            recommendation.put(user,new ConcurrentSkipListSet<>());
        }
        recommendation.get(user).add(item);
    }

    @Override
    public void recommendation(long user, Collection<Long> items) {
        for(long item:items){
            recommendation(user, item);
        }
    }

    @Override
    public void item(long item, String title, String text) {
        //ignore
    }

    @Override
    public Collection<Long> getRecommendations(long user) {
        if(recommendation.containsKey(user)){
            return recommendation.get(user);
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public void reset() {
        recommendation = new ConcurrentHashMap<>();
        impression = new ConcurrentHashMap<>();
    }
}
