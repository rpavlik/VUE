/*
 * TabularLayout.java
 *
 * Created on October 8, 2008, 2:32 PM
 *
* Copyright 2003-2010 Tufts University  Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

/**
 *
 * @author akumar03
 */
package edu.tufts.vue.layout;


import java.util.*;
import tufts.vue.*;
import edu.tufts.vue.dataset.*;

/*
 *  This is similar to circular layout, except nodes that have more link
 * gravitate to the center.
 */


public class ForceLayout extends Layout {
    
    /** Creates a new instance of TabularLayout */
    public ForceLayout() {
    }
    public   LWMap createMap(Dataset ds,String mapName) throws Exception {
        return new LWMap(mapName);
    }
    
    public   void layout(LWSelection selection) throws Exception {
        Map<LWComponent,Integer> repeatMap = new HashMap<>();
        
        double minX =10000;
        double minY = 10000;
        int nodeSize = 100; // we assume node size is 100 which needs to be fixed.
        boolean applyLayout = false;
        int count = 0;
        int total = 0;
        int mod = 4;
        int xAdd = 100;
        int yAdd = 50;
        Iterator<LWComponent> i = selection.iterator();
        
        while (i.hasNext()) {
            LWComponent c = i.next();
            if (c.isManagedLocation())
                continue; 
            if(c instanceof LWNode) {
                LWNode node = (LWNode) c;
                minX = Math.min(node.getLocation().getX(), minX);
                minY = Math.min(node.getLocation().getY(), minY);
                if (!repeatMap.containsKey(node))
                    repeatMap.put(node, 0);
                total++;
            }
            
        }
        // interate to map for links
        i = VUE.getActiveMap().getAllDescendents(LWContainer.ChildKind.PROPER).iterator();
        while (i.hasNext()) {
            LWComponent c = i.next();
            if (c.isManagedLocation())
                continue; 
            if(c instanceof LWLink) {
                applyLayout = true;
                LWLink link = (LWLink)c;
                LWComponent head= link.getHead();
                LWComponent tail = link.getTail();
                if(repeatMap.containsKey( head)) {
                    repeatMap.compute(head, (k, nc) -> nc + 1);
                    
                }
                if(repeatMap.containsKey(tail)) {
                    repeatMap.compute(tail, (k, nc) -> nc + 1);
                }
            }
        }
        if(applyLayout) {
            double size = Math.sqrt(total)* nodeSize;
            double radius = size/2;
            double centerX = minX +radius;
            double centerY = minY +radius;
            i = selection.iterator();
            while (i.hasNext()) {
                LWComponent c = i.next();
                if (c.isManagedLocation())
                    continue; 
                if(c instanceof LWNode) {
                    LWNode node = (LWNode)c;
                    double angle = Math.PI*2 * Math.random();
                    int nc= repeatMap.get(node);
                    double r = radius*Math.pow(1.00-(double)nc/total,2.00);
                    node.setLocation(centerX+r*Math.cos(angle),centerY+r*Math.sin(angle));
//                    System.out.println("Node: "+node.getLabel()+" r:"+r+" angle:"+angle+" nc:"+nc+" total:"+total+" radius:"+radius);
                }
            }
        }
    }
    
}
