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

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A Recommendation describes the different
 * interfaces that can be used to create a recommendation system.
 *
 * There are to main sub interfaces of this interface,
 * SingeDomain witch is used do recommend elements for a single domain
 * and MultiDomain witch deals with multiple domains.
 *
 *
 *  @author Sebastian Werner
 */
public interface Recommendation {

    public interface SingeDomain  extends Recommendation {

        void setRegistry(Registry registry);

        /**
         * this functions is called if a user choose an item e.g. clicked on it
         *
         * @param user ID of the user can be 0 if the user could not be identified
         * @param item ID of the item
         * @param values set of addition session de.dailab.mhbc.data like location, browser etc.
         * @throws Exception
         */
        void update(long user, long item, ORPMessage values) throws Exception;

        /**
         * this function is called when a new item is published
         *
         * @param item unique identifier of the item
         * @param title title of the item
         * @param text first 200 characters of the content
         * @param time post de.dailab.mhbc.data
         * @param values set of addition session de.dailab.mhbc.data
         * @throws Exception
         */
        void item(long item, String title, String text, long time, ORPMessage values) throws Exception;

        /**
         * this function is called if a user visits a page. the items[] contains all article that where linked to
         * on the page
         *
         * @param user ID of the user can be 0 if the user could not be identified
         * @param items all article that where linked to on the page
         * @param values  set of addition session de.dailab.mhbc.data
         * @throws Exception
         */
        void impression(long user, long items[], ORPMessage values) throws Exception;

        /**
         * recommend a set of articles for a given user
         *
         * @param user ID of a user
         * @param number number of articles that should be shown
         * @param values set of addition session de.dailab.mhbc.data like location, browser etc.
         * @return
         * @throws Exception
         */
        ArrayList<Long> recommend(long user, int number, ORPMessage values) throws Exception;

        /**
         * recommend a set of articles for a given user
         *
         * @param user ID of a user
         * @param number number of articles that should be shown
         * @param values set of addition session de.dailab.mhbc.data like location, browser etc.
         * @return
         * @throws Exception
         */
        ArrayList<Long> recommend(long user, long item, int number, ORPMessage values) throws Exception;

        void persist(CSVWriter writer) throws IOException;

        void reload(CSVReader reader) throws IOException;

    }

    public interface ObservableSingleDomain extends Recommendation.SingeDomain{
        /**
         * the number of times the update function has been called
         * @return
         */
        int getUpdateCount();

        /**
         * the number of times the item function has been called
         * @return
         */
        int getItemCount();

        /**
         * the number of times the recommend function has been called
         * @return
         */
        int getRequestCount();
    }

    /**
     * a wrapper interface for the dailab challenge to make recommendations for multiple domains
     */
    public interface MultiDomain extends Recommendation,Observable{
        /**
         * @see Recommendation.SingeDomain#update(long, long, ORPMessage)
         * @param domain unique Domain ID
         * @param userID
         * @param itemID
         * @param values
         * @throws Exception
         */
        void update(long domain, long userID, long itemID, ORPMessage values) throws Exception;

        /**
         * @see Recommendation.SingeDomain#item(long, String, String, long, ORPMessage)
         * @param id
         * @param domain unique Domain ID
         * @param title
         * @param text
         * @param time
         * @param values
         * @throws Exception
         */
        void item(long id, long domain, String title, String text, long time, ORPMessage values) throws Exception;

        /**
         * @see Recommendation.SingeDomain#impression(long, long[], ORPMessage)
         * @param user
         * @param domain unique Domain ID
         * @param items
         * @param values
         * @throws Exception
         */
        void impression(long user, long domain, long items[], ORPMessage values) throws Exception;

        /**
         * @see Recommendation.SingeDomain#recommend(long, int, ORPMessage)
         * @param domain unique Domain ID
         * @param userId
         * @param number
         * @param values
         * @return
         * @throws Exception
         */
        ArrayList<Long> recommend(long domain, long userId, int number, ORPMessage values) throws Exception;

        /**
         * @see Recommendation.SingeDomain#recommend(long, int, ORPMessage)
         * @param domain unique Domain ID
         * @param userId
         * @param number
         * @param values
         * @return
         * @throws Exception
         */
        ArrayList<Long> recommend(long domain, long userId, long itemID, int number, ORPMessage values) throws Exception;

        void persist(File dir) throws IOException;

        void reload(File dir) throws IOException;
    }

    /**
     * resets the recommender to free memory
     */
    void reset();

    void free();
}
