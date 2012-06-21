package pl.edu.agh.ui;

import org.springframework.util.Assert;
import org.valkyriercp.binding.form.NewFormObjectAware;
import org.valkyriercp.component.MessagableTabbedPane;
import org.valkyriercp.component.SkipComponentsFocusTraversalPolicy;
import org.valkyriercp.widget.AbstractFocussableWidgetForm;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * @author mateusz
 * @date 21.06.12
 */
public abstract class CustomTabbedForm extends AbstractFocussableWidgetForm implements ChangeListener, NewFormObjectAware {
    private JTabbedPane tabbedPane = null;

    protected JTabbedPane getTabbedPane()
    {
        return tabbedPane;
    }

    protected JComponent createFormControl()
    {
        tabbedPane = new MessagableTabbedPane(SwingConstants.TOP);
        tabbedPane.setFocusTraversalPolicyProvider(true);
        tabbedPane.setFocusTraversalPolicy(SkipComponentsFocusTraversalPolicy.skipJTextComponentTraversalPolicy);
        for (Tab tab : getTabs())
        {
            tab.setParent(tabbedPane);
        }
        tabbedPane.addChangeListener(this);
        return tabbedPane;
    }

    public JComponent getRevertComponent()
    {
        return getRevertCommand().createButton();
    }

    public void setFormObject(Object formObject)
    {
        if (formObject == null)
            selectTab(0);
        super.setFormObject(formObject);
    }

    public void setNewFormObject(Object formObject)
    {
        if (formObject != null)
        {
            super.setFormObject(formObject);
        }
        else
        {
            getNewFormObjectCommand().execute();
        }
        selectTab(0);
    }

    public void selectTab(int tabIndex)
    {
        if ((tabbedPane != null) && (tabbedPane.getTabCount() > tabIndex))
            tabbedPane.setSelectedIndex(tabIndex);
    }

    public void selectTab(Tab tab)
    {
        if (tab.getTabIndex() > 0)
        {
            tabbedPane.setSelectedIndex(tab.getTabIndex());
        }
    }

    protected abstract Tab[] getTabs();

    protected class Tab
    {

        private final String tabId;

        private final String title;

        private final JComponent panel;

        private FocusTraversalPolicy focusTraversalPolicy;

        private JTabbedPane parentPane;

        private int tabIndex = -1;

        private boolean enabled = true;

        private boolean visible = true;

        public Tab(String tabId, JComponent panel)
        {
            Assert.notNull(panel);
            this.tabId = tabId;
            this.title = getApplicationConfig().messageResolver().getMessage(getId(), this.tabId, "title");
            this.panel = panel;
            this.panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }

        /**
         * Set parent for overlays and enabling
         *
         * @param parentPane
         */
        protected void setParent(JTabbedPane parentPane)
        {
            this.parentPane = parentPane;
            if (this.parentPane != null)
                setVisible(visible);
        }

        public void setVisible(boolean visible)
        {
            if (parentPane != null)
            {
                if (visible)
                {
                    parentPane.addTab(title, panel);
                    tabIndex = parentPane.indexOfComponent(panel);
                    parentPane.setEnabledAt(tabIndex, isEnabled());
                }
                else
                {
                    parentPane.remove(panel);
                    tabIndex = -1;
                }
            }
            this.visible = visible;
        }

        public void setEnabled(boolean enabled)
        {
            if ((parentPane != null) && (tabIndex > -1))
                parentPane.setEnabledAt(tabIndex, enabled);

            this.enabled = enabled;
        }

        /**
         * Gets the index of the tab on the tabbedpane
         *
         * @return index of the tab, -1 if not visible
         */
        public int getTabIndex()
        {
            return tabIndex;
        }

        public boolean isEnabled()
        {
            return this.enabled;
        }

        public void setMarked(boolean enable)
        {
            Icon icon = getApplicationConfig().iconSource().getIcon(tabId + ".icon");
            if ((parentPane != null) && (tabIndex > -1))
                parentPane.setIconAt(getTabIndex(), enable ? icon : null);

        }

        public void setFocusTraversalPolicy(FocusTraversalPolicy focusTraversalPolicy)
        {
            this.focusTraversalPolicy = focusTraversalPolicy;
            panel.setFocusTraversalPolicy(this.focusTraversalPolicy);
            panel.setFocusTraversalPolicyProvider(this.focusTraversalPolicy == null ? false : true);
        }
    }

    public void stateChanged(ChangeEvent e)
    {
    }
}
