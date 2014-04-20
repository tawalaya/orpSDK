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
 * This Interface represents the TestCase.
 *
 * Use it to implement your test scenario as needed. For example @see STStressAgent
 *
 * If used as intended, it will be called after the TestCase is setup @see TestServer#setUp and all components are
 * ready.
 *
 * @author Sebastian Werner
 * @version 1.0
 *
 */
public interface TestAgent extends Component{

    public void run(ReportAgent protocol, EvaluationStrategy evaluation);

}
