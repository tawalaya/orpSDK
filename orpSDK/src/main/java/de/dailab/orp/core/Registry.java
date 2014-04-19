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

package de.dailab.orp.core;

import java.util.Collection;

/**
 * This interface describes a set of functions to
 * globally store information used in recommendation algorithms
 * mostly user impressions and recommendations
 */
public interface Registry {

    public long getID();

    public void impression(long user, long item);
    public void impression(long user, Collection<Long> items);

    public Collection<Long> getImpressions(long user);

    public void recommendation(long user, long item);
    public void recommendation(long user, Collection<Long> items);

    public void item(long item, String title, String text);

    public Collection<Long> getRecommendations(long user);

    /**
     * clears all associated de.dailab.mhbc.data structures (to free memory)
     */
    public void reset();
}
