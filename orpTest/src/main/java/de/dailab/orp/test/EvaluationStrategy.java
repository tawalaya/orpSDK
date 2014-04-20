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
import java.util.function.BiConsumer;

/**
 * A EvaluationStrategy can be used by the TestAgent to evaluate responses of the system under test.
 *
 *
 * @author Sebastian Werner
 * @version 1.0
 */
public interface EvaluationStrategy extends BiConsumer<Long,Long>, Component{

    /**
     * can be used to differentiate between different steps of a test case
     *
     * It is expected, that sessions are separated within the output file.
     */
    public void session(String sessionID);

    /**
     * stores a recommendation response for a given user.
     *
     * Important: this method is expected to be thread save.
     *
     * @param userID
     * @param recommendations
     *
     */
    public void add(long userID,Iterable<Long> recommendations);

    /**
     * can be called at any time during runtime to evaluate all stored recommendation response
     */
    public void evaluate();

    /**
     * writes the result of all evaluated recommendation response to a file
     * @throws IOException
     */
    public void write() throws IOException;


}
