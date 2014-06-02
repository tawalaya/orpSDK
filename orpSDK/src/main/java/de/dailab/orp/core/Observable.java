/*
 * orpSDK Copyright (C) 2014 Sebastian Werner
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

package de.dailab.orp.core;

import com.google.common.collect.HashBasedTable;

/**
 * Interface for exposing statistics information about a recommendation system
 */
public interface Observable {

    /**
     * table of counter
     * domain | counter name | counter value
     * -------------------------------------
     *        |              |
     * @return
     */
    public HashBasedTable<Long,String,Integer> getStatisticPerDomain();

    /**
     * average response time of any action
     * @return
     */
    public float getAverageResponseTime();

    /**
     * time in milliseconds since the last reset @see Recommendation#reset()
     * @return
     */
    public long latestReset();


}
