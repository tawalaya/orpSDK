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

/**
 * A ConfigProvider describes the scenario of this test, it is used by most components of this framework
 * to set up the test. It contains all relevant classes to run the test as well as the address to the
 * system under test.
 *
 * @author Sebastian Werner
 */
public interface ConfigProvider  {
    public TestAgent getTestAgent();

    public ReportAgent getReport();

    public String getClientAddress();

    public int getClientPort();

    public ContentProvider getImpressionProvider();
    public ContentProvider getItemProvider();
    public ContentProvider getRequestProvider();

    public EvaluationStrategy getEvaluationStrategy();

    public String[] getTestOptions();

    public String[] getReportOptions();

    public String[] getEvaluationOptions();

    public String[] getImpressionOptions();

    public String[] getItemOptions();

    public String[] getRequestOptions();


    public final int silent = 0x0;
    public final int verbose = 0x1;
    public final int veryVerbose = 0x2;

    /**
     * verboseness indicates how much log information should be
     * printed to system.out
     * @return
     */
    public int getVerboseness();
}
