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

import java.util.Iterator;

/**
 * A ContentProvider is used by the TestAgent and other parts of this framework
 * to get JSON messages that can be send to the system under test.
 * The JSON messages should be conform to the standards of the orp challenge.
 *
 * see http://orp.dailab.com/documentation/download
 *
 * @author Sebastian Werner
 * @version 1.0
 */
public interface ContentProvider extends Iterable<String>{


    /**
     * returns a Iterator over the next number messages
     * @param number
     * @return
     */
    public Iterator<String> next(int number);

    /**
     * returns a iterator over messages in the specified window
     * @param start
     * @param size
     * @return
     */
    public Iterator<String> next(int start,int size);

    public void prepare(String ... options) throws ConfigurationException;

    public void free();

    /**
     *
     */
    public void reset();

    /**
     * is called to indicated that the next number of messages should be skipped
     * @param number
     */
    public void forward(long number);

    /**
     * @return the current number of read messages
     */
    public long position();
}
