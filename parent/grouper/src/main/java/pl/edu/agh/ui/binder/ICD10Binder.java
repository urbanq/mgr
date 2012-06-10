package pl.edu.agh.ui.binder;

import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.binding.swing.editor.AbstractLookupBinder;
import org.valkyriercp.form.binding.swing.editor.AbstractLookupBinding;
import pl.edu.agh.domain.ICD10;
import pl.edu.agh.domain.ICD10Filter;

import java.awt.*;
import java.util.Map;

/**
 * User: mateusz
 * Date: 20.05.12
 */
public class ICD10Binder extends AbstractLookupBinder
{
    public ICD10Binder()
    {
        super("icd10DataEditor");
    }

    protected AbstractLookupBinding getLookupBinding(FormModel formModel, String formPropertyPath, Map context)
    {
        return new AbstractLookupBinding(getDataEditor(), formModel, formPropertyPath, ICD10.class)
        {
            public String getObjectLabel(Object o)
            {
                return ((ICD10) o).getName();
            }

            protected Object createFilterFromString(String textFieldValue)
            {
                ICD10Filter filter = new ICD10Filter();
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