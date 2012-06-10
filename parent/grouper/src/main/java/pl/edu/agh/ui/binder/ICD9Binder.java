package pl.edu.agh.ui.binder;

import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.binding.swing.editor.AbstractLookupBinder;
import org.valkyriercp.form.binding.swing.editor.AbstractLookupBinding;
import pl.edu.agh.domain.ICD9;
import pl.edu.agh.domain.ICD9Filter;

import java.awt.*;
import java.util.Map;

/**
 * User: mateusz
 * Date: 20.05.12
 */
public class ICD9Binder extends AbstractLookupBinder
{
    public ICD9Binder()
    {
        super("icd9DataEditor");
    }

    protected AbstractLookupBinding getLookupBinding(FormModel formModel, String formPropertyPath, Map context)
    {
        return new AbstractLookupBinding(getDataEditor(), formModel, formPropertyPath, ICD9.class)
        {
            public String getObjectLabel(Object o)
            {
                return ((ICD9) o).getName();
            }

            protected Object createFilterFromString(String textFieldValue)
            {
                ICD9Filter filter = new ICD9Filter();
                filter.setName(textFieldValue);
                filter.setCode(textFieldValue);
                return filter;
            }

            @Override
            public Dimension getDialogSize()
            {
                return new Dimension(800,600);
            }
        };
    }
}