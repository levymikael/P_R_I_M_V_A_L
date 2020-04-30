package com.evalutel.primval_desktop.General;

import com.badlogic.gdx.scenes.scene2d.ui.Table;


public class TableUtils
{
    /**
     * Sets default table spacing for VisUI skin. Uses values from current skin {@link Sizes} class obtained from {@link VisUI#getSizes()}.
     */
    public static void setSpacingDefaults(Table table)
    {
        Sizes sizes = VisUI.getSizes();
        if (sizes.spacingTop != 0) table.defaults().spaceTop(sizes.spacingTop);
        if (sizes.spacingBottom != 0) table.defaults().spaceBottom(sizes.spacingBottom);
        if (sizes.spacingRight != 0) table.defaults().spaceRight(sizes.spacingRight);
        if (sizes.spacingLeft != 0) table.defaults().spaceLeft(sizes.spacingLeft);
    }
}