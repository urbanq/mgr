package pl.edu.agh.ui.binder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.binding.swing.AbstractGlazedListsBinding;
import org.valkyriercp.widget.table.PropertyColumnTableDescription;
import org.valkyriercp.widget.table.TableDescription;
import pl.edu.agh.domain.AgeLimit;
import pl.edu.agh.domain.Department;
import pl.edu.agh.domain.HospitalLimit;
import pl.edu.agh.domain.reason.*;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author mateusz
 * @date 22.06.12
 */
public class ReasonListBinding extends AbstractGlazedListsBinding {
    @Autowired
    private MessageSourceAccessor messages;

    public ReasonListBinding(FormModel formModel, String formPropertyPath) {
        super(formModel, formPropertyPath);
        setAddSupported(false);
        setEditSupported(false);
        setRemoveSupported(false);
        setShowDetailSupported(false);
    }

    @Override
    protected TableDescription getTableDescription() {
        PropertyColumnTableDescription desc = new PropertyColumnTableDescription("reasonListBinding", Reason.class);
        desc.addPropertyColumn("this").withRenderer(new ReasonTableCellRenderer());
        return desc;
    }

    private class ReasonTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            if (value instanceof AgeReason) {
                AgeReason ageReason = (AgeReason) value;
                AgeLimit ageLimit = ageReason.required();
                StringBuilder builder = new StringBuilder();
                if (ageLimit.getUnder() != null) {
                    builder.append(messages.getMessage("reason.age.under", new Object[]{ageLimit.getUnder()}));
                }
                if (ageLimit.getOver() != null) {
                    if (ageLimit.getUnder() != null) {
                        builder.append(", ");
                    }
                    builder.append(messages.getMessage("reason.age.over", new Object[]{ageLimit.getOver()}));
                }
                builder.append(" (");
                builder.append(messages.getMessage("pl.edu.agh.domain.TimeUnit." + ageLimit.getTimeUnit().name()));
                builder.append(")");
                setText(builder.toString());
            } else if(value instanceof DepartmentsReason) {
                DepartmentsReason departmentsReason = (DepartmentsReason) value;
                StringBuilder builder = new StringBuilder(messages.getMessage("reason.department"));
                builder.append(" ");
                for (Department department : departmentsReason.required()) {
                    builder.append(department.getName());
                    builder.append(", ");
                }
                builder.delete(builder.length()-3, builder.length()-1);
                setText(builder.toString());
            } else if(value instanceof HospitalReason) {
                HospitalReason hospitalReason = (HospitalReason) value;
                StringBuilder builder = new StringBuilder();
                HospitalLimit hospLimit = hospitalReason.required();
                if (hospLimit.getUnder() != null) {
                    builder.append(messages.getMessage("reason.hospital.under", new Object[]{hospLimit.getUnder()}));
                }
                if (hospLimit.getOver() != null) {
                    if (hospLimit.getUnder() != null) {
                        builder.append(", ");
                    }
                    builder.append(messages.getMessage("reason.hospital.over", new Object[]{hospLimit.getOver()}));
                }
                builder.append(" (");
                builder.append(messages.getMessage("pl.edu.agh.domain.TimeUnit." + hospLimit.getTimeUnit().name()));
                builder.append(")");
                setText(builder.toString());
            } else if(value instanceof ICDReason) {
                ICDReason icdReason = (ICDReason) value;
                setText(messages.getMessage("reason.icd." + icdReason.icdCondition().name(),
                                            new Object[]{icdReason.required()}));
            } else if(value instanceof ICDSizeReason) {
                ICDSizeReason icdSizeReason = (ICDSizeReason) value;
                setText(messages.getMessage("reason.icdSize." + icdSizeReason.listType().name(),
                                            new Object[]{icdSizeReason.required()}));
            } else if(value instanceof IncomeModeReason) {
                IncomeModeReason incomeModeReason = (IncomeModeReason) value;
                setText(messages.getMessage("reason.incomeMode",
                                            new Object[]{incomeModeReason.required().getName()}));
            } else if(value instanceof NegativeICDReason) {
                NegativeICDReason negativeICDReason = (NegativeICDReason) value;
                setText(messages.getMessage("reason.negative.icd." + negativeICDReason.listType().name(),
                                            new Object[]{negativeICDReason.required()}));
            } else if(value instanceof OutcomeModeReason) {
                OutcomeModeReason outcomeModeReason = (OutcomeModeReason) value;
                setText(messages.getMessage("reason.outcomeMode",
                                            new Object[]{outcomeModeReason.required().getName()}));
            } else if(value instanceof RangeReason) {
                RangeReason rangeReason = (RangeReason) value;
                setText(messages.getMessage("reason.range." + rangeReason.required().name()));
            } else if(value instanceof SexReason) {
                SexReason sexReason = (SexReason) value;
                setText(messages.getMessage("reason.sex." + sexReason.required().name()));
            } else {
                setText((value == null) ? "" : value.toString());
            }
        }
    }
}
