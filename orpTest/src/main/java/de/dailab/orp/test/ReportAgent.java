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

package de.dailab.orp.test;

import java.io.IOException;
import java.util.Collection;

/**
 * A ReportAgents can be used by the TestAgent to log relevant data.
 * For example to record response times for each recommendation Request that is issued as part of
 * a test scenario and calculating the avg response time for this test case.
 *
 * A valid implementation of this interface needs to accept all key values for @see add(String,long)
 * without blocking since most TestAgents will run time critical operations in parallel.
 *
 * @author Sebastian Werner
 * @version 1.0
 */
public interface ReportAgent extends Component{


    /**
     * starts a new recoding frame
     * @param frameID
     */
    public void next(long frameID);

    /**
     * adds a new value to the record
     * @param key
     * @param value
     */
    public void add(String key,long value);
    public void add(String key,Collection<Long> values);

    /**
     * writes all but the current frame as specified in the config
     */
    public void flush() throws IOException;

    /**
     * same as flush but writes the current frame as well
     * (warning dose not have do be thread save)
     */
    public void write()  throws IOException;

}
