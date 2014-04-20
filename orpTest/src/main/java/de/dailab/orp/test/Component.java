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

/**
 * Used for life cycle methods within the framework @see TestServer
 *
 * @author Sebastian Werner
 */
public interface Component {

    /**
     * called prior to the test
     * @param config
     * @throws ConfigurationException
     */
    public void prepare(ConfigProvider config) throws ConfigurationException;

    /**
     * called after prepare.
     * should indicates if the component is configured properly
     * @return
     */
    public boolean validate();

    /**
     * called after the test is finished
     */
    public void free();

}
