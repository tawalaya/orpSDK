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

package de.dailab.orp.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A BalletBox describes a collection of elements,
 * where elements can be added and removed.
 *
 * It is possible and even necessary that the same element
 * can be added to the BalletBox multiple times. Each time
 * a already added element is insert into the box counts as
 * a vote.
 *
 * A sample method provides the a view of a subset of all elements.
 * where the element whit the most votes is the most likely element to
 * be part of the sample and so on.
 *
 * @author Sebastian Werner
 */
public interface BalletBox<E> {

    /**
     * adding an element into the collection
     * @param id
     */
    public void add(E id);

    /**
     * calls add for all elements of ids
     * @param ids
     */
    public void add(Collection<E> ids);

    /**
     * removes a element from the collection
     * @param id
     */
    public void remove(E id);

    public void remove(Collection<E> ids);

    /**
     * generates a view of a subset of elements with in this collection.
     *
     * The element that has been added the most numbers of times into this collection
     * has the hightes probability of being part of the returned sample.
     * The sample dose not need to be ordered and dose not need to guaranty that
     * that the element with the most votes is part of the returned subset.
     * @param size
     * @return
     */
    public ArrayList<E> sample(int size);

    /**
     * generator function to create a object of the same type,
     * @param size reserved size of the new collection
     * @return
     */
    public BalletBox<E> getInstance(int size);

    /**
     * number of unique elements in the bix
     * @return
     */
    int size();
}
