/*
 * mhpalista Copyright (C) 2014 Sebastian Werner
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


import java.util.Set;

/**
 * This interface specifies a Graph
 * @param <K>
 */
public interface Graph<K> extends Iterable<K> {

    public void addListener(Listener<K> graphListener);
    public void removeListener(Listener<K> graphListener);


    /**
     * adds a node to this graph
     * @param node
     */
    public void add(K node);

    /**
     * removes a node form this graph
     * @param node
     */
    public void remove(K node);

    /**
     * sets a bi-directional edge between to nodes
     * @param left
     * @param right
     * @throws java.lang.IllegalAccessException if one of the nodes dose not exists in this graph
     */
    public void connect(K left,K right);

    /**
     * sets a directional edge between to nodes from left to right
     * @param left
     * @param right
     * @throws java.lang.IllegalAccessException if one of the nodes dose not exists in this graph
     */
    public void addEdge(K left,K right);

    /**
     * yields the degree of connection between to nodes,
     * if not stated differently in the implementation of this function
     *  a degree of 0 means that no edges elitists
     *  a degree greater thant 0 means there elitists a edge between the nodes
     * this function only tests for directional edges.
     *
     * (isConnected(left,right)*isConnected(right,left) > 0) tests for bi-directional edges between left and right
     *
     * @param left
     * @param right
     * @return
     */
    public float isConnected(K left,K right);

    /**
     * yields the degree of the given node,
     * if not stated differently in the implementation of this function
     * the degree refers to the number of edges that this edge connects to.
     * a degree less than 0 means this node is not part of this graph.
     *
     * @param node
     * @return
     */
    public float getNodeDegree(K node);

    /**
     * yields a set of nodes that a connected to the specified node
     * @param node
     * @return
     */
    public Set<K> getNeighbors(K node);

    /**
     * checks if a this graph contains the specified node
     * @param item
     * @return
     */
    public boolean contains(K item);

    /**
     * yields the number of nodes in this graph
     * @return
     */
    public int size();

    /**
     * This Class functions as a Event-Listener for events on this Graph
     * @param <K>
     */
    public interface Listener<K>{
        public void notifyExpansion(K node);
        public void notifyReduction(K node);
        public void notifyConnection(K left,K right);
    }

}
