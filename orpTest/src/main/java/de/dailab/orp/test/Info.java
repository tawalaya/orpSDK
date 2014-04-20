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

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * This annotation should be used by all implementations
 * of the following interfaces:
 * <ul>
 *     <li>Analyser</li>
 *     <li>Component</li>
 *     <li>ContentProvider</li>
 *     <li>EvaluationStrategy</li>
 *     <li>ReportAgent</li>
 *     <li>TestAgent</li>
 * </ul>
 *
 * If this annotation is present, it can be used to describe all
 * parameter of the prepare function witch is otherwise not possible.
 *
 */
public @interface Info {
    //lists the parameter, that the  prepare function of Component expects
    String parameter();
    String description() default "";

}
