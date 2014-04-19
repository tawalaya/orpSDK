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

package de.dailab.orp;

import com.eclipsesource.json.ParseException;
import de.dailab.orp.core.ORPMessage;
import de.dailab.orp.core.Recommendation;
import de.dailab.orp.data.MessageRecorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * This is a basic implementation of a Server Controller for the ORP using the jetty framework.
 */
public class ORPServiceHandler extends AbstractHandler {
    private final static Logger logger = LogManager.getLogger(ORPServiceHandler.class.getName());

    public static final String COMMAND_RESET        = "reset";
    public static final String COMMAND_BYE          = "bye";
    public static final String COMMAND_REQUEST      = "recommendation_request";
    public static final String COMMAND_ITEM_UPDATE  = "item_update";
    public static final String COMMAND_EVENT        = "event_notification";
    public static final String COMMAND_ERROR        = "error_notification";
    public static final int response_time = 200;

    private final Recommendation.MultiDomain handler;
    private final long MEM_LIMIT;
    private final HashSet<Long> domainWhiteList;

    public ORPServiceHandler(Recommendation.MultiDomain handler) {
        this(handler,null,-1l,false);
    }

    public ORPServiceHandler(Recommendation.MultiDomain handler,
                             final HashSet<Long> whiteList,
                             long memLimit){
        this(handler,whiteList,memLimit,false);
    }

    public ORPServiceHandler(Recommendation.MultiDomain handler,
                             final HashSet<Long> whiteList,
                             long memLimit, boolean logMessages) {
        this.handler = handler;
        domainWhiteList = whiteList;
        this.MEM_LIMIT = memLimit;
        logger.info("using handler:" + handler);

        if(logMessages){
            try{
                messageRecorder  = MessageRecorder.getInstance("message_record.log.gz");
            } catch (IOException e){
                messageRecorder = null;
            }
        } else {
            messageRecorder = null;
        }

    }

    private MessageRecorder messageRecorder;

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long time = System.currentTimeMillis();
        try {
            String typeMessage = "unknown",bodyMessage = null,_response = null;
            if (baseRequest.getMethod().equalsIgnoreCase("POST")) {

                if (baseRequest.getContentLength() > 0) {
                    typeMessage = baseRequest.getParameter("type");
                    bodyMessage = baseRequest.getParameter("body");
                    if (typeMessage == null || bodyMessage == null) {
                        logger.error("bad request!" + baseRequest.getParameterMap());
                        response(null, response, baseRequest);
                    } else {
                        _response = handle(typeMessage, bodyMessage);
                        response(_response, response, baseRequest);
                    }


                } else {
                    logger.warn("Empty Message received");
                    response(null, response, baseRequest);
                }
            } else {
                baseRequest.setHandled(true);
                response.setStatus(HttpServletResponse.SC_OK);
            }
            time = System.currentTimeMillis() - time;
            if(time > response_time){
                if(messageRecorder != null){
                    messageRecorder.write(bodyMessage);
                }
                logger.debug(String.format("[response %s took: %d]",typeMessage, time));
                if(messageRecorder != null){
                    messageRecorder.write(_response);
                }
            }

        } catch (Exception e) {
            logger.error("failed to answer", e);
            response(null, response, baseRequest);
        } catch (OutOfMemoryError e){
            handler.reset();
        }

        if(MEM_LIMIT > 0){
            long free = Runtime.getRuntime().freeMemory();

            if (free < MEM_LIMIT) {
                logger.warn("Low On Memory!");
                handler.reset();
                logger.warn("before:" + free + ", after:" + Runtime.getRuntime().freeMemory());
            }
        }


    }

    private void response(String msg, HttpServletResponse response, Request baseRequest) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        if (msg != null) {
            response.getWriter().println(msg);
        }

        baseRequest.setHandled(true);
    }


    private String handle(String type, String _msg) {
        if(type.equalsIgnoreCase(COMMAND_RESET)){
            this.handler.reset();
            return null;
        }
        ORPMessage msg;
        try{
            msg  = ORPMessage.getInstanceFormJson(_msg);
        } catch (ParseException e){
            logger.error(String.format("%s:%s","Parsing Error",_msg),e);
            return null;
        }

        boolean blackListed = domainWhiteList != null && !domainWhiteList.contains(msg.getDomain()) ;

        switch (type) {
            case COMMAND_ERROR:
                logger.error(_msg);
                break;
            case COMMAND_BYE:
                try {
                    handler.persist(new File("data"));
                    if(messageRecorder != null){
                        messageRecorder.run();
                    }
                    handler.free();
                } catch (IOException e) {
                    logger.catching(e);
                }
                break;
            case COMMAND_REQUEST:
                if (msg.getDomain() == 0) {
                    logger.warn("Bad Domain:" + msg.getDomain()+" "+_msg);
                    return null;
                }
                ArrayList<Long> result;
                if (!blackListed) {
                    try {

                        if (msg.getItem() == 0) {
                            result = handler.recommend(msg.getDomain(), msg.getUser(), msg.getLimit(), msg);
                        } else {
                            result = handler.recommend(msg.getDomain(), msg.getUser(), msg.getItem(), msg.getLimit(), msg);
                        }

                        if (result.size() < msg.getLimit()) {
                            logger.warn(String.format("limit error - domain:%d user:%d - expected: %d got: %d", msg.getDomain(), msg.getUser(), msg.getLimit(), result.size()));
                        }

                        String response = String.format("{\"recs\": {\"ints\": {\"3\":%s }}}", result.toString());
                        return response;
                    } catch (Exception e) {
                        logger.trace("recommendation",e);
                    }
                } else {
                    return String.format("{\"recs\": {\"ints\": {\"3\":%s }}}","[]");
                }
                break;
            case COMMAND_ITEM_UPDATE:
                if (!blackListed) {
                    if (msg.getDomain() == 0 || msg.getItem() == 0) {
                        logger.warn("Bad Message:" + msg);
                        return null;
                    }

                    try {
                        handler.item(msg.getItem(), msg.getDomain(), msg.getTitle(), msg.getTitle(), msg.getCreated_at(), msg);
                    } catch (Exception e) {
                        logger.trace("item_update",e);
                    }
                }
                break;
            case COMMAND_EVENT:
                if (!blackListed) {
                    String etype = msg.getType();
                    if (etype.matches("impression")) {
                        try {
                            if(msg.getItems() == null){
                                logger.info("no items");
                            } else {
                                handler.impression(msg.getUser(), msg.getDomain(), msg.getItems(), msg);
                            }
                        } catch (Exception e) {
                            logger.trace("impression",e);
                        }
                    } else if (etype.matches("click")) {
                        try {
                            handler.impression(msg.getUser(), msg.getDomain(), msg.getItems(), msg);
                        } catch (Exception e) {
                            logger.trace("click",e);
                        }
                    } else if (etype.matches("impression_empty")) {
                        try {
                            if (msg.getItem() != 0) {
                                handler.impression(msg.getUser(), msg.getDomain(), new long[0], msg);
                            }
                        } catch (Exception e) {
                            logger.trace("impression_empty",e);
                        }
                    } else {
                        logger.error("unknown etype: " + etype + " " + _msg);
                    }
                }
                break;
        }
        return null;
    }


}
