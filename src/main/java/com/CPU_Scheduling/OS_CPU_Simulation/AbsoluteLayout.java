package com.CPU_Scheduling.OS_CPU_Simulation;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.awt.*;

/** AbsoluteLayout is a LayoutManager that works as a 
 * replacement for "null" layout to
* allow placement of components in absolute positions.
*
* @see AbsoluteConstraints
* @version 1.01, Aug 19, 1998
*/
public class AbsoluteLayout implements LayoutManager2, java.io.Serializable {
    /** generated Serialized Version UID */
    static final long serialVersionUID = -1919857869177070440L;

    /** Adds the specified component with the specified name to
    * the layout.
    * @param name the component name
    * @param comp the component to be added
    * Purpose: This method is required by the LayoutManager 
    * interface but is unused in this implementation. Instead, 
    * it throws an IllegalArgumentException.
	Reason: Absolute positioning doesn’t require a name for each component, so this method is irrelevant here
    * 
    */
    public void addLayoutComponent(String name, Component comp) {
        throw new IllegalArgumentException();
    }

    /** Removes the specified component from the layout.
    * @param comp the component to be removed
    */ 
    public void removeLayoutComponent(Component comp) {
        constraints.remove(comp);
    }

    /** Calculates the preferred dimension for the specified
    * panel given the components in the specified parent container.
    * @param parent the component to be laid out
    *
    * @see #minimumLayoutSize
    */
    public Dimension preferredLayoutSize(Container parent) {
        int maxWidth = 0;
        int maxHeight = 0;
        for (java.util.Enumeration e = constraints.keys(); e.hasMoreElements();) {
            Component comp = (Component)e.nextElement();
            AbsoluteConstraints ac = (AbsoluteConstraints)constraints.get(comp);
            Dimension size = comp.getPreferredSize();

            int width = ac.getWidth ();
            if (width == -1) width = size.width;
            int height = ac.getHeight ();
            if (height == -1) height = size.height;

            if (ac.x + width > maxWidth)
                maxWidth = ac.x + width;
            if (ac.y + height > maxHeight)
                maxHeight = ac.y + height;
        }
        return new Dimension (maxWidth, maxHeight);
    }

    /** Calculates the minimum dimension for the specified
    * panel given the components in the specified parent container.
    * @param parent the component to be laid out
    * @see #preferredLayoutSize
    */
    public Dimension minimumLayoutSize(Container parent) {
        int maxWidth = 0;
        int maxHeight = 0;
        for (java.util.Enumeration e = constraints.keys(); e.hasMoreElements();) {
            Component comp = (Component)e.nextElement();
            AbsoluteConstraints ac = (AbsoluteConstraints)constraints.get(comp);

            Dimension size = comp.getMinimumSize();

            int width = ac.getWidth ();
            if (width == -1) width = size.width;
            int height = ac.getHeight ();
            if (height == -1) height = size.height;

            if (ac.x + width > maxWidth)
                maxWidth = ac.x + width;
            if (ac.y + height > maxHeight)
                maxHeight = ac.y + height;
        }
        return new Dimension (maxWidth, maxHeight);
    }

    /** Lays out the container in the specified panel.
    * @param parent the component which needs to be laid out
    * Purpose: This is the core method of the LayoutManager. 
    * It positions each component within the parent container 
    * according to its constraints (absolute x, y coordinates 
    * and size).
	* Reason: It iterates over all components, retrieves 
	* their constraints, and then calls setBounds on each 
	* component to position it based on the absolute coordinates
	*  (ac.x, ac.y) and dimensions (width, height).
    */
    public void layoutContainer(Container parent) {
        for (java.util.Enumeration e = constraints.keys(); e.hasMoreElements();) {
            Component comp = (Component)e.nextElement();
            AbsoluteConstraints ac = (AbsoluteConstraints)constraints.get(comp);
            Dimension size = comp.getPreferredSize();
            int width = ac.getWidth ();
            if (width == -1) width = size.width;
            int height = ac.getHeight ();
            if (height == -1) height = size.height;

            comp.setBounds(ac.x, ac.y, width, height);
        }
    }

    /** Adds the specified component to the layout, using the specified
    * constraint object.
    * @param comp the component to be added
    * @param constr  where/how the component is added to the layout.
    * This method checks if the constraint provided is of type 
    * AbsoluteConstraints (the type that stores the position 
    * and size). 
    * If valid, it stores the component and its constraints 
    * in the constraints map.
    */
    public void addLayoutComponent(Component comp, Object constr) {
        if (!(constr instanceof AbsoluteConstraints))
            throw new IllegalArgumentException();
        constraints.put(comp, constr);
    }

    /** Returns the maximum size of this component.
    * @see java.awt.Component#getMinimumSize()
    * @see java.awt.Component#getPreferredSize()
    * @see LayoutManager
    */
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /** Returns the alignment along the x axis.  This specifies how
    * the component would like to be aligned relative to other
    * components.  The value should be a number between 0 and 1
    * where 0 represents alignment along the origin, 1 is aligned
    * the furthest away from the origin, 0.5 is centered, etc.
    */
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    /** Returns the alignment along the y axis.  This specifies how
    * the component would like to be aligned relative to other
    * components.  The value should be a number between 0 and 1
    * where 0 represents alignment along the origin, 1 is aligned
    * the furthest away from the origin, 0.5 is centered, etc.
    */
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    /** Invalidates the layout, indicating that if the layout manager
    * has cached information it should be discarded.
    */
    public void invalidateLayout(Container target) {
    }

    
    /** A mapping <Component, AbsoluteConstraints>
     * Purpose: A Hashtable that maps components to their AbsoluteConstraints.
	   Behavior:
	   It stores the position and size information for 
	   each component added to the layout. */
    protected java.util.Hashtable constraints = new java.util.Hashtable();
}