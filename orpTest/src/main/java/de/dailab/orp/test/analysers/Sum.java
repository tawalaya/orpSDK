/*
 * orpTest Copyright (C) 2014 Sebastian Werner
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

package de.dailab.orp.test.analysers;

import de.dailab.orp.test.Analyser;

/**
 * Consumer that calculates the sum of all consumed values
 */
public class Sum implements Analyser {
    long sum = 0;

    @Override
    public String getHeader(String key) {
        return key+";";
    }

    @Override
    public String getResult() {
        return String.format("%d",sum);
    }

    @Override
    public void clear() {
        sum = 0;
    }

    @Override
    public boolean inline() {
        return true;
    }

    @Override
    public void accept(Long aLong) {
        sum+=aLong;
    }
}
