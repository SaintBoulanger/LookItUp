package com.brigitte.lookitup.jsf;

import com.brigitte.lookitup.jpa.controllers.AnalysisRecordJpaController;
import com.brigitte.lookitup.jpa.entities.AnalysisRecord;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author mbohm
 */
public class AnalysisRecordConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Integer id = new Integer(string);
        AnalysisRecordJpaController controller = (AnalysisRecordJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "analysisRecordJpa");
        return controller.findAnalysisRecord(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof AnalysisRecord) {
            AnalysisRecord o = (AnalysisRecord) object;
            return o.getAnalysisRecordId() == null ? "" : o.getAnalysisRecordId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: jpa.entities.AnalysisRecord");
        }
    }

}
