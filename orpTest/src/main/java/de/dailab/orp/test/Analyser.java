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

package de.dailab.orp.test;

import java.util.function.Consumer;

/**
 * A functional framework to analyse testing data,
 * especially responses to recomendation request.
 *
 * @author Sebastian Werner
 */
public interface Analyser extends Consumer<Long> {
    public String getHeader(String key);
    public String getResult();
    public void clear();
    public boolean inline();
}
