/*
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
package tufts.vue;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class LaunchPresentationTool extends VueSimpleTool
{
    private static JPanel sControlPanel;

    public LaunchPresentationTool() {
        super();
        
    }
    
    @Override
    public boolean handleKeyPressed(java.awt.event.KeyEvent e)  {
        return false;
    }
    
    @Override
    public boolean handleMousePressed(MapMouseEvent e)
    {
            return false;
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        Actions.LaunchPresentation.fire(this, e);
    }


    @Override
    public boolean supportsSelection() { return false; }

    // todo: need selection, but no drag, and click-to-deselect still working
    @Override
    public boolean supportsDraggedSelector(MapMouseEvent e) { return false; }
}