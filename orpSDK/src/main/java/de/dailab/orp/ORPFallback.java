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

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.HashBasedTable;
import de.dailab.orp.core.ORPMessage;
import de.dailab.orp.core.Recommendation;
import de.dailab.orp.core.Registry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Default Recommender (basic Buffer to return valid recommendation)
 */
public class ORPFallback implements Recommendation.SingeDomain {

    final long[] buffer = new long[8];
    int pointer = 0;

    @Override
    public void setRegistry(Registry registry) {

    }

    @Override
    public void update(long user, long item, ORPMessage values) throws Exception {
        pointer = (pointer+1)%buffer.length;
        buffer[pointer] = item;
    }

    @Override
    public void item(long item, String title, String text, long time, ORPMessage values) throws Exception {
        pointer = (pointer+1)%buffer.length;
        buffer[pointer] = item;
    }

    @Override
    public void impression(long user, long[] items, ORPMessage values) throws Exception {
        for(long item:items){
            pointer = (pointer+1)%buffer.length;
            buffer[pointer] = item;
        }
    }

    @Override
    public ArrayList<Long> recommend(long user, int number, ORPMessage values) throws Exception {
        ArrayList<Long> l = new ArrayList<>();
        int p = pointer;
        for (int i = 0; i < number; i++) {
            p = (p+1)%buffer.length;
            l.add(buffer[p]);
        }
        return l;
    }

    @Override
    public ArrayList<Long> recommend(long user, long item, int number, ORPMessage values) throws Exception {
        update(user,item,values);
        return recommend(user,number,values);
    }

    @Override
    public void persist(CSVWriter writer) throws IOException {}

    @Override
    public void reload(CSVReader reader) throws IOException {}

    @Override
    public void reset() {}

    @Override
    public void free() {}
}
