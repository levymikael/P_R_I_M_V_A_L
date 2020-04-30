package com.evalutel.primval_desktop.General;

public class Sizes
{
    public float scaleFactor;

    public float spacingTop;
    public float spacingBottom;
    public float spacingRight;
    public float spacingLeft;

    public float buttonBarSpacing;

    public float menuItemIconSize;

    /**
     * Size of focus border. 1 for standard Vis skin. This is used to avoid showing overlapping borders when two widgets
     * have borders (for example button can have it's own focus border which without this padding would overlap with menu border)
     */
    public float borderSize;

    public float spinnerButtonHeight;
    public float spinnerFieldSize;

    public float fileChooserViewModeBigIconsSize;
    public float fileChooserViewModeMediumIconsSize;
    public float fileChooserViewModeSmallIconsSize;
    public float fileChooserViewModeListWidthSize;

    public Sizes()
    {
    }

    public Sizes(Sizes other)
    {
        this.scaleFactor = other.scaleFactor;
        this.spacingTop = other.spacingTop;
        this.spacingBottom = other.spacingBottom;
        this.spacingRight = other.spacingRight;
        this.spacingLeft = other.spacingLeft;
        this.buttonBarSpacing = other.buttonBarSpacing;
        this.menuItemIconSize = other.menuItemIconSize;
        this.borderSize = other.borderSize;
        this.spinnerButtonHeight = other.spinnerButtonHeight;
        this.spinnerFieldSize = other.spinnerFieldSize;
        this.fileChooserViewModeBigIconsSize = other.fileChooserViewModeBigIconsSize;
        this.fileChooserViewModeMediumIconsSize = other.fileChooserViewModeMediumIconsSize;
        this.fileChooserViewModeSmallIconsSize = other.fileChooserViewModeSmallIconsSize;
        this.fileChooserViewModeListWidthSize = other.fileChooserViewModeListWidthSize;
    }
}
