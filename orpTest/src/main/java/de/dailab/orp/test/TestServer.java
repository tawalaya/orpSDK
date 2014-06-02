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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class functions as main entry point for all test that this framework provides.
 *
 * Given a Configuration it will set up all necessary elements of the test @see TestServer#setUp()
 * and provide a run method for executing this test. @see TestServer#run
 *
 * @author Sebastian Werner
 * @version 1.0
 *
 */
public class TestServer implements Runnable{

    Logger logger = LogManager.getLogger(TestServer.class);

    private ConfigProvider config;

    private TestAgent agent;
    private EvaluationStrategy evaluation;
    private ReportAgent protocol;
    private boolean verboseness;

    public TestServer(ConfigProvider configProvider){
        config = configProvider;
    }

    private void log(String msg){
        if(verboseness) logger.info(msg);
    }

    public void setUp(){
        if(verboseness){
            logger.info("Veboseness:"+config.getVerboseness());
        }

        ContentProvider item,impression,request;
        try {
            item = config.getItemProvider();
            item.prepare(config.getItemOptions());
            impression = config.getImpressionProvider();
            impression.prepare(config.getImpressionOptions());
            request = config.getRequestProvider();
            request.prepare(config.getRequestOptions());
        } catch (ConfigurationException e) {
            logger.error("could not load all Provider",e);
            throw new ConfigurationException("could not load all Provider: ",e);
        }

        agent = config.getTestAgent();
        agent.prepare(config);
        log("Loaded Agent");

        protocol = config.getReport();
        protocol.prepare(config);
        log("Report Generator is ready");

        evaluation = config.getEvaluationStrategy();
        evaluation.prepare(config);
        log("de.dailab.mhbc.evaluation is ready");

        if(!agent.validate() || !protocol.validate()){
            logger.error("test server configuration is invalid! "+config);
        }


    }

    public void shutdown(){
        log("shutting down");
        try{
            protocol.write();
            evaluation.write();
        } catch (Exception e){
            logger.error("Faild to write Results",e);
        }


        agent.free();
        protocol.free();
        evaluation.free();
        log("done");
    }

    public void run(){
        log("starting agent");
        agent.run(protocol,evaluation);
        log("agent finished");
    }

    public void setVerboseness(boolean verboseness) {
        this.verboseness = verboseness;
    }

    public boolean isVerboseness() {
        return verboseness;
    }
}
