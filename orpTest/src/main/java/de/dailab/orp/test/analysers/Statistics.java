/*
 * orpTest Copyright (C) 2014 Sebastian Werner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU LesserGeneral Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.dailab.orp.test.analysers;

import de.dailab.orp.test.Analyser;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Consumer that derives the mean, max,min and standard deviation of all consumed values
 */
public class Statistics implements Analyser {

    ArrayList<Long> data = new ArrayList<>();

    @Override
    public String getHeader(String key) {
        return key+";mean;min;max;σ;";
    }

    @Override
    public String getResult() {
        double sum = 0,min = Double.MAX_VALUE,max = Double.MIN_VALUE;
        for(long l:data){
            sum += l;
            min = Math.min(min,l);
            max = Math.max(max,l);
        }
        double mean = sum/data.size();

        double s = 0;
        for(long l:data){
            s += Math.pow(l-mean,2);
        }
        double standardDeviation = Math.sqrt(((1.0/(data.size()))*s));

        return String.format(Locale.ENGLISH,";%.4f;%.0f;%.0f;%.4f",mean,min,max,standardDeviation);

    }

    @Override
    public void clear() {
        data = new ArrayList<>();
    }

    @Override
    public boolean inline() {
        return true;
    }

    @Override
    public void accept(Long aLong) {
        data.add(aLong);
    }
}
